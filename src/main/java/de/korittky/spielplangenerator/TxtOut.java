package de.korittky.spielplangenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TxtOut {
    private String dest="F:/IdeaProjects/Facharbeit/spielplangenerator/test.txt";
    public TxtOut(){
    }
    public void write() throws IOException {



    }
    public void printPlanTxt(Plan plan, Spielfest spielfest)throws Exception{
        PrintWriter writer = new PrintWriter(new FileWriter(dest));

        int spielnummer = 1;
        for(Runde runde : plan.getRunden()){
            writer.println("Runde "+(runde.getRundennummer()+1));
            int platz=1;
            for(Spiel spiel : runde.getSpiele()){
            writer.println((spielnummer++)+": "+spiel.toString());
            }
        }
        writer.close();
    }

}
