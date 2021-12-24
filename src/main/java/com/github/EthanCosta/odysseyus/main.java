package com.github.EthanCosta.odysseyus;

import com.github.EthanCosta.odysseyus.ui.panels.pages.content.Home;
import com.github.EthanCosta.odysseyus.ui.panels.pages.content.addons;
import javafx.application.Application;

import javax.swing.*;
import javafx.scene.control.TextField;


public class main {

    public static void main(String[] args) {
        try {
            Class.forName("javafx.application.Application");
            Application.launch(Launcher.class, args);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erreur:\n" + e.getMessage() + " not find",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );

        }

        System.out.println(addons.modAddons);
    }


}
