package com.example.cveservertest.Entity;


import com.example.cveservertest.DTO.CveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cve")
public class Cve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cveId;

    @Column
    private String cveUrl;

    public CveDTO convertToCve(Cve cve){
        CveDTO cveDTO = new CveDTO();
        cveDTO.setCveId(cve.getCveId());
        cveDTO.setCveUrl(cve.getCveUrl());
        return cveDTO;
    }

}
