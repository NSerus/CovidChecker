package com.boom.covidchecker;

public class Covid {
    private Integer progresso;
    private long id = -1;
    private Integer toggle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer conteudo) {
        this.progresso = conteudo;
    }

    public Integer getToggle() {
        return toggle;
    }

    public void setToggle(Integer toggle) {
        this.toggle = toggle;
    }


}
