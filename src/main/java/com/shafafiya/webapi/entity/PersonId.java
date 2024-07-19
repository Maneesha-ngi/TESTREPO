package com.shafafiya.webapi.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PersonId implements Serializable {

	
	
	    @Column(name = "HAAD_POL_NO")
	    private String companyID;
	    @Column(name = "HAAD_END_SRL_NO")
	    private String endNo;
	    @Column(name = "HAAD_MEM_CARD_NO")
	    private String id;



	
}
