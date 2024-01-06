package org.basics;

import com.jogamp.opengl.awt.GLJPanel;
import org.basics.Dataset;

public abstract class SecondaryGraph extends Graph{
    protected Dataset dataset;

    public SecondaryGraph(GLJPanel container) {
        super(container);
    }

    public SecondaryGraph(int graphX, int graphY, int graphWidth, int graphHeight) {
        super(graphX, graphY, graphWidth, graphHeight);
    }

    protected SecondaryGraph() {
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
