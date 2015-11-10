package sjsu.umlparser.InMemoryDataStructures;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;

public class ClassList {
	private static List<Parsed_Classes> classlist = new ArrayList<Parsed_Classes>();

	public static void add_into_classlist(String class_name, Boolean isInterface) {
		Parsed_Classes e = new Parsed_Classes();
		e.add_class_name(class_name);
		e.setInterface(isInterface);
		classlist.add(e);
	}

	public static void display_classlist() {
		for (Parsed_Classes element : classlist) {
			element.display_class();
		}

	}

	public static List<Parsed_Classes> get_classlist() {
		return classlist;
	}

	public static List<Parsed_Classes> get_interface_list() {
		
		List<Parsed_Classes> interfacelist = new ArrayList<Parsed_Classes>();
		for(Parsed_Classes element : classlist){
			if(element.checkIfInterface())
			{
				interfacelist.add(element);
			}
				
		}
		return interfacelist;
	}

	public static int get_latest_index() {
		return classlist.size() - 1;

	}

	public static void add_modifiers_to_latest_class(int modifier) {
		classlist.get(classlist.size() - 1).add_modifier(modifier);
	}

	public static void add_associations_to_class(String association_class, Parsed_Classes classname) {
		classname.add_associations(association_class);
	}

	public static void add_implemented_interface_to_latest_class(List<ClassOrInterfaceType> list) {
		if (list != null)
			for (ClassOrInterfaceType element : list) {
				classlist.get(classlist.size() - 1).add_implemented_interface(element.getName());
			}
	}

	public static void add_extended_to_latest_class(List<ClassOrInterfaceType> list) {

		if (list != null)
			for (ClassOrInterfaceType element : list) {
				classlist.get(classlist.size() - 1).add_extended_class(element.getName());
			}
	}

	public static void add_methods_to_latest_class(int modifier, String return_type, List<Parameter> arguments,
			String method_name, BlockStmt blockStmt) {
		List<String> argument_types = null;
		List<String> argument_literals = null;
		if (arguments.size() != 0) {
			argument_types = new ArrayList();
			argument_literals = new ArrayList<String>();
			for (Parameter param : arguments) {

				argument_types.add(param.getType().toString());
				argument_literals.add(param.getId().getName());
				
			}

		}
		if(blockStmt!=null)
		classlist.get(classlist.size() - 1).add_method(modifier, return_type, argument_types,argument_literals, method_name, blockStmt.toString());
		else
			classlist.get(classlist.size() - 1).add_method(modifier, return_type, argument_types,argument_literals, method_name, null);
	}

	public static void add_variables_to_latest_class(int modifier, Type type, List<VariableDeclarator> variable,
			String multiplicity) {
		String variable_literal = variable.toString();
		String datatype = type.toString();

		List<String> variables = null;
		if (variable.size() != 0) {
			variables = new ArrayList<String>();

			for (VariableDeclarator var : variable) {
				variables.add(var.toString());
			}
		}

		classlist.get(classlist.size() - 1).add_variables(modifier, datatype, variables, multiplicity);
	}

}
