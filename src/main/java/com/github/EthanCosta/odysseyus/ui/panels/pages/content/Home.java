package com.github.EthanCosta.odysseyus.ui.panels.pages.content;

import com.github.EthanCosta.odysseyus.Launcher;
import com.github.EthanCosta.odysseyus.game.MinecraftInfos;
import com.github.EthanCosta.odysseyus.ui.PanelManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.flowarg.flowstringer.StringUtils;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.flowupdater.download.json.OptiFineInfo;

import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;

import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Home extends contentpanel {


    private final Saver saver = Launcher.getInstance().getSaver();
    GridPane boxPane = new GridPane();
    Label stepLabel = new Label();
    Label serverStatus = new Label();
    Label fileLabel = new Label();
    ProgressBar progressBar = new ProgressBar();
    boolean isDownloading = false;



    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getStylesheetPath() {
        return "css/content/home.css";
    }

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setMinHeight(75);
        rowConstraints.setMaxHeight(85);
        this.layout.getRowConstraints().addAll(rowConstraints, new RowConstraints());
        boxPane.getStyleClass().add("box-pane");
        setCanTakeAllSize(boxPane);
        boxPane.setPadding(new Insets(19));
        this.layout.add(boxPane, 0, 0);
        this.layout.getStyleClass().add("home-layout");

        progressBar.getStyleClass().add("download-progress");
        stepLabel.getStyleClass().add("download-status");
        fileLabel.getStyleClass().add("download-status");

        progressBar.setTranslateY(-15);
        setCenterH(progressBar);
        setCanTakeAllSize(progressBar);

        stepLabel.setTranslateY(5);
        setCenterH(stepLabel);
        setCanTakeAllSize(stepLabel);

        fileLabel.setTranslateY(20);
        setCenterH(fileLabel);
        setCanTakeAllSize(fileLabel);

        this.showPlayButton();

    }

    private void showPlayButton() {
        boxPane.getChildren().clear();
        Button playBtn = new Button("Jouer");
        FontAwesomeIconView playIcon = new FontAwesomeIconView(FontAwesomeIcon.GAMEPAD);
        setCanTakeAllSize(playBtn);
        setCenterH(playBtn);
        setCenterV(playBtn);
        playBtn.setMaxWidth(150);
        playBtn.getStyleClass().add("play-btn");
        playBtn.setGraphic(playIcon);
        playBtn.setOnMouseClicked(e -> this.play());
        boxPane.getChildren().add(playBtn);
    }

    private void play() {
        isDownloading = true;
        boxPane.getChildren().clear();
        setProgress(0, 0);
        boxPane.getChildren().addAll(progressBar, stepLabel, fileLabel);

        Platform.runLater(() -> new Thread(this::update).start());
    }

    public void update() {

        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
            private String stepTxt = "";
            private String percentTxt = "0.0%";

            @Override
            public void step(Step step) {
                Platform.runLater(() -> {
                    stepTxt = StepInfo.valueOf(step.name()).getDetails();
                    setStatus(String.format("%s (%s)", stepTxt, percentTxt));

                });
            }


            //@Override TRUC DE MERDE QUI FAIT TOUS BUG
            public void update(DownloadList.DownloadInfo info) {
                Platform.runLater(() -> {
                    percentTxt = decimalFormat.format(info.getDownloadedFiles() * 100.d / info.getTotalToDownloadFiles()) + "%";
                    setStatus(String.format("%s (%s)", stepTxt, percentTxt));
                    setProgress(info.getDownloadedFiles(), info.getTotalToDownloadFiles());
                });
            }

            @Override
            public void onFileDownloaded(Path path) {
                Platform.runLater(() -> {
                    String p = path.toString();
                    fileLabel.setText("..." + p.replace(Launcher.getInstance().getLauncherDir().toFile().getAbsolutePath(), ""));
                });
            }
        };

        try {


            final VanillaVersion version = new VanillaVersion.VanillaVersionBuilder()
                    .withName(MinecraftInfos.GAME_VERSION)
                    .withVersionType(MinecraftInfos.VERSION_TYPE)
                    .build();


            final  List<CurseFileInfo> modInfos = new ArrayList<>();

            modInfos.add(new CurseFileInfo(316867, 3328009)); //ElectroDyna
            modInfos.add(new CurseFileInfo(318646, 3316889)); //nuclear science
            modInfos.add(new CurseFileInfo(443915, 3328078)); //Ballistix
            modInfos.add(new CurseFileInfo(220318, 3300293)); //Biomes O Plenty
            modInfos.add(new CurseFileInfo(303557, 3286395)); //Better Animal +
            modInfos.add(new CurseFileInfo(231095, 3234842)); //chisel and bits
            modInfos.add(new CurseFileInfo(246640, 3321276)); //mystical
            modInfos.add(new CurseFileInfo(272335, 3349690)); //cucumber
            modInfos.add(new CurseFileInfo(55438, 3081350)); //furniture mod
            modInfos.add(new CurseFileInfo(289380, 3336021)); //Obfuscate
            modInfos.add(new CurseFileInfo(289479, 3553336)); //cgm
            modInfos.add(new CurseFileInfo(286660, 3103940)); //vehicle Mod
            modInfos.add(new CurseFileInfo(238222, 3590083)); //JEI

            modInfos.add(new CurseFileInfo(520208, 3637586)); //Odysseyus

            modInfos.add(new CurseFileInfo(377448, 3581611)); //Beyond Earth
            modInfos.add(new CurseFileInfo(546554, 3581789)); //Space Bosstools Addons
            modInfos.add(new CurseFileInfo(284324, 3543338)); //Emojiful ( A rajouter sur Odysseyus)
            modInfos.add(new CurseFileInfo(289310, 3381163)); //Camera
            //modInfos.add(new CurseFileInfo(285612, 3211323)); //RandomPatches
            modInfos.add(new CurseFileInfo(470193, 3434629)); //Connectivity
            modInfos.add(new CurseFileInfo(492574, 3449703)); //My server is Compatible
            modInfos.add(new CurseFileInfo(497637, 3406131)); //XK's
            modInfos.add(new CurseFileInfo(388800, 3604756)); //Polymorph
            modInfos.add(new CurseFileInfo(266515, 3525789)); //industrial Forgegoing
            modInfos.add(new CurseFileInfo(309927, 3456953)); //Curios API
            modInfos.add(new CurseFileInfo(287342, 3346366)); //Titanium
            modInfos.add(new CurseFileInfo(306770, 3459118)); //industrial Forgegoing





            final  List<CurseFileInfo> mekanism = new ArrayList<>();
            mekanism.add(new CurseFileInfo(345425, 3590103)); //addition
            mekanism.add(new CurseFileInfo(268567, 3590102)); //tools
            mekanism.add(new CurseFileInfo(268566, 3590101)); //generator









            modInfos.addAll(addons.modAddons);
            modInfos.addAll(mekanism);

            final List<Mod> files = Mod.getModsFromJson(MinecraftInfos.MODS_LIST_URL);

            ExternalFile.getExternalFilesFromJson("https://odysseyus.fr/external.json");




            final UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder()
                    .build();


            final AbstractForgeVersion forge = new ForgeVersionBuilder(MinecraftInfos.FORGE_VERSION_TYPE)
                    .withForgeVersion(MinecraftInfos.FORGE_VERSION)
                    .withMods(files)
                    .withCurseMods(modInfos)
                    .withOptiFine(new OptiFineInfo(MinecraftInfos.OPTIFINE_VERSION, false))
                    .withFileDeleter(new ModFileDeleter(true))
                    .build();

            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(version)
                    .withLogger(Launcher.getInstance().getLogger())
                    .withProgressCallback(callback)
                    .withForgeVersion(forge)
                    .withUpdaterOptions(options)
                    .build();

            updater.update(Launcher.getInstance().getLauncherDir());
            this.startGame();


        } catch (Exception exception) {
            Launcher.getInstance().getLogger().printStackTrace(exception);
            Platform.runLater(() -> panelManager.getStage().show());
        }
    }



    public void startGame() {

        try {
            final var framework = new NoFramework(Launcher.getInstance().getLauncherDir(), Launcher.getInstance().getAuthInfos(), GameFolder.FLOW_UPDATER);

            framework.setServerName("OdysseyusV2");
            framework.setAdditionalArgs(Arrays.asList("--server=45.90.163.68", "--port=25565"));
            framework.setAdditionalVmArgs(Collections.singletonList(this.getRamArgsFromSaver()));

            if(fr.flowarg.flowcompat.Platform.isOnMac())
                framework.getAdditionalVmArgs().add("-XstartOnFirstThread");

            framework.launch(MinecraftInfos.GAME_VERSION, StringUtils.empty(MinecraftInfos.FORGE_VERSION, MinecraftInfos.GAME_VERSION + "-"));
        }
        catch (Exception e)
        {
            Launcher.getInstance().getLogger().printStackTrace(e);
        }

    }


    public String getRamArgsFromSaver() {
        int val = 1024;
        try {
            if (saver.get("maxRam") != null) {
                val = Integer.parseInt(saver.get("maxRam"));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException error) {
            saver.set("maxRam", String.valueOf(val));
            saver.save();
        }

        return "-Xmx" + val + "M";
    }

    public void setStatus(String status) {
        this.stepLabel.setText(status);
    }

    public void setProgress(double current, double max) {
        this.progressBar.setProgress(current / max);
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public enum StepInfo {
        READ("Préparation du chantier..."),
        DL_LIBS("Téléchargement d'Odysseyus"), //Téléchargement des libraries...
        DL_ASSETS("Création du réacteur..."),
        EXTRACT_NATIVES("Extraction de l'uranium..."),
        FORGE("Mise en place des planêtes..."),
        FABRIC("Installation de fabric..."),
        MODS("Démarrage d'Odysseyus..."),
        EXTERNAL_FILES("Téléchargement des fichier externes..."),
        POST_EXECUTIONS("Exécution post-installation..."),
        END("Bon jeu sur Odysseyus^^ ! ");
        String details;

        StepInfo(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }
}
