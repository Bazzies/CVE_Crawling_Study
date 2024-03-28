package com.example.cveservertest.Controller;

import com.example.cveservertest.Entity.Cve;
import com.example.cveservertest.Service.CveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CVEController {

    private final CveService cveService;

    @GetMapping("/cve/find")
    public List<Cve> saveCVE(@RequestParam List<String> cveId){
        return cveService.getCve(cveId);
    }
}
