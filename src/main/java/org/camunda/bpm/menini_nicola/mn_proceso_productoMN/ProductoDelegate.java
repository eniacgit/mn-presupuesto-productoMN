package org.camunda.bpm.menini_nicola.mn_proceso_productoMN;

import java.awt.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.lang.*;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.Variables.SerializationDataFormats;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.ScrappingWeb;
import org.camunda.bpm.menini_nicola.mn_proceso_productoMN.valueObjects.*;

public class ProductoDelegate implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
	    
		//mostrar cliente
		execution.setVariable("CLIENTE", execution.getVariable("CLIENTE"));
		
	    //obtener indice de la categoria seleccionada
	    String categoriaIndice= (String)execution.getVariable("categoria");
	    	    
	    //obtener lista de categorías del sitio web 
	    //para seleccionar texto de la categoria de pantalla previa 
	    ArrayList categorias = new ArrayList<String>();
	    String urlMN= "http://menini-nicola.com/tienda";
	    categorias= ScrappingWeb.obtenerCategorias(urlMN);
	    String categoriaTexto= (String)categorias.get(Integer.parseInt(categoriaIndice));
	    
	    //setear en pantalla texto de categoria
	    execution.setVariable("CATEGORIA_SELECCIONADA", categoriaTexto);
	    
	    //efectuar scrapping de productos de la web de Menini-Nicola
		ArrayList productos = new ArrayList();
	    productos= ScrappingWeb.obtenerProductosCategoria(categoriaTexto);
	    
	    Integer i= 0;
	    Map<Integer,String> productosMap = new HashMap<Integer,String>();
	    while( i < productos.size() )
	    {
	    	productosMap.put(i, (String)productos.get(i));
	    	i++;
	    }
	    
	    //serializar productos a json
	    ObjectValue productosDataValue = Variables.objectValue(productosMap)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

	    //asignar datos con selectbox de formulario html
	    execution.setVariable("PRODUCTOS_DISPONIBLES", productosDataValue);
	    	    
	}
	
}
