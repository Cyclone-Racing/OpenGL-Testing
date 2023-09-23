package org.basics;

import javax.swing.JFrame;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.*;

public class Triangle2 implements GLEventListener {
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        Color color1 = new Color(255, 255, 255, 255);
        gl.glClearColor(color1.getRed() / 255.0f, color1.getBlue() / 255.0f, color1.getGreen() / 255.0f, color1.getAlpha() / 255.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        Color color2 = new Color(93, 0, 0);
        gl.glColor3d(color2.getRed() / 255.0, color2.getBlue() / 255.0, color2.getGreen() / 255.0);

        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2d(-0.5, -0.5);
        gl.glVertex2d( 0.0,  0.5);
        gl.glVertex2d( 0.5, -0.5);
        gl.glEnd();
    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    @Override public void dispose(GLAutoDrawable drawable) {

    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glCanvas = new GLCanvas(capabilities);

        glCanvas.addGLEventListener(new Triangle2());
        glCanvas.setSize(400, 400);

        Animator animator = new Animator(glCanvas);
        animator.setUpdateFPSFrames(1, null);
        animator.start();

        final JFrame frame = new JFrame ("Triangle 2");

        frame.getContentPane().add(glCanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }

}