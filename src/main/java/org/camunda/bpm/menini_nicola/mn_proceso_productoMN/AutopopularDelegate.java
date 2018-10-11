package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica.*;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOProductoMN;

public class AutopopularDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Fachada facade= new Fachada();
		
		//obtener indice del producto seleccionado en pantalla anterior
	    String productoIndice= (String)execution.getVariable("producto");
	    
	    //mostrar producto indice
	    execution.setVariable("prodIndice", productoIndice);
	    
		//obtener categoria del producto
		String categoria= (String)execution.getVariable("CATEGORIA_SELECCIONADA");
		execution.setVariable("CATEGORIA_PRODUCTO", categoria);
	
	    //obtener lista de productos del sitio web 
	    //para seleccionar texto del producto 
	    ArrayList productos = new ArrayList();
	    productos= ScrappingWeb.obtenerProductosCategoria(categoria);
	    
	    String productoTexto=(String)productos.get(Integer.parseInt(productoIndice));
	    
	    //mostrar producto obtenido en pantalla
	    execution.setVariable("PRODUCTO_SELECCIONADO",productoTexto);
	    
	    //obtener informacion del producto a mostrar en pantalla mediante scrapping
	    VOProductoMN productoMN= new VOProductoMN();
	    productoMN= ScrappingWeb.obtenerProducto(productoTexto);
	    
	    //obtener nombre del presupuesto
	    String nombrePresupuesto= facade.generarNroCotizacionFechaActual();
	    
	    //obtener texto condiciones
	    String textoCondiciones= facade.textoCondiciones();
	    
	    //obtener texto forma de pago
	    String textoFormaDePago= facade.textoFormaDePago();
	    
	    //obtener texto tiempo de entrega
	    String textoTiempoDeEntrega= facade.textoTiempoDeEntrega();
	     
	    //setear valores para descuento y sobrecosto
	    String descuento= "0";
	    String sobrecosto= "0";
	    
	    //obtener cotización de la moneda
	    
	    //autocompletar los campos restantes
	    execution.setVariable("COTIZACION", nombrePresupuesto);
	    execution.setVariable("PRECIO", productoMN.getPrecio());
	    execution.setVariable("DESCRIPCION", productoMN.getDescripcion());
	    execution.setVariable("DIMENSIONES", productoMN.getDimensiones());
	    execution.setVariable("CONDICIONES", textoCondiciones);
	    execution.setVariable("FORMA_DE_PAGO", textoFormaDePago);
	    execution.setVariable("TIEMPO_DE_ENTREGA",textoTiempoDeEntrega);
	    execution.setVariable("DESCUENTO", descuento);
	    execution.setVariable("SOBRECOSTO", sobrecosto);
	    
	}

}
