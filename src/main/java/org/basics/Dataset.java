package org.basics;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private List<Float> values;
    private String name;

    public Dataset(String name) {
        this.name = name;
        this.values = new ArrayList<Float>();
    }

    public void add(float value) {
        values.add(value);
    }

    public List<Float> getValues() {
        return values;
    }

    public int getLength() {
        return values.size();
    }

    public String getName() {
        return name;
    }

    public Float getSample(int i) {
        return values.get(i);
    }

    public Float getLastSample() {
        return values.get(values.size() - 1);
    }
}
