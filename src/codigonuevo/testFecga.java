package codigonuevo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utilities.UserGenerator;

public class testFecga {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		generateRandomDate();
		
		
	}

	static private  void  generateRandomDate()
	{

		for (int i=0;i<10;i++)
		System.out.println(UserGenerator.generateRandomDate());
	}

}
