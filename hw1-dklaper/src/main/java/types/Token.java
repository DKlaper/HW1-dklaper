

/* First created by JCasGen Tue Sep 16 20:58:34 EDT 2014 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Represents a Token
 * Updated by JCasGen Tue Sep 16 20:58:34 EDT 2014
 * XML source: /usr/data/CMU/791/HW1-dklaper/hw1-dklaper/src/main/resources/typeSystemDescriptor.xml
 * @generated */
public class Token extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Token(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Wordform

  /** getter for Wordform - gets 
   * @generated
   * @return value of the feature 
   */
  public String getWordform() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_Wordform == null)
      jcasType.jcas.throwFeatMissing("Wordform", "types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_Wordform);}
    
  /** setter for Wordform - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWordform(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_Wordform == null)
      jcasType.jcas.throwFeatMissing("Wordform", "types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_Wordform, v);}    
   
    
  //*--------------*
  //* Feature: PartOfSpeech

  /** getter for PartOfSpeech - gets PoS Tag
   * @generated
   * @return value of the feature 
   */
  public String getPartOfSpeech() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_PartOfSpeech == null)
      jcasType.jcas.throwFeatMissing("PartOfSpeech", "types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_PartOfSpeech);}
    
  /** setter for PartOfSpeech - sets PoS Tag 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartOfSpeech(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_PartOfSpeech == null)
      jcasType.jcas.throwFeatMissing("PartOfSpeech", "types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_PartOfSpeech, v);}    
  }

    