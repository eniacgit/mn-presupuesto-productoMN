package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOPresupuesto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOReporte;

public class PersistirPresupuestoDelegate implements JavaDelegate {
	
	private final static Logger LOG = Logger.getLogger(PersistirPresupuestoDelegate.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Fachada fachada = new Fachada();
		
		//traer valores del formulario
		
		String cotizacion= (String) execution.getVariable("COTIZACION");
		String cliente= (String) execution.getVariable("CLIENTE");
		String email= (String) execution.getVariable("EMAIL");
		String tel= (String)execution.getVariable("TEL");
		String celular= (String)execution.getVariable("CELULAR");
		String moneda= (String) execution.getVariable("moneda");
		String precio=(String)execution.getVariable("PRECIO");
		String descripcion= (String) execution.getVariable("DESCRIPCION");
		String dimensiones= (String) execution.getVariable("DIMENSIONES");
		String condiciones= (String) execution.getVariable("CONDICIONES");
		String formaDePago= (String) execution.getVariable("FORMA_DE_PAGO");
		String tiempoDeEntrega= (String) execution.getVariable("TIEMPO_DE_ENTREGA");
		String descuento= (String) execution.getVariable("DESCUENTO");
		String sobrecosto= (String) execution.getVariable("SOBRECOSTO");
		String precioFinal= (String) execution.getVariable("PRECIO_FINAL");
		
		//persistir datos del cliente
		
		VOCliente voCliente = new VOCliente();
		voCliente.setNombre(cliente);
		voCliente.setEmail(email);
		voCliente.setTelefono(tel);
		voCliente.setCelular(celular);
	 		 
		 if (!fachada.existeCliente(cliente)) {
			 fachada.insertarCliente(voCliente);
		 }
		 
		 //persistir datos del presupuesto
		 
		 if(moneda.equals("dolares"))
			 moneda="USD";
		 else
			 moneda="$U";		 
		 //estado: 1-aprobado, 0-no aprobado
		 byte estado= 1;
		 //formatear fecha
		 DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		 Date date= new Date();
		 String fecha= dateFormat.format(date);
		 
		 VOPresupuesto voPresupuesto= new VOPresupuesto();
		 voPresupuesto.setCotizacion(cotizacion);
		 voPresupuesto.setFecha(fecha);
		 voPresupuesto.setMoneda(moneda);
		 voPresupuesto.setCondicionesVenta(condiciones);
		 voPresupuesto.setDescripcion(descripcion);
		 
		 fachada.insertarPresupuesto(voPresupuesto);
		 
		 //persistir datos del cliente-presupuesto
		 
		 //obtener Ids de inserts
		 int idPresupuesto= fachada.obtenerIdPresupuesto(voPresupuesto.getCotizacion());
		 int idCliente= fachada.obtenerIdCliente(voCliente.getNombre());
		 
		 VOClientePresupuesto voClientePresupuesto= new VOClientePresupuesto();
		 voClientePresupuesto.setEstado(estado);
		 voClientePresupuesto.setIdCliente(idCliente);
		 voClientePresupuesto.setIdPresupuesto(idPresupuesto);
		 
		 fachada.insertarClientePresupuesto(voClientePresupuesto);
		 

		 // persitir categoria
		 String categoria = (String)execution.getVariable("CATEGORIA_PRODUCTO");
		 // LOG.info("\n## CATEGORIA: " + categoria);
		 if (!fachada.existeCategoria(categoria)) {
			 fachada.insertarCategoriaProducto(categoria);
		 }		 
	 
		 //persistir datos del producto		 
		 String nombreProducto= (String) execution.getVariable("PRODUCTO_SELECCIONADO");
		 int idCategoria = fachada.obtenerIdCategoria(categoria);
		 
		 fachada.insertarProducto(nombreProducto, descripcion, Double.parseDouble(precio), Double.parseDouble(descuento), Double.parseDouble(sobrecosto), idCategoria, idPresupuesto);
		 
		 
		 
		 
		 
		 
		// LOG.info("\n## NOMBRE DEL PRODUCTO: " + nombreProducto);
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	}

}
