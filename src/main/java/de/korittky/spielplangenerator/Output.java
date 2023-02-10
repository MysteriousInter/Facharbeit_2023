package de.korittky.spielplangenerator;



public class Output {

    public Output(){
    }

    public void printPlan(Plan plan){
        for(Runde runde : plan.getRunden()){
            for(Spiel spiel : runde.getSpiele()){
                for(Team team : spiel.getTeams()){
                    System.out.print(toString());
                }
            }
        }
    }

    public void printTeams(Spielfest fest){
            for(Team team : fest.getAlleTeams()){
                System.out.println(team);
            }

    }
}
