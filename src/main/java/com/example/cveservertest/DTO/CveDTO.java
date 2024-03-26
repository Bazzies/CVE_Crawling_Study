package com.example.cveservertest.DTO;

import com.example.cveservertest.Entity.Cve;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class CveDTO {
    private String cveId;
    private String cveUrl;

    public Cve convertToCveEntity(){
        Cve cve = new Cve();
        cve.setCveId(getCveId());
        cve.setCveUrl(getCveUrl());
        return cve;
    }
}
