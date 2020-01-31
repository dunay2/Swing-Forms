package utilities;

import java.util.ResourceBundle;

//Centraliza el acceso a los recursos de propiedades
public class ProyectBundle {
	
	public static ResourceBundle getProyectBundle(String language)

	{
		ResourceBundle rb;
		
		switch (language)
		{
		
		case "ES":
		rb=  ResourceBundle.getBundle("properties.ApplicationResources_es_ES_es");
		break;
		
		case "EN":
		rb=  ResourceBundle.getBundle("properties.ApplicationResources_en_EN_en");
		break;
		
		case "FR":
		rb=  ResourceBundle.getBundle("properties.ApplicationResources_fr_FR_fr");
		break;
		
		case "GE":
		rb=  ResourceBundle.getBundle("properties.ApplicationResources_ge_GE_ge");
		break;
		
		default:
			rb=  ResourceBundle.getBundle("properties.ApplicationResources_en_EN_en");
		}
		return rb;	
		
	}
	
		

}
