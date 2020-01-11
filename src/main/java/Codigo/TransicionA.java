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
public class TransicionA implements Cloneable {

    public EstadoNF e1;
    public ArrayList<EstadoNF> e2;

    protected Object clone() {
        TransicionA tr = null;
        try {
            tr = (TransicionA) super.clone();
            tr.e2 = (ArrayList<EstadoNF>) tr.e2.clone();
        } catch (CloneNotSupportedException e) {
        }
        return tr;
    }

    public EstadoNF getE1() {
        return e1;
    }

    public ArrayList<EstadoNF> getE2() {
        return e2;
    }

    public TransicionA() {
        this.e2 = new ArrayList<EstadoNF>();
    }

    //Mirar
    public TransicionA(String e1) {
        this.e2 = new ArrayList<EstadoNF>();
    }

    public TransicionA(EstadoNF e1) {
        this.e1 = e1;
        this.e2 = new ArrayList<EstadoNF>();
    }

    public TransicionA(EstadoNF e1, ArrayList<EstadoNF> e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        String s = "Estado 1: " + e1 + " + λ -> " + "Estado 2: " + e2;
        return s;
    }

}
