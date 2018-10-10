package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia.*;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.*;;



public class Fachada{
	
	private AccesoBD accesoBD;
	
	public void insertarCategoria(VOCategoria categoria)
	{
		String nombre= categoria.getNombre();

		accesoBD.insertarCategoria(nombre);
	}

	public int  cantidadRegistrosCategoria()
	{
		int cantidadRegistrosCategoria=0;
		
		cantidadRegistrosCategoria= accesoBD.cantidadRegistrosCategoria();
		
		return cantidadRegistrosCategoria;
	}
	
	public String textoValidezPresupuesto()
	{
		String texto="";
	
		texto="CONDICIONES"+"El diseño de TODOS los productos es propiedad exclusiva del estudio menini nicola."+
				"Esta cotizacizón tiene una validez de 30 días corridos."+
				"Los precios incluyen IVA y transporte dentro de los límites de Montevideo. "+
				"En caso de que la entrega sea fuera de la ciudad o si la entrada de los productos deba"+
				"realizarse por fuera de la vivienda, se cotizará aparte."+
				"En caso de que el cliente solicite cambio en los materiales y/o terminaciones detallados en el producto"+
				"se deberá recotizar el mismo."+" Si los cambios solicitados por el cliente, implican modificaciones en el diseño del producto,"+
				"se deberá trabajar como un proyecto de desarrollo para ese producto.";
				
		return texto;
	}
	
	public String textoFormaDePago()
	{
		String texto="";
		
		texto="Forma de pago: ";
				
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

}
