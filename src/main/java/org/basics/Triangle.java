package org.basics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.JFrame;
import java.nio.DoubleBuffer;
import java.nio.ShortBuffer;

public class Triangle implements GLEventListener {
    private DoubleBuffer vertices;
    private ShortBuffer indices;
    private int VBOVertices;
    private int VBOIndices;
    @Override
    public void init(GLAutoDrawable drawable)
    {
        final GL2 gl = drawable.getGL().getGL2();

        double[] vertexArray = {
                -0.5,   -0.5,
                0.5,    -0.5,
                0,      0.5,
        };

        vertices = Buffers.newDirectDoubleBuffer(vertexArray.length);
        vertices.put(vertexArray);
        vertices.flip();

        short[] indexArray = {0, 1, 2, 0};
        indices = Buffers.newDirectShortBuffer(indexArray.length);
        indices.put(indexArray);
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
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, VBOVertices);
        gl.glVertexPointer(2, GL2.GL_DOUBLE, 0, 0);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, VBOIndices);
        gl.glDrawElements(GL2.GL_LINE_STRIP, indices.capacity(), GL2.GL_UNSIGNED_SHORT, 0);
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

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }
}