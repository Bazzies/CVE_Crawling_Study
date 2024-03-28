package com.example.cveservertest.DTO;

import com.example.cveservertest.Entity.Cve;
import lombok.*;

@Setter
@Getter
public class CveDTO {
    private String cveId;
    private String cveUrl;

    public CveDTO(String cveId, String cveUrl) {
        this.cveId = cveId;
        this.cveUrl = cveUrl;
    }

    public Cve convertToCveEntity(){
        Cve cve = new Cve();
        cve.setCveId(getCveId());
        cve.setCveUrl(getCveUrl());
        return cve;
    }
}
