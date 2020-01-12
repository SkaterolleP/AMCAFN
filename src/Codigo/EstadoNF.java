/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alberto's PC
 */
public class EstadoNF implements Cloneable {

    /**
     *
     */
    public String nombre;

    /**
     *
     */
    public List<TransicionAFND> transiciones; //Lista de transiciones AFND del estado

    /**
     *
     */
    public TransicionA transicionesA;         //Indica la lista de transiciones A del AFND

    /**
     *
     */
    public boolean esfinal;

    /**
     *
     * @param e
     */
    public EstadoNF(EstadoNF e) {
        this.nombre = e.nombre;
        transiciones = new ArrayList<TransicionAFND>();
        transicionesA = new TransicionA();
    }

    /**
     *
     * @param nombre
     */
    public EstadoNF(String nombre) {
        this.nombre = nombre;
        transiciones = new ArrayList<TransicionAFND>();
        transicionesA = new TransicionA();
    }

    /**
     *
     * @param nombre
     * @param esfinal
     */
    public EstadoNF(String nombre, boolean esfinal) {
        this.nombre = nombre;
        transiciones = new ArrayList<TransicionAFND>();
        transicionesA = new TransicionA();
        this.esfinal = esfinal;
    }

    protected Object clone() {
        EstadoNF es = null;
        try {
            es = (EstadoNF) super.clone();
            es.transiciones = (ArrayList<TransicionAFND>) ((ArrayList<TransicionAFND>) es.transiciones).clone();
            for (int i = 0; i < es.transiciones.size(); i++) {
                es.transiciones.set(i, (TransicionAFND) es.transiciones.get(i).clone());
            }
            es.transicionesA.clone();
        } catch (CloneNotSupportedException e) {

        }
        return es;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public List<TransicionAFND> getTransiciones() {
        return transiciones;
    }

    /**
     *
     * @return
     */
    public TransicionA getTransicionesA() {
        return transicionesA;
    }

    /**
     *
     * @return
     */
    public boolean isFinal() {
        return isFinal();
    }

    public boolean equals(Object o) {
        if (this != o) {
            return this.nombre.equals(((EstadoNF) o).nombre);
        } else {
            return true;
        }
    }

    public String toString() {
        String s = "";
        if (esfinal) {
            s = "Estado : " + nombre + " es final";
        } else {
            s = "Estado : " + nombre;
        }
        return s;
    }

    boolean existeTransicion(EstadoNF e1, char simbolo, EstadoNF e2) {
        boolean encontrado = false;
        int i = 0;
        int l = transiciones.size();
        while (!encontrado && i < l) {
            if ((transiciones.get(i).e1.equals(e1)) && (transiciones.get(i).simbolo == simbolo)) {
                encontrado = true;
            } else {
                i++;
            }
        }
        if (encontrado == true) {
            encontrado = false;
            int in = i;
            i = 0;
            ArrayList<EstadoNF> aux = this.transiciones.get(in).e2;
            int l2 = aux.size();
            while (!encontrado && i < l2) {
                if ((aux.get(i).nombre).equals(e2.nombre)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
        }
        return encontrado;
    }

    boolean modificarTransicion(EstadoNF e1, char Simbolo, EstadoNF e2, EstadoNF e3) {
        boolean encontrado = false;
        if (existeTransicion(e1, Simbolo, e2)) {
            int i = 0;
            int j = 0;
            int l = e1.transiciones.size();
            while (!encontrado && i < l) {
                j = 0;
                TransicionAFND aux = e1.transiciones.get(i);
                int ltr = aux.e2.size();
                while (!encontrado && j < ltr) {
                    if ((aux.simbolo == Simbolo) && ((aux.e2.get(j).nombre).equals(e2.nombre))) {
                        encontrado = true;
                    } else {
                        j++;
                    }
                }
                if (!encontrado) {
                    i++;
                }
            }
            if (encontrado == true) {
                TransicionAFND tr = transiciones.get(i);
                if (!existeTransicion(e1, Simbolo, e3)) {
                    tr.e2.set(j, e3);
                } else {
                    encontrado = false;
                }
            }
        }
        return encontrado;
    }

    boolean agregarTransicion(EstadoNF e1, char simbolo, EstadoNF e2) {
        boolean encontrado = false;
        if (!existeTransicion(e1, simbolo, e2)) {
            int i = 0;
            int l = transiciones.size();
            while (!encontrado && i < l) {
                if ((transiciones.get(i).e1.equals(e1)) && (transiciones.get(i).simbolo == simbolo)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            TransicionAFND tr;
            if (encontrado == true) {
                tr = transiciones.get(i);
                tr.e2.add(e2);
            } else {
                ArrayList<EstadoNF> arr = new ArrayList<EstadoNF>();
                arr.add(e2);
                tr = new TransicionAFND(e1, simbolo, arr);
                transiciones.add(tr);
                encontrado = true;
            }
        }
        return encontrado;
    }

    boolean eliminarTransicion(EstadoNF e1, char simbolo, EstadoNF e2) {
        boolean encontrado = false;
        if (existeTransicion(e1, simbolo, e2)) {
            int i = 0;
            int j = 0;
            int l = e1.transiciones.size();
            while (!encontrado && i < l) { //Encuentro la transicion
                j = 0;
                TransicionAFND aux = e1.transiciones.get(i);
                int ltr = aux.e2.size();
                while (!encontrado && j < ltr) {
                    if ((aux.simbolo == simbolo) && ((aux.e2.get(j).nombre).equals(e2.nombre))) {
                        encontrado = true;
                    } else {
                        j++;
                    }
                }
                if (!encontrado) {
                    i++;
                }
            }
            if (encontrado == true) {
                TransicionAFND tr = transiciones.get(i);
                tr.e2.remove(j);
                if (tr.e2.size() == 0)//Si l==1 al eliminar el destino quedaría a 0 por lo que borramos la transicion de las transiciones del estado.
                {
                    e1.transiciones.remove(tr);
                }
            }
        }
        return encontrado;
    }

    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    public boolean existeTransicionA(EstadoNF e1, EstadoNF e2) {
        boolean encontrado = false;
        int i = 0;
        int l = transicionesA.e2.size();

        while (!encontrado && i < l) {
            if ((transicionesA.e2.get(i).nombre).equals(e2.nombre)) {
                encontrado = true;
            } else {
                i++;
            }
        }

        return encontrado;
    }

    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    public boolean agregarTransicionA(EstadoNF e1, EstadoNF e2) {
        boolean encontrado = false;
        if (!existeTransicionA(e1, e2)) {
            encontrado = true;
            transicionesA.e1 = e1;
            transicionesA.e2.add(e2);
        }
        return encontrado;
    }

    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    public boolean eliminarTransicionλ(EstadoNF e1, EstadoNF e2) {
        boolean encontrado = false;
        if (existeTransicionA(e1, e2)) {
            encontrado = true;
            transicionesA.e2.remove(transicionesA.e2.indexOf(e2));
        }
        if (transicionesA.e2.size() == 0) {
            transicionesA.e2.clear();
        }
        return encontrado;
    }

    /**
     *
     * @param e1
     * @param e2
     * @param e3
     * @return
     */
    public boolean modificarTransicionA(EstadoNF e1, EstadoNF e2, EstadoNF e3) {
        boolean encontrado = false;
        if (existeTransicionA(e1, e2)) {
            encontrado = true;
            transicionesA.e2.set(transicionesA.e2.indexOf(e2), e3);
        }
        return encontrado;
    }
}
