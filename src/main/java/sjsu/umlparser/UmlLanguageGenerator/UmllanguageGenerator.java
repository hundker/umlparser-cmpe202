package sjsu.umlparser.UmlLanguageGenerator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import sjsu.umlparser.umlParserConstants;
import sjsu.umlparser.InMemoryDataStructures.ClassList;
import sjsu.umlparser.InMemoryDataStructures.Parsed_Classes;
import sjsu.umlparser.InMemoryDataStructures.Parsed_Methods;
import sjsu.umlparser.InMemoryDataStructures.Parsed_Variables;

public class UmllanguageGenerator {

	public static HashMap<String, ArrayList<String>> association_multiplicity = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, String> class_string_map = new HashMap<String, String>();
	public static List<String> yUMLLanguage = new ArrayList<String>();
	public static HashMap<Pair<String, String>, Boolean> strongerRelationship = new HashMap<Pair<String, String>, Boolean>();

	public static void generateUML() {
		init();
		removeGetterSetter();
		findAssociationAndMultiplicity();

		makeClassString();
		generateyUMLLanguage();

		displayInterfacesAndImplementation();
		displayExtends();
		displayInterfaceClients();
		appendClassStrings();
		displayGeneratedyUMLlanguage();

		// String value = "Vinit";

		// strongerRelationship.put(Pair.of("B", "A"), "Gaikwad");
		// System.out.println(strongerRelationship);
		// System.out.println("A-B"+strongerRelationship.get(Pair.of("A",
		// "B")));
		UmlDiagramRequestor.requestUMLdiagram();
	}

	private static void appendClassStrings() {
		for (Entry<String, String> entry : class_string_map.entrySet()) {
			yUMLLanguage.add(entry.getValue());
		}
	}

	private static void removeGetterSetter() {
		for (Parsed_Classes a_class : ClassList.get_classlist()) {
			for (Parsed_Variables pv : a_class.get_parsed_variables()) {
				for (String a_name : pv.getVariable_names()) {
					if (pv.getModifier() != null && pv.getModifier().equals(umlParserConstants.PRIVATE)) {
						for (Parsed_Methods pm : a_class.get_parsed_methods()) {
							if (pm.getMethod_name().startsWith("get") && org.apache.commons.lang3.StringUtils
									.containsIgnoreCase(pm.getMethod_name(), a_name)) {
								pm.setIfGetterSetter(Boolean.TRUE);
								pv.setHasGetter(Boolean.TRUE);
							} else if (pm.getMethod_name().startsWith("set") && org.apache.commons.lang3.StringUtils
									.containsIgnoreCase(pm.getMethod_name(), a_name)) {
								pm.setIfGetterSetter(Boolean.TRUE);
								pv.setHasSetter(Boolean.TRUE);
							}
						}
					}
				}

				if (pv.getHasGetter().equals(Boolean.TRUE) && (pv.getHasSetter().equals(Boolean.TRUE))) {
					pv.setModifier(umlParserConstants.PUBLIC);
					// System.out.println("pv is "+pv);

				}

			}
		}

	}

	private static void setStrongerRelation(Parsed_Classes a_class, Parsed_Classes b_class) {
		strongerRelationship.put(Pair.of(a_class.get_class_name(), b_class.get_class_name()), true);
		strongerRelationship.put(Pair.of(b_class.get_class_name(), a_class.get_class_name()), true);
		/*
		 * for(Parsed_Classes a_interface : a_class.get_implemented_interface())
		 * { strongerRelationship.put(Pair.of(b_class.get_class_name(),
		 * a_interface.get_class_name()),true);
		 * strongerRelationship.put(Pair.of(a_interface.get_class_name(),
		 * b_class.get_class_name()),true);
		 * 
		 * }
		 */
	}

	private static void displayInterfaceClients() {

		for (Parsed_Classes a_class : ClassList.get_classlist()) {
			outerloop: for (Parsed_Classes a_interface : ClassList.get_interface_list()) {

				for (Parsed_Methods a_Method : a_class.get_parsed_methods()) {

					if (a_Method.getArgument_types() != null) {
						for (String argument_type : a_Method.getArgument_types()) {
							if (argument_type.equals(a_interface.get_class_name())) {
								UmlLanguageGenerationConstants.makeUMLLanguage(
										class_string_map.get(a_class.get_class_name()),
										UmlLanguageGenerationConstants.DEPENDS, null,
										class_string_map.get(a_interface.get_class_name()));
								break outerloop;
							}
						}
					}

					if (a_Method.getMethod_body() != null) {
						if (Arrays.asList(a_Method.getMethod_body().split(" "))
								.contains(a_interface.get_class_name())) {
							UmlLanguageGenerationConstants.makeUMLLanguage(
									class_string_map.get(a_class.get_class_name()),
									UmlLanguageGenerationConstants.DEPENDS, null,
									class_string_map.get(a_interface.get_class_name()));
							break outerloop;
						}
					}

				}

				for (Parsed_Variables pv : a_class.get_parsed_variables()) {
					if (pv.getType().equals(a_interface.get_class_name())) {
						UmlLanguageGenerationConstants.makeUMLLanguage(class_string_map.get(a_class.get_class_name()),
								UmlLanguageGenerationConstants.DEPENDS, null,
								class_string_map.get(a_interface.get_class_name()));
						break outerloop;
					}

				}

			}

		}

	}

	private static void displayExtends() {
		for (Parsed_Classes a_class : ClassList.get_classlist()) {

			if (!a_class.get_extended_class().isEmpty())
				for (String a_extended_class : a_class.get_extended_class()) {
					UmlLanguageGenerationConstants.makeUMLLanguage(class_string_map.get(a_class.get_class_name()),
							UmlLanguageGenerationConstants.EXTENDS, null, class_string_map.get(a_extended_class));

				}
		}

	}

	private static void displayInterfacesAndImplementation() {

		String yUMLline = null;
		for (Parsed_Classes a_class : ClassList.get_classlist()) {

			for (String a_implemented_interface : a_class.get_implemented_interface()) {
				// System.out.println("The interface is found which is " +
				// a_class.get_class_name());
				for (Parsed_Classes a_interface : ClassList.get_interface_list()) {
					if (a_implemented_interface.contains(a_interface.get_class_name())) {
						UmlLanguageGenerationConstants.makeUMLLanguage(class_string_map.get(a_class.get_class_name()),
								UmlLanguageGenerationConstants.IMPLEMENTS, null,
								class_string_map.get(a_interface.get_class_name()));
					}

				}
			}
		}
	}

	private static void init() {
		for (Parsed_Classes a_class : ClassList.get_classlist()) {
			for (Parsed_Classes b_class : ClassList.get_classlist()) {
				strongerRelationship.put(Pair.of(a_class.get_class_name(), b_class.get_class_name()), false);
				strongerRelationship.put(Pair.of(b_class.get_class_name(), a_class.get_class_name()), false);
			}
		}
	}

	private static void displayGeneratedyUMLlanguage() {
		System.out.println("------------------------------------------------------------");
		System.out.println("Generated yUML language is as follows \n");
		for (String value : yUMLLanguage) {
			// System.out.println(value);

			byte[] array = null;
			try {
				array = value.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String s = new String(array, Charset.forName("UTF-8"));
			System.out.println(s); // Prints as expected
			// printBox();
		}
		System.out.println("------------------------------------------------------------");
	}

	public static void printBox() {
		for (int i = 0x2500; i <= 0x257F; i++) {
			System.out.printf("0x%x : %c\n", i, (char) i);
		}
	}

	private static void generateyUMLLanguage() {
		String yUMLline = null;
		for (Entry<String, ArrayList<String>> map : association_multiplicity.entrySet()) {
			System.out.println(class_string_map.get(map.getKey()));

			for (String value : map.getValue()) {
				// yUMLline +=
				// getClassNamefromAssociation(value)
				// System.out.println(value);
				// System.out.println(getClassNamefromAssociation(value));
				// yUMLline = class_string_map.get(map.getKey()) + "->" +
				// getMultiplicityfromAssociation(value)
				// + class_string_map.get(getClassNamefromAssociation(value));

				// yUMLLanguage.add(yUMLline);

				UmlLanguageGenerationConstants.makeUMLLanguage(class_string_map.get(map.getKey()),
						UmlLanguageGenerationConstants.ASSOCIATION, getMultiplicityfromAssociation(value),
						class_string_map.get(getClassNamefromAssociation(value)));
			}

			// System.out.println(yUMLline+"\n");
			yUMLline = null;
		}

	}

	// ＜＜interface＞＞

	private static void makeClassString() {
		String class_string = new String();

		for (Parsed_Classes a_class : ClassList.get_classlist()) {
			class_string += "[";

			if (a_class.checkIfInterface()) {
				class_string += "＜＜interface＞＞;" + a_class.get_class_name();
			} else {
				class_string += a_class.get_class_name();
			}

			if (!a_class.get_parsed_variables().isEmpty()) {
				class_string += "|";
				for (Parsed_Variables a_Variable : a_class.get_parsed_variables()) {

					if (a_Variable.getIndicateAssociativity()) {
						continue;
					}
					if (a_Variable.getModifier() != null
							&& a_Variable.getModifier().equals(umlParserConstants.PRIVATE)) {
						class_string += "-";
					} else if (a_Variable.getModifier() != null
							&& a_Variable.getModifier().equals(umlParserConstants.PUBLIC)) {
						class_string += "+";
					} else {
						continue;
					}

					class_string += a_Variable.getVariable_names().toString().replace("[", "").replace("]", "")
							.replace(",", "，");
					class_string += ":" + a_Variable.getType().replace("<", "＜").replace(">", "＞").replace("[", "［")
							.replace("]", "］");
					class_string += ";";

				}

			}
			if (!a_class.get_parsed_methods().isEmpty()) {
				if (a_class.get_parsed_variables().isEmpty()) {
					class_string += "|";
				}

				class_string += "|";
				for (Parsed_Methods a_Method : a_class.get_parsed_methods()) {
					if (a_Method.getModifier() != null && a_Method.getModifier().equals(umlParserConstants.PRIVATE)) {
						// class_string += "-";
						continue;
					} else if (a_Method.getIfGetterSetter()) {
						continue;
					} else if (a_Method.getModifier() != null
							&& a_Method.getModifier().equals(umlParserConstants.PUBLIC)) {
						class_string += "+";
					}
					if (a_Method.getArgument_types() != null) {
						class_string += a_Method.getMethod_name().toString() + "("
								+ a_Method.getArgument_literals().toString().replace(",", "，").replace("<", "＜")
										.replace(">", "＞").replace("[", "［").replace("]", "］")
										.substring(1, a_Method.getArgument_literals().toString().length() - 1)
								+ ":"
								+ a_Method.getArgument_types().toString().replace(",", "，").replace("<", "＜")
										.replace(">", "＞").replace("[", "［").replace("]", "］")
										.substring(1, a_Method.getArgument_types().toString().length() - 1)
								+ ")";
					} else {
						class_string += a_Method.getMethod_name().toString() + "()";
					}

					if (a_Method.getReturn_type() != null) {
						class_string += ":" + a_Method.getReturn_type();
					}

					class_string += ";";

				}

			}
			class_string += "]";
			class_string_map.put(a_class.get_class_name(), class_string);

			class_string = "";
		}
		System.out.println("classstringmap is " + class_string_map);
	}

	private static void findAssociationAndMultiplicity() {
		System.out.println("--------------------------------Finding Association ----------------------------------");

		// Variables
		for (Parsed_Classes a_class : ClassList.get_classlist()) {
			System.out.println("\nClass considering =" + a_class.get_class_name());
			for (Parsed_Variables a_Variables : a_class.get_parsed_variables()) {
				System.out.println("\nVariables considering =" + a_Variables.getVariable_names() + " of Type = "
						+ a_Variables.getType());
				for (Parsed_Classes index : ClassList.get_classlist()) {
					System.out.println("Class compared = " + index.get_class_name());
					if (stripVariableType(a_Variables.getType()).equals(index.get_class_name())) {
						// if(a_Variables.ty)
						System.out.println("Class found = " + index.get_class_name());
						// if (!index.checkIfInterface()) {
						a_Variables.setIndicateAssociativity(true);
						ClassList.add_associations_to_class(index.get_class_name(), a_class);

						addToAssociationMultiplicity(a_class.get_class_name(), index.get_class_name(),
								recognizeMultiplicity(a_Variables));
						// }
					}

				}

			}

		}

		System.out.println("Found Association and Multiplicity =" + association_multiplicity);

		System.out.println("--------------------------------Finding Association ----------------------------------");
	}

	private static String recognizeMultiplicity(Parsed_Variables a_Variables) {

		System.out.println("dsd -" + a_Variables.getType());
		if (a_Variables.getType().contains("Collection")) {
			return "*";
		} else if (a_Variables.getVariable_names().size() > 1) {
			return String.valueOf(a_Variables.getVariable_names().size());
		} else
			return "1";

	}

	private static String addToAssociationMultiplicity(String get_class_name, String associated_class,
			String multiplicity) {

		if (association_multiplicity.containsKey(get_class_name)) {
			if (!isTheClassRelationshipAlreadyDefined(get_class_name, associated_class))
				association_multiplicity.get(get_class_name).add(compositeString(associated_class, multiplicity));
		} else if (association_multiplicity.containsKey(associated_class)) {
			if (!isTheClassRelationshipAlreadyDefined(associated_class, get_class_name))
				association_multiplicity.get(associated_class).add(compositeString(get_class_name, multiplicity));
		} else {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(compositeString(associated_class, multiplicity));
			association_multiplicity.put(get_class_name, temp);
		}

		return get_class_name + "_" + multiplicity;
	}

	private static boolean isTheClassRelationshipAlreadyDefined(String Keyclass, String associated_class) {
		return getAssociatedClasslist(association_multiplicity.get(Keyclass), Keyclass).contains(associated_class);

	}

	private static ArrayList<String> getAssociatedClasslist(ArrayList<String> classList, String associated_class) {
		// TODO Auto-generated method stub
		ArrayList<String> returnList = new ArrayList<String>();
		for (String element : classList) {
			returnList.add(element.split("_")[0]);
		}
		System.out.println("Associated Class =  " + associated_class + " List=" + returnList);
		return returnList;
	}

	private static String compositeString(String get_class_name, String multiplicity) {

		return get_class_name + "_" + multiplicity;
	}

	private static String stripVariableType(String type) {
		if (type.contains("Collection")) {
			type = type.replace("Collection", "").replace(">", "").replace("<", "");
		}
		return type;
	}

	private static String getClassNamefromAssociation(String association) {
		return association.split("_")[0];

	}

	private static String getMultiplicityfromAssociation(String association) {
		return association.split("_")[1];
	}
}
