package com.github.rintilyx.azure.cosmos.data;

import java.util.List;

public class State {

    private String name;
    private Long populationDensity;
    private List<City> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(Long populationDensity) {
        this.populationDensity = populationDensity;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
