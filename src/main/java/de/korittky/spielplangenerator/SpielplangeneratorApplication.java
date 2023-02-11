package de.korittky.spielplangenerator;

import com.google.gson.stream.JsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.PriorityQueue;

@SpringBootApplication
public class SpielplangeneratorApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(SpielplangeneratorApplication.class, args);
		//Verein test= new Verein("tollerverein",2);
		String eingabedatei= "F:/IdeaProjects/Facharbeit/spielplangenerator/src/main/resources/testSpielfest3.txt";

		if (args.length>0) {
			eingabedatei=args[0];
		}

		Gson gson2 = new Gson();
		JsonReader reader = new JsonReader(new FileReader(eingabedatei));
		Spielfest spielfest = gson2.fromJson(reader, Spielfest.class);
		spielfest.init();
		Plan plan=new Plan(spielfest.getAlleTeams().length,spielfest.getAnzahlRunden());
		FillPlan fillPlan=new FillPlan(spielfest);
		fillPlan.fillRek(plan, spielfest.getAlleTeams());
		PdfOut pdfOut =new PdfOut("F://IdeaProjects//Facharbeit//spielplangenerator/test3.pdf");
		try {
			pdfOut.printPlanPdf(plan,spielfest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		TxtOut test = new TxtOut();
		try {
			test.printPlanTxt(plan,spielfest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}

}
