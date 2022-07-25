package com.github.EthanCosta.odysseyus;

import javafx.application.Application;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("javafx.application.Application");
            System.out.println("Made by Tathan and bibi_fire because i suck in java");
            Application.launch(Launcher.class, args);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erreur:\n" + e.getMessage() + " not find",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


}
