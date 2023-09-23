package org.basics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class TimeDomain implements GLEventListener {

    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    @Override
    public void init(GLAutoDrawable arg0) {

    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {

    }
// Dataset
    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        Dataset yValues = DatasetController.getDataset(0);

        final int yValuesNum = Math.min(5000, yValues.getLength());
        double maxRange = 2.0 / 5000;
        double tempX = -1;
        double tempY = yValues.getSample((yValues.getLength()) - (yValuesNum));

        for (int i = 1; i<yValuesNum; i++){
            double x1 = -1 + i*maxRange;
            double y1 = yValues.getSample((yValues.getLength()) - (yValuesNum - (i)));

            // double sample1x = -1.0 + ((i - 1) * xDiff);

            gl.glBegin (GL2.GL_LINES);
            //gl.glVertex2d(-0.80,0);
            gl.glVertex2d(tempX,tempY);
            gl.glVertex2d(x1, y1);
            gl.glEnd();
            tempY = y1;
            tempX = x1;

        }
    }
}
