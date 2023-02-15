package de.korittky.spielplangenerator;

/**
 * Die Plan-Klasse enthält alle Runden des Spielfestes
 */
public class Plan {
    Runde [] plan;
    public Plan(int anzahlTeams, int runden){
        plan=new Runde[runden];
        for (int r = 0; r < runden; r++) {
            plan[r] = new Runde(anzahlTeams, r);
        }
    }
    public Runde[] getRunden(){
        return plan;
    }
}
