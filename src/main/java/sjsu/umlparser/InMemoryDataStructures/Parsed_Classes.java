package sjsu.umlparser.InMemoryDataStructures;

import java.util.ArrayList;
import java.util.List;

import sjsu.umlparser.umlParserConstants;

public class Parsed_Classes {

	private String class_name;
	private Boolean isInterface;
	private List<Parsed_Methods> methods;
	private List<Parsed_Variables> variables;
	private List<String> dependecies;
	private List<String> associations; // 1_*_A -->This notation signals one to
										// many relation with Class A
	private List<String> implemented_interface;
	private List<String> extended_class;
	private String modifier;

	Parsed_Classes() {
		methods = new ArrayList<Parsed_Methods>();
		variables = new ArrayList<Parsed_Variables>();
		dependecies = new ArrayList<String>();
		associations = new ArrayList<String>();
		implemented_interface = new ArrayList<String>();
		extended_class = new ArrayList<String>();
	}

	public void add_modifier(int modifier) {
		if (java.lang.reflect.Modifier.PUBLIC == modifier) {
			this.modifier = umlParserConstants.PUBLIC;
		} else if (java.lang.reflect.Modifier.PRIVATE == modifier) {
			this.modifier = umlParserConstants.PRIVATE;
		}
	}

	
	public void add_class_name(String classname) {
		this.class_name = classname;
	}

	public void setInterface(Boolean isInterface) {
		this.isInterface = isInterface;
	}

	public Boolean checkIfInterface() {
		return this.isInterface;
	}

	public String get_modifier() {
		return modifier;
	}

	public String get_class_name() {
		return class_name;
	}

	public List<Parsed_Methods> get_parsed_methods() {
		return methods;

	}

	public List<Parsed_Variables> get_parsed_variables() {
		return variables;

	}

	public List<String> get_dependecies() {
		return dependecies;
	}

	public List<String> get_association() {
		return dependecies;
	}

	public List<String> get_implemented_interface() {
		return implemented_interface;
	}
	
	public List<String> get_extended_class() {
		return extended_class;
	}

	public void add_dependecies(String dependent_class) {
		dependecies.add(dependent_class);
	}

	public void add_associations(String association_class) {
		associations.add(association_class);
	}

	public void add_implemented_interface(String implemented_interface) {
		this.implemented_interface.add(implemented_interface);
	}

	public void add_extended_class(String extended_class) {
		this.extended_class.add(extended_class);
	}

	public void add_method(int modifier, String return_type, List<String> argument_types, List<String> argument_literals, String method_name,
			String method_body) {
		Parsed_Methods mtd = new Parsed_Methods();
		mtd.setReturn_type(return_type);
		mtd.setArgument_types(argument_types);
		mtd.setArgument_literals(argument_literals);
		mtd.setMethod_name(method_name);
		mtd.setMethod_body(method_body);
		if (java.lang.reflect.Modifier.PUBLIC == modifier) {
			mtd.setModifier(umlParserConstants.PUBLIC);
		} else if (java.lang.reflect.Modifier.PRIVATE == modifier) {
			mtd.setModifier(umlParserConstants.PRIVATE);
		}
		methods.add(mtd);
	}

	public void add_variables(int modifier, String datatype, List<String> variable_names, String multiplicity) {
		Parsed_Variables var = new Parsed_Variables();
		var.setType(datatype);
		var.setVariable_names(variable_names);
		var.setMultiplicity(multiplicity);
		if (java.lang.reflect.Modifier.PUBLIC == modifier) {
			var.setModifier(umlParserConstants.PUBLIC);
		} else if (java.lang.reflect.Modifier.PRIVATE == modifier) {
			var.setModifier(umlParserConstants.PRIVATE);
		}
		variables.add(var);

	}
	
	//public void removingGetterSetter

	public void display_class() {
		System.out.println(
				"////////////////////////////////////////////////////////////////////////////////////////////////////////");
	
		System.out.println("\n\n-------------\n CLASS NAME = " + class_name);
		
		if (methods != null) {
			for (Parsed_Methods element : methods) {
				element.display_Parsed_Methods();
			}
		}
		if (variables != null)
			for (Parsed_Variables element : variables) {
				element.display_Parsed_Variables();
			}
		if (implemented_interface != null) {
			System.out.println("\nIMPLEMENTS = " + implemented_interface);
		}

		if (associations != null) {
			System.out.println("\nASSOCIATION = " + associations);
		}
		if (modifier != null) {
			System.out.println("\n MODIFIER = " + modifier);
		}
		System.out.println(
				"////////////////////////////////////////////////////////////////////////////////////////////////////////");
	
	}
	
	

}
