package sjsu.umlparser.InMemoryDataStructures;

import java.util.ArrayList;
import java.util.List;

public class Parsed_Methods {

	public List<String> getArgument_literals() {
		return argument_literals;
	}

	public void setArgument_literals(List<String> argumet_literals) {
		this.argument_literals = argumet_literals;
	}

	private String modifier;
	private String return_type;
	private List<String> argument_types;
	private List<String> argument_literals;
	private String method_name;
	private String method_body;
	private Boolean ifGetterSetter; 

	public Boolean getIfGetterSetter() {
		return ifGetterSetter;
	}

	public void setIfGetterSetter(Boolean ifGetterSetter) {
		this.ifGetterSetter = ifGetterSetter;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getReturn_type() {
		return return_type;
	}

	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}

	public List<String> getArgument_types() {
		return argument_types;
	}

	public void setArgument_types(List<String> argument_types) {
		this.argument_types = argument_types;
	}

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public String getMethod_body() {
		return method_body;
	}

	public void setMethod_body(String method_body) {
		this.method_body = method_body;
	}


	Parsed_Methods() {
		argument_types = new ArrayList<String>();
		argument_literals = new ArrayList<String>();
		return_type = new String();
		method_name = new String();
		ifGetterSetter = Boolean.FALSE;
	}

	public void display_Parsed_Methods() {
		System.out.println(
				"-----------------------------------------------METHOD--------------------------------------------------");
		System.out.println("\nMODIFIER = " + String.format("%20s", modifier) + "\nRETURN TYPE =  "
				+ String.format("%20s", return_type) + "\nARGUMENT TYPES = " + String.format("%20s", argument_types)
				+ "\nARGUMENTS LITERALS"+ argument_literals+ "\nMETHOD_NAME = " + method_name + "\nMETHOD_BODY = " + method_body + "\nGETTERSETTERFLAG ="+ ifGetterSetter.toString());
		System.out.println(
				"-----------------------------------------------END METHOD-------------------------------------------------");
	}

}
