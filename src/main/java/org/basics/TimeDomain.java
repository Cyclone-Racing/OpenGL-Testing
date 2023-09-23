package org.basics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class TimeDomain implements GLEventListener {
    private double maxYValue = Double.MIN_VALUE;
    private double minYValue = Double.MAX_VALUE;
    @Override
    public void display(GLAutoDrawable drawable) {
        Dataset yValues = DatasetController.getDataset(1);

        final int minSampleCount = yValues.getLength();
        final int maxSampleCount = 10000;
        final double diff = 2.0 / maxSampleCount;

        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        final int drawSampleCount = Math.min(minSampleCount, maxSampleCount);
        if (drawSampleCount < 2)
            return;

        for (int i = 1; i < drawSampleCount - 1; i++) {
            maxYValue = Math.max(maxYValue, yValues.getSample((yValues.getLength()) - (drawSampleCount - (i - 1))));
            minYValue = Math.min(minYValue, yValues.getSample((yValues.getLength()) - (drawSampleCount - (i - 1))));
            final double range = maxYValue - minYValue;

            double sample1x = -1.0 + ((i - 1) * diff);
            double sample2x = -1.0 + ((i) * diff);
            double sample1y = yValues.getSample((yValues.getLength()) - (drawSampleCount - (i - 1)));
            sample1y = (((sample1y - minYValue) / range) * 2) + -1;
            double sample2y = yValues.getSample((yValues.getLength()) - (drawSampleCount - (i)));
            sample2y = (((sample2y - minYValue)  / range) * 2) + -1;

//            System.out.println(sample1x + ", " + sample1y);

            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2d(sample1x, sample1y);
            gl.glVertex2d(sample2x, sample2y);
            gl.glEnd();
        }



//        gl.glBegin(GL2.GL_LINES);
//        gl.glVertex2d(-0.80, 0);
//
//        if (DataInput.isConnected())
//            gl.glVertex2d(0.80, DatasetController.getDataset(0).getLastSample());
//        else
//            gl.glVertex2d(0, 0);
//        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    @Override
    public void init(GLAutoDrawable arg0) {

    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {

    }
}