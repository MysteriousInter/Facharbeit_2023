package de.korittky.spielplangenerator;

/**
 * Diese Klasse repraesentiert eine Runde im gesamten Plan.
 * Sie enthält ein Array mit Spielen, die in dieser Runde gleichzeitig stattfinden.<br/>
 * Die Reihenfolge der Spiele wird implizit der Platzart zugeordnet.
 * Zuerst die Grossfelder, zuletzt die Kleinfelder.
 */
public class Runde {
    Spiel[] spiele;

    int rundennummer;

    public Runde(int anzahlTeams,int rundennummer) {
        this.spiele =new Spiel[(anzahlTeams/2)];
        for (int i = 0; i < (anzahlTeams/2); i++) {
            this.spiele[i]=new Spiel();
        }
        this.rundennummer=rundennummer;
    }

    public Spiel getSpiel(int i) {
        return spiele[i];
    }
    public int getRundennummer() {
        return rundennummer;
    }

    /**
     * Tauscht die Positionen zweier Teams in der Runde.
     * Dies hat evtl. eine implizite Auswirkungen auf den Platz (und die Platzart) auf dem die Spiele stattfinden.
     * @param t1 Ein Team
     * @param t2 Anderes Team
     */
    public void swapSpiele(int t1, int t2){
        Spiel temp= spiele[t1];
        spiele[t1]=spiele[t2];
        spiele[t2]=temp;
    }

    public Spiel[]getSpiele(){
        return spiele;
    }
    public int getAnzahlSpiele(){return spiele.length;}

    public void setSpiel(Spiel spiel, int i) {
        this.spiele[i] = spiel;
    }
}
