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


public class CategoriaDelegate implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
	    //este código obtiene las categorías del sitio web de Menini-Nicola 
		//y luego las despliega por pantalla en un control 'select' de html
		
		//obtener lista de categorías del sitio web
	    ArrayList categorias = new ArrayList<String>();
	    String urlMN= "http://menini-nicola.com/tienda";
	    categorias= ScrappingWeb.obtenerCategorias(urlMN);
	    
	    //convertir lista de categorias a HashMap 
	    //(el formulario de SDK trabaja con HashMap)
	    Integer i= 0;
	    Map<Integer,String> categoriasMap = new HashMap<Integer,String>();
	    while( i < categorias.size() )
	    {
	    	categoriasMap.put(i, i+"-"+(String)categorias.get(i));
	    	i++;
	    }
	    
	    //serializar datos de HashMap a json
	    ObjectValue categoriasDataValue = Variables.objectValue(categoriasMap)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();
	    
	    //bindear HashMap serializado (a JSON) sobre el control 'select' del formulario categoria
	    execution.setVariable("CATEGORIAS_DISPONIBLES", categoriasDataValue);
	  	   
	}
	
}