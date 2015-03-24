package core;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import entity.DNDEntity;

public class xmlLoader implements Runnable{
	private Thread xmlLoadThread;
	private String threadName;

	xmlLoader(String name){
		threadName = name;
		System.out.println("Creating " +  threadName );
	}

	public void loadFiles() throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Load the input XML document, parse it and return an instance of the
		// Document class.
		ArrayList<File> xmls = new ArrayList<File>();
		Files.walk(Paths.get(".\\XML")).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				xmls.add(filePath.toFile());
				//System.out.println(filePath);	
			}
		});
		xmls.forEach(File -> System.out.println(File.getName()));
		Document document = builder.parse(xmls.get(0));

		//List<Employee> employees = new ArrayList<Employee>();
		Hashtable<String, DNDEntity> spells = new Hashtable<String, DNDEntity>();
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < 2;/*nodeList.getLength();*/ i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				//System.out.println(node.getAttributes());
				//System.out.println(elem.getTagName());
				String name = elem.getElementsByTagName("NAME").item(0).getChildNodes().item(0).getNodeValue();
				name = name.trim();
				System.out.println(name);
			}
		}
	}

	@Override
	public void run() {
		try {
			loadFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Ending " + threadName );
	}

	public void start()
	{
		System.out.println("Starting " + threadName );
		if (xmlLoadThread == null)
		{
			xmlLoadThread = new Thread (this, threadName);
			xmlLoadThread.start ();
		}
	}
}
