package trainCRF;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.w3c.dom.*;

import types.*;

/**
 * A CollectionReader for the IEXML files from calbc
 * reading the gold standard
 */
public class CalbcReader extends CollectionReader_ImplBase {

	private Document doc;
	private NodeList sentences;
	private int currentNode = 0;
	private int length;

	@Override
	public void initialize() {
		try { // build the DOM document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(getUimaContext().getResourceAsStream("TrainCorpus"));
			doc.normalize();
			sentences = doc.getElementsByTagName("s"); // get all sentences from the corpus
			length = sentences.getLength();
		} catch (Exception e) {
			throw new UIMARuntimeException(e);
		}
	}

	@Override
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		// get the sentence
		Element sent = (Element)sentences.item(currentNode);
		currentNode++;
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new UIMARuntimeException(e);
		}
		jcas.setDocumentText(sent.getTextContent().trim());
		// iterate all children of the sentence
		NodeList mentions = sent.getChildNodes();
		String currentText = "";
		int begin = 0;
		int end = 0;
		int curr = 0;
		for(int i = 0; i < mentions.getLength(); ++i)
		{
			Node nd = mentions.item(i);
			// text not mentioned
			if (nd.getNodeType() == Node.TEXT_NODE)
			{
				currentText += nd.getTextContent();
				curr = currentText.replaceAll("\\s", "").length();
			}else if(nd.getNodeType() == Node.ELEMENT_NODE)
			{
				// possible mention, get indices
				begin = curr;
				String txt = nd.getTextContent().trim();
				currentText += txt;
				curr = currentText.replaceAll("\\s", "").length();
				end = curr - 1;
				if(((Element)nd).getTagName() == "e") // verify it's correct tag
				{
					GeneMention gen = new GeneMention(jcas, begin, end);
					gen.setMentionText(txt);
					gen.addToIndexes();
				}else{
					getLogger().log(Level.WARNING, Integer.toString(nd.getNodeType())+" is unknown");
				}
				
			}else{
				getLogger().log(Level.WARNING, ((Element)nd).getTagName()+" is unknown");
			}
			
		}
		
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return currentNode < length;
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[] {new ProgressImpl(currentNode, length, "SENTENCES")}; 
	}

	@Override
	public void close() throws IOException {
	}

}
