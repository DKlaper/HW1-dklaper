package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

/**
 * 
 * loads the evaluation data
 */
public class EvalMap_Impl implements SharedResourceObject, EvalMap {

	private HashMap<String, Set<String>> evalData = new HashMap<String, Set<String>>();

	@Override
	public void load(DataResource aData) throws ResourceInitializationException {

		try { // open file
			BufferedReader read = new BufferedReader(new InputStreamReader(
					aData.getInputStream()));
			String line = read.readLine();

			while (line != null) {
				line = line.trim();

				// get id
				String key = line.split("\\|")[0];
				// make sure set exists
				if(!evalData.containsKey(key))
				{
					evalData.put(key, new HashSet<String>());
				}
				// add mention to set
				evalData.get(key).add(line);
				
				line = read.readLine();
			}
			read.close();
			
			
		} catch (IOException e) {
			throw new UIMARuntimeException(e);
		}

	}

	@Override
	public Set<String> getSentenceAnnotations(String senID) {
		return evalData.get(senID);
	}

}
