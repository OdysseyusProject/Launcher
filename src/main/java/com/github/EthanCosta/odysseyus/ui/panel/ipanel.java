package com.github.EthanCosta.odysseyus.ui.panel;

import com.github.EthanCosta.odysseyus.ui.PanelManager;
import javafx.scene.layout.GridPane;

import java.io.IOException;


public interface ipanel {
    void init (PanelManager panelManager);
    GridPane getLayout();
    void onShow();
    String getName();
    String getStylesheetPath();

}
