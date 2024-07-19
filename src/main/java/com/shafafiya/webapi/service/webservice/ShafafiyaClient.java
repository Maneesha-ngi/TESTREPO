package com.shafafiya.webapi.service.webservice;



import com.shafafiya.webapi.webservice.live.UploadTransaction;
import com.shafafiya.webapi.webservice.live.UploadTransactionResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;




public class ShafafiyaClient  extends WebServiceGatewaySupport{
	
	
	
	public UploadTransactionResponse UploadTransaction(UploadTransaction req) {
		
					
			UploadTransactionResponse response =(UploadTransactionResponse) getWebServiceTemplate()
					.marshalSendAndReceive(
							  req,
							  new SoapActionCallback("https://www.shafafiya.org/v2/UploadTransaction")
								);
			return response;
		}
	
	

}