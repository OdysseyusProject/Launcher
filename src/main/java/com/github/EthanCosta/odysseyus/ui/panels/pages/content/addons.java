package com.github.EthanCosta.odysseyus.ui.panels.pages.content;

import com.github.EthanCosta.odysseyus.Launcher;
import com.github.EthanCosta.odysseyus.ui.PanelManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;

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
import java.util.ArrayList;
import java.util.List;

public class addons extends contentpanel {
    //private final Saver saver = Launcher.getInstance().getSaver()
    public final Path launcherDir = GameDirGenerator.createGameDir("OdysseyusV2", true);
    GridPane contentPane = new GridPane();
    CheckBox dynamic_surroundings = new CheckBox("Dynamic Surroundings");


    CheckBox boobs_mod = new CheckBox("Wildfire's Female Gender Mod");
    CheckBox inventory_hud_plus = new CheckBox("Inventory HUD +");
    CheckBox item_physics = new CheckBox("ItemPhysic Full");
    CheckBox better_animation2 = new CheckBox("Better Animations Collection");
    CheckBox Controllable = new CheckBox("Controllable");
    Button tutosbtn = new Button("Infos Addons");


    public final Saver saver = new Saver(Path.of(launcherDir.toString(), "addons.properties"));

    public static List<CurseFileInfo> modAddons = new ArrayList<>();




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

        //Dyna

        dynamic_surroundings.getStyleClass().add("addons-mods");
        setLeft(dynamic_surroundings);
        setTop(dynamic_surroundings);
        dynamic_surroundings.setTranslateX(10d);
        dynamic_surroundings.setIndeterminate(false);
        dynamic_surroundings.setTranslateY(60d);
        setCanTakeAllSize(dynamic_surroundings);
        dynamic_surroundings.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                modAddons.add(new CurseFileInfo(238891, 3502320)); //dynamic surrounding
                System.out.println("Dynamic Surroundings add");
                saver.set("dynamic", "true");
                saver.save();


            } else {

                modAddons.remove(new CurseFileInfo(238891, 3502320));
                System.out.println("Dynamic Surroundings remove");
                saver.remove("dynamic");
                saver.save();

            }

        });
        contentPane.getChildren().add(dynamic_surroundings);
        dynamic_surroundings.setSelected(Boolean.valueOf(saver.get("dynamic", "false")));


        //Boobs Mod
        boobs_mod.getStyleClass().add("addons-mods");
        setLeft(boobs_mod);
        setTop(boobs_mod);
        boobs_mod.setTranslateX(10d);
        boobs_mod.setTranslateY(85d);
        setCanTakeAllSize(boobs_mod);
        boobs_mod.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                //saver.set("boobs_mod", );
                modAddons.add(new CurseFileInfo(481655, 3459078)); //Wildfire's Female Gender Mod
                System.out.println("Wildfire's Female Gender Mod ajouté");
                saver.set("boobs", "true");
                saver.save();
            } else {
                modAddons.remove(new CurseFileInfo(481655, 3459078)); //Wildfire's Female Gender Mod
                System.out.println("Wildfire's Female Gender Mod supprimé de la liste");
                saver.remove("boobs");
                saver.save();



            }

        });
        contentPane.getChildren().add(boobs_mod);
        boobs_mod.setSelected(Boolean.valueOf(saver.get("boobs", "false")));

        //Inventory HUD +

        inventory_hud_plus.getStyleClass().add("addons-mods");
        setLeft(inventory_hud_plus);
        setTop(inventory_hud_plus);
        inventory_hud_plus.setTranslateX(10d);
        inventory_hud_plus.setTranslateY(110d);
        setCanTakeAllSize(inventory_hud_plus);
        inventory_hud_plus.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                modAddons.add(new CurseFileInfo(357540, 3505181)); //Wildfire's Female Gender Mod
                System.out.println("Inventory HUD+ ajouté");
               saver.set("Inventory", "true");
               saver.save();

            } else {
                modAddons.remove(new CurseFileInfo(357540, 3505181)); //Wildfire's Female Gender Mod
                System.out.println("Inventory HUD+ supprimé de la liste");
                saver.remove("Inventory");
                saver.load();


            }

        });
        contentPane.getChildren().add(inventory_hud_plus);
        inventory_hud_plus.setSelected(Boolean.valueOf(saver.get("Inventory", "false")));


        //item_physics

        item_physics.getStyleClass().add("addons-mods");
        setLeft(item_physics);
        setTop(item_physics);
        item_physics.setTranslateX(10d);
        item_physics.setTranslateY(135d);
        setCanTakeAllSize(item_physics);
        item_physics.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                modAddons.add(new CurseFileInfo(258587, 3285731)); //Item
                modAddons.add(new CurseFileInfo(257814, 3419983)); //creative core
                System.out.println("Item Physics ajouté");
                saver.set("physics", "true");
                saver.save();

            } else {
                modAddons.remove(new CurseFileInfo(258587, 3285731)); //Item
                modAddons.remove(new CurseFileInfo(257814, 3419983)); //creative core

                System.out.println("Item Physics supprimé de la liste");
                saver.remove("physics");
                saver.save();


            }

        });
        contentPane.getChildren().add(item_physics);
        item_physics.setSelected(Boolean.valueOf(saver.get("physics", "false")));




        better_animation2.getStyleClass().add("addons-mods");
        setLeft(better_animation2);
        setTop(better_animation2);
        better_animation2.setTranslateX(10d);
        better_animation2.setTranslateY(160d);
        setCanTakeAllSize(better_animation2);
        better_animation2.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                modAddons.add(new CurseFileInfo(323976, 3517404)); //animation
                modAddons.add(new CurseFileInfo(495476, 3517499)); //Puzzle Libs
                saver.set("better_anim", "true");
                saver.load();



                System.out.println("Better Animations Collection ajouté");
            } else {
                modAddons.remove(new CurseFileInfo(323976, 3517404)); //Animations
                modAddons.remove(new CurseFileInfo(495476, 3517499)); //Puzzle Libs
                System.out.println("Better Animations Collection supprimé de la liste");
                saver.remove("better_anim");
                saver.save();


            }

        });
        contentPane.getChildren().add(better_animation2);
        better_animation2.setSelected(Boolean.valueOf(saver.get("better_anim", "false")));


        Controllable.getStyleClass().add("addons-mods");
        setLeft(Controllable);
        setTop(Controllable);
        Controllable.setTranslateX(10d);
        Controllable.setTranslateY(185d);
        setCanTakeAllSize(Controllable);
        Controllable.selectedProperty().addListener((e, old, newValue) -> {
            if (newValue) {

                modAddons.add(new CurseFileInfo(317269, 3519536)); //Controllable


                System.out.println("Controllable ajouté");
                saver.set("Controllable", "true");

            } else {
                modAddons.remove(new CurseFileInfo(317269, 3519536)); //Controllable
                System.out.println("Controllable supprimé de la liste");
                saver.remove("Controllable");


            }

        });
        contentPane.getChildren().add(Controllable);
        Controllable.setSelected(Boolean.valueOf(saver.get("Controllable", "false")));





        //Explications
        Label titleInfos = new Label("Informations");
        titleInfos.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 20f));
        titleInfos.getStyleClass().add("addons-title");
        setLeft(titleInfos);
        setCanTakeAllSize(titleInfos);
        setTop(titleInfos);
        titleInfos.setTextAlignment(TextAlignment.LEFT);
        titleInfos.setTranslateY(225d);
        titleInfos.setTranslateX(10d);
        contentPane.getChildren().add(titleInfos);

        //Explications
        Label infosLabel = new Label("Ces mods ne sont pas nécessaires au fonctionnement d'Odysseyus et servent à améliorer\nvotre expérience de jeu.\nPour toutes informations : https://odysseyus.fr/addons");
        infosLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 12f));
        infosLabel.getStyleClass().add("addons-title");
        setLeft(infosLabel);
        setCanTakeAllSize(infosLabel);
        setTop(infosLabel);
        infosLabel.setTextAlignment(TextAlignment.LEFT);
        infosLabel.setTranslateY(250d);
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
        tutosbtn.setTranslateY(305d);
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




    }


}