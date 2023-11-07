package ca.ttms.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
@RequiredArgsConstructor
public class JasperService {

	public byte[] generateReport (Map<String, Object> parameters, String reportPath) throws IOException, JRException {
		InputStream reportStream = new ClassPathResource(reportPath).getInputStream();
		JasperPrint mainPrint = JasperFillManager.fillReport(reportStream, parameters, new JREmptyDataSource());
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(mainPrint);
		return pdfBytes;
	}
}
