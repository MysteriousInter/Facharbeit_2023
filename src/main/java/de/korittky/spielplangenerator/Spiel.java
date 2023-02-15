package de.korittky.spielplangenerator;

/**
 * Die Klasse Spiel repraesentiert ein Spiel zweier Teams.
 * Die Klasse enthält ein Array, welches die beiden Teams enthält.
 */
public class Spiel {

    private Team[] teams=new Team[2];

    public Spiel(Team t1, Team t2){
        this.teams[0] = t1;
        this.teams[1] = t2;

    }
    public Spiel(){
        this.teams[0] = Team.EMPTYTEAM;
        this.teams[1] = Team.EMPTYTEAM;
    }

    public Team getTeam1() {
        return teams[0];
    }
    public Team getTeam2() {
        return teams[1];
    }
    public Team[] getTeams(){
        return teams;
    }

    /**
     * Bei einem Machup gegen EMPTYTEAM stattdessen "TEAM setzt aus" ins PDF zu drucken.
     */
    @Override
    public String toString(){
        if(getTeam1()==Team.EMPTYTEAM) {
            return getTeam2().toString()+" setzt aus";
        }
        if(getTeam2()==Team.EMPTYTEAM) return getTeam1().toString()+" setzt aus";
        return getTeam1()+":"+getTeam2();
    }

}
