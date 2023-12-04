package org.basics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.nio.DoubleBuffer;
import java.nio.ShortBuffer;

public class MovingDataWithBuffer implements GLEventListener {
    private DoubleBuffer vertices;
    private ShortBuffer indices;
    private int VBOVertices;
    private int VBOIndices;
    private int length = 10000;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        vertices = Buffers.newDirectDoubleBuffer(length * 2);
        indices = Buffers.newDirectShortBuffer(length);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        vertices.rewind();
        indices.rewind();

        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL2.GL_BUFFER);

        double range = 2.0;
        double min = -1;
        double max = 1;

        for (int i = 0; i < length; i++) {
            double xCord = (((double) i / length) * range) + min;
            double yCord = Math.random() * range + min;
            vertices.put(xCord);
            vertices.put(yCord);
        }
        vertices.flip();

        short[] indexArray = new short[length];
        for (int i = 0; i < length; i ++) {
            indices.put((short) i);
        }
        indices.flip();

        int[] temp = new int[2];
        gl.glGenBuffers(2, temp, 0);

        VBOVertices = temp[0];
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, VBOVertices);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, (long) vertices.capacity() * Buffers.SIZEOF_DOUBLE,
                vertices, GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);

        VBOIndices = temp[1];
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, VBOIndices);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, (long) indices.capacity() * Buffers.SIZEOF_SHORT,
                indices, GL2.GL_STATIC_DRAW);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, VBOVertices);
        gl.glVertexPointer(2, GL2.GL_DOUBLE, 0, 0);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, VBOIndices);
        gl.glDrawElements(GL2.GL_LINE_STRIP, indices.capacity(), GL2.GL_UNSIGNED_SHORT, 0);

        vertices.rewind();
        indices.rewind();
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
