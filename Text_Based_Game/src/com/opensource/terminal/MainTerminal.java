package com.opensource.terminal;

import com.opensource.roles.MainCharatcter;
import com.opensource.roles.RivalCharacter;

import java.util.Scanner;

public class MainTerminal {
    public String input;
    public String output;
    public MainCharatcter mainCharatcter;
    public RivalCharacter rivalCharacter;

    public MainTerminal() {
        input = "";
        output = "";
        mainCharatcter = new MainCharatcter();
        rivalCharacter = new RivalCharacter();
    }

    public void init() {
        Scanner scan = new Scanner(System.in);
        System.out.println(" ---- This is a Text Based Pokemon Red/Blue Game ---- ");
        System.out.println("Hello there! Welcome to the world of Pokémon! My name is Oak! People call me the Pokémon \n" +
                "Prof! This world is inhabited by creatures called Pokémon! For some people, Pokémon are pets. Other use\n" +
                "them for fights. Myself… I study Pokémon as a profession. First, what is your name?");
        System.out.print("Enter You Name - ");
        input = scan.nextLine();
        mainCharatcter.setName(input);
        mainCharatcter.setAge(11);

        System.out.println("Right! So your name is " + mainCharatcter.name +"! This is my grandson. He's been your rival\n" +
                " since you were a baby. …Erm, what is his name again?");
        System.out.print("Enter Your Rival's Name -");
        input = scan.nextLine();
        rivalCharacter.setName(input);
        rivalCharacter.setAge(11);

        System.out.println("That's right! I remember now! His name is " +rivalCharacter.name+ "! " + mainCharatcter.name + "!\n" +
                "Your very own Pokémon legend is about to unfold! A world of dreams and adventures with Pokémon awaits! Let's go!");

    }

    public void run() {
        System.out.println("Waiting.. To Be Compelete \uD83D\uDE0E");
        wahtToDo();
    }

    private void wahtToDo() {
        System.out.println("Here you can Decide what to do with users input");
    }

}
