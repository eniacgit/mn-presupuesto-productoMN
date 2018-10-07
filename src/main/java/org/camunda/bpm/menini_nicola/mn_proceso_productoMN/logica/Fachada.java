package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia.*;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.*;

public class Fachada implements IFachada  {
	
	private AccesoBD accesoBD;
	
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.menini_nicola.mn_proceso_productoMN.fachada.IFachada#insertarCategoria(org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCategoria)
	 */
	@Override
	public void insertarCategoria(VOCategoria categoria)
	{
		String nombre= categoria.getNombre();

		accesoBD.insertarCategoria(nombre);
	}
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.menini_nicola.mn_proceso_productoMN.fachada.IFachada#cantidadRegistrosCategoria()
	 */
	@Override
	public int  cantidadRegistrosCategoria()
	{
		int cantidadRegistrosCategoria=0;
		
		cantidadRegistrosCategoria= accesoBD.cantidadRegistrosCategoria();
		
		return cantidadRegistrosCategoria;
	}
	
	

}
