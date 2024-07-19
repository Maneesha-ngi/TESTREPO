package com.shafafiya.webapi.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shafafiya.webapi.entity.MemReqBean;
import com.shafafiya.webapi.entity.Nationality;
import com.shafafiya.webapi.entity.Person;

import com.shafafiya.webapi.entity.PolReqBean;
import com.shafafiya.webapi.entity.Policy;
import com.shafafiya.webapi.repository.InhouseRepo;
import com.shafafiya.webapi.service.MemberService;
import com.shafafiya.webapi.service.NationalityService;
import com.shafafiya.webapi.service.PersonNationalityService;
import com.shafafiya.webapi.service.PolicyService;

import com.shafafiya.webapi.service.exception.ResourceNotFoundException;
import com.shafafiya.webapi.service.payload.ApiResponse;

import org.apache.logging.log4j.LogManager;


@CrossOrigin(origins = {"https://shafafia-git-main-farhanali2325-project.vercel.app/", "http://localhost:3000","http://localhost:5000", "http://INHOUSE:3000","http://INHOUSE:5000","https://inhouse:3000","https://inhouse:5000","https://localhost:3000","https://localhost:3000"})		
@RestController
@RequestMapping("/Policy")
public class PolicyController {
	
	private static final Logger log = LogManager.getLogger(PolicyController.class);
	
	@Autowired 
	PolicyService policyService;
	
	@Autowired 
	MemberService memberService;
	
	@Autowired 
	PersonNationalityService personnationService;
	
	
	@Autowired 
	NationalityService nationalityService;
	
	@Autowired 
	InhouseRepo inhouserepo;
	
	
	
	//@Autowired
    //private ShafafiyaClient  shafafiyaclient;
	
	
	
	
	@GetMapping("/Download-csv")
	public ResponseEntity<Resource> DownloadFile(@RequestParam("filePath") String filePath ) throws IOException {
		
		   File file = new File(filePath);

	        HttpHeaders header = new HttpHeaders();
	        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ErrorsReport_NEW.csv");
	        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        header.add("Pragma", "no-cache");
	        header.add("Expires", "0");

	        Path path = Paths.get(file.getAbsolutePath());
	        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

	        return ResponseEntity.ok()
	                .headers(header)
	                .contentLength(file.length())
	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	                .body(resource);
	    }
	
	
	
	
	@PostMapping("/CreatePersonEntry")
	public ResponseEntity<ApiResponse> createPersonEntry(@RequestBody  Person person){
		
		Optional<Person> memDetails= memberService.getInhouseMemberDetails(person.getMember().getID(),person.getMember().getContract().getCompanyID(),person.getEndSrl());
		
		ResponseEntity<ApiResponse> apiresp=null;
		if(memDetails==null)
			return createPerson(person);
		else
			return updatePerson(person);
	}		
			
		@PostMapping("/CreatePerson")
	public ResponseEntity<ApiResponse> createPerson(@RequestBody  Person person){
			
		
			
		Optional<Integer> response=memberService.createPerson(person);
		
		System.out.println("response ::::::::::::" + response);
		int stat =response.orElseThrow( ()->new NullPointerException());
		
		ApiResponse resp=null;
		
		if(stat==1)
			return new ResponseEntity<ApiResponse>(ApiResponse.builder().message("Success").success(true).status(HttpStatus.CREATED).build(),HttpStatus.CREATED);
		else if(stat==9)
			return new ResponseEntity<ApiResponse>(ApiResponse.builder().message("Member already exist !").success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		
		else
			return new ResponseEntity<ApiResponse>(ApiResponse.builder().message("Error").success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build(),HttpStatus.INTERNAL_SERVER_ERROR);
			 
	}
	
	
	@PostMapping("/UpdatePersonEntry")
	public ResponseEntity<ApiResponse> updatePerson(@RequestBody  Person person){
		
		Optional<Integer> response=memberService.updatePerson(person);
		
		System.out.println("response ::::::::::::" + response);
		int stat =response.orElseThrow( ()->new NullPointerException());
		
		ApiResponse resp=null;
		
		if(stat==1)
			return new ResponseEntity<ApiResponse>(ApiResponse.builder().message("Success").success(true).status(HttpStatus.OK).build(),HttpStatus.OK);
				else
			return new ResponseEntity<ApiResponse>(ApiResponse.builder().message("Error").success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build(),HttpStatus.INTERNAL_SERVER_ERROR);
			 
	}
	
	
	@PostMapping("/EndorsementDetails")
	public ResponseEntity<List<Policy>> getPolicyMember(@RequestBody  PolReqBean polreq){
		
		String CardNo=polreq.getCardNo();
		List<Policy> policyDetails=null;
		
		//if((CardNo==null) || (CardNo.equals(null)) || (CardNo.trim().equals("")))
		//	new ResourceNotFoundException("Card number field is mandatory !! :");
		//else {
		log.info(polreq.getCardNo()+"--"+polreq.getEidNo()+"--"+polreq.getFromDate()+"--"+polreq.getToDate());
		policyDetails= policyService.getPolicyDetails(polreq.getCardNo(),polreq.getEidNo(),polreq.getFromDate(),polreq.getToDate());
		
		
		if(policyDetails.size()>0)
			new ResourceNotFoundException("Member with given ID is not available !! :");
		//}
		return ResponseEntity.of(Optional.of((policyDetails)));
		
	}
	
	
	@PostMapping("/MemberDetails")
	public ResponseEntity<Person> getMemberDetails(@RequestBody  MemReqBean memreq){
		
		log.info(memreq.getCardNo()+"--"+memreq.getPolNo()+"--"+memreq.getEndSrl());
		Optional<Person> memDetails= memberService.getMemberDetails(memreq.getCardNo(),memreq.getPolNo(),memreq.getEndSrl());
		//List<Nationality> nationality = nationalityService.getNationality();
		
		//PersonNationality personNation=personnationService.getPesronNationDetails(memreq.getCardNo(),memreq.getPolNo(),memreq.getEndNo());
		
		
		if(memDetails.isEmpty())
			new ResourceNotFoundException("Member details  not available !! :");
		
		return ResponseEntity.of((memDetails));
	}
	
	@GetMapping("/GetNationality")
	public ResponseEntity<List<Nationality>> getNationality(){
		
		List<Nationality> nationality=nationalityService.getNationality();
		
		if(nationality == null)
			new ResourceNotFoundException("Error fetching nationality !! :");
		
		return ResponseEntity.of(Optional.of((nationality)));
	}
	
	
	
	/*
	@PostMapping("/UploadTransaction")
	public void uploadTransaction(){
		
		
		try {
			
			UploadTransaction request = new UploadTransaction();
		      request.setLogin("NationalGeneral");
		      request.setPwd("$$*&%$$Ngi#*&)01@ngi");
		      System.out.println("***********777777777777*********");
		      
		      String name = "<?xml version='1.0' encoding='utf-8' ?><Person.Register xmlns:tns='http://www.haad.ae/DataDictionary/CommonTypes' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:noNamespaceSchemaLocation='http://www.haad.ae/DataDictionary/CommonTypes/PersonRegister.xsd'>\r\n"
		      		+ "	<Header>\r\n"
		      		+ "		<SenderID>A025</SenderID>\r\n"
		      		+ "		<ReceiverID>HAAD</ReceiverID>\r\n"
		      		+ "		<TransactionDate>23/06/2024 12:37</TransactionDate>\r\n"
		      		+ "		<RecordCount>1</RecordCount>\r\n"
		      		+ "		<DispositionFlag>PRODUCTION</DispositionFlag>\r\n"
		      		+ "	</Header>\r\n"
		      		+ "	<Person>\r\n"
		      		+ "		<UnifiedNumber>212173419</UnifiedNumber>\r\n"
		      		+ "		\r\n"
		      		+ "		<FirstNameEn>MUHAMMAD</FirstNameEn>\r\n"
		      		+ "		<MiddleNameEn>NAFI</MiddleNameEn>\r\n"
		      		+ "		<LastNameEn>VATTA PPARAMBIL THAZHATHE KANDI</LastNameEn>\r\n"
		      		+ "		\r\n"
		      		+ "		<FirstNameAr>محمد</FirstNameAr>\r\n"
		      		+ "		<MiddleNameAr>نافى</MiddleNameAr>\r\n"
		      		+ "		<LastNameAr>ثازهاتى كاندى بوثيابوراييل بارامبيل فاتا</LastNameAr>\r\n"
		      		+ "		<ContactNumber>NA</ContactNumber>\r\n"
		      		+ "		<BirthDate>17/12/1988</BirthDate>\r\n"
		      		+ "		<Gender>1</Gender>\r\n"
		      		+ "		<Nationality>Indian</Nationality>\r\n"
		      		+ "		<NationalityCode>100</NationalityCode>\r\n"
		      		+ "		<City>ABU DHABI</City>\r\n"
		      		+ "	<PassportNumber>T6861417</PassportNumber>\r\n"
		      		+ "	<EmiratesIDNumber>784-1988-9068705-0</EmiratesIDNumber>\r\n"
		      		+ "	<SponsorNumber>162712</SponsorNumber>\r\n"
		      		+ "	<SponsorNameEn>AL ROMAIZAN GOLD AND JEWELLERY L L C ABU DHABI BRANCH</SponsorNameEn>\r\n"
		      		+ "	<SponsorNameAr>الرميزان للذهب والمجوهرات - ذ م م - فرع ابوظبي-6</SponsorNameAr>\r\n"
		      		+ "	<Member>\r\n"
		      		+ "		<ID>102-103-0002329201-01</ID>\r\n"
		      		+ "		<Relation>Principal</Relation>\r\n"
		      		+ "		<RelationTo>102-103-0002329201-01</RelationTo>\r\n"
		      		+ "		<Contract>\r\n"
		      		+ "		<PackageName>48783</PackageName>\r\n"
		      		+ "		<StartDate>23/04/2022</StartDate>\r\n"
		      		+ "		<RenewalDate>22/04/2024</RenewalDate>\r\n"
		      		+ "		<ExpiryDate>22/04/2024</ExpiryDate>\r\n"
		      		+ "		<GrossPremium>4793</GrossPremium>\r\n"
		      		+ "		<PolicyHolder>6</PolicyHolder>\r\n"
		      		+ "		<CompanyID>P/01/2023/000439</CompanyID>\r\n"
		      		+ "		<CollectedPremium>5032.65</CollectedPremium>\r\n"
		      		+ "		<VAT>239.65</VAT>\r\n"
		      		+ "		<VATPercent>5</VATPercent>\r\n"
		      		+ "		<Status>Renewed</Status>\r\n"
		      		+ "		</Contract>\r\n"
		      		+ "		</Member>\r\n"
		      		+ "</Person>\r\n"
		      		+ "</Person.Register>";
	      
		      request.setFileContent(name.getBytes());
		      request.setFileName("1E03521B3891B212.xml");
		      
	
	      UploadTransactionResponse response =  shafafiyaclient.UploadTransaction( request);
	  	
	     
	      
	      System.out.println("Got Response As below ========= : ");
	      System.out.println("UploadTransactionResult : " + response.getUploadTransactionResult());
	      System.out.println("ErrorMessage : " + response.getErrorMessage());
	      System.out.println("ErrorReport : " + response.getErrorReport());
	      System.out.println("TransactionID : " + response.getTransactionID());
	     
	      
	  
	      Path file = Paths.get("D:\\PersonUpload\\output999.zip");
	      Files.write(file, response.getErrorReport());
	      
	  	      
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    }*/

}
