package sjsu.umlparser.InMemoryDataStructures;

import java.util.ArrayList;
import java.util.List;

public class Parsed_Variables {

	
	

	public Boolean getIndicateAssociativity() {
		return indicateAssociativity;
	}

	public void setIndicateAssociativity(Boolean indicateAssociativity) {
		this.indicateAssociativity = indicateAssociativity;
	}

	public Boolean getHasGetter() {
		return hasGetter;
	}

	public void setHasGetter(Boolean hasGetter) {
		this.hasGetter = hasGetter;
	}

	public Boolean getHasSetter() {
		return hasSetter;
	}

	public void setHasSetter(Boolean hasSetter) {
		this.hasSetter = hasSetter;
	}

	private String modifier;
	private String type;
	private List<String> variable_names;
	private String multiplicity;
	private Boolean hasGetter;
	private Boolean hasSetter;
	private Boolean indicateAssociativity;
	
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getVariable_names() {
		return variable_names;
	}

	public void setVariable_names(List<String> variable_names) {
		this.variable_names = variable_names;
	}

	public String getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}



	Parsed_Variables() {
		type = new String();
		variable_names = new ArrayList<String>();
		multiplicity = new String();
		hasGetter = new Boolean(Boolean.FALSE);
		hasSetter = new Boolean(Boolean.FALSE);
		indicateAssociativity = new Boolean(false);
	}

	public void display_Parsed_Variables() {
		System.out.println(
				"-------------------------------------VARIABLE--------------------------------------------------------");
		System.out.println("\nMODIFIER = " + String.format("%30s", modifier) + "\nVARIABLE TYPE = "
				+ String.format("%30s", type) + "\nVARIABLE_NAME = " + String.format("%30s", variable_names)
				+ "\nMULTIPLICITY = " + String.format("%30s", multiplicity));
		System.out.println(
				"--------------------------------------END VARIABLE---------------------------------------------------------");
	}
}
