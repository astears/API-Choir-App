package org.androidtown.choir;

/**
 * Created by astears on 11/28/17.
 */

public class Uniform {
    private String mensUniform;
    private String womensUniform;

    public Uniform(String men, String women) {
        mensUniform = men;
        womensUniform = women;
    }

    public String getMensUniform() {
        return mensUniform;
    }

    public void setMensUniform(String uniform) {
        this.mensUniform = uniform;
    }

    public String getWomensUniform() {
        return womensUniform;
    }

    public void setWomensUniform(String uniform) {
        this.womensUniform = uniform;
    }
}
