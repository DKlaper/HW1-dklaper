package trainCRF.helpers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aliasi.crf.ChainCrfFeatureExtractor;
import com.aliasi.crf.ChainCrfFeatures;
import com.aliasi.util.ObjectToDoubleMap;

/**
 * 
 * Generates the actual CRF features from the Feature Object
 */
public class FeatureExtractor implements ChainCrfFeatureExtractor<Feature>, Serializable {

	/**
	 * make serializable
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ChainCrfFeatures<Feature> extract(List<Feature> toks,
			List<String> tags) {
		return new CRFFeatures(toks, tags);
	}
	
	public static class CRFFeatures extends ChainCrfFeatures<Feature>
	{

		public CRFFeatures(List<Feature> tokens, List<String> tags) {
			super(tokens, tags);
		}

		@Override
		public Map<String, ? extends Number> edgeFeatures(int nTok, int prevTag) {
			Map<String, Double> res = new ObjectToDoubleMap<String>();
			
			res.put("PREV_TAG_" + tag(prevTag), 1.0);
			
			return res;
		}

		@Override
		public Map<String, ? extends Number> nodeFeatures(int nTok) {
			Map<String, Double> res = new ObjectToDoubleMap<String>();
			
			res.put("TOK_" + token(nTok), 1.0);
			
			return res;
		}
		
	}

}
