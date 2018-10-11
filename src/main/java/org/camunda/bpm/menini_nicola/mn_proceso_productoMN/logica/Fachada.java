package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia.AccesoBD;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCategoria;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOPrecio;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOReporte;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Fachada{
	
	//private AccesoBD accesoBD;
	
	public void insertarCategoria(VOCategoria categoria)
	{
		String nombre= categoria.getNombre();
		AccesoBD accesoBD = new AccesoBD();

		accesoBD.insertarCategoria(nombre);
	}

	public int  cantidadRegistrosCategoria()
	{
		int cantidadRegistrosCategoria=0;
		AccesoBD accesoBD = new AccesoBD();
		cantidadRegistrosCategoria= accesoBD.cantidadRegistrosCategoria();
		
		return cantidadRegistrosCategoria;
	}
	
	public String textoCondiciones()
	{
		String texto="";
	
		texto=  "El diseño de TODOS los productos es propiedad exclusiva del estudio menini nicola."+
				"Esta cotizacizón tiene una validez de 30 días corridos."+
				"Los precios incluyen IVA y transporte dentro de los límites de Montevideo. "+
				"En caso de que la entrega sea fuera de la ciudad o si la entrada de los productos deba"+
				"realizarse por fuera de la vivienda, se cotizará aparte."+
				"En caso de que el cliente solicite cambio en los materiales y/o terminaciones detallados en el producto"+
				"se deberá recotizar el mismo."+
				" Si los cambios solicitados por el cliente, implican modificaciones en el diseño del producto,"+
				"se deberá trabajar como un proyecto de desarrollo para ese producto.";
				
		return texto;
	}
	
	public String textoFormaDePago()
	{
		String texto= "Seña y saldo (Débito de VISA, MasterCard, o American Express,"+
				" efectivo, cheque al día, transferencia bancaria o depósito BROU)"+
				" Seña del 40% - Saldo del 60% contra entrega"+
				"Opción 2: Pago total con tarjeta de crédito VISA, MasterCard o "+
				"American Express hasta en 3."+
				"Opción 3: Comprar directamente en MercadoLibre en las cuotas "+
				"que el cliente desee. En esta modalidad no se realizan cambios "+
				"de dimensiones, ni materiales ni otros detalles del producto.";
		
		return texto;
	}
	
	public String textoTiempoDeEntrega()
	{
		String texto="El tiempo estimado de entrega es de 35-45 días corridos luego "+
					 "del pago del adelanto, pudiendo existir variaciones dependiendo "+
					 "de las cantidades solicitadas o del cronograma productivo a la fecha.";
			
		return texto;
	}

	public String generarNroCotizacionFechaActual() {
		// A partir de la fecha actual genera un nro de cotizacion
		// Si ya hay una cotizcion para el dia actual incremente el digito del indice
		// Ejemplo: Si ya existe la cotizacion 180924-01,la siguiente sera 180924-02 
		
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Calendar cal = Calendar.getInstance();
		
		// Obtengo nro de cotizacion del dia actual
		String fecha = dateFormat.format(cal.getTime());
		String nroCotizacion = fecha  + "-01";
		int cont=1;
		AccesoBD accesoBD = new AccesoBD();
		while (accesoBD.existeNroCotizacion(nroCotizacion)) {
			cont++;
			nroCotizacion = fecha + "-" + String.format("%02d",cont);
		}		
		return nroCotizacion;
	}
	
	public Double calcularPrecioVentaFinal(VOPrecio voPrecio)
	{
		Double precio= voPrecio.getPrecio();
		Double precioFinal= precio;
		
		Double descuento= voPrecio.getDescuento();
		Double sobrecosto= voPrecio.getSobrecosto();
		
		
		if(descuento !=0)
		{
			descuento= (descuento * precio)/100;
			precioFinal= precio - descuento;
		}
		else if(sobrecosto != 0)
		{
			sobrecosto= (sobrecosto * precio)/100;
			precioFinal= precio + sobrecosto;
		}
		
		return precioFinal;
	}
	
	public void generarReporte(VOReporte voReporteParametros)
	{
		HashMap parametros = new HashMap<String, Object>();
		parametros.put("cotizacion", voReporteParametros.getNombrePresupuesto());
		//fecha?
		parametros.put("cliente", voReporteParametros.getCliente());
		parametros.put("email", voReporteParametros.getEmail());
		parametros.put("tel", voReporteParametros.getTel());
		parametros.put("descripcion", voReporteParametros.getDescripcion());
		parametros.put("moneda", voReporteParametros.getMoneda());
		parametros.put("costo", voReporteParametros.getPrecio());
		parametros.put("condiciones", voReporteParametros.getCondiciones());
		parametros.put("forma_de_pago", voReporteParametros.getFormaDePago());
		parametros.put("tiempo_de_entrega", voReporteParametros.getTiempoDeEntrega());
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("reportes//jasper//presupuestoProductoMN.jasper");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
	 
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream); 
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,new JREmptyDataSource());

			Properties p = new Properties();
			p.load(new FileInputStream("config/parametros.txt"));
			String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");

			// Se crae el archivo pd con el nombre:
			// Ejemplo: Cotizacion_ESPACIO_180926-01_Fernando_Pelaez.pdf
			String cotizacion=(String)parametros.get("cotizacion");
			String cliente=(String)parametros.get("cliente");
			String nombreArchivoAdjunto="Cotizacion_PRODUCTO_" + cotizacion + "_" + cliente.replace(' ' , '_') +".pdf" ;

			JasperExportManager.exportReportToPdfFile(jasperPrint,rutaArchivoAdjunto + nombreArchivoAdjunto);
			
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
