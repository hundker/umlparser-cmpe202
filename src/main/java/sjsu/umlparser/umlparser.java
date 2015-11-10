package sjsu.umlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import junit.framework.Assert;
import sjsu.umlparser.InMemoryDataStructures.ClassList;
import sjsu.umlparser.UmlLanguageGenerator.UmlLanguageGenerationConstants;
import sjsu.umlparser.UmlLanguageGenerator.UmllanguageGenerator;

public class umlparser {
	int a;

	public static void main(String[] args) throws Exception {

		CompilationUnit cu;
		BodyDeclaration bd;
		FileInputStream in = null;
		
		//File folder = new File(args[0]);
		//UmlLanguageGenerationConstants.IMG_PATH=args[1];
		File folder = new File("/home/vinit/javasrc");
		UmlLanguageGenerationConstants.IMG_PATH="image.png";
	/*	
		File folder1 = new File("/home/vinit/PaulTestCases/uml-parser-test-1");
		File folder2 = new File("/home/vinit/PaulTestCases/uml-parser-test-2");
		File folder3 = new File("/home/vinit/PaulTestCases/uml-parser-test-3");
		File folder4 = new File("/home/vinit/PaulTestCases/uml-parser-test-4");
		File folder5 = new File("/home/vinit/PaulTestCases/uml-parser-test-5");
		File folder_extra = new File("/home/vinit/workspace/umlparser/testcases/Test_x3");
*/
		
		File[] listOfFiles = folder.listFiles();
		Arrays.sort(listOfFiles);
		for (File file : listOfFiles) {
			if (file.isFile() && (file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("java"))) {

				try {
					in = new FileInputStream(file.getAbsolutePath());

					cu = JavaParser.parse(in);

					System.out.println("\n\n Compilation Unit \n" + cu.toString());
				} catch (FileNotFoundException e) {

					e.printStackTrace();
					throw new Exception(e);

				} finally {
					in.close();
				}
				visitClasses(cu);

			}

		}
		UmllanguageGenerator.generateUML();
		ClassList.display_classlist();

	}

	private static void visitClasses(CompilationUnit cu) {
		new ClassVisitor().visit(cu, null);

	}

	private static class ClassVisitor extends VoidVisitorAdapter {

		public void visit(ClassOrInterfaceDeclaration n, Object arg) {

			ClassList.add_into_classlist(n.getName(), n.isInterface());
			// System.out.println(n.getName());
			// System.out.println(n.getMembers());
			List<BodyDeclaration> bd = n.getMembers();

			ClassList.add_implemented_interface_to_latest_class(n.getImplements());
			ClassList.add_extended_to_latest_class(n.getExtends());
			ClassList.add_modifiers_to_latest_class(n.getModifiers());

			for (BodyDeclaration ele : bd) {
				{

					if (ele instanceof FieldDeclaration) {

						FieldDeclaration fe = (FieldDeclaration) ele;

						ClassList.add_variables_to_latest_class(fe.getModifiers(), fe.getType(), fe.getVariables(),
								null);
					} else if (ele instanceof MethodDeclaration) {

						MethodDeclaration me = (MethodDeclaration) ele;

						try {

							ClassList.add_methods_to_latest_class(me.getModifiers(), me.getType().toString(),
									me.getParameters(), me.getName(), me.getBody());
						} catch (NullPointerException e) {
							e.printStackTrace();
						}
					} else if (ele instanceof ConstructorDeclaration) {

						ConstructorDeclaration cd = (ConstructorDeclaration) ele;

						try {
							ClassList.add_methods_to_latest_class(cd.getModifiers(), null, cd.getParameters(),
									cd.getName(), cd.getBlock());
						} catch (NullPointerException e) {
							e.printStackTrace();
						}

					}
				}
			}

		}
	}

}
