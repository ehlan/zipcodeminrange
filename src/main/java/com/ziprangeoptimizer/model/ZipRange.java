
package com.ziprangeoptimizer.model;

import java.io.Serializable;

public class ZipRange implements Serializable {

    private static final long serialVersionUID = 4149811375917252270L;

    private Integer begZip;
    private Integer endZip;

    public Integer getBegZip() {
        return begZip;
    }

    public void setBegZip(Integer begZip) {
        this.begZip = begZip;
    }

    public Integer getEndZip() {
        return endZip;
    }

    public void setEndZip(Integer endZip) {
        this.endZip = endZip;
    }

    public ZipRange(Integer begZip, Integer endZip) {
        this.begZip = begZip;
        this.endZip = endZip;
    }

    @Override
    public String toString() {
        return "ZipRange{" +
                "begZip=" + begZip +
                ", endZip=" + endZip +
                '}';
    }
}
