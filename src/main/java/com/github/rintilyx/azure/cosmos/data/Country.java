package com.github.rintilyx.azure.cosmos.data;

import java.util.List;

public class Country {

    private String name;
    private String code;
    //private Long populationDensity;
    private List<State> states;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    public Long getPopulationDensity() {
//        return populationDensity;
//    }
//
//    public void setPopulationDensity(Long populationDensity) {
//        this.populationDensity = populationDensity;
//    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
}
