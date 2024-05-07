package com.example.cveserver.Controller;

import com.example.cveserver.Entity.Cve;
import com.example.cveserver.Service.CveService;
import com.example.cveserver.Service.CveWebCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CVEController {

    private final CveService cveService;
    private final CveWebCrawlingService cveWebCrawlingService;

    @GetMapping("/cve/find")
    public List<Cve> saveCVE(@RequestParam List<String> cveId){
        return cveService.getCve(cveId);
    }

    @PostMapping("/cve/test")
    public void CrawlingTest() throws IOException {
        cveWebCrawlingService.YearCrawler();
    }
}
