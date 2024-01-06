package org.basics;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Graph extends MouseAdapter implements GLEventListener {
    protected int sampleCount = 10000;
    protected List<Dataset> datasets;

    protected float graphX;
    protected float graphY;
    protected float graphWidth;
    protected float graphHeight;

    protected boolean mouseOnCanvas;

    private Container container;

    public Graph(float graphX, float graphY, float graphWidth, float graphHeight) {
        this.graphX = graphX;
        this.graphY = graphY;
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
    }

    public Graph() {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println(e);
        mouseOnCanvas = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseOnCanvas = false;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets.addAll(datasets);
    }

    public void setPosition(int x, int y, Dimension location) {
        this.graphX = x;
        this.graphY = y;
        this.graphWidth = location.width;
        this.graphHeight = location.height;
    }

    private void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    protected double convertPointOverHeight(double value) {
        return ((value / graphHeight) * (2)) + -1;
    }

    protected double convertPointOverWidth(double value) {
        return ((value / graphWidth) * (2)) + -1;
    }

    protected double convertValueOverHeight(double value) {
        return ((value / graphHeight) * (2));
    }

    protected double convertValueOverWidth(double value) {
        return ((value / graphWidth) * (2));
    }
}
