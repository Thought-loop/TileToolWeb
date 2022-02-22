package com.thought_loop.TileToolWeb;

import javax.swing.*;

public class DrawLayoutRendering extends JFrame {

    private LayoutRendering layoutRendering;
    String windowTitle;

    public DrawLayoutRendering(LayoutRendering layoutRendering, String windowTitle) {
        this.layoutRendering = layoutRendering;
        this.windowTitle = windowTitle;
        startUI();
    }

    private void startUI() {
        int windowSizeX = layoutRendering.getWindowSize()[0];
        int windowSizeY = layoutRendering.getWindowSize()[1];


        setSize(windowSizeX, windowSizeY);
        add(layoutRendering);
        //add(scaleUp);
        //add(scaleDown);
        setTitle(windowTitle);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


}
