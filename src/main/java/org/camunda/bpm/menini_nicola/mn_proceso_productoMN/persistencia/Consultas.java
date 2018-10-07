package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia;

public class Consultas {
	
	public String insertarProveedor(){
		String consulta="INSERT INTO mn_proveedor(nombre) VALUES (?);";
		return consulta;
	}
	
	public String cantidadRegistrosCategoria(){
		String consulta="SELECT COUNT(*) FROM mn_categoria;";
		return consulta;
	}
	
	public String insertarCategoria() {
		String consulta="INSERT INTO mn_categoria(nombre) VALUES (?);";
		return consulta;
	}
	
	public String obtenerNombreCategoria() {
		String consulta="\n" + 
				"SELECT nombre FROM mn_categoria WHERE idCategoria=?;";
		return consulta;
	}
	
	public String obtenerIdCategoria() {
		String consulta="\n" + 
				"SELECT idCategoria FROM mn_categoria WHERE nombre=?;";
		return consulta;
	}
	
	public String insertarProducto() {
		String consulta="\n" + 
				"INSERT INTO mn_producto(nombre,descripcion,costo,descuento,sobreCosto,\n" + 
				"idCategoria,idClientePresupuesto,idProveedor) VALUES (?,?,?,?,?,?,?,?);";
		return consulta;
	}
	
	public String obtenerNombreProducto() {
		String consulta="\n" + 
				"SELECT nombre FROM mn_producto WHERE idProducto=?;";
		return consulta;
	}
	
	public String obtenerIdProducto() {
		String consulta="\n" + 
				"SELECT idProducto FROM mn_producto WHERE nombre=?;";
		return consulta;
	}
	
	public String cantidadProductos() {
		String consulta="SELECT COUNT(*) FROM mn_producto;";
		return consulta;
	}
}
