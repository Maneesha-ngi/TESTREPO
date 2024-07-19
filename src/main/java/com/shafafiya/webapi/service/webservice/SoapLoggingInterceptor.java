package com.shafafiya.webapi.service.webservice;

import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.context.MessageContext;

public class SoapLoggingInterceptor extends ClientInterceptorAdapter {

    @Override
    public boolean handleRequest(MessageContext messageContext) {
       
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
       
        return true;
    }
}