package com.example.cveservertest.Service;

import com.example.cveservertest.DTO.CveDTO;
import com.example.cveservertest.Entity.Cve;
import com.example.cveservertest.Repository.CVERepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CVEService {
    private final CVERepository cveRepository;

    public void getCve(List<String> cveIds) {
        //사용자가 admin권한을 가지고 있는지를 확인
//        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {

            for (String cveId : cveIds) {
                try {
                    isCveIdFormat(cveId);

                    Cve cve = cveRepository.findCveByCveId(cveId);
                    if (cve != null) {
                        // 필요한 로직 수행
                        System.out.println("CVE_ID: " + cve.getCveId() + ", URL: " + cve.getCveUrl());
                        continue;
                    }

                    CveDTO cveDTO = new CveDTO();
                    cveDTO.setCveId(cveId);
                    cveDTO.setCveUrl("https://nvd.nist.gov/vuln/detail/" + cveId);

                    Cve newCve = cveDTO.convertToCveEntity();

                    cveRepository.save(newCve);

                } catch (IllegalArgumentException e) {
                    System.out.println("올바른 CVE 형식이 아닙니다.");
                }
            }
        }
    //}

    private void isCveIdFormat(String cveId) {
        String format1 = "^CVE-\\d{4}-\\d{4}$";
        String format2 = "^CVE-\\d{4}-\\d{5}$";
        if (!cveId.matches(format1) && !cveId.matches(format2)) {
            throw new IllegalArgumentException("올바른 CVE 형식이 아닙니다.");
        }
    }
}
