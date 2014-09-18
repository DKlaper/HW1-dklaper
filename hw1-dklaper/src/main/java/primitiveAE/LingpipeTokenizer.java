package primitiveAE;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;

import types.*;
/**
 * A tokenizer from Lingpipe
 * 
 */
public class LingpipeTokenizer extends JCasAnnotator_ImplBase {
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		String text = aJCas.getDocumentText();
		Tokenizer sent = IndoEuropeanTokenizerFactory.INSTANCE.tokenizer(text.toCharArray(), 0, text.length());
		
		// get token and normalized indices
		String token = sent.nextToken();
		int begin = text.substring(0, sent.lastTokenStartPosition()).replaceAll("\\s", "").length();
		int end = text.substring(0, sent.lastTokenEndPosition()).replaceAll("\\s", "").length();
		
		while(token != null)
		{
			// add annotation
			Token tok = new Token(aJCas, begin, end);
			tok.setWordform(token);
			tok.addToIndexes();
			
			// update token values 
			token = sent.nextToken();
			begin = text.substring(0, sent.lastTokenStartPosition()).replaceAll("\\s", "").length();
			end = text.substring(0, sent.lastTokenEndPosition()).replaceAll("\\s", "").length()-1;
		}

	}

}
