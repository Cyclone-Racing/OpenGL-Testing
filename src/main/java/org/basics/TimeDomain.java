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
        double max = tempY;
        double min = tempY;
        double scale;

        for (int i = 1; i<yValuesNum; i++){
            double x1 = -1 + i*maxRange;
            double y1 = yValues.getSample((yValues.getLength()) - (yValuesNum - (i)));
            //System.out.println("y1 is: " + y1);
            //System.out.println("y1 is: " + y1);

            min = Math.min(y1, min);
            max = Math.max(y1, max);

            double yRange = max - min;

            // Formula for scaling y: (((y - min) / (range)) * actual range) + -1

            double drawY1 = 0.0; // Scaled y1
            double drawTempY = 0.0; // Scaled TempY

            gl.glBegin (GL2.GL_LINES);
            gl.glVertex2d(tempX,tempY);
            gl.glVertex2d(x1, y1);
            gl.glEnd();
            tempY = y1;
            tempX = x1;

        }
    }
}
