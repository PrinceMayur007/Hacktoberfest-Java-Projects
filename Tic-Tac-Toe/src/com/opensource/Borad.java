package com.opensource;

public class Borad {
    private String place1;
    private String place2;
    private String place3;
    private String place4;
    private String place5;
    private String place6;
    private String place7;
    private String place8;
    private String place9;

    public Borad() {
        this.place1 =  "1";
        this.place2 =  "2";
        this.place3 =  "3";
        this.place4 =  "4";
        this.place5 =  "5";
        this.place6 =  "6";
        this.place7 =  "7";
        this.place8 =  "8";
        this.place9 =  "9";
    }


    @Override
    public String toString() {
        String board;

        board = "\t" + place1 + " | " + place2 + " | " + place3 + "\n" +
                "\t" + place4 + " | " + place5 + " | " + place6 + "\n" +
                "\t" + place7 + " | " + place8 + " | " + place9 + "\n";

        return board;
    }
}
