package de.korittky.spielplangenerator;

public class Plan {
    Runde [] plan;
    public Plan(int anzahlTeams, int runden){
        plan=new Runde[runden];
        for (int r = 0; r < runden; r++) {
            plan[r]=new Runde(anzahlTeams,r);
        }
    }
    public String[][] planToArray(){
        String[][] ret=new String[plan.length][plan[1].getAnzahlSpiele()];

        int i=0;
        for(Runde runde : this.getRunden()){
            for(Spiel spiel : runde.getSpiele()){
                int j=0;
                for(Team team : spiel.getTeams()){
                    ret[i][j]= team.toString();
                    j++;
                }
            }
            i++;
        }
        return ret;
    }

    public Runde getRunde(int i){
        return this.plan[i];
    }
    public Runde[] getRunden(){
        return plan;
    }

    public void setRunde(int i, Runde runde){
        this.plan[i]=runde;
    }
}
