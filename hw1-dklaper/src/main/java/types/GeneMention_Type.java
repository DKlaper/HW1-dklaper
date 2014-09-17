
/* First created by JCasGen Mon Sep 15 18:41:04 EDT 2014 */
package types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Tag something as GeneMention
 * Updated by JCasGen Tue Sep 16 20:58:34 EDT 2014
 * @generated */
public class GeneMention_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GeneMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GeneMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GeneMention(addr, GeneMention_Type.this);
  			   GeneMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GeneMention(addr, GeneMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GeneMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("types.GeneMention");



  /** @generated */
  final Feature casFeat_MentionText;
  /** @generated */
  final int     casFeatCode_MentionText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMentionText(int addr) {
        if (featOkTst && casFeat_MentionText == null)
      jcas.throwFeatMissing("MentionText", "types.GeneMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_MentionText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMentionText(int addr, String v) {
        if (featOkTst && casFeat_MentionText == null)
      jcas.throwFeatMissing("MentionText", "types.GeneMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_MentionText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GeneMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_MentionText = jcas.getRequiredFeatureDE(casType, "MentionText", "uima.cas.String", featOkTst);
    casFeatCode_MentionText  = (null == casFeat_MentionText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_MentionText).getCode();

  }
}



    