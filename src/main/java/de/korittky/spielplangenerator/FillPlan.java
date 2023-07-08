package de.korittky.spielplangenerator;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Die FillPlan-Klasse befüllt den leeren Plan mit Runden, welche mit Spielen und diese wiederum mit Teams gefüllt werden.
 * Dabei darf kein Spiel zwei Teams des gleichen Vereins enthalten und keine Begegnung zweier Teams mehrmals Vorkommen.
 * Die einzelnen Runden werden rekursiv gefüllt (siehe fillRundeRek).
 */
public class FillPlan {
    /**
     * Hilfsklasse
     */
    private class TeamStillHasToBeMatchedUp {
        private boolean[]rundeGespielt;
        private int anzahlTeams;

        public TeamStillHasToBeMatchedUp(int anzahlTeams) {
            rundeGespielt=new boolean[anzahlTeams];
            this.anzahlTeams=anzahlTeams;
            for (boolean b : rundeGespielt) b = false;
        }
        public int giveNextFrei() {
            for (int i = 0; i < anzahlTeams; i++) {
                if (!rundeGespielt[i]) {
                    rundeGespielt[i]=true;
                    return i;
                }
            }
            //tritt nie ein(hoffentlich)
            return -1;
        }
        public int giveNextFrei(int t1) {
            for (int i = 0; i < anzahlTeams; i++) {
                if (!rundeGespielt[i] && isMatchupLegal(t1, i)) {
                    rundeGespielt[i] = true;
                    return i;
                }
            }
            //tritt nie ein(hoffentlich)
            return -1;
        }
        public void setHasBeenMatched(int t1,int t2){
            this.rundeGespielt[t1]=true;
            this.rundeGespielt[t2]=true;
        }
        public void freeUp(int t1,int t2){
            rundeGespielt[t1]=false;
            rundeGespielt[t2]=false;
        }
        public boolean isAvailable(int i){
            return !rundeGespielt[i];
        }
        public boolean[] getRundeGespielt(){return rundeGespielt;}
        public boolean isEmpty(){
            for (boolean b : rundeGespielt) if(!b)return false;
            return true;
        }
    }

    private Spielfest spielfest = null;
    /**
     * Matrix der schon gvergebenen Matchups. Verhindert doppelte Teambegegnungen.
     */
    private boolean[][] usedMatchups = null;
    private Team[] alleTeams = null;

    public FillPlan(Spielfest spielfest){
        this.spielfest=spielfest;
        init(spielfest.getAlleTeams());
    }

    private boolean isMatchupLegal(Team t1, Team t2, TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp){
        if(!(   teamStillHasToBeMatchedUp.isAvailable(t1.getTeamNummerGlobal())
             && teamStillHasToBeMatchedUp.isAvailable(t2.getTeamNummerGlobal()) )){
            return false;
        }
        return !usedMatchups[t1.getTeamNummerGlobal()][t2.getTeamNummerGlobal()];
    }
    private boolean isMatchupLegal(int t1,int t2 ){
        return !usedMatchups[t1][t2];
    }
    private void setMatchup(Team t1, Team t2){
        usedMatchups[t1.teamNummerGlobal][t2.getTeamNummerGlobal()]=true;
        usedMatchups[t2.getTeamNummerGlobal()][t1.teamNummerGlobal]=true;
    }
    private void removeMatchup(Team t1, Team t2){
        usedMatchups[t1.teamNummerGlobal][t2.getTeamNummerGlobal()]=false;
        usedMatchups[t2.getTeamNummerGlobal()][t1.teamNummerGlobal]=false;
    }

    public Plan fill(Plan plan, Team[] teams){
       for(Runde runde : plan.getRunden()){
           Random random = new Random();
           while (!fillRundeRek(runde, new TeamStillHasToBeMatchedUp(alleTeams.length),0)){
                for(Team team:teams){
                    for (Team team_:teams){
                        if(team != team_ && random.nextBoolean()){
                            removeMatchup(team_,team);
                        }
                    }
                }
           }
       }
        return plan;
    }

    /**
     * Abbruchbedingungen:
     * <ol><li>
     * Die Runde hat genügend Spiele und ist voll (return true).
     * </li><li>
     * Es gibt keine legalen Matchups mehr, um den Rest der Runde zu füllen (return false).
     * </li></ol>
     *
     * Rekursionsschritte:
     * <ol><li>
     *    Eine Priority-Queue Erzeugen, in der die möglichen Spiele danach sortiert sind,
     *    wie groß der Unterschied ihrer Teamnummern im Verein ist (Dadurch werden vermehrt gleichstarke Teams gegeneinander spielen)
     * </li><li>
     *    Aus dieser Priority-Queue das erste Element wählen und in die nächste Rekursionsebene gehen.
     *    Wenn ture zurückgegeben wird, dann wird dieses weiter in der Rekursion zurückgegeben, weil dies bedeutet, dass eine Lösung gefundwen wurde.
     * </li><li>
     *    Wenn der Rekursionsaufruf nicht zu einem Ergebniss geführt hat, dann wird das nächste Element
     *    aus Der Priority Queue probiert bis Abbruchbedingung 2 erfüllt ist und false zurückgegeben wird oder vorher
     *    eine Lösung erreicht wurde und true zurückgegeben wird.
     * </li></ol>
     *
     * @param runde Die Runde in der die Spiele stattfinden sollen
     * @param teamStillHasToBeMatchedUp Die Teams, die in der Runde noch nicht gespielt haben.
     * @param spielnummer Das wievielte Spiel dieser Runde gerade generiert wird (wichtig fuer pos in Spiel-Array in Runde und für Abbruchbedingung)
     * @return Ob der letzte Rekursionschritt erfolgreich war.
     */
    public boolean fillRundeRek(Runde runde, TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp, int spielnummer) {
       if (spielnummer == runde.getAnzahlSpiele()){
           return true;
       }
       PriorityQueue<Spiel> pq = priorityQueueMitNochNichtGespieltenSpielen(teamStillHasToBeMatchedUp);
       while (!pq.isEmpty()) {
           Spiel s = pq.poll();
           setMatchup(s.getTeam1(), s.getTeam2());
           teamStillHasToBeMatchedUp.setHasBeenMatched(s.getTeam1().getTeamNummerGlobal(),s.getTeam2().getTeamNummerGlobal());
           runde.setSpiel(s, spielnummer);
           if (fillRundeRek(runde, teamStillHasToBeMatchedUp,spielnummer+1)) return true;
           // Matchup wieder rueckgängig machen
           removeMatchup(s.getTeam1(), s.getTeam2());
           teamStillHasToBeMatchedUp.freeUp(s.getTeam1().getTeamNummerGlobal(),s.getTeam2().getTeamNummerGlobal());
       }
       return false;
    }

    /**
     * Ermoeglicht eine Sortierung nach "besseren" Matchups. Ein Matchup zwischen zwei Teams vergleichbarer
     * Spielstaerke (anhand ihrer Teamnummern) ist besser als ein Matchup zwischen zwwei Teams mit grösserer
     * Differenz ihrer Spielstaerke,
     */
    static class SpielComparator implements Comparator<Spiel> {
        public int compare(Spiel s1, Spiel s2) {
            return Integer.compare(scorediff(s1),scorediff(s2));
        }
        private int scorediff(Spiel spiel){
            if (spiel.getTeam1()==Team.EMPTYTEAM||spiel.getTeam2()==Team.EMPTYTEAM){
                return 1000;
            }
            return Math.abs(spiel.getTeam1().getTeamNummerVerein()-spiel.getTeam2().getTeamNummerVerein());
        }
    }

    /**
     * Erzeuge eine PriorityQueue, die zuerst Spiele von Teams gleicher Spielstaerke ausspuckt und später unguenstigere.
     * @param teamStillHasToBeMatchedUp
     * @return
     */
    PriorityQueue<Spiel> priorityQueueMitNochNichtGespieltenSpielen(TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp) {
        PriorityQueue<Spiel> pq = new PriorityQueue<>(new SpielComparator());
        for (Team t1 : alleTeams) {
            for (Team t2 : alleTeams) {
                if (isMatchupLegal(t1, t2, teamStillHasToBeMatchedUp)) {
                    pq.add(new Spiel(t1, t2));
                }
            }
        }
        return pq;
    }

    public void init(Team[] alleTeams){
        usedMatchups = new boolean[alleTeams.length][alleTeams.length];
        this.alleTeams = alleTeams;
        for(Team team1: alleTeams){
            for(Team team2 : alleTeams){
                if(team1.getVerein()==team2.getVerein()){
                    usedMatchups[team1.teamNummerGlobal][team2.getTeamNummerGlobal()]=true;
                } else {
                    usedMatchups[team1.teamNummerGlobal][team2.getTeamNummerGlobal()]=false;
                }
            }
        }
    }
}
