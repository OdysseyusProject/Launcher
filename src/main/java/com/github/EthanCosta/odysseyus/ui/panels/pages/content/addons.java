package com.github.EthanCosta.odysseyus.ui.panels.pages.content;

import com.github.EthanCosta.odysseyus.Launcher;
import com.github.EthanCosta.odysseyus.ui.PanelManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;

import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class addons extends contentpanel {
    //private final Saver saver = Launcher.getInstance().getSaver()
    public final Path launcherDir = GameDirGenerator.createGameDir("Odysseyus", true);
    GridPane contentPane = new GridPane();

    Button tutosbtn = new Button("Infos Addons");

    CheckBox replaymod = new CheckBox("Replay Mod");
    CheckBox dyna = new CheckBox("Dynamic Surrounding");
    CheckBox controllable = new CheckBox("controllable");




    public final Saver saver = new Saver(Paths.get(launcherDir.toString(), "addons.properties"));

    public static List<Mod> modAddons = new ArrayList<>();
    public static List<CurseFileInfo> curseModAddons = new ArrayList<>();

    public String getName() {
        return "addons";
    }

    @Override
    public String getStylesheetPath() {
        return "css/content/addons.css";
    }


    @Override
    public void onShow() {
        super.onShow();
    }

    public void init(PanelManager panelManager) {
        super.init(panelManager);

        // Background
        this.layout.getStyleClass().add("settings-layout");
        this.layout.setPadding(new Insets(40));
        setCanTakeAllSize(this.layout);

        // Content
        contentPane.getStyleClass().add("content-pane");
        setCanTakeAllSize(contentPane);
        this.layout.getChildren().add(contentPane);

        // Titre
        Label title = new Label("Addons");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 30f));
        title.getStyleClass().add("addons-title");
        setLeft(title);
        setCanTakeAllSize(title);
        setTop(title);
        title.setTextAlignment(TextAlignment.LEFT);
        title.setTranslateY(10d);
        title.setTranslateX(10d);
        contentPane.getChildren().add(title);


        replaymod.getStyleClass().add("addons-mods");
        setLeft(replaymod);
        setTop(replaymod);
        replaymod.setTranslateX(10d);
        replaymod.setIndeterminate(false);
        replaymod.setTranslateY(60d);
        setCanTakeAllSize(replaymod);
        replaymod.setFont(Font.font("Verdana", FontPosture.REGULAR, 13f));
        replaymod.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                modAddons.add(new Mod("Replaymod.jar", "984489e9ec23c1b4e6c4ff5a00b35eef1be8dde8", 13504148, "https://odysseyus.fr/mods/replaymod.jar"));
                System.out.println("ReplayMod");
                saver.set("replay", "true");
                saver.save();


            } else {

                modAddons.remove(new Mod("Replaymod.jar", "984489e9ec23c1b4e6c4ff5a00b35eef1be8dde8", 13504148, "https://odysseyus.fr/mods/replaymod.jar"));
                System.out.println("ReplayMod remove");
                saver.remove("replay");
                saver.save();

            }

        });
        contentPane.getChildren().add(replaymod);
        replaymod.setSelected(Boolean.valueOf(saver.get("replay", "false")));


        dyna.getStyleClass().add("addons-mods");
        setLeft(dyna);
        setTop(dyna);
        dyna.setTranslateX(10d);
        dyna.setIndeterminate(false);
        dyna.setTranslateY(80d);
        setCanTakeAllSize(dyna);
        dyna.setFont(Font.font("Verdana", FontPosture.REGULAR, 13f));
        dyna.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                curseModAddons.add(new CurseFileInfo(307806, 2820815)); //OreLib
                curseModAddons.add(new CurseFileInfo(238891, 3497269)); //Dynamic

                System.out.println("Dynamic and OreLib");
                saver.set("dyna", "true");
                saver.save();


            } else {

                curseModAddons.remove(new CurseFileInfo(307806, 2820815)); //OreLib
                curseModAddons.remove(new CurseFileInfo(238891, 3497269)); //Dynamic
                System.out.println("Dynamic and OreLib remove");
                saver.remove("dyna");
                saver.save();

            }

        });
        contentPane.getChildren().add(dyna);
        dyna.setSelected(Boolean.valueOf(saver.get("dyna", "false")));




        controllable.getStyleClass().add("addons-mods");
        setLeft(controllable);
        setTop(controllable);
        controllable.setTranslateX(10d);
        controllable.setIndeterminate(false);
        controllable.setTranslateY(100d);
        setCanTakeAllSize(controllable);
        controllable.setFont(Font.font("Verdana", FontPosture.REGULAR, 13f));
        controllable.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                curseModAddons.add(new CurseFileInfo(317269, 3222475)); //controllable

                System.out.println("controllable");
                saver.set("controllable", "true");
                saver.save();

            } else {
                curseModAddons.add(new CurseFileInfo(317269, 3222475)); //controllable
                System.out.println("controllable remove");
                saver.remove("controllable");
                saver.save();

            }

        });
        contentPane.getChildren().add(dyna);
        controllable.setSelected(Boolean.valueOf(saver.get("controllable", "false")));




        //Explications
        Label titleInfos = new Label("Informations");
        titleInfos.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 22));
        titleInfos.getStyleClass().add("addons-title");
        setLeft(titleInfos);
        setCanTakeAllSize(titleInfos);
        setTop(titleInfos);
        titleInfos.setTextAlignment(TextAlignment.LEFT);
        titleInfos.setTranslateY(250d);
        titleInfos.setTranslateX(10d);
        contentPane.getChildren().add(titleInfos);

        //Explications
        Label infosLabel = new Label("Ces mods ne sont pas nécessaires au fonctionnement d'Odysseyus et servent à améliorer\nvotre expérience de jeu.\nPour toutes informations : https://odysseyus.fr/addons");
        infosLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14f));
        infosLabel.getStyleClass().add("addons-title");
        setLeft(infosLabel);
        setCanTakeAllSize(infosLabel);
        setTop(infosLabel);
        infosLabel.setTextAlignment(TextAlignment.LEFT);
        infosLabel.setTranslateY(275d);
        infosLabel.setTranslateX(10d);
        contentPane.getChildren().add(infosLabel);

        //boutons
        setCanTakeAllSize(tutosbtn);
        FontAwesomeIconView linkBtn = new FontAwesomeIconView(FontAwesomeIcon.LINK);
        tutosbtn.getStyleClass().add("play-btn");
        tutosbtn.setGraphic(linkBtn);
        setCenterV(tutosbtn);
        setCenterH(tutosbtn);
        setTop(tutosbtn);
        setLeft(tutosbtn);

        tutosbtn.setMaxWidth(150);
        tutosbtn.setTranslateX(10d);
        tutosbtn.setTranslateY(330d);
//50
        tutosbtn.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://odysseyus.fr/addons"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.getChildren().add(tutosbtn);

//Moi je suis un BG


    }


}