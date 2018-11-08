package de.htw.ai.kbe.runmerunner;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.cli.*;

/**
 * 
 * RunMeRunner CommandLine Parser
 * 
 */
public class App {
	public static void main(String[] args) {
		Options options = new Options();
		Option className = new Option("c", true, "Name der Klasse");
		className.setRequired(true);

		Option outputFile = new Option("o", true, "Name eines Ausgabefiles");
		options.addOption(className);
		options.addOption(outputFile);

		CommandLine cmd = null;
		commandLineMethod(cmd, options, args);

	}

	public static void commandLineMethod(CommandLine cmd, Options options, String[] args) {

		CommandLineParser parser = new DefaultParser();

		HelpFormatter formatter = new HelpFormatter();
		String name = "RunMeRunner\njava -jar RunMeRunner-1.0-jar-with-dependencies.jar";
		Writer w;

		try {
			cmd = parser.parse(options, args);

			String cValue = cmd.getOptionValue("c");

			if (cmd.getOptionValue("o") != null) {
				String oValue = cmd.getOptionValue("o");
				System.out.println("Input class: " + cValue + "\nReport: " + oValue);
				w = new Writer(oValue);
				findMethods(cValue, w);

			} else {
				System.out.println("Input class: " + cValue + "\nReport (Default): report.txt");
				w = new Writer("report.txt");
				findMethods(cValue, w);
			}

		} catch (ParseException e) {
			formatter.printHelp(name, options, true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Die obere Report-Datei wurde nicht erstellt.\n"
					+ "Für weitere Informationen öffnen sie die Datei 'error.txt'.");
			w = new Writer("error.txt");
			try {
				w.writeErrorFile(e.toString());
			} catch (FileNotFoundException e1) {
				
				e1.printStackTrace();
			}
		}
	}

	public static void findMethods(String className, Writer w) throws ClassNotFoundException {
		Class<?> c;
		try {
			c = Class.forName(className);
			Object t = c.newInstance();

			ArrayList<Method> runMeMethods = new ArrayList<>();
			Method[] allMethods = c.getDeclaredMethods();
			ArrayList<Method> noRunMeMethods = new ArrayList<>();
			noRunMeMethods.addAll(Arrays.asList(allMethods));

			for (Method m : allMethods) {
				if (m.isAnnotationPresent(RunMe.class)) {
					runMeMethods.add(m);
					noRunMeMethods.remove(m);
				}
			}

			String errors = invokeRunMeMethods(runMeMethods, t);
			try {
				w.writeToFile(runMeMethods, noRunMeMethods, errors);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}

		
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
	}

	private static String invokeRunMeMethods(ArrayList<Method> runMeMethods, Object instance) {
		String errors = "";
		for (Method m : runMeMethods) {
			try {
				m.invoke(instance);
			} catch (IllegalAccessException e) {
				errors += "\n\t" + m.getName() + ": " + e.getClass().getSimpleName();
			} catch (IllegalArgumentException e) {
				errors += "\n\t" + m.getName() + ": " + e.getClass().getSimpleName();
			} catch (InvocationTargetException e) {
				errors += "\n\t" + m.getName() + ": " + e.getClass().getSimpleName();
			}
		}

		if (!errors.equalsIgnoreCase("")) {
			return "'Nicht invokierbare' Methode mit @RunMe" + errors;

		}
		return errors;
	}

}