package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects;

public class VOPresupuesto {
	private Integer idPresupuesto;
	private String cotizacion;
	private String  fecha;
	private String moneda;
	private Double costo;
	private String condicionesVenta;
	private String descripcion;
	private Integer unidades;
	
	public VOPresupuesto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOPresupuesto(Integer idPresupuesto, String cotizacion, String fecha, String moneda, Double costo,
			String condicionesVenta, String descripcion, Integer unidades) {
		super();
		this.idPresupuesto = idPresupuesto;
		this.cotizacion = cotizacion;
		this.fecha = fecha;
		this.moneda = moneda;
		this.costo = costo;
		this.condicionesVenta = condicionesVenta;
		this.descripcion = descripcion;
		this.unidades= unidades;
	}

	public Integer getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public String getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getCondicionesVenta() {
		return condicionesVenta;
	}

	public void setCondicionesVenta(String condicionesVenta) {
		this.condicionesVenta = condicionesVenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Integer getUnidades() {
		return unidades;
	}
	
	public void setUnidades(Integer unidades) {
		this.unidades= unidades;
	}
	
}
