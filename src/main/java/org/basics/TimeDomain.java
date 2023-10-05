package org.basics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.sun.tools.jconsole.JConsoleContext;

public class TimeDomain implements GLEventListener {
    private int[] dataArray;

    public TimeDomain(int[] dataArray) {
        this.dataArray = dataArray;
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
// Dataset
    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

//        System.out.println(DatasetController.getDatasets().size());

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);


        for (int j: dataArray){
            Dataset yValues = DatasetController.getDataset(j);

            final int yValuesNum = Math.min(5000, yValues.getLength());
            double maxRange = 2.0 / 5000;
            double tempX = -1;
            double tempY = yValues.getSample((yValues.getLength()) - (yValuesNum));
            //        double drawTempY = tempY; // Scaled TempY
            double max = tempY;
            double min = tempY;
            //        double drawY1 = 0.0; // Scaled y1

            for (int i = 1; i < yValuesNum; i++) {
                double y1 = yValues.getSample((yValues.getLength()) - (yValuesNum - (i)));
                //            y1 = y1 *2;
                min = Math.min(y1, min);
                //            System.out.println("max is: " + max);

                max = Math.max(y1, max);
                //            System.out.println("min is: " + min);

            }
            double yRange = (max - min);
            tempY = ((tempY - min) / (yRange) * 2) - 1;
            for (int i = 1; i < yValuesNum; i++) {
                double x1 = -1 + i * maxRange;
                double y1 = yValues.getSample((yValues.getLength()) - (yValuesNum - (i)));
                //            y1 = y1 *2;
                //            System.out.println("y1 is: " + y1);
                //            System.out.println("tempy1 is: " + tempY);


                // Formula for scaling y: (((y - min) / (range)) * actual range) + -1

                // Drops scale to 0-1, then doubles value to put it on a 0-2 and takes 1 away to put it on -1 to 1
                y1 = ((y1 - min) / (yRange) * 2) - 1;

//                System.out.println("scaled y1 is: " + y1);


                gl.glBegin(GL2.GL_LINES);
                //gl.glVertex2d(-0.80,0);
                gl.glVertex2d(tempX, tempY);
                gl.glVertex2d(x1, y1);
                gl.glEnd();
                tempY = y1;
                tempX = x1;
            }
        }
    }
}
