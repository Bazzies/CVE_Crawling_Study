package com.example.cveservertest.Service;

import com.example.cveservertest.DTO.CveDTO;
import com.example.cveservertest.Entity.Cve;
import com.example.cveservertest.Repository.CVERepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CveService {
    private final CVERepository cveRepository;

    public List<Cve> getCve(List<String> cveIds) {
        List<String> validCveIds = filterValidCveIds(cveIds);

        List<Cve> existingCves = findExistingCves(validCveIds);

        Set<String> existingCveIds = existingCves.stream()
                .map(Cve::getCveId)
                .collect(Collectors.toSet());

        List<String> newCveIds = findNewCveIds(validCveIds, existingCveIds);

        saveNewCves(newCveIds);

        //printCveDetails(existingCves);
        return existingCves;
    }

    private List<String> filterValidCveIds(List<String> cveIds) {
        return cveIds.stream()
                .filter(this::isCveIdFormat)
                .collect(Collectors.toList());
    }

    private List<Cve> findExistingCves(List<String> cveIds) {
        return cveRepository.findCvesByCveIdIn(cveIds);
    }

    private List<String> findNewCveIds(List<String> validCveIds, Set<String> existingCveIds) {
        return validCveIds.stream()
                .filter(cveId -> !existingCveIds.contains(cveId))
                .collect(Collectors.toList());
    }

    private void saveNewCves(List<String> newCveIds) {
        newCveIds.forEach(cveId -> {
            CveDTO newCveDTO = new CveDTO(cveId, "https://nvd.nist.gov/vuln/detail/" + cveId);
            cveRepository.save(newCveDTO.convertToCveEntity());
            System.out.println("New CVE_ID has been saved.");
        });
    }

    private void printCveDetails(List<Cve> cveList) {
        cveList.forEach(cve -> System.out.println("CVE_ID: " + cve.getCveId() + ", URL: " + cve.getCveUrl()));
    }

    private boolean isCveIdFormat(String cveId) {
        String format1 = "^CVE-\\d{4}-\\d{4}$";
        String format2 = "^CVE-\\d{4}-\\d{5}$";
        return cveId.matches(format1) || cveId.matches(format2);
    }
}
