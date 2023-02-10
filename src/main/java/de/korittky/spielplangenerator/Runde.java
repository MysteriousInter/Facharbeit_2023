package de.korittky.spielplangenerator;

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


    public Spiel[]getSpiele(){
        return spiele;
    }
    public int getAnzahlSpiele(){return spiele.length;}

    public void setSpiel(Spiel spiel, int i) {
        this.spiele[i] = spiel;
    }
}
