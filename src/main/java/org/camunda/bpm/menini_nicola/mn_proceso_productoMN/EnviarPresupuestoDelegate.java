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
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOProductoMN;
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
		voReporteParametros.setNombreProducto((String)execution.getVariable("PRODUCTO_SELECCIONADO"));
		voReporteParametros.setDescripcion((String)execution.getVariable("DESCRIPCION"));
		voReporteParametros.setDimensiones((String)execution.getVariable("DIMENSIONES"));
		voReporteParametros.setCondiciones((String)execution.getVariable("CONDICIONES"));
		voReporteParametros.setFormaDePago((String)execution.getVariable("FORMA_DE_PAGO"));
		voReporteParametros.setTiempoDeEntrega((String)execution.getVariable("TIEMPO_DE_ENTREGA"));
		voReporteParametros.setDescuento((String)execution.getVariable("DESCUENTO"));
		voReporteParametros.setSobreCosto((String)execution.getVariable("SOBRECOSTO"));
		voReporteParametros.setPrecioFinal((String)execution.getVariable("PRECIO_FINAL"));
		voReporteParametros.setUnidades((String)execution.getVariable("UNIDADES"));
		
		// obtengo materiales y terminacion
		VOProductoMN productoMN= new VOProductoMN();
		productoMN= ScrappingWeb.obtenerProducto((String)execution.getVariable("PRODUCTO_SELECCIONADO"));
	
		String materiales = productoMN.getMateriales();
				
		voReporteParametros.setMateriales(materiales);
		voReporteParametros.setTerminacion("");

		// url de la imagen del producto
		String urlImagen = productoMN.getUrlImagen();
		voReporteParametros.setUrlImagen(urlImagen);
		
		fachada.generarReporte(voReporteParametros);
		
		//enviar presupuesto por email
		
		Properties p = new Properties();
		p.load(new FileInputStream("config/parametros.txt"));
		String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");
						
		String nombreArchivoAdjunto="Cotizacion_PRODUCTO_" + voReporteParametros.getNombrePresupuesto() + "_" + voReporteParametros.getCliente().replace(' ' , '_') +".pdf" ;
		
		VOArchivoAdjunto arch1 = new VOArchivoAdjunto();
		arch1.setRutaArchivoAdjunto(rutaArchivoAdjunto);
		arch1.setNombreArchivoAdjunto(nombreArchivoAdjunto);
		
		execution.setVariable("rutaReportePDF",rutaArchivoAdjunto);	
		execution.setVariable("nombreReportePDF",nombreArchivoAdjunto);		
		
		// ArrayList de archivos adjuntos (reporte pdf, cronograma pdf)
		ArrayList<VOArchivoAdjunto> lstArchivosAdjuntos = new ArrayList<VOArchivoAdjunto>();
		lstArchivosAdjuntos.add(arch1);
		

		VOEmail voEmail = new VOEmail();
		voEmail.setDestinatario(voReporteParametros.getEmail());
		voEmail.setAsunto("Correo de prueba enviado desde proceso en camunda mediante Java");
		voEmail.setCuerpo("Estimado " + voReporteParametros.getCliente() +
				":\n\n Esta es una prueba de correo, y si lo estas viendo que es que quedó resuelto como mandar mails desde camunda...");
		voEmail.setLstArchivosAdjuntos(lstArchivosAdjuntos);

		Fachada f = new Fachada();
		f.enviarConGmail(voEmail);
		
		

	}

}
