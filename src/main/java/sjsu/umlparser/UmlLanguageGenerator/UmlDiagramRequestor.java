package sjsu.umlparser.UmlLanguageGenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class UmlDiagramRequestor extends UmllanguageGenerator {

	private static String imageUrl = new String("http://yuml.me/diagram/class/%2F%2F Cool Class Diagram,");
	private static String unencodedUrl = null;
	
	public static void requestUMLdiagram() {

		// imageUrl = "http://yuml.me/diagram/scruffy/class/%2F%2F Cool Class
		// Diagram,
		// [Customer|-forname:string;surname:string|doShiz()]<>-orders*>[Order],
		// [Order]++-0..*>[LineItem], [Order]-[note:Aggregate root{bg:wheat}]";
		makeImageUrl();
		displayImageUrl();

		String destinationFile = UmlLanguageGenerationConstants.IMG_PATH;

		try {
			saveImage(destinationFile);
			popUpImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void makeImageUrl() {

		
		for (String yUML : yUMLLanguage) {
			if(yUML.contains("uses"))
			{
			yUML.replace(" ","");	
			}
			 unencodedUrl+= "," + yUML;
			 System.out.println(yUML);
			 
		}
	}

	public static void saveImage(String destinationFile) throws IOException {
		
		String umlInput = URLEncoder.encode(unencodedUrl, "UTF-8");
		URL url = new URL(imageUrl+umlInput);
		System.out.println("URL " + url);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	public static void displayImageUrl() {
		System.out.println("The yUML URL is " + imageUrl);
	}

	public static void popUpImage() {
		try {
			BufferedImage img = ImageIO.read(new File(UmlLanguageGenerationConstants.IMG_PATH));
			ImageIcon icon = new ImageIcon(img);
			JLabel label = new JLabel(icon);
			JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JFrame f = new JFrame("UML diagram generated");
			f.getContentPane().add(scroller);
			f.setSize(1000, 3000);
			f.setVisible(true);

			// JOptionPane.showMessageDialog(null,);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
