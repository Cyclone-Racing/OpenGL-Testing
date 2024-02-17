package org.basics;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;

public class FarrelMovingLine2 implements GLEventListener {
    Dataset dataset;

    public FarrelMovingLine2(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        OpenGL.makeAllPrograms(glAutoDrawable.getGL().getGL2ES3());
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2ES3 gl = glAutoDrawable.getGL().getGL2ES3();
        OpenGL.buffer.rewind();
        float[] vertices = {-0.5f, 0.0f, 0.5f, dataset.getLastSample()};
        System.out.println(vertices[0] + " | " + vertices[1] + " | " + vertices[2] + " | " + vertices[3]);
        OpenGL.buffer.put(-0.5f);
        OpenGL.buffer.put(0.0f);
        OpenGL.buffer.put(0.5f);
        OpenGL.buffer.put(dataset.getLastSample());
        /*
        public static void drawLinesXy(     GL2ES3 gl,
        int lineType,
        float[] color,
        FloatBuffer buffer,
        int vertexCount )
         */
        OpenGL.drawLinesXy(gl, GL3.GL_LINE, new float[]{1f, 1f, 1f, 1f}, OpenGL.buffer, 2);
        OpenGL.buffer.rewind();
    }

    public static void main(String[] args) throws InterruptedException {
//        OpenGL.makeAllPrograms();
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glCanvas = new GLCanvas(capabilities);

        DataInput.connect(DataInput.TEST);
        glCanvas.addGLEventListener(new FarrelMovingLine2(DatasetController.getDataset(0)));
        glCanvas.setSize(400, 400);

        Animator animator = new Animator(glCanvas);
        animator.setUpdateFPSFrames(1, null);
        animator.start();

        final JFrame frame = new JFrame ("Dial With Buffer Data");

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
