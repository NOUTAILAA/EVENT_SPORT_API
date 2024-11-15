package com.sport.app.entity;

public class ClassementSimple {
    private Long participantId;
    private String participantName;
    private double temps;
	public Long getParticipantId() {
		return participantId;
	}
	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}
	public String getParticipantName() {
		return participantName;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	public double getTemps() {
		return temps;
	}
	public void setTemps(double temps) {
		this.temps = temps;
	}
	public ClassementSimple(Long participantId, String participantName, double temps) {
		super();
		this.participantId = participantId;
		this.participantName = participantName;
		this.temps = temps;
	}
public ClassementSimple() {}
}
