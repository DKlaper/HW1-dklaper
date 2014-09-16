package primitiveAE;

import java.util.Set;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceProcessException;

import types.*;
import resources.*;

/**
 * Evaluates based on the given gold standard file (see resources bound in descriptor)
 */
public class EvaluationConsumer extends CasConsumer_ImplBase {

	private int tp; // true positive
	private int fp; // false positive
	private int fn;  // false negative 
	
	@Override
	public void initialize()
	{
		tp = 0;
		fp = 0;
		fn = 0;
	}
	
	@Override
	public void destroy()
	{
		// job done print accuracy
		double prec = tp/((double)tp+fp)*100.0;
		double recall = tp/((double)tp+fn) *100.0;
		System.out.println(String.format("Precision: %.2f", prec));
		System.out.println(String.format("Recall: %.2f", recall));
		System.out.println(String.format("F1: %.2f", 2*recall*prec/(recall+prec)));
	}
	
	@Override
	public void processCas(CAS aCAS) throws ResourceProcessException {
		try {
			EvalMap evaldata = (EvalMap)getUimaContext().getResourceObject("evaluation");
			JCas aJCas;
			try {
				aJCas = aCAS.getJCas();
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
			
			Set<String> gold = evaldata.getSentenceAnnotations(senID);
			
			for(Annotation gmt : aJCas.getAnnotationIndex(GeneMention.type))
			{
				GeneMention gene = (GeneMention)gmt;
				
				String out = ResultConsumer.getOutString(senID, gene.getBegin(), gene.getEnd(), gene.getMentionText()).trim();
				
				if(gold != null && gold.contains(out))
				{
					++tp;
					gold.remove(out);
				}else{
					++fp;
				}
			}
			if(gold != null)
			{
				fn += gold.size();
			}
			
		} catch (ResourceAccessException e) {
			throw new UIMARuntimeException(e);
		}

	}

}
