package Codigo;

import java.util.ArrayList;

/**
 *
 * @author Alberto's PC
 */
public class EstadoF implements Cloneable {

    /**
     *
     */
    public String nombre;

    /**
     *
     */
    public ArrayList<TransicionAFD> transiciones;//Lista de transiciones de AFD de estado. Estas seran : origen, simbolo, destino

    /**
     *
     */
    public boolean esfinal;

    /**
     *
     * @param nombre
     */
    public EstadoF(String nombre) {
        this.nombre = nombre;
        transiciones = new ArrayList<TransicionAFD>();
    }

    /**
     *
     */
    public EstadoF() {
        nombre = "null";
    }

    /**
     *
     * @param nombre
     * @param esfinal
     */
    public EstadoF(String nombre, boolean esfinal) {
        this.nombre = nombre;
        transiciones = new ArrayList<TransicionAFD>();
        this.esfinal = esfinal;
    }

    /**
     *
     * @param nombre
     * @param transiciones
     * @param esfinal
     */
    public EstadoF(String nombre, ArrayList<TransicionAFD> transiciones, boolean esfinal) {
        super();
        this.nombre = nombre;
        this.transiciones = transiciones;
        this.esfinal = esfinal;
    }

    protected Object clone() {
        EstadoF es = null;
        try {
            es = (EstadoF) super.clone();
            es.transiciones = (ArrayList<TransicionAFD>) this.transiciones.clone();
            for (int i = 0; i < es.transiciones.size(); i++) {
                es.transiciones.set(i, (TransicionAFD) es.transiciones.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {

        }
        return es;
    }

    public boolean equals(Object o) {
        if (this != o) {
            return this.nombre.equals(((EstadoF) o).nombre);
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

    /*
        Permite conocer si el estado indicado tiene alguna transición mediante un simbolo determinado
     */
    private boolean existeTransicion(EstadoF e1, char simbolo) {
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
        return encontrado;
    }

    /*
        Añade una transicion al estado
     */
    boolean agregaTransicion(EstadoF e1, char simbolo, EstadoF e2) {
        boolean encontrado = false;
        if (!existeTransicion(e1, simbolo)) {
            int i = 0;
            int l = transiciones.size();
            while (!encontrado && i < l) {
                if ((transiciones.get(i).e1.equals(e1)) && (transiciones.get(i).simbolo == simbolo)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            TransicionAFD tr = new TransicionAFD(e1, simbolo, e2);
            transiciones.add(tr);
        }
        return encontrado;
    }

    /*
        Elimina una transición de la lisata de transiciones del estado
     */
    boolean eliminarTransicion(EstadoF e1, char simbolo, EstadoF e2) {
        boolean encontrado = false;
        if (existeTransicion(e1, simbolo)) {
            int i = 0;
            int l = transiciones.size();
            while (!encontrado && i < l) {
                if ((transiciones.get(i).e1.equals(e1)) && (transiciones.get(i).simbolo == simbolo)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            TransicionAFD tr = new TransicionAFD(e1, simbolo, e2);
            transiciones.remove(tr);
        }
        return encontrado;
    }

    /*
        Modifica una transición ya existente de la lista de transiciones del estado
     */
    boolean modificarTransicion(EstadoF e1, char simbolo, EstadoF e2) {
        boolean encontrado = false;
        if (existeTransicion(e1, simbolo)) {
            int i = 0;
            int l = transiciones.size();
            while (!encontrado && i < l) {
                if ((transiciones.get(i).e1.equals(e1)) && (transiciones.get(i).simbolo == simbolo)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            if (encontrado) {
                TransicionAFD tr = new TransicionAFD(e1, simbolo, e2);
                transiciones.set(i, tr);
            }
        }
        return encontrado;
    }

    
}
