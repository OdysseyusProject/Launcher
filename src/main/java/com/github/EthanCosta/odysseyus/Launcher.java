package com.github.EthanCosta.odysseyus;

import com.azuriom.azauth.AzAuthenticator;
import com.github.EthanCosta.odysseyus.ui.PanelManager;
import com.github.EthanCosta.odysseyus.ui.panels.pages.App;
import com.github.EthanCosta.odysseyus.ui.panels.pages.login;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Launcher extends Application {
private PanelManager panelManager;
    private static Launcher instance;
    private final ILogger logger;
    public final Path launcherDir = GameDirGenerator.createGameDir("Odysseyus", true);
    private final Saver saver;
    private AuthInfos authInfos = null;


    public Launcher() {
        instance = this;
        this.logger = new Logger("[Odysseyus Launcher]", Paths.get(this.launcherDir.toString(), "launcher.log"));
        if (!this.launcherDir.toFile().exists()) {
            if (!this.launcherDir.toFile().mkdir()) {
                this.logger.err("Impossible de créer le dossier .Odysseyus");
            }
        }

        saver = new Saver(Paths.get(launcherDir.toString(), "config.properties"));
        saver.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.logger.info("Starting Launcher");
        this.panelManager = new PanelManager(this, stage);
        this.panelManager.init();
        this.panelManager.showPanel(new login());

        if (this.isUserAlreadyLoggedIn()) {
            logger.info("Hello " + authInfos.getUsername());

            this.panelManager.showPanel(new App());

        } else {
            this.panelManager.showPanel(new login());
        }

    }

    public boolean isUserAlreadyLoggedIn() {
        if (saver.get("accessToken") != null ) {

            AzAuthenticator authenticator = new AzAuthenticator("https://odysseyus.fr");



            try {

                AuthInfos response = authenticator.verify(saver.get("accessToken"), AuthInfos.class);

                this.setAuthInfos(new AuthInfos(
                        response.getUsername(),
                        response.getAccessToken(),
                        response.getUuid()

                ));



                return true;
            } catch (IOException | com.azuriom.azauth.AuthenticationException ignored) {
                saver.remove("accessToken");
                saver.remove("clientToken");
                saver.save();
            }
        }

        return false;
    }



    public void setAuthInfos(AuthInfos authInfos) {
        this.authInfos = authInfos;
    }

    public AuthInfos getAuthInfos() {
        return authInfos;
    }

    public ILogger getLogger() {
        return logger;
    }

    public static Launcher  getInstance() {
        return instance;
    }

    public Saver getSaver() {
    return saver;

    }

    public Path getLauncherDir() {
        return launcherDir;
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public void hideWindow() {
        this.panelManager.getStage().hide();
    }
}
