package de.htw.ai.kbe.runmerunner;

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

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;

		HelpFormatter formatter = new HelpFormatter();
		String name = "RunMeRunner\njava -jar RunMeRunner-1.0-jar-with-dependencies.jar";

		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption("c")) {

				String cValue = cmd.getOptionValue("c");

				if (cmd.hasOption("o")) {
					String oValue = cmd.getOptionValue("o");
					System.out.println("Input class: " + cValue + "\nReport: " + oValue);
					findMethods(cValue);

				} else {
					System.out.println("Input class: " + cValue);
					findMethods(cValue);
				}
			} else {
				formatter.printHelp(name, options, true);
			}
		} catch (ParseException e) {
			formatter.printHelp(name, options, true);
		}
	}

	public static void findMethods(String className) {
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

			printWithoutRunMe(noRunMeMethods);
			printWithRunMe(runMeMethods);
			invokeRunMeMethods(runMeMethods, t);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	public static void invokeRunMeMethods(ArrayList<Method> runMeMethods, Object instance) {
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
			System.out.println("'Nicht invokierbare' Methode mit @RunMe" + errors);
			
		}
	}
}
