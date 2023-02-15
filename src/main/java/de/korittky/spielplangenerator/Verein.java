package de.korittky.spielplangenerator;

/**
 * Die Vereinsklasse repraesentiert einen Verein, der am Spielfest teilnimmt.
 * Sie enthält ein Array mit den ihr zugehörigen Teams nach Stärke (Teamnummer) geordnet.
 * Außerdem enthält sie noch den Namen des Vereins und die Menge von Teams, die dieser Mitbringt.
 */
public class Verein {
    private String vereinsname;
    private int anzahlTeams;

    private Team[] teams;
    public Verein(){
    }

    /**
     * Dieser Verein ist Team.EMPTYTEAM zugeordnet
     */
    public static Verein EMPTYVEREIN=new Verein("",1);
    public Verein(String vereinsname, int anzahlTeams){
        this.anzahlTeams =anzahlTeams;
        this.vereinsname = vereinsname;
    }

    public Team getTeam(int i) {
        return teams[i];
    }

    public Team[] getTeams(){
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }


    public int getAnzahlTeams() {
        return anzahlTeams;
    }

    public void setAnzahlTeams(int anzahlTeams) {
        this.anzahlTeams = anzahlTeams;
    }

    public String getVereinsname() {
        return vereinsname;
    }

    public void setVereinsname(String vereinsname) {
        this.vereinsname = vereinsname;
    }

    /**
     * Die Menge der Teams wird aus dem Parameter anzahlTeams des Vereins erzeugt.
     */
    public void init()  {
        this.teams = new Team[anzahlTeams];
        for (int i = 0; i < anzahlTeams; i++) {
            this.teams[i] = new Team(this,i);
        }
    }

}
