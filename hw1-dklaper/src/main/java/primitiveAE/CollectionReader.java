package primitiveAE;

import java.io.*;

import org.apache.uima.UIMA_IllegalArgumentException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Progress;
import types.*;

/**
 * Reads the input files as given in the assignment and sets them as SOFA
 * 
 */
public class CollectionReader extends CollectionReader_ImplBase {

	private BufferedReader bufread;
	
	@Override
	public void initialize()
	{
		// open the resource
		String path = (String)getConfigParameterValue("InputFile");
		try {
			FileReader read = new FileReader(path);
			bufread = new BufferedReader(read);
			
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, e.getLocalizedMessage());
			throw new UIMA_IllegalArgumentException();
		}
	}
	
	@Override
	public void getNext(CAS newcas) throws IOException, CollectionException {
		// get the sentence and ID 
		try {
			// split key and set SOFA
			JCas jcas = newcas.getJCas();
			String[] data = bufread.readLine().split("\\s", 2);
			jcas.setSofaDataString(data[1], "text");
			
			// annotate with sentence ID
			SentenceMetadata smt;
			smt = new SentenceMetadata(newcas.getJCas());
			smt.setSentenceID(data[0]);
			smt.addToIndexes();
		} catch (CASException e) {
			getLogger().log(Level.SEVERE, e.getLocalizedMessage());
			throw new UIMA_IllegalArgumentException();
		}
	}

	@Override
	public void close() throws IOException {
		// close the data
		bufread.close();
		
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[0]; // cannot say progress, otherwise we have to read whole input into file
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return bufread.ready(); // check if ready
	}

}
