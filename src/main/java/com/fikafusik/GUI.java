
package com.fikafusik;

import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class GUI extends JFrame {
    public GUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        ClockRenderer clockRenderer = new ClockRenderer();

        GLCanvas clockCanvas = new GLCanvas();
        clockCanvas.addGLEventListener(clockRenderer);
        clockCanvas.display();

        JPanel panelProperties = new JPanel();
        panelProperties.setLayout(new BoxLayout(panelProperties, BoxLayout.Y_AXIS));

        JSplitPane splitPaneGUI = new JSplitPane();
        splitPaneGUI.setLeftComponent(panelProperties);
        splitPaneGUI.setRightComponent(clockCanvas);

        add(splitPaneGUI);
    }

    public void run() {
        setVisible(true);
    }
}
