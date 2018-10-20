package de.htw.ai.kbe.RunMeRunner;

import org.apache.commons.cli.*;

/**
 * 
 * RunMeRunner 
 * CommandLine Parser
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
				if (cmd.hasOption("o")) {
					System.out.println("Input class: " + args[1] +"\nReport: " + args[3]);
				} else {
					System.out.println("Input class: " + args[1]);
				}
			}
		} catch (ParseException e) {
			formatter.printHelp(name, options, true);
		} 
		
		

	}
	
	
}
