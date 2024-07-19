//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.7 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.23 at 12:43:38 PM GST 
//


package com.shafafiya.webapi.webservice.live;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UploadTransactionResult" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="errorReport" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "uploadTransactionResult",
    "errorMessage",
    "errorReport",
    "transactionID"
})
@XmlRootElement(name = "UploadTransactionResponse")
public class UploadTransactionResponse {

    @XmlElement(name = "UploadTransactionResult")
    protected int uploadTransactionResult;
    protected String errorMessage;
    protected byte[] errorReport;
    @XmlElement(name = "TransactionID")
    protected String transactionID;

    /**
     * Gets the value of the uploadTransactionResult property.
     * 
     */
    public int getUploadTransactionResult() {
        return uploadTransactionResult;
    }

    /**
     * Sets the value of the uploadTransactionResult property.
     * 
     */
    public void setUploadTransactionResult(int value) {
        this.uploadTransactionResult = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the errorReport property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getErrorReport() {
        return errorReport;
    }

    /**
     * Sets the value of the errorReport property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setErrorReport(byte[] value) {
        this.errorReport = value;
    }

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

}