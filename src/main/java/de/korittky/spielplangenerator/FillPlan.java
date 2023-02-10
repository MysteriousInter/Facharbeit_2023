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
           fillRundeRek2(runde,new TeamStillHasToBeMatchedUp(alleTeams.length),0);
       }

        //fillRundeRek2(plan.getRunde(0),0);
        return plan;
    }

   /* public void fillRundeRek(Runde runde,FreieTeams freieTeams){
        if(freieTeams.isEmpty())return;
        for (Spiel spiel: runde.getSpiele()){
            while (spiel.getTeam1()==Team.EMPTYTEAM) {
                int t1 = freieTeams.giveNextFrei();
                int t2 = freieTeams.giveNextFrei(t1);
                if (isMatchupLegal(alleTeams[t1], alleTeams[t2])) {
                    spiel.setTeam1(alleTeams[t1]);
                    spiel.setTeam2(alleTeams[t2]);
                    setMatchup(alleTeams[t1], alleTeams[t2]);
                } else {
                freieTeams.freeUp(t1);
                t1=t2;
                t2=freieTeams.giveNextFrei(t1);
                }
            }
        }

    }
   public boolean fillRundeRek(Runde runde, TeamsDieNochNichtinRundeGespieltHaben teamsDieNochNichtinRundeGespieltHaben){
       if(teamsDieNochNichtinRundeGespieltHaben.isEmpty())return true;
       for (Spiel spiel: runde.getSpiele()){
            if(spiel.isEmpty()){
                for (int i=0;i<schrödingersliste.length;i++) {
                    for (int j=0;j<schrödingersliste.length;j++) {
                        if(isMatchupLegal(schrödingersliste[i],schrödingersliste[j])){
                            setMatchup(schrödingersliste[i],schrödingersliste[j]);
                            if (fillRundeRek(runde,schrödingersliste))return true;
                            removeMatchup(schrödingersliste[i],schrödingersliste[j]);
                        }
                    }
                }
            }
       }
       return false;
   }*/

    public boolean fillRundeRek2(Runde runde,TeamStillHasToBeMatchedUp teamStillHasToBeMatchedUp ,int spielnummer) {
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
           if (fillRundeRek2(runde, teamStillHasToBeMatchedUp,spielnummer+1)) return true;
           removeMatchup(s.getTeam1(), s.getTeam2());
           teamStillHasToBeMatchedUp.freeUp(s.getTeam1().getTeamNummerGlobal(),s.getTeam2().getTeamNummerGlobal());
       }
       return false;
    }
    static class SpielComparator implements Comparator<Spiel> {

        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(Spiel s1, Spiel s2) {
//            if (Math.abs(s1.getTeam1().getTeamNummerVerein()-s1.getTeam2().getTeamNummerVerein())
//              < Math.abs(s1.getTeam1().getTeamNummerVerein()-s1.getTeam2().getTeamNummerVerein()))
            return Integer.compare(Math.abs(s1.getTeam1().getTeamNummerVerein()-s1.getTeam2().getTeamNummerVerein()),
                    Math.abs(s2.getTeam1().getTeamNummerVerein()-s2.getTeam2().getTeamNummerVerein()));
//                return 1;
//            else if (s1.cgpa > s2.cgpa)
//                return -1;
//            return 0;
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
