package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects;

public class VOCategoria {
	
	public int idCategoria;
	public String nombre;
	
	public VOCategoria() {}
	
	public VOCategoria(int idCategoria, String nombre)
	{
		this.idCategoria= idCategoria;
		this.nombre= nombre;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
