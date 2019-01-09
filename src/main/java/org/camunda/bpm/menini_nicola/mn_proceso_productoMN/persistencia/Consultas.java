package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia;

public class Consultas {
	
	public String insertarCliente() {
		String consulta ="INSERT INTO mn_cliente (nombre, email, telefono, celular,rut,razonSocial,tipo,direccion) VALUES (?,?,?,?,?,?,?,?);";
		return consulta;	
	}
	
	public String existeCliente() {
		String consulta = "SELECT nombre FROM mn_cliente WHERE nombre = ?;";
		return consulta;
	}
	
	public String obtenerIdCliente() {
		String consulta = "SELECT idCliente FROM mn_cliente WHERE nombre = ?;";
		return consulta;
	}
	
	public String insertarPresupuesto(){
		String consulta = "INSERT INTO mn_presupuesto (cotizacion,fecha,moneda,costo,condicionesVenta,descripcion,unidades) VALUES(?,?,?,?,?,?,?);";
		return consulta;
	}
	
	public String obtenerIdPresupuesto() {
		String consulta = "SELECT idPresupuesto FROM mn_presupuesto WHERE cotizacion = ?;";
		return consulta;
	}
	
	public String insertarClientePresupuesto() {
		String consulta ="INSERT INTO mn_cliente_presupuesto (estado, idCliente, idPresupuesto) VALUES (?,?,?);";
		return consulta;	
	}
	
	public String existeNroCotización() {
		String consulta ="select cotizacion from mn_presupuesto where cotizacion=?";
		return consulta;
	}
	
	public String obtenerRemitente() {
		String consulta ="select email from mn_email where id=?;";
		return consulta;
	}
	
	public String obtenerPasswordRemitente() {
		String consulta ="select password from mn_email where id=?;";
		return consulta;
	}
	
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
				"INSERT INTO mn_producto (nombre,descripcion,costo,descuento,sobreCosto,tipo,idCategoria,idPresupuesto) VALUES (?,?,?,?,?,?,?,?);";
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
	
	public String existeNroCotizacion() {
		String consulta ="select cotizacion from mn_presupuesto where cotizacion=?";
		return consulta;
	}
	
	public String existeCategoria() {
		String consulta = "SELECT nombre FROM mn_categoria WHERE nombre = ?;";
		return consulta;
	}
	
	public String obtenerUltimoIndiceInsertadoCategoria() {
		String consulta ="SELECT MAX(idCategoria) AS id FROM mn_categoria;";
		return consulta;
	}	
	
	
	
}
