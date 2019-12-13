/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amcparte2;

/**
 *
 * @author usuario
 */
public class AFND {

    private int[] estadosFinales;
    private List<TransicionAFND> transiciones;
    private List<TransicionA> transicionesA;

    public AFND() {

    }

    public void agregarTransicion(int e1, char simbolo, int[] e2) {

    }

    public void agregarTransicionA(int e1, int[] e2) {

    }

    private int[] transicion(int estado, char simbolo) {

    }

    public int[] transicion(int[] macroestado, char simbolo) {

    }

    public int[] transicionA(int estado) {

    }

    private boolean esFinal(int estado) {

    }

    public boolean esFinal(int[] macroestado) {

    }

    private int[] Aclausura(int[] macroestado) {

    }

    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        int[] estado = {0};
        int[] macroestado = Aclausura(estado);
        for (int i = 0; i < simbolo.length; i++) {
            macroestado = transicion(macroestado, simbolo[i]);
        }
        return esFinal(macroestado);
    }

    public static AFND pedir();
}
