package com.shafafiya.webapi.service.webservice;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.saaj.SaajSoapMessage;



public class SoapActionCallback implements WebServiceMessageCallback {

    private final String soapAction;

    public SoapActionCallback(String soapAction) {
        this.soapAction = soapAction;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
        if (message instanceof SaajSoapMessage) {
            SaajSoapMessage saajMessage = (SaajSoapMessage) message;
            saajMessage.setSoapAction(soapAction);
        } 
    }
}
