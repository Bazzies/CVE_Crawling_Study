package com.example.cveservertest.Controller;

import com.example.cveservertest.Service.CVEService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CVEController {

    private final CVEService cveService;

    @GetMapping("/cve/find")
    public void saveCVE(@RequestParam List<String> cveId){
        cveService.getCve(cveId);
    }
}
