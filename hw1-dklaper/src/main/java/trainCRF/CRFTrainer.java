package trainCRF;

import java.io.File;
import java.util.ArrayList;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import com.aliasi.crf.ChainCrf;
import com.aliasi.crf.ChainCrfFeatureExtractor;
import com.aliasi.io.LogLevel;
import com.aliasi.io.Reporters;
import com.aliasi.stats.AnnealingSchedule;
import com.aliasi.stats.RegressionPrior;
import com.aliasi.util.AbstractExternalizable;

import trainCRF.helpers.*;
import types.*;

/**
 * Extracts features, makes it a tagging and trains a crf
 *
 */
public class CRFTrainer extends JCasAnnotator_ImplBase {

	private FeatureCorpus corpus = new FeatureCorpus();
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		AnnotationIndex<Annotation> tokidx = aJCas.getAnnotationIndex(Token.type);
		AnnotationIndex<Annotation> labelidx = aJCas.getAnnotationIndex(GeneMention.type);
		
		ArrayList<String> labels = new ArrayList<String>();
		FSIterator<Annotation> lbls = labelidx.iterator();
		Annotation currentlbl = new Annotation(aJCas, Integer.MAX_VALUE, Integer.MAX_VALUE);
				
		if(lbls.hasNext()){
			currentlbl = lbls.next();
		}
		// Annotates BIOWE system (W = single token, E = end of multi unit token)
		for(Annotation ann : tokidx) // each token needs a label
		{
			if(ann.getBegin() < currentlbl.getBegin()) // before next annotation
			{
				labels.add("O");
			}else{ // inside annotation
				if(ann.getBegin() == currentlbl.getBegin()) // beginning of annotation
				{
					if(ann.getEnd() < currentlbl.getEnd())
					{// beginning of multilevel
						labels.add("B");
					}else
					{// single token, found end need next
						labels.add("W");
						if(lbls.hasNext())
						{
							currentlbl = lbls.next();
						}else{// no more annotations
							currentlbl = new Annotation(aJCas, Integer.MAX_VALUE, Integer.MAX_VALUE);
						}
					}
				}else{ // inside annotation after first token
					if(ann.getEnd() >= currentlbl.getEnd()) // annotation ends here
					{
						labels.add("E");
						if(lbls.hasNext())
						{
							currentlbl = lbls.next();
						}else{// no more annotations
							currentlbl = new Annotation(aJCas, Integer.MAX_VALUE, Integer.MAX_VALUE);
						}
					}else{ // annotation continues
						labels.add("I");
					}
					
				}
				
			}
		}
		// add the sentence to the corpus
		ArrayList<Feature> feats = getFeatures(tokidx);
		corpus.addNewSentence(feats, labels);
	}
	
	/**
	 * Get the features of a sentence (annotationindex must only have token elements)
	 * @param tokens the token annotations
	 * @return the arraylist of features for the lingpipe interface
	 */
	public static ArrayList<Feature> getFeatures(AnnotationIndex<Annotation> tokens)
	{
		ArrayList<Feature> res = new ArrayList<Feature>();
		for(Annotation ann : tokens)
		{
			Token tok = (Token)ann;
			Feature feat = new Feature(tok.getWordform(), tok.getPartOfSpeech());
			res.add(feat);
		}
		
		return res;
	}
	
	/**
	 * Trains the model
	 */
	@Override
	public void collectionProcessComplete()
	{	
		ChainCrfFeatureExtractor<Feature> featureExtractor = new FeatureExtractor();
		// train the crf and write it to a model file
		try {
			ChainCrf<Feature> res = ChainCrf.estimate(corpus, featureExtractor, true, 1, false, false, RegressionPrior.gaussian(0.5, true), 5, AnnealingSchedule.exponential(0.08, 0.995), 1e-7, 10, 150, Reporters.stdOut().setLevel(LogLevel.DEBUG));
			String filename = (String)getContext().getConfigParameterValue("ModelName");
			File file = new File(filename);
			AbstractExternalizable.serializeTo(res, file);
			
		} catch (Exception e) {
			throw new UIMARuntimeException(e);
		}
	}

}
