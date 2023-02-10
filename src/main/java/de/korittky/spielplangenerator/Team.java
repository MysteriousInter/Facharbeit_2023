package de.korittky.spielplangenerator;

public class Team {

    int teamNummerGlobal;
    int teamNummerVerein;

    Verein verein;
    public static Team EMPTYTEAM=new Team();
    public Team(){

    }
    public Team(Verein verein, int teamNummer){
        this.teamNummerVerein = teamNummer;
        this.verein=verein;
    }
    public int getTeamNummerGlobal() {
        return teamNummerGlobal;
    }

    public void setTeamNummerGlobal(int teamNummerGlobal) {
        this.teamNummerGlobal = teamNummerGlobal;
    }


    public int getTeamNummerVerein() {
        return teamNummerVerein;
    }

    public void setTeamNummerVerein(int teamNummerVerein) {
        this.teamNummerVerein = teamNummerVerein;
    }

    public Verein getVerein() {
        return verein;
    }

    @Override
    public String toString() {
        return verein.getVereinsname()+" "+(teamNummerVerein +1);
    }
}
