package trainCRF.helpers;

/**
 * Represent a feature for the CRF
 * A feature is not just the token but also the PoS 
 *
 */
public class Feature {
	private String token;
	private String pos;
	
	public Feature(String tok, String partofspeech)
	{
		token = tok;
		pos = partofspeech;
	}
	
	public String getToken()
	{
		return token;
	}
	
	public String getPoS()
	{
		return pos;
	}

}
