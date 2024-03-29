package de.korittky.spielplangenerator;
/**
 * Gibt den übergebenen Plan als Cvs aus
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class CvsOut {
    String dest;
    public CvsOut(String dest){
        this.dest = dest;
    }

    public void printPlanCvs(Plan plan, Spielfest spielfest)throws Exception{
        // Das ISO-Format ist wichtig fuer das Lesen deutscher Umlaute in Excel.
        PrintWriter writer = new PrintWriter(new FileWriter(dest, Charset.forName("ISO-8859-15")));

        int spielnummer = 1;
        for(Runde runde : plan.getRunden()){
            writer.println("Runde "+(runde.getRundennummer()+1));
            int platz=1;
            for(Spiel spiel : runde.getSpiele()){
            writer.println((spielnummer++)+";"+spiel.getTeam1()+";"+spiel.getTeam2()+";"+platz++);
            }
        }
        writer.close();
    }

}
