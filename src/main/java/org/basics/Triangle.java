package org.basics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.JFrame;

public class Triangle implements GLEventListener {

    @Override
    public void init(GLAutoDrawable arg0)
    {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        // Base edge
        gl.glBegin (GL2.GL_LINES);
        gl.glVertex2d(-0.5, -0.5);
        gl.glVertex2d(0.5, -0.5);
        gl.glEnd();

        // Right edge
        gl.glBegin (GL2.GL_LINES);
        gl.glVertex2d(0, 0.5);
        gl.glVertex2d(-0.5, -0.5);
        gl.glEnd();

        // Left edge
        gl.glBegin (GL2.GL_LINES);
        gl.glVertex2d(0, 0.5);
        gl.glVertex2d(0.5, -0.5);
        gl.glEnd();
        gl.glFlush();
    }
    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
    {

    }
    @Override
    public void dispose(GLAutoDrawable arg0)
    {

    }

    public static void main(String[] args) throws InterruptedException {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glCanvas = new GLCanvas(capabilities);

        glCanvas.addGLEventListener(new Triangle());
        glCanvas.setSize(400, 400);

        Animator animator = new Animator(glCanvas);
        animator.setUpdateFPSFrames(1, null);
        animator.start();

        final JFrame frame = new JFrame ("Triangle");

        frame.getContentPane().add(glCanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}