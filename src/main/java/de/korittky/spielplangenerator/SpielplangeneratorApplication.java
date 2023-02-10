package de.korittky.spielplangenerator;

import com.google.gson.stream.JsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

@SpringBootApplication
public class SpielplangeneratorApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(SpielplangeneratorApplication.class, args);
		//Verein test= new Verein("tollerverein",2);

/*
		Gson gson2 = new Gson();
		JsonReader reader = new JsonReader(new FileReader("F:/IdeaProjects/Facharbeit/spielplangenerator/src/main/resources/testSpielfest.txt"));
		Spielfest spielfest = gson2.fromJson(reader, Spielfest.class);
		spielfest.init();
		Output test=new Output();
		test.printTeams(spielfest);
		FillPlan fillPlan=new FillPlan(spielfest);

		PfdOut xdd =new PfdOut("F://IdeaProjects//Facharbeit//spielplangenerator/test.pdf");
		Plan plan = new Plan(1, 1);
		Runde runde1 = new Runde(1);
		for(Verein verein : spielfest.getVereine()) {
			for(Team team : verein.getTeams()) {
				Spiel spiel1 = new Spiel(team, team);
				runde1.setSpiel(spiel1,0);
			}
		}
		plan.setRunde(0,runde1 );

		try {
			xdd.printPlanPdf(plan);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
*/
		Gson gson2 = new Gson();
		JsonReader reader = new JsonReader(new FileReader("F:/IdeaProjects/Facharbeit/spielplangenerator/src/main/resources/testSpielfest3.txt"));
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


	}

}
