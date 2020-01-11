package Codigo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alberto
 *
 * Esta clase define los Autómatas Finitos Deterministas
 */
public class AFD implements Proceso<EstadoF>, Cloneable {

    public ArrayList<EstadoF> estados;

    public AFD() {
        estados = new ArrayList<EstadoF>();
    }

    /*
        Identifica si un estado es correcto
     */
    public boolean estadoCorrecto(String nombre) {
        if (this.estados.indexOf(new EstadoF(nombre)) == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
        Permite colar el AFD
     */
    protected Object clone() {
        AFD cl = null;
        try {
            cl = (AFD) super.clone();
            cl.estados = (ArrayList< EstadoF>) cl.estados.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return cl;
    }

    public boolean agregarTransicionAFD(String e1, char simbolo, String e2) {
        EstadoF es1 = new EstadoF(e1);
        EstadoF es2 = new EstadoF(e2);
        int i = this.estados.indexOf(es1);
        return estados.get(i).agregaTransicion(estados.get(estados.indexOf(es1)), simbolo, estados.get(estados.indexOf(es2)));
    }

    public boolean eliminarTransicion(String e1, char simbolo, String e2) {
        EstadoF es1 = new EstadoF(e1);
        EstadoF es2 = new EstadoF(e2);
        int i = this.estados.indexOf(es1);
        return estados.get(i).eliminarTransicion(estados.get(estados.indexOf(es1)), simbolo, estados.get(estados.indexOf(es2)));
    }

    public boolean modificarTransicion(String e1, char simbolo, String e2) {
        EstadoF es1 = new EstadoF(e1);
        EstadoF es2 = new EstadoF(e2);
        int i = this.estados.indexOf(es1);
        return estados.get(i).modificarTransicion(estados.get(estados.indexOf(es1)), simbolo, estados.get(estados.indexOf(es2)));
    }

    public EstadoF transicion(EstadoF estado, char simbolo) {
        List<TransicionAFD> aux = estados.get(estados.indexOf(estado)).transiciones;
        EstadoF dev = new EstadoF();
        boolean encontrado = false;
        int i = 0;
        while (i < aux.size() && !encontrado) {
            if (aux.get(i).simbolo == simbolo) {
                encontrado = true;
            } else {
                i++;
            }
        }
        try {
            TransicionAFD tr = aux.get(i);
            dev = tr.e2;
        } catch (Exception e) {
            System.out.println("No existe transicion para " + estado + " + " + simbolo);
        }
        return dev;
    }

    public boolean esFinal(EstadoF estado) {
        return estados.get(estados.indexOf(estado)).esfinal;
    }

    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        EstadoF estado = estados.get(0); //El estado inicial es el 0
        for (int i = 0; i < simbolo.length; i++) {
            EstadoF estado_anterior;
            estado_anterior = estado;
            if (!estado.nombre.equals("null")) {
                System.out.println(estado_anterior + " + " + simbolo[i]);
            }
            try {
                estado = transicion(estado, simbolo[i]);
            } catch (Exception e) {
                return false;
            }
        }
        return esFinal(estado);
    }

    public boolean modificarEstado(String anterior, String nuevo) {
        int n = this.estados.size();
        int i = 0;
        while (!(this.estados.get(i).nombre).equals(anterior)) {
            i++;
        }
        int in = i; //indice anterior
        if (!estadoCorrecto(nuevo)) { //si el estado no existe ya
            EstadoF aux = this.estados.get(in);
            aux.nombre = nuevo;
        } else { //Si el estado ya existe
            return false;
        }
        return true;
    }

    public void eliminarEstado(String nombre) {
        int n = this.estados.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < this.estados.get(i).transiciones.size(); j++) {
                TransicionAFD aux = this.estados.get(i).transiciones.get(j);
                if ((aux.e1.nombre).equals(nombre) || (aux.e2.nombre).equals(nombre)) {
                    this.estados.get(this.estados.indexOf(new EstadoF(aux.e1.nombre))).transiciones.remove(aux);
                }
                if (!(aux.e1.nombre).equals(nombre)) {
                    j++;
                }
            }
        }
        this.estados.remove(new EstadoF(nombre));
    }

    public boolean eliminarEstadoFinal(String nombre) {
        EstadoF tmp = this.estados.get(this.estados.indexOf(new EstadoF(nombre)));
        if (tmp.esfinal) {
            eliminarEstado(nombre);
            return true;
        } else {
            return false;
        }
    }

    public void agregarEstado(String nombre, boolean esfinal) {
        this.estados.add(new EstadoF(nombre, esfinal));
    }

    public void agregarEstado(String nombre) {
        this.estados.add(new EstadoF(nombre));
    }
    
}
