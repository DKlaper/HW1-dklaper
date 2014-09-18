package trainCRF.helpers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aliasi.crf.ChainCrfFeatureExtractor;
import com.aliasi.crf.ChainCrfFeatures;
import com.aliasi.util.ObjectToDoubleMap;

/**
 * 
 * Generates the rich CRF features from the  simple Feature Object
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
			String shape = shorten(tocForm(token(nTok-1).getToken()));
			res.put("PREV_SHAPE_"+shape,1.0);
			
			return res;
		}

		@Override
		public Map<String, ? extends Number> nodeFeatures(int nTok) {
			Map<String, Double> res = new ObjectToDoubleMap<String>();
			
			String token = token(nTok).getToken();
			res.put("TOK_" + token, 1.0);
			res.put("POS_" + token(nTok).getPoS(), 1.0);

			String shape = tocForm(token);
			res.put("SHAPE_"+shorten(shape),  1.0);
			
			boolean all = true;
			if(token.matches(".*\\w.*"))
			{
				res.put("HASALPHA", 1.0);
			}else{
				all = false;
			}
			boolean punnum = true;
			if(token.matches(".*\\d.*"))
			{
				res.put("HASNUMBER", 1.0);
			}else{
				all = false;
				punnum = false;
			}
			
			if(token.matches("\\d+"))
			{
				res.put("DIGITONLY", 1.0);
			}
			
			if(all){
				res.put("ISMIXED", 1.0);
			}
			
			if(token.matches(".*-.*"))
			{
				res.put("HASDASH", 1.0);
			}
			
			if(token.matches(".*\\..*"))
			{
				res.put("HASDOT", 1.0);
			}
			
			if(token.matches("\\p{Alpha}+"))
			{
				res.put("ISALPHA", 1.0);
			}
			
			if(token.matches("\\p{Upper}\\p{Lower}+"))
			{
				res.put("ISTITLE", 1.0);
			}
			
			if(token.matches(".*[a-z][A-Z].*"))
			{
				res.put("ISMIXED", 1.0);
			}
			
			if(token.toUpperCase().equals(token))
			{
				res.put("ISCAPS", 1.0);
			}
			
			if(token.matches(".*\\p{Punct}.*"))
			{
				res.put("PUNCT", 1.0);
			}else{
				punnum = false;
			}
			
			if(punnum)
			{
				res.put("PUNCTNUM", 1.0);
			}
			
			int toklen = token.length();
			if(token.length() >= 2)
			{
				res.put("PREF_2_"+token.substring(0, 2), 1.0);
				res.put("SUFF_2_"+token.substring(toklen-2, toklen), 1.0);
			}
			if(token.length() >= 3)
			{
				res.put("PREF_3_"+token.substring(0, 3), 1.0);
				res.put("SUFF_3_"+token.substring(toklen-3, toklen), 1.0);
			}
			if(token.length() >= 4)
			{
				res.put("PREF_4_"+token.substring(0, 4), 1.0);
				res.put("SUFF_4_"+token.substring(toklen-4, toklen), 1.0);
			}
			return res;
		}
		
		public static String tocForm(String token)
		{
			return token.replaceAll("[^\\W\\d]", "A").replaceAll("\\d", "#")
					.replaceAll("\\W","-");
		}
		
		public static String shorten(String shape)
		{
			return shape.replaceAll("A+", "A").replaceAll("#+", "#").replaceAll("-+", "-");
		}
		
		
	}

}
