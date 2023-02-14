package de.korittky.spielplangenerator;

import com.google.gson.stream.JsonReader;
import org.apache.commons.cli.*;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.*;

@SpringBootApplication
public class SpielplangeneratorApplication {

	public static void main(String[] args) {
		CommandLineParser parser = new DefaultParser();
		Options options = buildOptions();
		String eingabedatei = "spielfestTemplate.json";

		new SpringApplicationBuilder(SpielplangeneratorApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("spielplangenerator", options);
				System.exit(0);
			}

			if (line.hasOption("input")) {
				// initialise the member variable
				eingabedatei = line.getOptionValue("input").replaceAll("\"", "");
				//	eingabedatei = eingabedatei.replaceAll("\"","");
			}

			Gson gson2 = new Gson();
			JsonReader reader = null;
			try {
				reader = new JsonReader(new FileReader(eingabedatei));
			} catch (FileNotFoundException e) {
				System.out.println("Die Eingabedatei "+eingabedatei+ " wurde nicht gefunden. Eine Beispieldatei mit diesem Namen wurde erstellt.");
				printTemplate(eingabedatei);
				System.exit(0);
			}
			Spielfest spielfest = gson2.fromJson(reader, Spielfest.class);
			spielfest.init();

			Plan plan = new Plan(spielfest.getAlleTeams().length, spielfest.getAnzahlRunden());
			FillPlan fillPlan = new FillPlan(spielfest);

			PlatzVerteilen platzVerteilen = new PlatzVerteilen(spielfest, fillPlan.fillRek(plan, spielfest.getAlleTeams()));
			platzVerteilen.platzVerteilen();


			File inputfile = new File(eingabedatei);
			PdfOut pdfOut = new PdfOut(inputfile.getAbsolutePath().replace(inputfile.getName(), "Spielplan.pdf"));
			try {
				pdfOut.printPlanPdf(plan, spielfest);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			if (line.hasOption("csv")) {
				CvsOut test = new CvsOut(inputfile.getAbsolutePath().replace(inputfile.getName(), "Spielplan.csv"));
				try {
					test.printPlanCvs(plan, spielfest);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		} catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
		}


	}

	private static Options buildOptions() {
		Options options = new Options();
		options.addOption("h", "help", false, "This help");
		options.addOption("i", "input", true, "Dateiname mit Pfad zur Spielfestdefinition im json Format");
		options.addOption("p", "csv", false, "Wenn eine csv erzeugt werden soll");
		return options;
	}

	private static void printTemplate(String eingabedateiTemplate) {
		Verein[] vereine = {new Verein("Beispielverein1", 2), new Verein("Beispielverein2", 3), new Verein("Beispielverein3", 4)};
		Spielfest test = new Spielfest(3, 4, "12.2.24", "lessenich", "12:00", vereine);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter fileWriter = new FileWriter(eingabedateiTemplate)){

			gson.toJson(test, fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
