package de.korittky.spielplangenerator;

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

    public void setAnzahlRunden(int anzahlRunden) {
        this.anzahlRunden = anzahlRunden;
    }

    public int getFelderKlein() {
        return felderKlein;
    }

    public void setFelderKlein(int felderKlein) {
        this.felderKlein = felderKlein;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getGastgeber() {
        return gastgeber;
    }

    public void setGastgeber(String gastgeber) {
        this.gastgeber = gastgeber;
    }

    public String getBeginnzeit() {
        return beginnzeit;
    }

    public void setBeginnzeit(String beginnzeit) {
        this.beginnzeit = beginnzeit;
    }


    public Verein[] getVereine() {
        return vereine;
    }
    public Team[] getAlleTeams(){return alleTeams;}


    public void init() {
        int teamNummer=0;
        for (Verein verein : getVereine()) {
            verein.init();
            for(Team team : verein.getTeams()){
                team.setTeamNummerGlobal(teamNummer++);
            }
        }

        final int anzahlTeams=teamNummer;
        boolean gerade=(0==anzahlTeams%2)?true:false;
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
