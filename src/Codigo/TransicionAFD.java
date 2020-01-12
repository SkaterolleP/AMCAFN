/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

/**
 *
 * @author Alberto's PC
 */
public class TransicionAFD extends AFD implements Cloneable {

    /**
     *
     */
    public EstadoF e1;       //Origen

    /**
     *
     */
    public char simbolo;    //Simbolo de la transición

    /**
     *
     */
    public EstadoF e2;       //Destino

    /**
     *
     * @return
     */
    public EstadoF getE1() {
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
    public EstadoF getE2() {
        return e2;
    }

    protected Object clone() {
        TransicionAFD tr = null;
        tr = (TransicionAFD) super.clone();
        tr.e1 = new EstadoF(tr.e1.nombre, tr.e1.transiciones, tr.e1.esfinal);
        tr.e2 = new EstadoF(tr.e2.nombre, tr.e2.transiciones, tr.e2.esfinal);
        return tr;
    }

    public boolean equals(Object o) {
        TransicionAFD tmp = (TransicionAFD) o;
        return (this.e1.equals(tmp.e1) && this.e2.equals(tmp.e2) && this.simbolo == tmp.simbolo);
    }

    /**
     *
     */
    public TransicionAFD() {

    }

    /**
     *
     * @param e1
     * @param simbolo
     * @param e2
     */
    public TransicionAFD(EstadoF e1, char simbolo, EstadoF e2) {
        this.e1 = e1;
        this.simbolo = simbolo;
        this.e2 = e2;
    }
    
    /**
     *
     * @param e1
     * @param simbolo
     * @param e2
     */
    public TransicionAFD(String e1,char simbolo,String e2){
            this.e1 = new EstadoF(e1);
            this.simbolo=simbolo;
            this.e2 = new EstadoF(e2);
        }

    public String toString() {
        String s = e1 + "+" + simbolo + " -> " + e2;
        return s;
    }
}
