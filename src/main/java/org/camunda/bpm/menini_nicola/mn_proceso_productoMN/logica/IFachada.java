package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.logica;

import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.VOCategoria;

public interface IFachada {

	void insertarCategoria(VOCategoria categoria);

	int cantidadRegistrosCategoria();

}