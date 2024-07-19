package com.shafafiya.webapi.service.webservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;








@Configuration
public class Config {
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		//marshaller.setContextPath("testing.com.app.webapi.webservice");
		marshaller.setClassesToBeBound(com.shafafiya.webapi.webservice.live.GetInsuranceContinuityCertificate.class,
				com.shafafiya.webapi.webservice.live.GetInsuranceContinuityCertificateResponse.class,
				com.shafafiya.webapi.webservice.live.GetNewPriorAuthorizationTransactions.class,
				com.shafafiya.webapi.webservice.live.ObjectFactory.class,
				com.shafafiya.webapi.webservice.live.UploadTransaction.class,
				com.shafafiya.webapi.webservice.live.UploadTransactionResponse.class
				);
		return marshaller;
	}
	

	@Bean
	public ShafafiyaClient calculatorclient(Jaxb2Marshaller marshaller) {
		final ShafafiyaClient client = new ShafafiyaClient();
		//client.setDefaultUri("https://shafafiyapte.doh.gov.ae/v3/webservices.asmx");
		client.setDefaultUri("https://shafafiya.doh.gov.ae/v3/webservices.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	
}
