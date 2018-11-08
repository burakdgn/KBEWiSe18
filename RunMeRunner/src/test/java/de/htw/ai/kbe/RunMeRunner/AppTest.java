package de.htw.ai.kbe.RunMeRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import de.htw.ai.kbe.runmerunner.App;
import de.htw.ai.kbe.runmerunner.Writer;
//import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class AppTest extends TestCase {

	private static final String CLASSNAME = "de.htw.ai.kbe.runmerunner.MyClass";
	private static final String REPORTNAME = "report.txt";

	Options options;
	ByteArrayOutputStream outContent;
	PrintStream originalOut = System.out;

	@Before
	public void setUp() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		options = new Options();
		Option className = new Option("c", true, "Name der Klasse");
		className.setRequired(true);

		Option outputFile = new Option("o", true, "Name eines Ausgabefiles");
		options.addOption(className);
		options.addOption(outputFile);
	}

	@After
	public void tearDown() {
		System.setOut(originalOut);
	}

	@Test
	public void testWithAllArgumentsGiven() throws ParseException {

		CommandLine cmd = null;
		String[] args = { "-c", CLASSNAME, "-o", REPORTNAME };
		App.commandLineMethod(cmd, options, args);

		cmd = new DefaultParser().parse(options, args);

		String exp = ("Input class: " + cmd.getOptionValue("c") + "\nReport: " + cmd.getOptionValue("o")
				+ "\nLogfile wurde erstellt").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd.getOptionValue("c").equals(CLASSNAME));
		assertTrue(cmd.getOptionValue("o").equals(REPORTNAME));
		assertEquals(exp, act);

	}

	@Test
	public void testCOptionWithArgumentValid() throws ParseException {

		CommandLine cmd = null;
		String args[] = { "-c", CLASSNAME };
		App.commandLineMethod(cmd, options, args);

		cmd = new DefaultParser().parse(options, args);

		String exp = ("Input class: " + cmd.getOptionValue("c") + "\nReport (Default): report.txt"
				+ "\nLogfile wurde erstellt").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd.getOptionValue("c").equals(CLASSNAME));
		assertTrue(cmd.getOptionValue("o") == null);
		assertEquals(exp, act);

	}

	@Test
	public void testCWithoutArgumentFail() {

		CommandLine cmd = null;
		String args[] = { "-c" };
		App.commandLineMethod(cmd, options, args);

		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {

		}

		String exp = ("usage: RunMeRunner\n"
				+ "java -jar RunMeRunner-1.0-jar-with-dependencies.jar -c <arg> [-o <arg>]\n"
				+ " -c <arg>   Name der Klasse\n" + " -o <arg>   Name eines Ausgabefiles\n" + "").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd == null);
		assertEquals(exp, act);

	}

	@Test
	public void testCOptionWithArgumentAndOOptionWithoutArgumentFail() {

		CommandLine cmd = null;
		String args[] = { "-c", CLASSNAME, "-o" };
		App.commandLineMethod(cmd, options, args);

		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {

		}

		String exp = ("usage: RunMeRunner\n"
				+ "java -jar RunMeRunner-1.0-jar-with-dependencies.jar -c <arg> [-o <arg>]\n"
				+ " -c <arg>   Name der Klasse\n" + " -o <arg>   Name eines Ausgabefiles\n" + "").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd == null);
		assertEquals(exp, act);

	}

	@Test
	public void testOnlyOOptionWithArgumentFail() {

		CommandLine cmd = null;
		String args[] = { "-o", REPORTNAME };
		App.commandLineMethod(cmd, options, args);

		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {

		}

		String exp = ("usage: RunMeRunner\n"
				+ "java -jar RunMeRunner-1.0-jar-with-dependencies.jar -c <arg> [-o <arg>]\n"
				+ " -c <arg>   Name der Klasse\n" + " -o <arg>   Name eines Ausgabefiles\n" + "").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd == null);
		assertEquals(exp, act);

	}

	@Test
	public void testOnlyOOptionWithoutArgumentFail() {

		CommandLine cmd = null;
		String args[] = { "-o" };
		App.commandLineMethod(cmd, options, args);

		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {

		}

		String exp = ("usage: RunMeRunner\n"
				+ "java -jar RunMeRunner-1.0-jar-with-dependencies.jar -c <arg> [-o <arg>]\n"
				+ " -c <arg>   Name der Klasse\n" + " -o <arg>   Name eines Ausgabefiles\n" + "").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd == null);
		assertEquals(exp, act);

	}

	@Test
	public void testWithWrongOptions() {

		CommandLine cmd = null;
		String args[] = { "-x", CLASSNAME };
		App.commandLineMethod(cmd, options, args);

		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {

		}

		String exp = ("usage: RunMeRunner\n"
				+ "java -jar RunMeRunner-1.0-jar-with-dependencies.jar -c <arg> [-o <arg>]\n"
				+ " -c <arg>   Name der Klasse\n" + " -o <arg>   Name eines Ausgabefiles\n" + "").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd == null);
		assertEquals(exp, act);

	}

	@Test
	public void testWithoutOptionsAndArguments() {

		CommandLine cmd = null;
		String args[] = {};
		App.commandLineMethod(cmd, options, args);

		try {
			cmd = new DefaultParser().parse(options, args);
		} catch (ParseException e) {

		}

		String exp = ("usage: RunMeRunner\n"
				+ "java -jar RunMeRunner-1.0-jar-with-dependencies.jar -c <arg> [-o <arg>]\n"
				+ " -c <arg>   Name der Klasse\n" + " -o <arg>   Name eines Ausgabefiles\n" + "").trim();
		String act = outContent.toString().trim();

		assertTrue(cmd == null);
		assertEquals(exp, act);

	}

	@Test(expected = ClassNotFoundException.class)
	public void nonExistentClassNameGiven() throws ClassNotFoundException {

		CommandLine cmd = null;
		String[] args = { "-c", "de.htw.ai.kbe.runmerunner.nonExistingClass", "-o", REPORTNAME };
		App.findMethods(args[1], new Writer(REPORTNAME));

	}

}
