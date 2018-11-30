package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica.Fachada;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOReporte;

public class TestReporteJasper {

	public static void main(String[] args) {			
			
		VOReporte voReporte = new VOReporte();
		voReporte.setNombrePresupuesto("20181129-01");
		voReporte.setCliente("SKYnet");
		voReporte.setEmail("deleon.danielo@gmail.com");
		voReporte.setTel("26190000");
		voReporte.setUrlImagen("http://menini-nicola.com/wp-content/uploads/2015/08/Silla_JESSIE_72ppp-600x600.jpg");
		voReporte.setNombreProducto("SILLA JESSIE");
		voReporte.setMoneda("USD");
		voReporte.setPrecio("570");
		voReporte.setDescripcion("iviana y sin un ambiente definido, la silla JESSIE es una propuesta del estudio reaccionando frente a la actual escasez de oferta en sillas en madera del mercado. Su estilo relacionado con el diseño escandinavo llevó a ser seleccionada para formar parte del mobiliario del Café Allegro en el Teatro Solís!");
		voReporte.setDimensiones("41 x 52 x 84 cm");
		voReporte.setCondiciones("Precio en dólares americanos. Incluye impuestos y transporte dentro de Montevideo. Pago sin recargo con VISA, MasterCard y AmericanExpress. Tiempo de entrega 35 días (a confirmar al momento de la compra).\n" + 
				"El diseño es exclusivo de menini-nicola");
		voReporte.setFormaDePago("Precio en dólares americanos. Incluye impuestos y transporte dentro de Montevideo. Pago sin recargo con VISA, MasterCard y AmericanExpress. Tiempo de entrega 40 días (a confirmar al momento de la compra).\n" + 
				"El diseño es exclusivo de menini-nicola.");
		voReporte.setTiempoDeEntrega("30");
		voReporte.setDescuento("10");
		voReporte.setSobreCosto("0");
		voReporte.setPrecioFinal("565");
		voReporte.setMateriales("Materiales: Madera de Peteribí.");
		voReporte.setTerminacion("Terminación: Lustre PU.");
		
		
		
		
		
		
		Fachada f = new Fachada();
		f.generarReporte(voReporte);
		
		System.out.println("REPORTE GENERADO CON EXITO en /home/Danielo/reportes");

	}

}
