package org.basics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ConcurrentModificationException;

public class DialWithBuffer extends SecondaryGraph implements GLEventListener {
    private FloatBuffer vertices;
    private int vboId;
    private float maxValue;
    private float minValue;
    private final int sampleCount = 1000;
    private float value = 0f;

    public DialWithBuffer() {
        super(0,0,0,0);
        this.maxValue = 1;
        this.minValue = -1;
    }

    public void setMaxMin(float maxValue, float minValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    private void setVertices(GLAutoDrawable glAutoDrawable) {
        vertices = Buffers.newDirectFloatBuffer(sampleCount * 2);

        GL2 gl = glAutoDrawable.getGL().getGL2();

        int[] vboIds = new int[1];
        // Generate a buffer object name/ID
        gl.glGenBuffers(1, vboIds, 0);
        vboId = vboIds[0];

        // Set current dimensions
        graphWidth = glAutoDrawable.getSurfaceWidth();
        graphHeight = glAutoDrawable.getSurfaceHeight();

        final float desiredRadius = 0.7f;
        final float[] radiusOuter = {
                graphWidth < graphHeight ? desiredRadius : graphHeight / graphWidth * desiredRadius,
                graphHeight < graphWidth ? desiredRadius : graphWidth / graphHeight * desiredRadius};
        final float[] radiusInner = {radiusOuter[0] - (0.25f * radiusOuter[0]), radiusOuter[1] - (0.25f * radiusOuter[1])};

        float originX = 0;
        float originY = radiusOuter[1] / -2f;
        float increment = ((float) Math.PI / ((float) sampleCount / 2));
        for (double angle = 0; angle < Math.PI; angle += increment) {
            vertices.put(-1 * (originX + ((float) Math.cos(angle) * radiusInner[0])));
            vertices.put(originY + ((float)Math.sin(angle) * radiusInner[1]));
            vertices.put(-1 * (originX + ((float) Math.cos(angle) * radiusOuter[0])));
            vertices.put(originY + ((float)Math.sin(angle) * radiusOuter[1]));
        }

        vertices.rewind();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        setVertices(glAutoDrawable);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        if (graphWidth != glAutoDrawable.getSurfaceWidth() || graphHeight != glAutoDrawable.getSurfaceHeight())
            setVertices(glAutoDrawable);

        try {

            final GL2 gl = glAutoDrawable.getGL().getGL2();

            Color backgroundColor = new Color(150, 150, 150, 255);
            gl.glClearColor(backgroundColor.getRed() / 255.0f, backgroundColor.getBlue() / 255.0f, backgroundColor.getGreen() / 255.0f, backgroundColor.getAlpha() / 255.0f);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

            Color c = Color.RED;
            gl.glColor3d(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0);

            // Code for smoothing random value changes
            // Following line should be removed, just for visually appealing things
            value += (Math.random() * (0.01f - -0.01f)) - 0.01f;

            float percent = Math.max(Math.min(1f, (value - minValue) / (maxValue - minValue)), 0);
            System.out.println(value + "\t|\t" + percent);

            gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboId);
            gl.glBufferData(GL2.GL_ARRAY_BUFFER, (long) vertices.limit() * Buffers.SIZEOF_FLOAT, vertices, GL2.GL_STATIC_DRAW);
            gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
            gl.glVertexPointer(2, GL2.GL_FLOAT, 0, 0);
            gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP, 0, (int) (sampleCount * percent) /* DrawCount Goes Here! */);

            gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
            gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        } catch (ConcurrentModificationException e) {
            System.out.println("Cannot draw dataset");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glCanvas = new GLCanvas(capabilities);

        glCanvas.addGLEventListener(new DialWithBuffer());
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

    public String toString() {
        return "Dial";
    }
}
