package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class TestReporteJasper {

	public static void main(String[] args) throws IOException {
		// Creación de presupuesto espacio
		HashMap parametros = new HashMap<String, Object>();
		parametros.put("cotizacion", "180920-01");
		parametros.put("cliente", "Maria Valenzuela");
		parametros.put("email", "mariavelenzuela@gmail.com");
		parametros.put("descripcion", "bla bla bla");
		parametros.put("moneda", "US$");
		float costo = 150;
		parametros.put("costo", costo);
		parametros.put("condiciones", "30 dias");
		
		FileInputStream fis;
		
		try {
			fis = new FileInputStream("reportes//jasper//presupuestoProductoMN.jasper");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
			
			//Load bufferedInputStream file.jasper 
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream); 
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,new JREmptyDataSource());
			
			Properties p = new Properties();
			p.load(new FileInputStream("config/parametros.txt"));
			String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");
					
			// Se crae el archivo pd con el nombre:
			// Ejemplo: Cotizacion_ESPACIO_180926-01_Fernando_Pelaez.pdf
			String cotizacion="180926-01";
			String cliente="Fernando Pelaez";
			String nombreArchivoAdjunto="Cotizacion_ESPACIO_" + cotizacion + "_" + cliente.replace(' ' , '_') +".pdf" ;

			//String rutaArchivoAdjunto = "//home//danielo//";
			//JasperExportManager.exportReportToPdfFile(jasperPrint,"//home//danielo//presupuestoEspacio.pdf");
			//JasperExportManager.exportReportToPdfFile(jasperPrint,rutaArchivoAdjunto + nombreArchivoAdjunto);
			JasperExportManager.exportReportToPdfFile(jasperPrint,rutaArchivoAdjunto + nombreArchivoAdjunto);
			//JasperViewer.viewReport(jasperPrint, false);
			
			//String destinatario = destinatarioIn;
			//String asunto ="Correo de prueba enviado desde proceso en camunda mediante Java";
			//String cuerpo = "Esta es una prueba de correo, y si lo estas viendo que es que quedó resuelto como mandar mails desde camunda...";
			//enviarConGmail(destinatario, asunto, cuerpo,rutaArchivoAdjunto,nombreArchivoAdjunto);
			
			
			
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		}
	}

}
