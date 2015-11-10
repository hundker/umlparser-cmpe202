package sjsu.umlparser.UmlLanguageGenerator;

public class UmlLanguageGenerationConstants extends UmllanguageGenerator {

	public static final String ASSOCIATION = "-";

	public static final String EXTENDS = "-^";

	public static final String IMPLEMENTS = "-.-^";

	public static final String DEPENDS = "uses-.->";
	
	public static String IMG_PATH;
	
	public static void makeUMLLanguage(String participantOne, final String relation, String annotation,
			String participantTwo) {
		String yUMLline = null;

		if(annotation!=null)
		yUMLline = participantOne + relation + annotation + participantTwo;
		else
		yUMLline = participantOne + relation + participantTwo;	
			
		yUMLLanguage.add(yUMLline);

	}

}
