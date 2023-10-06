package org.basics;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final int graphWidth = 800;
        final int graphHeight = 200;

        final GLCanvas glCanvas1 = new GLCanvas(capabilities);

        Dial dial = new Dial(0, 0, graphHeight, graphHeight);

        glCanvas1.addGLEventListener(dial);
        glCanvas1.setSize(graphHeight, graphHeight);

        Animator animator1 = new Animator(glCanvas1);
        animator1.setUpdateFPSFrames(1, null);
        animator1.start();

        final JFrame frame = new JFrame("Drawing");

        Box graphs1 = new Box(BoxLayout.X_AXIS);
        graphs1.add(glCanvas1);

        Box screen = new Box(BoxLayout.Y_AXIS);
        screen.add(graphs1);

        frame.getContentPane().add(screen);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        DataInput.connect(DataInput.TEST);
        List<Dataset> l = new ArrayList<>();
        l.add(DatasetController.getDataset(0));
        l.add(DatasetController.getDataset(1));

        dial.setDataset(l.get(0));

        Thread.sleep(10000);

        l.add(DatasetController.getDataset(2));
        dial.setDataset(l.get(2));
        dial.setMaxMin(-5, 5);
    }
}
