

/* First created by JCasGen Mon Sep 15 18:41:04 EDT 2014 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** The id of the sentence being analyzed
 * Updated by JCasGen Tue Sep 16 20:58:34 EDT 2014
 * XML source: /usr/data/CMU/791/HW1-dklaper/hw1-dklaper/src/main/resources/typeSystemDescriptor.xml
 * @generated */
public class SentenceMetadata extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SentenceMetadata.class);
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
  protected SentenceMetadata() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SentenceMetadata(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SentenceMetadata(JCas jcas) {
    super(jcas);
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
  //* Feature: SentenceID

  /** getter for SentenceID - gets the SentenceID
   * @generated
   * @return value of the feature 
   */
  public String getSentenceID() {
    if (SentenceMetadata_Type.featOkTst && ((SentenceMetadata_Type)jcasType).casFeat_SentenceID == null)
      jcasType.jcas.throwFeatMissing("SentenceID", "types.SentenceMetadata");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SentenceMetadata_Type)jcasType).casFeatCode_SentenceID);}
    
  /** setter for SentenceID - sets the SentenceID 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentenceID(String v) {
    if (SentenceMetadata_Type.featOkTst && ((SentenceMetadata_Type)jcasType).casFeat_SentenceID == null)
      jcasType.jcas.throwFeatMissing("SentenceID", "types.SentenceMetadata");
    jcasType.ll_cas.ll_setStringValue(addr, ((SentenceMetadata_Type)jcasType).casFeatCode_SentenceID, v);}    
  }

    