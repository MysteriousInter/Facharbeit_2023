package de.korittky.spielplangenerator;

public class Verein {
    private String vereinsname;
    private int anzahlTeams;

    private Team[] teams;
    public Verein(){
    }
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

    public void init()  {
        this.teams= new Team[anzahlTeams];
        for (int i = 0; i < anzahlTeams; i++) {
            this.teams[i]=new Team(this,i);
        }
    }

}
