package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import modelo.UsuarioDTO;

public class UserGenerator {

	private static final String LETRAS_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";
	private static final Random rand = new Random();

	private static final String[] name = { "Tatiana", "Adam", "Ernesto", "Lázaro", "Leandro", "León", "Cecilia", "Ray",
			"Maria", "Rosa", "Patricia", "Armando", "Lorenzo", "Mar", "Silvia", "Svenka", "Hernando", "Nadja", "Africa",
			"Abel", "", "Alma", "Amor", "Rei", "Cerene", "Mar", "Antonio", "Ainsley", "Josiah", "Tarik", "Adam",
			"Tatyana", "Warren", "Hollee", "Ali", "Armando", "Yuri", "Yoko", "Mary", "Ivy", "Aurora", "Graiden",
			"Allegra", "Philip", "Joel", "Brody", "Donna", "Wallace", "Orla", "Avye", "Cooper", "Fay", "Neil", "Vera",
			"Cade", "Kenyon", "Brenda", "Maya", "Jackson", "Malik", "Darius", "Denton", "Cassandra", "Harrison", "Avye",
			"Eleanor", "Hayley", "PetraR", "Stacey", "AmosCo", "Oliver", "Dominic", "Wayne", "Eden", "Knox", "Oleg",
			"Sonia", "Zorita", "Mollie", "Stephen", "Pearl", "Ursula", "Carter", "Tanisha", "Carson", "Kiara",
			"Guineve", "Iris", "Dustin", "Kelly", "Sandra", "Julian", "Quemby", "Anjolie", "Bree", "Daquan", "Brynne",
			"Xanthus", "Roanna", "Perry", "Arsenio", "Paul", "Helen", "Talon", "Whitney", "Aquila", "James", "Odysseu",
			"Chava", "Murphy", "Eugenia", "Hanae", "Declan", "Armando", "Zoe", "Cody", "Selma", "Dalton", "Aiko",
			"Matthew", "Keiko", "Wang", "Aurora", "Nora", "Channin", "Quail", "Zahir" };

	private static final String[] firstname = { "D.", "K.", "Albeicer", "Roque", "del Mar", "Pablo", "Vera", "Muñoz",
			"Alex", "Godoy", "Castillo", "Delgado", "Flores", "Silvestre", "Cres", "Suárez", "García", "Miranda",
			"Morales", "Ana", "Salmar", "Benitez" };
	private static final String[] lastname = { "De la vega", "Mateo", "Ruiz", "Afonso", "Suárez", "Pérez", "Smith",
			"Maqueda", "Díaz", "Romero", "Sosa", "García", "Pulido", "Holder", "Barlow", "Campos", "Potter" };

	/**
	 * Genera un NIF
	 *
	 * @return NIF (DNI + letra)
	 */
	public static String generateDni() {
		String dniNumber = "";

		for (int i = 0; 8 > i; i++) {
			Random r = new Random();

			dniNumber = dniNumber.concat(String.valueOf(r.nextInt(10)));
		}

		return getNifLetter(dniNumber);
	}

	/**
	 * Calcula el NIF (DNI + letra) para un número de identificación dado
	 *
	 * @param numerosDni - números de un dni
	 * @return NIF (DNI + letra)
	 */
	private static String getNifLetter(String numerosDni) {

		int numeros = Integer.valueOf(numerosDni);
		return String.valueOf(numerosDni) + LETRAS_NIF.charAt(numeros % 23);
	}

	public static String generateLastName() {

		return lastname[rand.nextInt(lastname.length)];
	}

	public static String generateFirstName() {

		return name[rand.nextInt(name.length)] + " " + firstname[rand.nextInt(firstname.length)];
	}

	public static UsuarioDTO generateRandomPerson() {

		

		UsuarioDTO person;

		person = new UsuarioDTO();

		person.setNombreUsuario(generateFirstName());
		person.setPassword(generateDni());

		person.setFechaAlta( generateRandomDate());
		person.setIdioma("ES".toCharArray());

		person.setCodigoRol(01);

		person.setCarpetaDoc("c:\\" + person.getNombreUsuario());

		return person;
	}

	
	static public Date generateRandomDate()
	{
		String DEFAULT_PATTERN = "dd/MM/yyyy";
		Date date=null; 
		byte day,month;
		int year;
		String sDate;
		
		day=(byte) (createRandomNumber()*2);
		month=createRandomNumber();
		year= createRandomNumber()+2010;

		StringBuilder sb = new StringBuilder();
		sb.append(day);
		sb.append("/");
		sb.append(month);
		sb.append("/");
		sb.append(year);
					
		sDate=    sb.toString();
		
	    try {
			date=new SimpleDateFormat(DEFAULT_PATTERN).parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	      
		
	    
	    
		return date;
		
	}
	
	static private Byte createRandomNumber() {
		
		//dia aleatorio
		Random random = new Random();
		byte rand = 0;
		while (true) {
			rand = (byte)random.nextInt(11);
			if (rand != 0)
				break;
		}		
		return rand;		
	}

}

/*
 * var data = [ {"name": "Ainsley K. Holder"}, {"name": "Josiah D. Barlow"},
 * {"name": "Tarik U. Cross"}, {"name": "Adam G. Campos"}, {"name":
 * "Tatyana Potter"}, {"name": "Warren Sweeney"}, {"name": "Hollee York"},
 * {"name": "Ali R. James"}, {"name": "Armando Olson"}, {"name":
 * "Yuri Elliott"}, {"name": "Yoko Mayer"}, {"name": "Mary Marshall"}, {"name":
 * "Ivy Jacobson"}, {"name": "Aurora Dale"}, {"name": "Graiden W. Cook"},
 * {"name": "Allegra Sloan"}, {"name": "Philip L. Little"}, {"name":
 * "Joel J. Gutierrez"}, {"name": "Brody Mayo"}, {"name": "Donna Baxter"},
 * {"name": "Wallace Hines"}, {"name": "Orla Odonnell"}, {"name": "Avye James"},
 * {"name": "Cooper W. Gordon"}, {"name": "Fay N. Ramirez"}, {"name":
 * "Neil Miles"}, {"name": "Vera X. Pierce"}, {"name": "Cade T. Bradford"},
 * {"name": "Kenyon Q. Herrera"}, {"name": "Brenda Boyer"}, {"name":
 * "Maya T. Calhoun"}, {"name": "Jackson Dickerson"}, {"name": "Malik Kelley"},
 * {"name": "Darius B. Rosario"}, {"name": "Denton Pate"}, {"name":
 * "Cassandra X. Fuller"}, {"name": "Harrison Leach"}, {"name":
 * "Avye R. Montoya"}, {"name": "Eleanor E. Robles"}, {"name": "Hayley Bender"},
 * {"name": "Petra Rose"}, {"name": "Stacey V. Nichols"}, {"name":
 * "Amos Cochran"}, {"name": "Oliver Mcneil"}, {"name": "Dominic M. Hunt"},
 * {"name": "Wayne L. Small"}, {"name": "Eden D. Obrien"}, {"name":
 * "Knox V. Carver"}, {"name": "Oleg Jennings"}, {"name": "Sonia Carroll"},
 * {"name": "Zorita G. Shepherd"}, {"name": "Mollie K. Dale"}, {"name":
 * "Stephen Y. Serrano"}, {"name": "Pearl Clark"}, {"name": "Ursula Merrill"},
 * {"name": "Carter Grimes"}, {"name": "Tanisha H. Sandoval"}, {"name":
 * "Carson Whitfield"}, {"name": "Kiara Webster"}, {"name":
 * "Guinevere Y. Mullins"}, {"name": "Iris N. Mccoy"}, {"name":
 * "Dustin L. Johnston"}, {"name": "Kelly H. Robbins"}, {"name": "Sandra Huff"},
 * {"name": "Julian E. Langley"}, {"name": "Quemby Sweet"}, {"name":
 * "Anjolie F. Banks"}, {"name": "Bree T. Griffin"}, {"name": "Daquan Hartman"},
 * {"name": "Brynne George"}, {"name": "Xanthus I. Albert"}, {"name":
 * "Roanna Small"}, {"name": "Perry L. Craig"}, {"name": "Arsenio H. Smith"},
 * {"name": "Paul Marshall"}, {"name": "Helen N. Kaufman"}, {"name":
 * "Talon Harrell"}, {"name": "Whitney Cortez"}, {"name": "Aquila Jefferson"},
 * {"name": "James W. Castaneda"}, {"name": "Odysseus Z. Matthews"}, {"name":
 * "Chava Stuart"}, {"name": "Murphy E. Santos"}, {"name":
 * "Eugenia C. Justice"}, {"name": "Hanae T. Kelly"}, {"name": "Declan Lawson"},
 * {"name": "Armando Humphrey"}, {"name": "Zoe U. Ferguson"}, {"name":
 * "Cody Riddle"}, {"name": "Selma Turner"}, {"name": "Dalton U. Simpson"},
 * {"name": "Aiko Acosta"}, {"name": "Matthew A. Hawkins"}, {"name":
 * "Keiko Davidson"}, {"name": "Wang Charles"}, {"name": "Aurora Rosa"},
 * {"name": "Nora P. Phelps"}, {"name": "Channing C. Ball"}, {"name":
 * "Quail B. Welch"}, {"name": "Zahir Kelley"} ];
 */