package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import java.sql.SQLException;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia.AccesoBD_conException;

public class AccesoBDTest {

	public static void main(String[] args) {
	
		AccesoBD_conException accesoBD= new AccesoBD_conException();
		
		String intro="--TESTING DE CLASE AccesoBD--";
		System.out.println(intro);
		
		//test insertarCategoria
		
		System.out.println("test insertarCategoria");
		String categoria= new String("mesas");
		accesoBD.insertarCategoria(categoria);
		System.out.println("se inserto la categoria: "+categoria);
		
		//test cantidadRegistrosCategoria

		System.out.println("test cantidadRegistrosCategoria");
		int registrosCategoria= accesoBD.cantidadRegistrosCategoria();
		System.out.println("cantidad de registros de la tabla: mn_categoria = "
						+registrosCategoria);		
		
		
	}

}
