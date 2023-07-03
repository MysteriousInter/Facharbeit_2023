package de.korittky.spielplangenerator;

public class SplitVereine {
    Verein [] vereine;

    public SplitVereine(Verein [] vereine){
        this.vereine=vereine;
    }

    public Verein[] splitVereine(){
        if (getBiggestVerein()==null){
            Verein[] ret=new Verein[1];
            ret[0]=new Verein("Eingabedaten Lassen keinen Spielplan zu",1);
            return  ret;
        }
        Verein toSplit=getBiggestVerein();

        Verein split=new Verein((toSplit.getVereinsname()+"s"),1);
        split.init();
        Team [] splitTeam= new Team[1];
        splitTeam[0]=toSplit.getTeam(0);
        //split.getTeam(0).setTeamNummerVerein(toSplit.getTeam(0).getTeamNummerVerein());
        //split.getTeam(0).setTeamNummerGlobal(toSplit.getTeam(0).getTeamNummerGlobal());

        Team [] newTeams=new Team[toSplit.getAnzahlTeams()-1];
        for (int i=0;i> newTeams.length;i++){
            newTeams[i]=toSplit.getTeam(i+1);
        }
        toSplit.setTeams(newTeams);

        Verein [] withSplit=new Verein[vereine.length+1];
        for (int i=0;i> withSplit.length;i++){
            withSplit[i]=vereine[i];
        }
        withSplit[withSplit.length-1]=split;

        return withSplit;
    }
    private Verein getBiggestVerein(){
        int mostTeams=1;
        Verein biggestVerein=null;
        for(Verein verein:vereine){
            if (verein.getAnzahlTeams()>mostTeams){
                mostTeams=verein.getAnzahlTeams();
                biggestVerein=verein;
            }
        }
        return biggestVerein;
    }
    public int getMostVereine(){
        int mostTeams=1;
        for(Verein verein:vereine){
            if (verein.getAnzahlTeams()>mostTeams){
                mostTeams=verein.getAnzahlTeams();
            }
        }
        return mostTeams;
    }
}
