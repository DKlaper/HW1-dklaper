package primitiveAE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.UIMA_IllegalArgumentException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;

import types.*;

/**
 * Writes the CAS to the prescribed output format
 * 
 */
public class ResultConsumer extends CasConsumer_ImplBase {

	private BufferedWriter outstr;
	
	@Override
	public void initialize() 
	{
		try {
			outstr = new BufferedWriter(new FileWriter((String)getConfigParameterValue("OutputFile")));
		} catch (Exception e) {
			throw new UIMA_IllegalArgumentException(e);
		}
	}
	
	@Override
	public void destroy()
	{
		try {
			outstr.close();
		} catch (IOException e) {
		}
	}
	
	@Override
	public void processCas(CAS cas) throws AnalysisEngineProcessException {
		
		JCas aJCas;
		try {
			aJCas = cas.getJCas();
		} catch (CASException e) {
			throw new AnalysisEngineProcessException(e);
		}
		
		String senID = "N/A";
		FSIterator<TOP> it = aJCas.getJFSIndexRepository().getAllIndexedFS(SentenceMetadata.type);
		
		if(it.hasNext())
		{
			SentenceMetadata dat = (SentenceMetadata)it.next();
			senID = dat.getSentenceID();
		}
		
		
		for(Annotation gmt : aJCas.getAnnotationIndex(GeneMention.type))
		{
			GeneMention gene = (GeneMention)gmt;
			
			String out = getOutString(senID, gene.getBegin(), gene.getEnd(), gene.getMentionText());
			// write out result
			try {
				outstr.write(out);
				outstr.flush();
			} catch (IOException e) {
				throw new UIMARuntimeException(e);
			}
		}
	}
	
	/**
	 * Gets the prescribed output format from the data
	 * @param senID Id of the sentence
	 * @param begin Start index non-whitespace
	 * @param end End index non-whitespace
	 * @param txt Text of the annotation
	 * @return the correctly formatted string
	 */
	public static String getOutString(String senID, Integer begin, Integer end, String txt)
	{
		return senID+"|"+begin.toString()+" "+end.toString()+"|"+txt+"\n";
	}

}
