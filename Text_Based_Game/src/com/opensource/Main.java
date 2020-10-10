package com.opensource;

import com.opensource.terminal.MainTerminal;

public class Main {
    public static void main(String[] args) {
        MainTerminal mainTerminal = new MainTerminal();
        mainTerminal.init();
        mainTerminal.run();
    }
}
