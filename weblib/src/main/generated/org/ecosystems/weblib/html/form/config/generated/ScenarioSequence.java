//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.28 at 03:40:37 PM CEST 
//


package org.ecosystems.weblib.html.form.config.generated;

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
 *         &lt;element ref="{http://www.e-cosystems.org/forms_test}formScenario" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.e-cosystems.org/forms_test}begin default="/""/>
 *       &lt;attribute ref="{http://www.e-cosystems.org/forms_test}name use="required""/>
 *       &lt;attribute ref="{http://www.e-cosystems.org/forms_test}enabled default="true""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "formScenario"
})
@XmlRootElement(name = "scenarioSequence")
public class ScenarioSequence {

    @XmlElement(required = true)
    protected List<FormScenario> formScenario;
    @XmlAttribute(name = "begin", namespace = "http://www.e-cosystems.org/forms_test")
    @XmlSchemaType(name = "anyURI")
    protected String begin;
    @XmlAttribute(name = "name", namespace = "http://www.e-cosystems.org/forms_test", required = true)
    protected String name;
    @XmlAttribute(name = "enabled", namespace = "http://www.e-cosystems.org/forms_test")
    protected Boolean enabled;

    /**
     * 
     * 					Scénarios exécutés par la séquence.
     * 					Gets the value of the formScenario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formScenario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormScenario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormScenario }
     * 
     * 
     */
    public List<FormScenario> getFormScenario() {
        if (formScenario == null) {
            formScenario = new ArrayList<FormScenario>();
        }
        return this.formScenario;
    }

    /**
     * 
     * 				Url relative de début du scénario (commence par /).
     * 				
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBegin() {
        if (begin == null) {
            return "/";
        } else {
            return begin;
        }
    }

    /**
     * Sets the value of the begin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBegin(String value) {
        this.begin = value;
    }

    /**
     * 
     * 				Nom de la séquence de scénario.
     * 				
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 
     * 				indique si le scénario est actif.
     * 				
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

}
