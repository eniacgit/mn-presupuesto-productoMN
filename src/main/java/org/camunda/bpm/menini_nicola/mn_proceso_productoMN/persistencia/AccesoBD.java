package org.camunda.bpm.menini_nicola.mn_proceso_productoMN.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


import org.jfree.util.Log;


public class AccesoBD {
	private String driver;
	private String username;
	private String pass;
	private String url;
	private String base;
	
	/**
	 * El metodo conectarBD() es utilizado en todos los demas metodos
	 * de la clase y establece la conexion con la BD 
	 * @author christian
	 * @return
	 */
	
	public Connection conectarBD() {
	
		FileInputStream inputStream= null;
		Connection con = null;		

		try {
			
			//cargar credenciales de conexion a BD
			//desde archivo de configuracion y conectarse a BD
			
			File archivoConfiguracion= new File("config/parametros.txt");
			inputStream= new FileInputStream(archivoConfiguracion);
			Properties p = new Properties();
			p.load(inputStream);
			
			driver = p.getProperty("driver");
			Class.forName(driver);
			username = p.getProperty("usuario");
			pass = p.getProperty("password");
			url = p.getProperty("url");
			base = p.getProperty("bdatos");	
			con = DriverManager.getConnection(url + base, username, pass);
			
		} catch (FileNotFoundException e) {
			Log.error("no se encontro archivo de configuracion de conexion a BD"+e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("error al intentar leer archivo de configuracion de conexion a BD"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(inputStream != null)
			{
				try {
					inputStream.close();
				}
				catch(IOException e)
				{
					Log.error("error al intentar leer archivo de configuracion de conexion a BD"+e);
					e.printStackTrace();
				}
			}
		}
		return con;
	}
	
	/**
	 * El metodo desconectarBD sera invocado en cada catch 
	 * dentro de los metodos que deban conectarse a BD
	 * @param con
	 */
	public void desconectarBD(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			Log.error("error al intentar desconectarse de BD"+e);
			e.printStackTrace();
		}
	}
	
	public void insertarCategoria(String nombre) 
	{
		Connection con= this.conectarBD();
		Consultas consultas= new Consultas();
		String insert= consultas.insertarCategoria();
		PreparedStatement pstmt=null;
		
		try {
			pstmt = con.prepareStatement(insert);
			pstmt.setString(1, nombre);
			pstmt.executeUpdate();
		} catch (SQLException e) {
		Log.error("error al insertar categoria"+e);
			e.printStackTrace();
		}
		finally {
			if(pstmt != null)
			{
				try {
					pstmt.close();
					this.desconectarBD(con);
				}
				catch(SQLException e)
				{
					Log.error("error al insertar categoria"+e);
					e.printStackTrace();
				}
			}
		}
	}
	
	public int cantidadRegistrosCategoria() 
	{
		Connection con= this.conectarBD();
		Consultas consultas= new Consultas();
		String select= consultas.cantidadRegistrosCategoria();
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		int numeroFilas=0;
		
		try {
			pstmt= con.prepareStatement(select);
			rs= pstmt.executeQuery();
			
			if(rs.next())
			{
				numeroFilas= rs.getInt(1);
			}
		} catch (SQLException e) {
			Log.error("error al contar cantidad de registros en tabla categoria"+e);
			e.printStackTrace();
		}finally {
			if(pstmt != null)
			{
				try {
					pstmt.close();
					this.desconectarBD(con);
				}
				catch(SQLException e)
				{
					Log.error("error al contar cantidad de registros en tabla categoria"+e);
					e.printStackTrace();
				}
			}
		}
		
		return numeroFilas;
	}
	
	public boolean existeNroCotizacion(String nroCotizacion) {
			// Retorna true si el nro de la cotizacion ya existe en la bd
			boolean existeCliente= false;
			Connection con= this.conectarBD();
			Consultas consultas= new Consultas();			
			String select= consultas.existeNroCotizacion();
			
			PreparedStatement pstmt= null;
			ResultSet rs= null;
			try {
				con.prepareStatement(select);
				pstmt.setString(1, nroCotizacion);
				
				rs = pstmt.executeQuery();
				if (rs.next())
					existeCliente = true;
				rs.close();
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Log.error("error al verificar existencia de nombre de cotizacion"+e);
				e.printStackTrace();
			}finally {
				if(pstmt != null)
				{
					try
					{
						rs.close();
						pstmt.close();
						this.desconectarBD(con);
					}
					catch(SQLException e)
					{
						Log.error("error al verificar existencia de nombre de cotizacion"+e);
						e.printStackTrace();
					}
				}
			}		
			this.desconectarBD(con);
			return existeCliente;
		}
	
	//public void insertarProveedor(String nombre)
	
	//public void insertarProducto(String nombre, String descripcion, double costo,
	//								double descuento, double sobreCosto, int idCategoria, int idClientePresupuesto, 
	//								int idProveedor)
	
	//public int cantidadProductos()
		
}
