package com.github.EthanCosta.odysseyus.ui.panels.pages.content;

import com.github.EthanCosta.odysseyus.Launcher;
import com.github.EthanCosta.odysseyus.game.MinecraftInfos;
import com.github.EthanCosta.odysseyus.ui.PanelManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
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
import java.util.List;

public class Home extends ContentPanel {


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
        playIcon.setSize("20px");
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


            public void update(DownloadList.DownloadInfo info) {
                double progress = info.getDownloadedBytes() / info.getTotalToDownloadBytes();
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
                    .build();

            final  List<CurseFileInfo> modInfos = new ArrayList<>();
            modInfos.add(new CurseFileInfo(340583, 3003242)); //Bettercaves
            modInfos.add(new CurseFileInfo(389665, 3194993)); //BetterMineshaft

            modInfos.add(new CurseFileInfo(220318, 3558882)); //Biome O plenty

           // modInfos.add(new CurseFileInfo(226321, 2537917)); //BloodMoon
            modInfos.add(new CurseFileInfo(226321, 2537917)); //BloodMoon
            modInfos.add(new CurseFileInfo(237749, 2902920)); //CoroUtil
            modInfos.add(new CurseFileInfo(267602, 2915363)); //CTM
            modInfos.add(new CurseFileInfo(229449, 2874756)); //EpicSiegeMod
            modInfos.add(new CurseFileInfo(317310, 3278090)); //FlansMod
            modInfos.add(new CurseFileInfo(231951, 2676501)); //Immersive Engineering

            modInfos.add(new CurseFileInfo(269024, 2861574)); //The Lost Cities
            modInfos.add(new CurseFileInfo(407884, 3355108)); //Modular Voice Chat
            modInfos.add(new CurseFileInfo(238222, 3043174)); //JEI
            modInfos.add(new CurseFileInfo(243788, 3157722)); //ModernWarfare 4.6.2
            modInfos.add(new CurseFileInfo(360795, 3634226)); //Rough Mobs revamped
            modInfos.add(new CurseFileInfo(246391, 2710969)); //Though as nails
            modInfos.add(new CurseFileInfo(237754, 2663393)); //Zombie Awereness
            modInfos.add(new CurseFileInfo(268250, 3382321)); //Immersive Petroleum
            modInfos.add(new CurseFileInfo(321792, 3682998)); //Aquatic
            modInfos.add(new CurseFileInfo(419286, 3679872)); //Mixin Booter
            modInfos.add(new CurseFileInfo(312141, 3172004)); //Grand Economy
            modInfos.add(new CurseFileInfo(318414, 2985978)); //Oxygen Core
            modInfos.add(new CurseFileInfo(285742, 2949817)); //Smooth Fonts
            modInfos.add(new CurseFileInfo(328248, 2974811)); //Oxygen Merchant
            modInfos.add(new CurseFileInfo(334155, 2981869)); //Oxygen Mail
            modInfos.add(new CurseFileInfo(342390, 2983953)); //Oxygen Market
            modInfos.add(new CurseFileInfo(334153, 3050476)); //Oxygen Currency
            modInfos.add(new CurseFileInfo(274159, 2568579)); //Immersive tech
            modInfos.add(new CurseFileInfo(306549, 2906537)); //Tips
            modInfos.add(new CurseFileInfo(605442, 3733003)); //Odysseyus Rich Presence
            modInfos.add(new CurseFileInfo(309110, 2655843)); //Bigger Packet Please
            modInfos.add(new CurseFileInfo(263420, 3738188)); //Xaeros Minimap
            modInfos.add(new CurseFileInfo(305840, 2748065)); //Absent by design
            modInfos.add(new CurseFileInfo(532127, 3622425)); //Legendary Tooltips

            //modInfos.add(new CurseFileInfo(268560, 2835175)); //Mekanism
            modInfos.add(new CurseFileInfo(277736, 3437738)); //Immersive Railroading
            modInfos.add(new CurseFileInfo(282613, 2825260)); //track api
            modInfos.add(new CurseFileInfo(371784, 3654955)); //Univeral mod core
            modInfos.add(new CurseFileInfo(229769, 3109315)); //Stream
            modInfos.add(new CurseFileInfo(229708, 3093852)); //FarseekAPI
            modInfos.add(new CurseFileInfo(354143, 3511568)); //Performant

            modInfos.add(new CurseFileInfo(229073, 2960397)); //Hats
            modInfos.add(new CurseFileInfo(229060, 2801262)); //ichunutil





            //modInfos.add(new CurseFileInfo(310343, 3481536)); //WatchDog Anti-Cheat









            final List<ExternalFile> extFiles = new ArrayList<>(ExternalFile.getExternalFilesFromJson("https://odysseyus.fr/extfiles.json"));







            final List<Mod> mods = new ArrayList<>();
           // mods.add(new Mod("WatchDog-1.0.8.jar", "e17890ef3cf420757728fa05dd522b8930b367f7", 56562, "https://odysseyus.fr/mods/WatchDog-1.0.8.jar"));
            mods.add(new Mod("PTRLib.jar", "https://odysseyus.fr/mods/PTRLib.jar", "C33DDF5660C2A1C39537BD6552B2CDD73B654E18", 169749));
            mods.add(new Mod("parasite.jar", "https://odysseyus.fr/mods/parasite.jar", "BE7577C663272A2013B876846A3EAF561E933A0E", 9630654 ));

            mods.add(new Mod("decocraft.jar", "https://odysseyus.fr/mods/decocraft.jar", "E226ED02E3181ACA765A52CEE7BAD8865D93815B", 16976718));
            mods.add(new Mod("hwyla.jar", "https://odysseyus.fr/mods/hwyla.jar", "7280d5c0dab42436549bcefc63ff64a1049e5501", 453778));
         //mods.add(new Mod("wawla.jar", "783157c607149875de1e045d72382d370256257c",  "https://odysseyus.fr/mods/wawla.jar", 94115));
            mods.addAll(Addons.modAddons);
            modInfos.addAll(Addons.curseModAddons);


            final UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder()
                    .build();


            final AbstractForgeVersion forge = new ForgeVersionBuilder(MinecraftInfos.FORGE_VERSION_TYPE)
                    .withForgeVersion(MinecraftInfos.FORGE_VERSION)
                    .withCurseMods(modInfos)
                    .withMods(mods)

                    .withOptiFine(new OptiFineInfo(MinecraftInfos.OPTIFINE_VERSION, false))
                    .withFileDeleter(new ModFileDeleter(true))
                    .build();

            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(version)
                    .withLogger(Launcher.getInstance().getLogger())
                    .withExternalFiles(extFiles)
                    .withProgressCallback(callback)
                    .withModLoaderVersion(forge)
                    .withUpdaterOptions(options)
                    .build();

            updater.update(Launcher.getInstance().getLauncherDir());
            this.startGame(MinecraftInfos.GAME_VERSION);


        } catch (Exception exception) {
            Launcher.getInstance().getLogger().printStackTrace(exception);
            Platform.runLater(() -> panelManager.getStage().show());
        }
    }

    public void startGame(String gameVersion) {
        GameInfos infos = new GameInfos(
                MinecraftInfos.SERVER_NAME,
                true,
                new GameVersion(gameVersion, MinecraftInfos.OLL_GAME_TYPE),
                new GameTweak[]{GameTweak.FORGE}
        );

        try {
            ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(infos, GameFolder.FLOW_UPDATER, Launcher.getInstance().getAuthInfos());
            profile.getVmArgs().add(this.getRamArgsFromSaver());
            profile.getArgs().addAll(Arrays.asList("--server=45.90.163.68", "--port=25565"));
            ExternalLauncher launcher = new ExternalLauncher(profile);

          Platform.runLater(() -> panelManager.getStage().hide());

            Process p = launcher.launch();
            p.waitFor();
            Platform.runLater(Platform::exit);
        } catch (Exception exception) {
            exception.printStackTrace();
            Launcher.getInstance().getLogger().err(exception.toString());
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
        INTEGRATION("Pr??paration..."),
        READ("Mise en place des zombies..."),
        DL_LIBS("T??l??chargement d'Odysseyus"), //T??l??chargement des libraries...
        DL_ASSETS("Explosion des nukes..."),
        EXTRACT_NATIVES("Cr??ation des armes..."),
        FORGE("Construction du bunker..."),
        FABRIC("Installation de fabric..."),
        MODS("D??marrage d'Odysseyus..."),
        EXTERNAL_FILES("T??l??chargement des fichier externes..."),
        POST_EXECUTIONS("Ex??cution post-installation..."),
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
