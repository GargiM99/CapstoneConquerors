package ca.ttms.controllers;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {
	@GetMapping("/generate-report")
    public ResponseEntity<byte[]> generateReport(HttpServletResponse response) {
        try {
        	// Load and compile the JasperReports templates
            InputStream mainReportStream = new ClassPathResource("ttms_schedule.jasper").getInputStream();
            InputStream subReportStream = new ClassPathResource("subreport_event_details.jasper").getInputStream();

            // Create sample values for tripDetails and eventDetails
            Map<String, Object> tripDetails = new HashMap<>();
            tripDetails.put("tripType", "Sample Trip");
            tripDetails.put("tripStartDate", "2023-10-25");
            tripDetails.put("tripEndDate", "2023-10-30");
            
            // For eventDetails, you can create a list of maps
            Map<String, Object> event1 = new HashMap<>();
            event1.put("eventName", "Event 1");
            event1.put("eventDate", "2023-10-26");
            event1.put("eventDescription", "Description for Event 1");

            Map<String, Object> event2 = new HashMap<>();
            event2.put("eventName", "Event 2");
            event2.put("eventDate", "2023-10-28");
            event2.put("eventDescription", "Description for Event 2");
            
            // Create parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tripDetails", tripDetails);
            parameters.put("eventDetails", new Object[]{event1, event2});

            // Generate the main report
            JasperPrint mainPrint = JasperFillManager.fillReport(mainReportStream, parameters, new JREmptyDataSource());

            // Export the report to a PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(mainPrint);
            
            // Set the response headers for PDF download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "event_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
