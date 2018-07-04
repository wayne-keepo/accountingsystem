package domain;

import entities.Summary;

public class ElectrodeSummary {
    private Electrod electrod;
    private Summary summary;

    public ElectrodeSummary() {
    }

    public ElectrodeSummary(Electrod electrod, Summary summary) {
        this.electrod = electrod;
        this.summary = summary;
    }

    public Electrod getElectrod() {
        return electrod;
    }

    public void setElectrod(Electrod electrod) {
        this.electrod = electrod;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }
}
