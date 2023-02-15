package de.korittky.spielplangenerator;

/**
 * Die Spielfest-Klasse bildet die Eingabeparameter des zu berechnenden Spielfestes ab.
 * Sie enthält einerseits Werte wie datum gastgeber und beginnzeit, welche nur am Ende auf das PDF eingefügt werden.
 * Und andererseits anzahlRunden, was nur benutzt wird, um den Spiel-Plan zu generieren.
 * Den Parameter felderKlein, der nur genutzt wird, um am Ende die Spiele auf die Klein- und Gross-Felder zuzuweisen.
 * Das Array alleteams enthält alle Teams und wird immer dann verwendet, wenn eine flache Struktur
 * (ohne die zugehörigen Vereine) durchlaufen wird (z.B. beim Sortieren).
 * Die Position eines Teams in diesem Array entspricht ihrer globalen Teamnummer.
 * Außerdem enthält die Klasse noch ein Array mit allen Vereinen.
 */
public class Spielfest {

    private int anzahlRunden;
    private int felderKlein;

    private String datum;
    private String gastgeber;
    private String beginnzeit;

    private Verein[] vereine;
    private Team[] alleTeams;

    public Spielfest(int anzahlRunden, int felderKlein, String datum, String gastgeber, String beginnzeit, Verein[] vereine ){
        this.anzahlRunden=anzahlRunden;
        this.felderKlein = felderKlein;
        this.datum=datum;
        this.gastgeber=gastgeber;
        this.beginnzeit=beginnzeit;
        this.vereine=vereine;
    }

    public int getAnzahlRunden() {
        return anzahlRunden;
    }
    public int getFelderKlein() {
        return felderKlein;
    }
    public String getDatum() {
        return datum;
    }
    public String getGastgeber() {
        return gastgeber;
    }
    public String getBeginnzeit() {
        return beginnzeit;
    }
    public Verein[] getVereine() {
        return vereine;
    }
    public Team[] getAlleTeams(){return alleTeams;}

    /**
     * Weist die globalen Teamnummern zu und fügt evtl. das {@link Team#EMPTYTEAM} hinzu.
     * Am Ende ist die Anzahl der Teams immer gerade.
     */
    public void init() {
        int teamNummer=0;
        for (Verein verein : getVereine()) {
            verein.init();
            for(Team team : verein.getTeams()){
                team.setTeamNummerGlobal(teamNummer++);
            }
        }

        final int anzahlTeams = teamNummer;
        boolean gerade = (0==anzahlTeams%2)?true:false;
        if(gerade){
            alleTeams=new Team[anzahlTeams];
        }else {
            alleTeams=new Team[anzahlTeams+1];
        }

        teamNummer=0;
        for (Verein verein : getVereine()) {
            for(Team team : verein.getTeams()){
                alleTeams[teamNummer++]=team;
            }
            if(!gerade){
                alleTeams[teamNummer]=Team.EMPTYTEAM;
                Team.EMPTYTEAM.setTeamNummerGlobal(teamNummer);
            }
        }

    }

}
