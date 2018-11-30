package com.example.hema.cableapp;

public class SpinnerAgentPlanet {

    private String AgentId;
    private String Agentname;

    public SpinnerAgentPlanet(String agentname)
    {
//        this.AgentId = agentId;
        this.Agentname = agentname;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getAgentname() {
        return Agentname;
    }

    public void setAgentname(String agentname) {
        Agentname = agentname;
    }
}
