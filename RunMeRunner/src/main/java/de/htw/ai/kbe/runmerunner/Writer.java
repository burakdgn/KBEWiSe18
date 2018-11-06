package de.htw.ai.kbe.runmerunner;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Writer {
	
	private String fileName;
	
	public Writer(String fileName) {
	
		this.fileName = fileName;
		
		
	}
	
	
	public void writeToFile(ArrayList<Method> runMeMethods, ArrayList<Method> noRunMeMethods, String errors) throws FileNotFoundException{
		
		
		File file = new File(fileName);
		FileOutputStream fileOut = new FileOutputStream(fileName);
		
		try(OutputStreamWriter out = new OutputStreamWriter(fileOut)){
			
			out.write("Logfile für den runMeRunner-Beleg\n");
			
			out.write("Methodennamen ohne @RunMe\n");
			writeWithoutRunMeMethods(noRunMeMethods , out);
			
			out.write("Methodennamen mit @RunMe\n");
			writeWithRunMeMethods(runMeMethods, out);
			
			out.write(errors);
			
			System.out.print("Logfile wurde erstellt");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void writeWithoutRunMeMethods(ArrayList<Method> noRunMeMethods, OutputStreamWriter out) {
		
		for (Method m : noRunMeMethods) {

			try {
				out.write("\t" + m.getName()+ "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void writeWithRunMeMethods(ArrayList<Method> runMeMethods, OutputStreamWriter out) {
		
		for (Method m : runMeMethods) {

			try {
				out.write("\t" + m.getName() + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	public static void printWithoutRunMe(ArrayList<Method> noRunMeMethods) {
		System.out.println("Methodennamen ohne @RunMe");
		for (Method m : noRunMeMethods) {
			System.out.println("\t" + m.getName());
		}
	}

	public static void printWithRunMe(ArrayList<Method> runMeMethods) {
		System.out.println("Methodennamen mit @RunMe");
		for (Method m : runMeMethods) {
			System.out.println("\t" + m.getName());
		}
	}
	
	*/
	

}
