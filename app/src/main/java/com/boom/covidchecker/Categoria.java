package com.boom.covidchecker;

public class Categoria {
    private long id = -1;
    private String descricao;

    //Sets e Gets dos parametros
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
