package com.example.cveserver.DTO;

import com.example.cveserver.Entity.Cve;
import lombok.*;

@Setter
@Getter
public class CveServiceDTO {
    private String cveId;
    private String cveUrl;

    public CveServiceDTO(String cveId, String cveUrl) {
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
