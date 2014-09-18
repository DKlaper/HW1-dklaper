package primitiveAE;

import java.io.File;
import java.util.ArrayList;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import trainCRF.helpers.Feature;
import types.*;

import com.aliasi.crf.ChainCrf;
import com.aliasi.tag.Tagging;
import com.aliasi.util.AbstractExternalizable;

/**
 * Applies custom learned crf model
 *
 */
public class LingpipeCRFApplicator extends JCasAnnotator_ImplBase {

	private ChainCrf<Feature> crf;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(UimaContext aUimaContext)
	{
		try {
			crf = (ChainCrf<Feature>) AbstractExternalizable.readObject(new File(aUimaContext.getResourceFilePath("ModelFile")));
		} catch (Exception e)
		{
			throw new UIMARuntimeException(e);
		}
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		ArrayList<Feature> data = new ArrayList<Feature>(); 
		AnnotationIndex<Annotation> idx = aJCas.getAnnotationIndex(Token.type);
		for(Annotation ann : idx)
		{ // get tokens
			Token tok = (Token)ann;
			Feature feat = new Feature(tok.getWordform(), tok.getPartOfSpeech());
			data.add(feat);
		}
		
		String sofa = aJCas.getSofaDataString();
		// run sequence through crf
		Tagging<Feature> tagresult = crf.tag(data);
		int begin = -1;
		int realbegin = 0;
		int i = 0;
		// recover gene sequences from tagging
		for(Annotation ann : idx)
		{
			Token tok = (Token)ann;
			if(tagresult.tag(i).equals("B") || tagresult.tag(i).equals("W"))
			{
				begin = tok.getBegin();
			}
			
			if(tagresult.tag(i).equals("E") || tagresult.tag(i).equals("W"))
			{
				if(begin < 0)
				{
					throw new UIMARuntimeException(new IndexOutOfBoundsException("impossible beginning"));
				}
				
				GeneMention gen = new GeneMention(aJCas, begin, tok.getEnd());
				String mention = getTextWS(begin, tok.getEnd(), sofa);
				gen.setMentionText(mention);
				gen.addToIndexes();
				begin = -1;
			}
			
			
			++i;
		}
		

	}

	/**
	 * Takes whitespace off sets and returns the original text with whitespace that is contained
	 * @param begin first character index
	 * @param end last character index
	 * @param sofa Original text
	 * @return substring of original text corresponding to the indices
	 */
	private String getTextWS(int begin, int end, String sofa) {
		String res = "";
		int rindex = 0;
		boolean record = false;
		for(int i = 0; i < sofa.length(); ++i)
		{
			// go through
			Character c = sofa.charAt(i);
			
			// found beginning
			if(rindex == begin)
			{
				record = true;
			}
			
			if(rindex > end)
			{ // stop the process once we're at end
				break;
			}
			
			if(record) // construct the string
			{
				res += c.toString();
			}
			
			if(!Character.isWhitespace(c)) // count only non whitespace
			{
				rindex++;
			}
		}
		
		return res.trim();
	}

}
