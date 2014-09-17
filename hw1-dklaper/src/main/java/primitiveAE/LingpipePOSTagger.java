package primitiveAE;

import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import types.Token;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;
import com.aliasi.tag.Tagging;

public class LingpipePOSTagger extends JCasAnnotator_ImplBase {

	private HmmDecoder tagger;

	@Override
	public void initialize(UimaContext aUimaContext) {
		try { // load the model
			ObjectInputStream objin = new ObjectInputStream(
					aUimaContext.getResourceAsStream("POSModel"));
			HiddenMarkovModel hmm = (HiddenMarkovModel) objin.readObject();
			objin.close();
			tagger = new HmmDecoder(hmm);
		} catch (Exception e) {
			throw new UIMARuntimeException(e);
		}
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		ArrayList<String> tokens = new ArrayList<String>();
		// get token list for tagging
		for(Annotation anno : aJCas.getAnnotationIndex(Token.type))
		{
			Token tok = (Token) anno;
			tokens.add(tok.getWordform());
		}
		
		Tagging<String> res = tagger.tag(tokens);
		// assign tags
		int i = 0;
		for(Annotation anno : aJCas.getAnnotationIndex(Token.type))
		{
			Token tok = (Token)anno;
			tok.setPartOfSpeech(res.tag(i));
			++i;
		}
		// since PoS is not indexed no need for add to Indexes
	}

}
