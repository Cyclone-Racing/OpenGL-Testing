package org.basics;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FarrelMovingLine implements GLEventListener {
    Dataset dataset;

    public FarrelMovingLine(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2ES3 gl = glAutoDrawable.getGL().getGL2ES3();
        OpenGL.makeAllPrograms(gl);
        float[] screenMatrix = new float[1];
//        OpenGL.makeOrthoMatrix(screenMatrix, 0, 1, 0, 1, -100000, 100000);
        OpenGL.useMatrix(gl, screenMatrix);
        OpenGL.updateFontTextures(gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2ES3 gl = glAutoDrawable.getGL().getGL2ES3();

        OpenGL.buffer.rewind();
        float[] vertices = {-0.5f, -1 * dataset.getLastSample() * 0.9f, 0.5f, dataset.getLastSample() * 0.9f};
        OpenGL.buffer.put(vertices);
//        System.out.println(vertices[0] + " | " + vertices[1] + " | " + vertices[2] + " | " + vertices[3]);
//        GL2ES3 gl,
//        float[] color,
//        float lowerLeftX,
//        float lowerLeftY,
//        float width,
//        float height
        OpenGL.drawBox(gl, new float[]{1f,1f,1f,1f}, -1, -1, 2, 2);
        OpenGL.buffer.rewind();
        OpenGL.drawLinesXyrgba(gl, GL3.GL_LINE, OpenGL.buffer, 2);
        OpenGL.buffer.rewind();
//        GL2ES3 gl,
//        @NotNull  String text,
//        int x,
//        int y,
//        float degrees
        OpenGL.drawLargeText(gl, "Test", 0, 0, 0);
//        OpenGL.drawLinesXy();
        OpenGL.buffer.rewind();
    }

    public static void main(String[] args) throws InterruptedException {
        DataInput.connect(DataInput.TEST);

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glCanvas = new GLCanvas(capabilities);

        glCanvas.addGLEventListener(new FarrelMovingLine(DatasetController.getDataset(0)));
        glCanvas.setSize(400, 400);

        Animator animator = new Animator(glCanvas);
        animator.setUpdateFPSFrames(1, null);
        animator.start();

        final JFrame frame = new JFrame ("Moving Buffer Data");

        frame.getContentPane().add(glCanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
