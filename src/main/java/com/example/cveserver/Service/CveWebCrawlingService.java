package com.example.cveserver.Service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CveWebCrawlingService {

    private final static String baseUrl = "https://github.com";
    private final List<String> cveList = new ArrayList<>();

    @Transactional
    public void YearCrawler() throws IOException {

        String url = "https://github.com/CVEProject/cvelist";

        Document doc = Jsoup.connect(url).get();

        Elements yearLinks = doc.select(".react-directory-row-name-cell-large-screen a.Link--primary");

        for (Element link : yearLinks) {
            String year = link.attr("aria-label").replaceAll("[^0-9]", "");
            if (!year.isEmpty()) {
                String cveYearUrl = baseUrl + link.attr("href");

                log.info(cveYearUrl);

                SerialNumberCrawler(cveYearUrl);
            }
        }
    }

    @Transactional
    public void SerialNumberCrawler(String cveYearUrl) {

        ChromeOptions options = ChromeSetup();

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(cveYearUrl);

            List<WebElement> serialNumberLinks = driver.findElements(By.cssSelector("td.react-directory-row-name-cell-large-screen .react-directory-filename-column .react-directory-truncate a.Link--primary"));

            for (WebElement link : serialNumberLinks) {
                try {
                    String serialNumberUrl = link.getAttribute("href");
                    CveIdCrawler(serialNumberUrl, driver);

                } catch (StaleElementReferenceException e) {
                    continue;
                }
            }
        } finally {
            driver.quit();
        }
    }
    //TODO. CVE_URL이 하나만 걸리고 다른 하나는 예외처리때문에 빠져나가서 안읽힘
    @Transactional
    public void CveIdCrawler(String serialNumberUrl, WebDriver driver) {
        try {
            driver.get(serialNumberUrl);
            List<WebElement> cveIdLinks = driver.findElements(By.cssSelector("td.react-directory-row-name-cell-large-screen .react-directory-filename-column a.Link--primary"));

            List<WebElement> selectedLinks = cveIdLinks.subList(0, Math.min(cveIdLinks.size(), 5)); // TEST 5개만 실행

            //TEST용도
            for (WebElement link : selectedLinks) {
                try {
                    String cveId = link.getAttribute("title").replaceAll(".json", "");
                    cveList.add(cveId);

                }catch (StaleElementReferenceException e){
                    continue;
                }
            }

            //실제용
//            for (WebElement link : cveIdLinks) {
//                try {
//                    String cveId = link.getAttribute("title").replaceAll(".json", "");
//                    cveList.add(cveId);
//
//                }catch (StaleElementReferenceException e){
//                    continue;
//                }
//            }
        } catch (Exception e){
            log.error("Error while crawling CVE IDs", e);
        }

    }

//    public void CveFileCvs(List<String> cveList) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet cveListSheet = workbook.createSheet("cveList");
//
//        // 첫 번째 행에 헤더 추가
//        Row headerRow = cveListSheet.createRow(0);
////        for (int i = 0; i < cveList.size(); i++) {
////            Cell cell = headerRow.createCell(i);
////            cell.setCellValue(cveList.get(0).split("-")[1]);
////        }
//
//        Cell rowCell = headerRow.createCell(0);
//        rowCell.setCellValue(cveList.get(0).split("-")[1]);
//
//
//        // 데이터 행 추가
//        for (int rowIndex = 1; rowIndex <= cveList.size(); rowIndex++) {
//            Row row = cveListSheet.createRow(rowIndex);
//            row.createCell(0).setCellValue(cveList.get(rowIndex - 1));
//        }
//
//        // 스타일 적용 (예: 헤더 스타일)
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerCellStyle.setFont(headerFont);
//        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        for (Cell cell : headerRow) {
//            cell.setCellStyle(headerCellStyle);
//        }
//
//        // 엑셀 파일로 저장
//        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/cveList.xlsx")) {
//            workbook.write(fileOut);
//        }
//
//        // 메모리 해제
//        workbook.close();
//    }

    @Transactional
    public ChromeOptions ChromeSetup() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        return options;
    }


}
