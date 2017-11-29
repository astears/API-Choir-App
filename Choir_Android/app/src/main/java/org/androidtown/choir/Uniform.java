package org.androidtown.choir;

/**
 * Created by astears on 11/28/17.
 */

public class Uniform {
    private String mens;
    private String womens;

    public Uniform(String men, String women) {
        mens = men;
        womens = women;
    }

    public String getMens() {
        return mens;
    }

    public void setMens(String messageText) {
        this.mens = messageText;
    }

    public String getWomens() {
        return womens;
    }

    public void setWomens(String messageUser) {
        this.womens = messageUser;
    }
}
