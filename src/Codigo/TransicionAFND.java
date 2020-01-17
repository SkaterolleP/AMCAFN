/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.util.ArrayList;

/**
 *
 * @author Alberto's PC
 */
public class TransicionAFND implements Cloneable {

    /**
     *
     */
    public EstadoNF e1;

    /**
     *
     */
    public char simbolo;

    /**
     *
     */
    public ArrayList<EstadoNF> e2;

    protected Object clone() {
        TransicionAFND tr = null;
        try {
            tr = (TransicionAFND) super.clone();
            tr.e2 = (ArrayList<EstadoNF>) tr.e2.clone();
        } catch (CloneNotSupportedException e) {

        }
        return tr;
    }

    /**
     *
     * @return
     */
    public EstadoNF getE1() {
        return e1;
    }

    /**
     *
     * @return
     */
    public char getSimbolo() {
        return simbolo;
    }

    /**
     *
     * @return
     */
    public ArrayList<EstadoNF> getE2() {
        return e2;
    }

    /**
     *
     * @param e1
     * @param simbolo
     * @param e2
     */
    public TransicionAFND(EstadoNF e1, char simbolo, ArrayList<EstadoNF> e2) {
        this.e1 = e1;
        this.simbolo = simbolo;
        this.e2 = e2;
    }
    public TransicionAFND(EstadoNF e1, char simbolo, EstadoNF e2) {
        this.e1 = e1;
        this.simbolo = simbolo;
        this.e2.add(e2);
    }

    public String toString() {
        String s = "Estado 1: " + e1 + " + " + simbolo + " -> " + "Estado 2: " + e2;
        return s;
    }
    
    public boolean agregarTransicion(EstadoNF ori, char simbolo, EstadoNF des) {
        int indice = e2.indexOf(ori);
        return e2.get(indice).agregarTransicion(ori, simbolo, des);
    }
}
