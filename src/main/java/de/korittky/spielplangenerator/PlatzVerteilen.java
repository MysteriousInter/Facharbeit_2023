package de.korittky.spielplangenerator;

/**
 * Sortiert einen fertigen Plan so, dass die Teams möglichst
 * gleichmässig oft auf kleinen und grossen plaetzen spielen.
 * die letzten n Spiele jeder Runde sind immer die n Kleinplaetze
 */
public class PlatzVerteilen {
    Spielfest spielfest;
    Plan plan;
    int kleinFelder;
    int aussetzen=0;
    public PlatzVerteilen(Spielfest spielfest, Plan plan){
        this.spielfest=spielfest;
        this.plan=plan;
        this.kleinFelder=spielfest.getFelderKlein();
        //wenn ungerade Anzahl teams(EMPTYTEAM nicht eingeschlossen), dann wird aussetzten auf 1 gesetzt um später das Aussetztspiel nicht von der letzten Stelle wegzutauschen.
        if(spielfest.getAlleTeams()[spielfest.getAlleTeams().length-1]==Team.EMPTYTEAM){
            this.aussetzen=1;
        }
    }
    public Plan platzVerteilen(){
        int posLow=0;
        int [] anzahlKleinGespielt= new int[spielfest.getAlleTeams().length];
        for(int i : anzahlKleinGespielt){
            anzahlKleinGespielt[i]=0;
        }
        for(Runde runde : plan.getRunden()) {
            for (int i = 1; i < kleinFelder+1; i++) {
                //for (Spiel spiel : runde.getSpiele()) {
                posLow=-1;
                for (int j = runde.getAnzahlSpiele() - i; j > 0; j--) {
                    Spiel spiel = runde.getSpiel(j-1);
                    int f = 1000;
                    if (f > (getSpielKleinfeldwert(spiel, anzahlKleinGespielt))) {
                        f = (getSpielKleinfeldwert(spiel, anzahlKleinGespielt));
                        posLow = j-1;
                    }
                }
                anzahlKleinGespielt[runde.getSpiel(posLow).getTeam1().teamNummerGlobal]++;
                anzahlKleinGespielt[runde.getSpiel(posLow).getTeam2().teamNummerGlobal]++;
                runde.swapSpiele(posLow,runde.getAnzahlSpiele()-i-aussetzen);
            }
        }
        return plan;
    }
    private int getSpielKleinfeldwert(Spiel s1, int[] anzahlKleinGespielt){
        return anzahlKleinGespielt[s1.getTeam1().teamNummerGlobal]+anzahlKleinGespielt[s1.getTeam2().teamNummerGlobal];
    }
}
