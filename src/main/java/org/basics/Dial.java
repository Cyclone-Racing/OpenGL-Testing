package org.basics;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.awt.*;
import java.util.ConcurrentModificationException;

public class Dial extends SecondaryGraph {
    private float maxValue;
    private float minValue;

    public Dial(int graphX, int graphY, int graphWidth, int graphHeight) {
        super(graphX, graphY, graphWidth, graphHeight);
        this.maxValue = 1;
        this.minValue = -1;
    }

    public Dial(int graphX, int graphY, int graphWidth, int graphHeight, float maxValue, float minValue) {
        super(graphX, graphY, graphWidth, graphHeight);
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public void setMaxMin(float maxValue, float minValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (datasets == null) return;

        final GL2 gl = drawable.getGL().getGL2();

//        Color backgroundColor = new Color(150, 150, 150, 255);
//        gl.glClearColor(backgroundColor.getRed() / 255.0f, backgroundColor.getBlue() / 255.0f, backgroundColor.getGreen() / 255.0f, backgroundColor.getAlpha() / 255.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        double radius = Math.min(graphWidth, graphHeight) * 0.8;

        float value = dataset.getLastSample();

        final int minSampleCount = (int) (((value - minValue) / (maxValue - minValue)) * sampleCount);
        final int drawSampleCount = Math.min(minSampleCount, sampleCount);

        try {
            gl.glBegin(GL.GL_TRIANGLE_FAN);
            Color c = dataset.getColor();
            gl.glColor3d(c.getRed() / 255.0, c.getBlue() / 255.0, c.getGreen() / 255.0);

            gl.glVertex2d(0, -0.8);
            gl.glVertex2d(-0.8, -0.4);
            gl.glVertex2d(-0.4, -0);
            gl.glVertex2d(0, 0.4);
            gl.glVertex2d(0.4, 0);
            gl.glVertex2d(0.8, -0.4);

            gl.glEnd();
        } catch (ConcurrentModificationException e) {
            System.out.println("Cannot draw dataset");
        }
    }
}
