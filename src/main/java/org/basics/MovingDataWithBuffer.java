package org.basics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.nio.FloatBuffer;
import java.util.Date;

public class MovingDataWithBuffer implements GLEventListener {
    private FloatBuffer vertices;
    private int vboId;
    private int length = 1000;

    // For testing purpose
    private Date startTime;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        vertices = Buffers.newDirectFloatBuffer(length * 2);

        GL2 gl = glAutoDrawable.getGL().getGL2();


        int[] vboIds = new int[1];
        // Generate a buffer object name/ID
        gl.glGenBuffers(1, vboIds, 0);
        vboId = vboIds[0];
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        vertices.rewind();

        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL2.GL_BUFFER);

        double range = 2.0;
        double min = -1;
        double max = 1;

        // Setup vertices buffer
        for (int i = 0; i < length; i++) {
            float xCord = (float) ((((float) i / length) * range) + min);
            float yCord = (float) ((Math.random() * range) + min);
            vertices.put(xCord);
            vertices.put(yCord);
        }
        vertices.flip();

        try {

            int[] vboIds = new int[1];
            gl.glGenBuffers(1, vboIds, 0);
            vboId = vboIds[0];

            gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboId);
            gl.glBufferData(GL2.GL_ARRAY_BUFFER, (long) vertices.limit() * Buffers.SIZEOF_FLOAT, vertices, GL2.GL_STATIC_DRAW);
            gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
            gl.glVertexPointer(2, GL2.GL_FLOAT, 0, 0);
            gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, length /* DrawCount Goes Here! */);

            gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
            gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

            vertices.rewind();

        } catch (Exception e) {
            Date endTime = new Date();
            System.out.println(new Date(endTime.getTime() - startTime.getTime()));
            System.out.println();
            System.out.println(gl.glGetError());
            throw e;
        }

        vertices.rewind();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    public static void main(String[] args) throws InterruptedException {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glCanvas = new GLCanvas(capabilities);

        glCanvas.addGLEventListener(new MovingDataWithBuffer());
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
}
