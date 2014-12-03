//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.28 at 03:40:37 PM CEST 
//


package org.ecosystems.weblib.html.form.config.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.e-cosystems.org/forms_test}submit"/>
 *           &lt;element ref="{http://www.e-cosystems.org/forms_test}link"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.e-cosystems.org/forms_test}fieldValue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.e-cosystems.org/forms_test}waitedResults"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.e-cosystems.org/forms_test}senarioIndex use="required""/>
 *       &lt;attribute ref="{http://www.e-cosystems.org/forms_test}workingFormId"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "submit",
    "link",
    "fieldValue",
    "waitedResults"
})
@XmlRootElement(name = "formScenario")
public class FormScenario {

    protected Submit submit;
    protected Link link;
    protected List<FieldValue> fieldValue;
    @XmlElement(required = true)
    protected WaitedResults waitedResults;
    @XmlAttribute(name = "senarioIndex", namespace = "http://www.e-cosystems.org/forms_test", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger senarioIndex;
    @XmlAttribute(name = "workingFormId", namespace = "http://www.e-cosystems.org/forms_test")
    protected String workingFormId;

    /**
     * Gets the value of the submit property.
     * 
     * @return
     *     possible object is
     *     {@link Submit }
     *     
     */
    public Submit getSubmit() {
        return submit;
    }

    /**
     * Sets the value of the submit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Submit }
     *     
     */
    public void setSubmit(Submit value) {
        this.submit = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link Link }
     *     
     */
    public Link getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link Link }
     *     
     */
    public void setLink(Link value) {
        this.link = value;
    }

    /**
     * Gets the value of the fieldValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldValue }
     * 
     * 
     */
    public List<FieldValue> getFieldValue() {
        if (fieldValue == null) {
            fieldValue = new ArrayList<FieldValue>();
        }
        return this.fieldValue;
    }

    /**
     * Gets the value of the waitedResults property.
     * 
     * @return
     *     possible object is
     *     {@link WaitedResults }
     *     
     */
    public WaitedResults getWaitedResults() {
        return waitedResults;
    }

    /**
     * Sets the value of the waitedResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link WaitedResults }
     *     
     */
    public void setWaitedResults(WaitedResults value) {
        this.waitedResults = value;
    }

    /**
     * Gets the value of the senarioIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSenarioIndex() {
        return senarioIndex;
    }

    /**
     * Sets the value of the senarioIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSenarioIndex(BigInteger value) {
        this.senarioIndex = value;
    }

    /**
     * Gets the value of the workingFormId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkingFormId() {
        return workingFormId;
    }

    /**
     * Sets the value of the workingFormId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkingFormId(String value) {
        this.workingFormId = value;
    }

}
