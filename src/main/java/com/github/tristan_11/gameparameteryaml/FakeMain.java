package com.github.tristan_11.gameparameteryaml;

/**
 * This class is just a fake main, that calls the real main method to start the programm.
 * When creating a jar, the main must not extend anything and the original main method
 * extends Application.
 */
public class FakeMain {
    public static void main(String[] args) {
        Main.main(args);
    }
}
