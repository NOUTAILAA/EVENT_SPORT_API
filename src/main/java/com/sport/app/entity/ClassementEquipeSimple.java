package com.sport.app.entity;


public class ClassementEquipeSimple {
    private Long equipeId;
    private int scoreTotal;

    // Constructors, getters and setters
    public ClassementEquipeSimple(Long equipeId, String equipeNom, int scoreTotal) {
        this.equipeId = equipeId;
        this.scoreTotal = scoreTotal;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }



    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
