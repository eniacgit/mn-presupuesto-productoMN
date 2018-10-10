package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.*;

public class ResumenDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		//Calcular precio final de venta 
		
		Double descuento;
		Double sobrecosto;
		Double precio;
		Double precioFinal;
		
		String descuentoStr=(String)execution.getVariable("DESCUENTO");
		descuento= Double.parseDouble(descuentoStr);
		String sobrecostoStr=(String)execution.getVariable("SOBRECOSTO");
		sobrecosto= Double.parseDouble(sobrecostoStr);
		precio= (Double)execution.getVariable("PRECIO");		
		VOPrecio voPrecio= new VOPrecio(descuento,sobrecosto,precio);		
		
		//pre-condicion: se ingresa solamente descuento o sobrecosto. 
		
		Fachada f= new Fachada();
		precioFinal= f.calcularPrecioVentaFinal(voPrecio);
		
		//mostrar en pantalla

		descuentoStr= Double.toString(descuento);
		sobrecostoStr= Double.toString(sobrecosto);
		String precioStr= Double.toString(precio);
		String precioFinalStr= Double.toString(precioFinal);
				
		execution.setVariable("PRECIO", precioStr);
		execution.setVariable("DESCUENTO", descuentoStr );
		execution.setVariable("SOBRECOSTO", sobrecostoStr );
		execution.setVariable("PRECIO_FINAL",precioFinalStr);		
	}
	

}
