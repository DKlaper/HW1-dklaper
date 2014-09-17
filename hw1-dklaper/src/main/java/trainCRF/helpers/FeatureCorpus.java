package trainCRF.helpers;

import java.util.ArrayList;

import com.aliasi.corpus.Corpus;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.tag.Tagging;

/**
 * Represent a corpus of features for the CRF training
 * 
 */
public class FeatureCorpus extends Corpus<ObjectHandler<Tagging<Feature>>> {
	
	private ArrayList<ArrayList<Feature> > toks = new ArrayList< ArrayList<Feature> >();
	private ArrayList<ArrayList<String> > tags = new ArrayList<ArrayList<String>>();
	
	public FeatureCorpus() {
	}
	
	public void visitTrain(ObjectHandler<Tagging<Feature>> handler)
	{
		 for(int i = 0; i < toks.size(); ++i)
		 {
			 Tagging<Feature> sent = new Tagging<Feature>(toks.get(i), tags.get(i));
			 handler.handle(sent);
		 }
	}
	
	public void addNewSentence(ArrayList<Feature> token, ArrayList<String> label)
	{
		toks.add(token);
		tags.add(label);
	}

}
