/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amcparte2;

import java.util.List;

/**
 *
 * @author usuario
 */
public class AFD {
    private int[] estadosFinales; //Indica cuales son los estados Finales
    private List<TransicionAFD> transiciones; //Indica la lista de transiciones del AFD
    
    /**
     *
     */
    public AFD(){
        
    }
    public void agregarTransicion(int e1, char simbolo, int e2){
        
    }
    public int transicion(int estado, char simbolo){
        
    }
    public boolean esFinal(int estado){
        
    }
    public boolean reconocer(String cadena){
        char[] simbolo = cadena.toCharArray();
        int estado = 0; //El estado inicial es el 0
        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);
        }
        return esFinal(estado);
    }
    public static AFD pedir();
}
