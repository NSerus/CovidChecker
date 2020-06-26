package com.boom.covidchecker;

public class Solidao {
    private String data;
    private long id = -1;
    private String conteudo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataSolidao() {
        return data;
    }

    public void setDataSolidao(String data) {
        this.data = data;
    }


}
