
/* First created by JCasGen Tue Sep 16 20:58:34 EDT 2014 */
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

/** Represents a Token
 * Updated by JCasGen Tue Sep 16 20:58:34 EDT 2014
 * @generated */
public class Token_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("types.Token");
 
  /** @generated */
  final Feature casFeat_Wordform;
  /** @generated */
  final int     casFeatCode_Wordform;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getWordform(int addr) {
        if (featOkTst && casFeat_Wordform == null)
      jcas.throwFeatMissing("Wordform", "types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Wordform);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWordform(int addr, String v) {
        if (featOkTst && casFeat_Wordform == null)
      jcas.throwFeatMissing("Wordform", "types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_Wordform, v);}
    
  
 
  /** @generated */
  final Feature casFeat_PartOfSpeech;
  /** @generated */
  final int     casFeatCode_PartOfSpeech;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPartOfSpeech(int addr) {
        if (featOkTst && casFeat_PartOfSpeech == null)
      jcas.throwFeatMissing("PartOfSpeech", "types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_PartOfSpeech);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPartOfSpeech(int addr, String v) {
        if (featOkTst && casFeat_PartOfSpeech == null)
      jcas.throwFeatMissing("PartOfSpeech", "types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_PartOfSpeech, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Wordform = jcas.getRequiredFeatureDE(casType, "Wordform", "uima.cas.String", featOkTst);
    casFeatCode_Wordform  = (null == casFeat_Wordform) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Wordform).getCode();

 
    casFeat_PartOfSpeech = jcas.getRequiredFeatureDE(casType, "PartOfSpeech", "uima.cas.String", featOkTst);
    casFeatCode_PartOfSpeech  = (null == casFeat_PartOfSpeech) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_PartOfSpeech).getCode();

  }
}



    