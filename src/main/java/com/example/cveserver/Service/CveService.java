package com.example.cveserver.Service;

import com.example.cveserver.DTO.CveServiceDTO;
import com.example.cveserver.Entity.Cve;
import com.example.cveserver.Repository.CVERepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CveService {
    private final CVERepository cveRepository;

    public List<Cve> getCve(List<String> cveIds) {
        //정규 표현식에 맞는지 확인
        List<String> validCveIds = filterValidCveIds(cveIds);

        List<Cve> existingCves = findExistingCves(validCveIds);

        Set<String> existingCveIds = existingCves.stream()
                .map(Cve::getCveId)
                .collect(Collectors.toSet());

        List<String> newCveIds = findNewCveIds(validCveIds, existingCveIds);

        saveNewCves(newCveIds);

        List<Cve> allCves = new ArrayList<>(existingCves);
        allCves.addAll(findExistingCves(newCveIds)); // 새로 추가된 CVE를 조회하여 추가

        printCveDetails(allCves); // 모든 CVE 출력
        return allCves;
        //printCveDetails(existingCves);
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
            CveServiceDTO newCveServiceDTO = new CveServiceDTO(cveId, "https://nvd.nist.gov/vuln/detail/" + cveId);
            cveRepository.save(newCveServiceDTO.convertToCveEntity());
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