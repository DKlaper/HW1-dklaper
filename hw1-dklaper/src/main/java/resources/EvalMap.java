package resources;

import java.util.Set;

public interface EvalMap {
	/**
	 * Get the set of annotations that are output by the gold standard resource
	 * @param senID Sentence ID of the sentence we want to have the gold annotations
	 * @return Set of output strings (trimmed)
	 */
	Set<String> getSentenceAnnotations(String senID);

}
