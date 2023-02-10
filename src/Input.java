package de.korittky.spielplangenerator;

import java.util.Scanner;

public class Input {
    public Verein newVerein(){
        Scanner sc = new Scanner(System.in);
        int teams= sc.nextInt();
        String name=sc.next();


        Verein ret =new Verein(name,teams);
        return ret;
    }

}
