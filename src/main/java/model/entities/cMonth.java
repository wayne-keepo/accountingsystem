package model.entities;

import utils.Searcher;

import java.time.LocalDate;

public class cMonth {
    private String ruTitle;
    private String engTitle;
    private int pos;

    public cMonth(LocalDate now){
        this.engTitle = now.getMonth().name();
        this.pos = now.getMonthValue();
        this.ruTitle = Searcher.searchRuMonthByEng(now.getMonth()).name();
    }
}
