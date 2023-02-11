package de.korittky.spielplangenerator;

import java.util.Comparator;
import java.util.PriorityQueue;

public class FillPlan {
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

    Spielfest spielfest;
    public boolean[][] usedMatchups =null;
    protected Team[] alleTeams;

    public FillPlan(Spielfest spielfest){
        this.spielfest=spielfest;
        init(spielfest.getAlleTeams());
        System.out.print("");
    }


    public boolean isMatchupLegal(Team t1,Team t2,TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp){
        if(!(teamStillHasToBeMatchedUp.isAvailable(t1.getTeamNummerGlobal())&&teamStillHasToBeMatchedUp.isAvailable(t2.getTeamNummerGlobal()))){
            return false;
        }
        return !usedMatchups[t1.getTeamNummerGlobal()][t2.getTeamNummerGlobal()];
    }
    public boolean isMatchupLegal(int t1,int t2 ){
        return !usedMatchups[t1][t2];
    }
    public void resetMatchup(Team t1, Team t2){
        usedMatchups[t1.teamNummerGlobal][t2.getTeamNummerGlobal()]=false;
        usedMatchups[t2.getTeamNummerGlobal()][t1.teamNummerGlobal]=false;
    }

    public void setMatchup(Team t1, Team t2){
        usedMatchups[t1.teamNummerGlobal][t2.getTeamNummerGlobal()]=true;
        usedMatchups[t2.getTeamNummerGlobal()][t1.teamNummerGlobal]=true;
    }
    public void removeMatchup(Team t1, Team t2){
        usedMatchups[t1.teamNummerGlobal][t2.getTeamNummerGlobal()]=false;
        usedMatchups[t2.getTeamNummerGlobal()][t1.teamNummerGlobal]=false;
    }

    public Plan fillRek(Plan plan,Team[] teams){
       for(Runde runde : plan.getRunden()){
           fillRundeRek(runde,new TeamStillHasToBeMatchedUp(alleTeams.length),0);
       }
        return plan;
    }
    public boolean fillRundeRek(Runde runde, TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp , int spielnummer) {
       if (spielnummer == runde.getAnzahlSpiele()){
           System.out.print(teamStillHasToBeMatchedUp.giveNextFrei());
           return true;
       }
       PriorityQueue<Spiel> pq = priorityQueueMitNochNichtGepieltenSpielen(teamStillHasToBeMatchedUp);
       while (!pq.isEmpty()) {
           Spiel s = pq.poll();
           setMatchup(s.getTeam1(), s.getTeam2());
           teamStillHasToBeMatchedUp.setHasBeenMatched(s.getTeam1().getTeamNummerGlobal(),s.getTeam2().getTeamNummerGlobal());
           runde.setSpiel(s,spielnummer);
           if (fillRundeRek(runde, teamStillHasToBeMatchedUp,spielnummer+1)) return true;
           removeMatchup(s.getTeam1(), s.getTeam2());
           teamStillHasToBeMatchedUp.freeUp(s.getTeam1().getTeamNummerGlobal(),s.getTeam2().getTeamNummerGlobal());
       }
       return false;
    }
    static class SpielComparator implements Comparator<Spiel> {
        public int compare(Spiel s1, Spiel s2) {
            return Integer.compare(scorediff(s1),scorediff(s2));
        }
        private int scorediff(Spiel spiel){
            if (spiel.getTeam1()==Team.EMPTYTEAM||spiel.getTeam2()==Team.EMPTYTEAM){
                return 10;
            }
            return Math.abs(spiel.getTeam1().getTeamNummerVerein()-spiel.getTeam2().getTeamNummerVerein());
        }
    }
    PriorityQueue<Spiel> priorityQueueMitNochNichtGepieltenSpielen(TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp) {
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
        usedMatchups =new boolean[alleTeams.length][alleTeams.length];
        this.alleTeams=alleTeams;
        for(Team team1: alleTeams){
            for(Team team2 : alleTeams){
                if(team1.getVerein()==team2.getVerein()){
                    usedMatchups[team1.teamNummerGlobal][team2.getTeamNummerGlobal()]=true;
                }else {
                    usedMatchups[team1.teamNummerGlobal][team2.getTeamNummerGlobal()]=false;
                }
            }
        }
    }

}
