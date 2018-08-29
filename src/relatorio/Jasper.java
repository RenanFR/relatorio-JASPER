package relatorio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class Jasper {
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/vetweb_database", "postgres", "postgres");
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}	
	
	public static void main(String[] args) {
		try {
			Connection connection = getConnection();
			String path = "/home/renanfr/dev/iReport-5.6.0/meus_relatorios/Ocorrencia.jrxml";
			JasperCompileManager.compileReportToFile(path);
			String jasper = "/home/renanfr/dev/iReport-5.6.0/meus_relatorios/Ocorrencia.jasper";
			Map<String, Object> map = new HashMap<>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, map, connection);
			JRExporter jrExporter = new JRPdfExporter();
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("/home/renanfr/dev/iReport-5.6.0/meus_relatorios/Ocorrencia.pdf"));
			jrExporter.exportReport();
			connection.close();
		} catch (JRException e) {
			System.out.println("JRException " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
	}
	
}
