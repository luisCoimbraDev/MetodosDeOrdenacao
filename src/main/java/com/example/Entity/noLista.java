package com.example.Entity;

public class noLista {
    private int info;


    private noLista ant;
    private noLista prox;


    public noLista(int info, noLista ant, noLista prox){
        this.ant = ant;
        this.info = info;
        this.prox = prox;
    }
    public noLista getAnterior() {
        return ant;
    }

    public void setAntetior(noLista ant) {
        this.ant = ant;
    }

    public noLista getProximo() {
        return prox;
    }

    public void setProximo(noLista proximo) {
        this.prox = proximo;
    }


    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
}
