package de.korittky.spielplangenerator;

/**
 * Die Teamklasse Repraesentiert ein Team einer Mannschaft.
 * Sie enthält die globale Teamnummer des Teams (nur für die Sortierung wichtig)
 * und die Teamnummer innerhalb des eigenen Vereins, welche gleichzeitig die Stärke representiert:
 * Eine kleinere Teamnummer bedeutet eine höhere Spielstaerke.
 */
public class Team {

    int teamNummerGlobal;
    int teamNummerVerein;

    /**
     * Der zugehörige Verein eines Teams. Wichtig um zu entscheiden, dass keine zwei Teams des gleichen Vereins
     * gegeneinander spielen.
     */
    Verein verein;

    /**
     * Das EMPTYTEAM ist der Platzhalter für ein nicht stattfindendes Spiel. Im Fall einer insgesamt ungeraden Anzahl
     * von Teams, muss pro Runde eine Mannschaft aussetzen. Dies wird mit meinem Spiel des aussetzenden Teams gegen
     * EMPTYTEAM abgebildet. Zusammen mit der allgemeinen Bedingung, dass jedes Spiel von zwei Teams
     * nur einmal pro Spielfest stattfinden darf, erreichen wir, dass eine Mannschaft höchstens einmal aussetzt.
     * Die Hohe Teamnummer bewirkt, dass die Begegnung des aussetzenden Teams als letzte einer Spielrunde
     * gewählt wird, weil sie aufgrund der hohen Spielstärkendifferenz immer die ungünstigste Begegnung ist.
     */
    public static Team EMPTYTEAM=new Team(Verein.EMPTYVEREIN,1000);
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
