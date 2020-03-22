
package com.fikafusik;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class GUI extends JFrame {

    private final FPSAnimator animator;

    public GUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);

        ClockRenderer clockRenderer = new ClockRenderer();

        GLCanvas clockCanvas = new GLCanvas();
        clockCanvas.addGLEventListener(clockRenderer);

        animator = new FPSAnimator(clockCanvas, 60);

        JPanel panelProperties = new JPanel();
        panelProperties.setLayout(new BoxLayout(panelProperties, BoxLayout.Y_AXIS));

        JSplitPane splitPaneGUI = new JSplitPane();
        splitPaneGUI.setLeftComponent(panelProperties);
        splitPaneGUI.setRightComponent(clockCanvas);

        add(splitPaneGUI);
    }

    public void run() {
        setVisible(true);
        animator.start();

    }
}
