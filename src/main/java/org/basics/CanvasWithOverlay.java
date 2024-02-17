package org.basics;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;

public class CanvasWithOverlay {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GLCanvas Overlay Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 400));

        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener((GLEventListener) new MyGLEventListener());
        canvas.setSize(400, 400);

        Animator animator = new Animator(canvas);
        animator.setUpdateFPSFrames(1, null);
        animator.start();

        JLabel label = new JLabel("Overlay Text");
        label.setSize(label.getPreferredSize());
        label.setLocation(50, 50); // Adjust the position as needed

        layeredPane.add(canvas, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(label, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane);
        frame.pack();
        frame.setVisible(true);
    }

    static class MyGLEventListener implements GLEventListener {

        @Override
        public void display(GLAutoDrawable drawable) {
            // Your drawing code for sin wave
            GL2 gl = drawable.getGL().getGL2();
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);
            // Draw your sin wave

            // Example:
            gl.glBegin(GL.GL_LINE_STRIP);
            for (float x = -1.0f; x <= 1.0f; x += 0.01f) {
                float y = (float)Math.sin(x * Math.PI * 10.0) / 2.0f;
                gl.glVertex2f(x, y);
            }
            gl.glEnd();

            // This disables the repaint manager temporarily
            RepaintManager.currentManager((Component) drawable).markCompletelyClean((JComponent) ((Component) drawable).getParent());
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // Reshape code
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {
            // Cleanup code
        }

        @Override
        public void init(GLAutoDrawable glAutoDrawable) {

        }
    }
}
