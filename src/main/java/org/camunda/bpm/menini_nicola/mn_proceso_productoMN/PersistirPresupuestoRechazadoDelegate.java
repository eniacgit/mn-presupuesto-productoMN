package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.modelo.Cliente;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCliente;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOClientePresupuesto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOPresupuesto;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOReporte;

public class PersistirPresupuestoRechazadoDelegate implements JavaDelegate {
	
	private final static Logger LOG = Logger.getLogger(PersistirPresupuestoRechazadoDelegate.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Fachada fachada = new Fachada();
		//traer datos del cliente		
		Cliente cliente= new Cliente();
		cliente = (Cliente)execution.getVariable("cliente");
		
		//traer datos del producto
		String moneda= (String) execution.getVariable("moneda");
		String precio=(String)execution.getVariable("PRECIO");
		String descuento= (String) execution.getVariable("DESCUENTO");
		String sobrecosto= (String) execution.getVariable("SOBRECOSTO");
			
		//persistir datos del cliente
		VOCliente voCliente = new VOCliente();
		voCliente.setNombre(cliente.getNombre());
		voCliente.setEmail(cliente.getEmail());
		voCliente.setTelefono(cliente.getTelefono());
		voCliente.setCelular(cliente.getCelular());
		voCliente.setRut(cliente.getRut());
		voCliente.setRazonSocial(cliente.getRazonSocial());
		voCliente.setTipo(cliente.getTipo());
		voCliente.setDireccion(cliente.getDireccion());	 
		
		 if (!fachada.existeCliente(cliente.getNombre())) {
			 fachada.insertarCliente(voCliente);
		 }
		 
		//traer datos del presupuesto
		String cotizacion= (String) execution.getVariable("COTIZACION");
		String condiciones= (String) execution.getVariable("CONDICIONES");
		String descripcion= (String) execution.getVariable("DESCRIPCION");
		String precioFinal= (String) execution.getVariable("PRECIO_FINAL");
		String unidades= (String) execution.getVariable("UNIDADES");
		
		if(moneda.equals("dolares"))
		 moneda="USD";
		else
		 moneda="$U";		 
		//estado: 0-rechazado, 1-aprobado
		byte estado= 0;
		//formatear fecha
		DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
		String fecha= dateFormat.format(date);
		
		//persistir datos del presupuesto 
		VOPresupuesto voPresupuesto= new VOPresupuesto();
		voPresupuesto.setCotizacion(cotizacion);
		voPresupuesto.setFecha(fecha);
		voPresupuesto.setMoneda(moneda);
		voPresupuesto.setCosto(Double.parseDouble(precioFinal));
		voPresupuesto.setCondicionesVenta(condiciones);
		voPresupuesto.setDescripcion(descripcion);
		voPresupuesto.setUnidades(Integer.parseInt(unidades));
		fachada.insertarPresupuesto(voPresupuesto);
		 
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
		if (!fachada.existeCategoria(categoria)) {
		 fachada.insertarCategoriaProducto(categoria);
		}		 
	 
		//persistir datos del producto		 
		String nombreProducto= (String) execution.getVariable("PRODUCTO_SELECCIONADO");
		int idCategoria = fachada.obtenerIdCategoria(categoria);
		// tipo producto: 1-productoMN 2-producto diselo
		int tipo= 1;
				 
		fachada.insertarProducto(nombreProducto, descripcion, Double.parseDouble(precio), Double.parseDouble(descuento), Double.parseDouble(sobrecosto), tipo, idCategoria, idPresupuesto);
	}
	
}
