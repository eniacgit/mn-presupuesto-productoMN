package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia.AccesoBD;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia.AccesoBD_conException;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOArchivoAdjunto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCategoria;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOEmail;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOPrecio;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOPresupuesto;
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
	
	public void insertarCliente(VOCliente cliente) throws SQLException, IOException {
		AccesoBD accesoBD = new AccesoBD();
		String nombre = cliente.getNombre();
		String email = cliente.getEmail();
		String telefono = cliente.getTelefono();
		String celular = cliente.getCelular();
		String rut = cliente.getRut();
		String razonSocial= cliente.getRazonSocial();
		String tipo= cliente.getTipo();
		String direccion= cliente.getDireccion();
		accesoBD.insertarCliente(nombre, email, telefono, celular,rut,razonSocial, tipo,direccion);
	}
	
	public boolean existeCliente(String nombre) {
		AccesoBD accesoBD = new AccesoBD();
		return accesoBD.existeCliente(nombre);
	}
	
	public int obtenerIdCliente(String nombre) {
		AccesoBD accesoBD = new AccesoBD();
		return accesoBD.obtenerIdCliente(nombre);
	}
	
	public void insertarPresupuesto(VOPresupuesto presupuesto) throws SQLException, IOException {
		AccesoBD accesoBD = new AccesoBD();
		String cotizacion = presupuesto.getCotizacion();
		String fecha = presupuesto.getFecha();
		String moneda = presupuesto.getMoneda();
		Double costo = presupuesto.getCosto();
		String condicionesVenta = presupuesto.getCondicionesVenta();
		String descripcion = presupuesto.getDescripcion();
		Integer unidades = presupuesto.getUnidades();
		accesoBD.insertarPresupuesto(cotizacion, fecha, moneda, costo,condicionesVenta, descripcion, unidades);
	}
	
	public int obtenerIdPresupuesto(String cotizacion) {
		AccesoBD accesoBD = new AccesoBD();
		return accesoBD.obtenerIdPresupuesto(cotizacion);
	}
	
	public void insertarClientePresupuesto(VOClientePresupuesto clientePresupuesto) throws  SQLException, IOException {
		int idClientePresupuesto = clientePresupuesto.getIdClientePresupuesto();
		byte estado = clientePresupuesto.getEstado();
		int idCliente = clientePresupuesto.getIdCliente();
		int idPresupuesto = clientePresupuesto.getIdPresupuesto();
		
		AccesoBD accesoBD = new AccesoBD();
		accesoBD.insertarClientePresupuesto(estado, idCliente, idPresupuesto);
	}
	
	public void insertarCategoria(VOCategoria categoria)
	{
		String nombre= categoria.getNombre();
		AccesoBD_conException accesoBD = new AccesoBD_conException();

		accesoBD.insertarCategoria(nombre);
	}

	public int  cantidadRegistrosCategoria()
	{
		int cantidadRegistrosCategoria=0;
		AccesoBD_conException accesoBD = new AccesoBD_conException();
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
		AccesoBD_conException accesoBD = new AccesoBD_conException();
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
		Integer unidades= voPrecio.getUnidades();
	
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
		
		return precioFinal * unidades;
	}
	
	public void generarReporte(VOReporte voReporteParametros)
	{
		HashMap parametros = new HashMap<String, Object>();
		parametros.put("cotizacion", voReporteParametros.getNombrePresupuesto());
		//fecha, no es necesario, se genera en el jasper
		parametros.put("cliente", voReporteParametros.getCliente());
		parametros.put("email", voReporteParametros.getEmail());
		parametros.put("tel", voReporteParametros.getTel());
		// agregar imagen (completar)
		parametros.put("nombre",voReporteParametros.getNombreProducto());
		parametros.put("dimensiones",voReporteParametros.getDimensiones());
		parametros.put("materiales",voReporteParametros.getMateriales());
		parametros.put("terminacion",voReporteParametros.getTerminacion());
		
		String moneda ="";
		if (voReporteParametros.getMoneda().equals("dolares"))
			moneda = "USD";
		else
			moneda = "$U";
		
		parametros.put("precio","Precio unitario ("+ moneda +"): "  + voReporteParametros.getPrecio());
		parametros.put("unidades","Unidades: "  + voReporteParametros.getUnidades());
		parametros.put("descuento", "Descuento (%): " + voReporteParametros.getDescuento());
		parametros.put("sobrecosto", "Sobre costo (%): " + voReporteParametros.getSobreCosto());
		parametros.put("precioFinal", "Precio final (" + moneda + "): " + voReporteParametros.getPrecioFinal());
		
		
		parametros.put("condiciones", voReporteParametros.getCondiciones());
		parametros.put("formaPago", voReporteParametros.getFormaDePago());
		
		
		
		// En el codigo de bpmn hay que setear lo siguiente:
		// <imageExpression><![CDATA[new java.net.URL($P{url})]]></imageExpression>
		// más info: https://coderanch.com/t/642610/java/Loading-byte-data-image-url
		parametros.put("urlimgProd", voReporteParametros.getUrlImagen());
		
		//parametros.put("tiempo_de_entrega", voReporteParametros.getTiempoDeEntrega());
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("reportes//jasper//presupuestoProductoMN.jasper");
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
	 
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream); 
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parametros,new JREmptyDataSource());

			Properties p = new Properties();
			p.load(new FileInputStream("config/parametros.txt"));
			String rutaArchivoAdjunto = p.getProperty("carpeta_reportes");

			// Se crea el archivo pdf con el nombre:
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
	
	private static void addAtachment(Multipart multipart, String rutaArcvhivo, String nombreArchivo) throws MessagingException {
		DataSource source = new FileDataSource(rutaArcvhivo+nombreArchivo);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(nombreArchivo);
		multipart.addBodyPart(messageBodyPart);
	}
	
	public void enviarConGmail (VOEmail voEmail) throws MessagingException {
	// Envia un correo electronico con archivos adjuntos (ArrayList) utilizando el email y contrasenia
	// almacenados en la tabla mn_email
		//String remitente = voEmail.getRemitente(); // este campo viene vacio
		String destinatario = voEmail.getDestinatario();
		String asunto = voEmail.getAsunto();
		String cuerpo = voEmail.getCuerpo();
		ArrayList<VOArchivoAdjunto> lstArchivosAdjuntos = voEmail.getLstArchivosAdjuntos();
		
		AccesoBD accesoBD = new AccesoBD();
		String email = accesoBD.obtenerRemitente();
		String [] arrayEmail = email.split("@");
		String remitente = arrayEmail[0];
		String clave = accesoBD.obtenerPasswordRemitente();
		
		
		// Se obtiene el objeto Session. La configuración es para una cuenta de gmail
		Properties props = new Properties();		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", "remitente");
		props.put("mail.smtp.clave", clave);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		message.addRecipients(Message.RecipientType.TO, destinatario);
		message.setSubject(asunto);
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(cuerpo);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		
		// obtengo archivos adjuntos de la lista
		for (int i=0; i<lstArchivosAdjuntos.size();i++) {
			String rutaArchivo = lstArchivosAdjuntos.get(i).getRutaArchivoAdjunto();
			String nombreArchivo = lstArchivosAdjuntos.get(i).getNombreArchivoAdjunto();
			addAtachment(multipart, rutaArchivo, nombreArchivo);
		}
		message.setContent(multipart);
		
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", remitente, clave);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();		
	}
	
	public void insertarCategoriaProducto(String categoria) throws  SQLException, IOException {
		AccesoBD accesoBD = new AccesoBD();
		accesoBD.insertarCategoriaProducto(categoria);
	}
	
	public boolean existeCategoria(String categoria) {
		AccesoBD accesoBD = new AccesoBD();
		return accesoBD.existeCategoria(categoria);
	}
	
	public int obtenerUltimoIndiceInsertadoCategoria() {
		AccesoBD accesoBD = new AccesoBD();
		return accesoBD.obtenerUltimoIndiceInsertadoCategoria();
	}
	
	public void insertarProducto(String nombre, String descripcion, double costo, double descuento, double sobreCosto,int tipo, int idCategoria, int idClientePresupuesto) throws  SQLException, IOException {
		AccesoBD accesoBD = new AccesoBD();
		accesoBD.insertarProducto(nombre, descripcion, costo, descuento, sobreCosto, tipo, idCategoria, idClientePresupuesto);
	}
	
	public int obtenerIdCategoria(String categoria) {
		AccesoBD accesoBD = new AccesoBD();
		return accesoBD.obtenerIdCategoria(categoria);
	}
	
}
