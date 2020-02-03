package utilities;

import java.util.ResourceBundle;

//Centraliza el acceso a los recursos de propiedades
public class ProyectBundle {
	private static ResourceBundle rb = null;

	private ProyectBundle() {
	}

	// Se debe obtener una instancia y en su caso llamar a los getters para obtener
	// el valor en el idioma apropiado
	public static ResourceBundle getInstance(String language)

	{

		switch (language) {

		case "ES":
			rb = ResourceBundle.getBundle("properties.ApplicationResources_es_ES_es");
			break;

		case "EN":
			rb = ResourceBundle.getBundle("properties.ApplicationResources_en_EN_en");
			break;

		case "FR":
			rb = ResourceBundle.getBundle("properties.ApplicationResources_fr_FR_fr");
			break;

		case "GE":
			rb = ResourceBundle.getBundle("properties.ApplicationResources_ge_GE_ge");
			break;

		default:
			rb = ResourceBundle.getBundle("properties.ApplicationResources_en_EN_en");
		}

		return rb;
	}

	public static String[] getRoleStrings() {
		String[] roleStrings = rb.getString("user.roles.strings").split(",");
		return roleStrings;
	}

	public static String[] getLabelStrings() {
		String[] labelStrings = rb.getString("user.labels.strings").split(",");
		return labelStrings;
	}

	public static String[] getfieldsName() {
		String[] labelStrings = rb.getString("user.fields.names").split(",");
		return labelStrings;
	}

	public static String[] getfieldsType() {
		String[] labelStrings = rb.getString("user.fields.types").split(",");
		return labelStrings;
	}

	public static String[] getfieldsMaxLength() {
		String[] labelStrings = rb.getString("user.fields.maxlength").split(",");
		return labelStrings;
	}

	public static String[] getfieldsFormat() {
		String[] labelStrings = rb.getString("user.fields.format").split(",");
		return labelStrings;
	}

	public static String[] getButtonStrings() {
		String[] labelStrings = rb.getString("user.buttons.strings").split(",");
		return labelStrings;
	}

	public static String[] getButtonActionStrings() {
		String[] labelStrings = rb.getString("user.buttons.actions.strings").split(",");
		return labelStrings;
	}
	public static String getGAP() {
		return  rb.getString("GAP");
	}

}
