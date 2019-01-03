package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects;

public class VOPrecio {
	
	private Double descuento;
	private Double sobrecosto;
	private Double precio;
	private Integer unidades;
	
	public VOPrecio(Double descuento, Double sobrecosto, Double precio, Integer unidades) {
		super();
		this.descuento = descuento;
		this.sobrecosto = sobrecosto;
		this.precio = precio;
		this.unidades= unidades;
	}
	
	public Double getDescuento() {
		return descuento;
	}
	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}
	public Double getSobrecosto() {
		return sobrecosto;
	}
	public void setSobrecosto(Double sobrecosto) {
		this.sobrecosto = sobrecosto;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getUnidades() {
		return this.unidades;
	}
	public void setUnidades(Integer unidades) {
		this.unidades= unidades;
	}
	
}
