package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOArchivoAdjunto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOEmail;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOReporte;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class EnviarPresupuestoDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Fachada fachada= new Fachada();

		//generar reporte pdf
		
		//setear valores value object
		VOReporte voReporteParametros= new VOReporte(); 
		voReporteParametros.setNombrePresupuesto((String)execution.getVariable("COTIZACION"));
		voReporteParametros.setCliente((String)execution.getVariable("CLIENTE"));
		voReporteParametros.setEmail((String)execution.getVariable("EMAIL"));
		voReporteParametros.setTel((String)execution.getVariable("TEL"));
		voReporteParametros.setMoneda((String)execution.getVariable("moneda"));
		voReporteParametros.setPrecio((String)execution.getVariable("PRECIO"));
		voReporteParametros.setDescripcion((String)execution.getVariable("DESCRIPCION"));
		voReporteParametros.setDimensiones((String)execution.getVariable("DIMENSIONES"));
		voReporteParametros.setCondiciones((String)execution.getVariable("CONDICIONES"));
		voReporteParametros.setFormaDePago((String)execution.getVariable("FORMA_DE_PAGO"));
		voReporteParametros.setTiempoDeEntrega((String)execution.getVariable("TIEMPO_DE_ENTREGA"));
		voReporteParametros.setDescuento((String)execution.getVariable("DESCUENTO"));
		voReporteParametros.setSobreCosto((String)execution.getVariable("SOBRECOSTO"));
		voReporteParametros.setPrecioFinal((String)execution.getVariable("PRECIO_FINAL"));

		fachada.generarReporte(voReporteParametros);
		
		//enviar presupuesto por email
		
		Properties p = new Properties();
		p.load(new FileInputStream("config/parametros.txt"));
		String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");
						
		String nombreArchivoAdjunto="Cotizacion_PRODUCTO_" + voReporteParametros.getNombrePresupuesto() + "_" + voReporteParametros.getCliente().replace(' ' , '_') +".pdf" ;
		
		VOArchivoAdjunto arch1 = new VOArchivoAdjunto();
		arch1.setRutaArchivoAdjunto(rutaArchivoAdjunto);
		arch1.setNombreArchivoAdjunto(nombreArchivoAdjunto);
		
		// ArrayList de archivos adjuntos (reporte pdf, cronograma pdf)
		ArrayList<VOArchivoAdjunto> lstArchivosAdjuntos = new ArrayList<VOArchivoAdjunto>();
		lstArchivosAdjuntos.add(arch1);
		

		VOEmail voEmail = new VOEmail();
		voEmail.setDestinatario(voReporteParametros.getEmail());
		voEmail.setAsunto("Correo de prueba enviado desde proceso en camunda mediante Java");
		voEmail.setCuerpo(
				"Esta es una prueba de correo, y si lo estas viendo que es que quedó resuelto como mandar mails desde camunda...");
		voEmail.setLstArchivosAdjuntos(lstArchivosAdjuntos);

		Fachada f = new Fachada();
		f.enviarConGmail(voEmail);
		
		
//		HashMap parametros = new HashMap<String, Object>();
//		parametros.put("cotizacion", voReporteParametros.getNombrePresupuesto());
//		//fecha?
//		parametros.put("cliente", voReporteParametros.getCliente());
//		parametros.put("email", voReporteParametros.getEmail());
//		parametros.put("tel", voReporteParametros.getTel());
//		parametros.put("descripcion", voReporteParametros.getDescripcion());
//		parametros.put("moneda", voReporteParametros.getMoneda());
//		parametros.put("costo", voReporteParametros.getPrecio());
//		parametros.put("condiciones", voReporteParametros.getCondiciones());
//		parametros.put("forma_de_pago", voReporteParametros.getFormaDePago());
//		parametros.put("tiempo_de_entrega", voReporteParametros.getTiempoDeEntrega());
//		
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream("reportes//jasper//presupuestoProductoMN.jasper");
//			BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
//	 
//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream); 
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,new JREmptyDataSource());
//
//			Properties p = new Properties();
//			p.load(new FileInputStream("config/parametros.txt"));
//			String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");
//
//			// Se crea el archivo pdf con el nombre:
//			// Ejemplo: Cotizacion_ESPACIO_180926-01_Fernando_Pelaez.pdf
//			String cotizacion=(String)parametros.get("cotizacion");
//			String cliente=(String)parametros.get("cliente");
//			String nombreArchivoAdjunto="Cotizacion_PRODUCTO_" + cotizacion + "_" + cliente.replace(' ' , '_') +".pdf" ;
//
//			JasperExportManager.exportReportToPdfFile(jasperPrint,rutaArchivoAdjunto + nombreArchivoAdjunto);
//			
//		} catch (FileNotFoundException | JRException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
