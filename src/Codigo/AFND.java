package Codigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alberto
 *
 * Esta clase define los Autómatas Finitos No Deterministas
 */
public class AFND implements Proceso<EstadoNF>, Cloneable {

    private int[] estadosFinales;

    /**
     *
     */
    public List<EstadoNF> estados;
    private List<TransicionAFND> transiciones;
    private List<TransicionA> transicionesA;

    /**
     *
     */
    public AFND() {
        estados = new ArrayList<EstadoNF>();
    }

    boolean estadosCorrecto(String nombre) {
        if (this.estados.indexOf(new EstadoNF(nombre)) == -1) {
            return false;
        } else {
            return true;
        }
    }

    protected Object clone() {
        AFND cl = new AFND();
        try {
            cl = (AFND) super.clone();
            cl.estados = (ArrayList<EstadoNF>) ((ArrayList<EstadoNF>) cl.estados).clone();
            for (int i = 0; i < cl.estados.size(); i++) {
                cl.estados.set(i, (EstadoNF) cl.estados.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            System.err.println("Error");
        }
        return cl;
    }

    /**
     * Permite modificar un estado por otro nuevo Al modificarlo el nuevo estado
     * heredará las transiciones AFND y A entrantes y salientes
     *
     * @param anterior
     * @param nuevo
     * @return
     */
    public boolean modificarEstado(String anterior, String nuevo) {
        int n = this.estados.size();
        int i = 0;
        while (!(this.estados.get(i).nombre).equals(anterior)) {
            i++;
        }
        int in = i;
        if (!estadosCorrecto(nuevo)) {
            EstadoNF aux = this.estados.get(in);
            aux.nombre = nuevo;
        } else {
            return false;
        }
        return true;
    }

    /**
     * Elimina una transicion AFND
     *
     * @param e1
     * @param simbolo
     * @param e2
     * @return
     */
    public boolean eliminarTransicion(String e1, char simbolo, String e2) {
        EstadoNF origen = new EstadoNF(e1);
        EstadoNF destino = new EstadoNF(e2);
        int i = this.estados.indexOf(origen);
        return estados.get(i).eliminarTransicion(estados.get(estados.indexOf(origen)), simbolo, estados.get(estados.indexOf(destino)));
    }

    /**
     * Elimina una transicion A
     *
     * @param e1
     * @param e2
     * @return
     */
    public boolean eliminarTransicionA(EstadoNF e1, EstadoNF e2) {
        int i = this.estados.indexOf(e1);
        return estados.get(i).eliminarTransicionλ(estados.get(estados.indexOf(e1)), estados.get(estados.indexOf(e2)));
    }

    /**
     * Modifica una transición AFND
     *
     * @param e1
     * @param simbolo
     * @param e2
     * @param e3
     * @return
     */
    public boolean modificarTransicion(String e1, char simbolo, String e2, String e3) {
        EstadoNF origen = new EstadoNF(e1);
        EstadoNF destino = new EstadoNF(e2);
        EstadoNF nuevo = new EstadoNF(e3);
        int i = this.estados.indexOf(origen);
        return estados.get(i).modificarTransicion(estados.get(estados.indexOf(origen)), simbolo, estados.get(estados.indexOf(destino)), estados.get(estados.indexOf(nuevo)));
    }

    /**
     * Modifica una transicion A
     *
     * @param e1
     * @param e2
     * @param e3
     * @return
     */
    public boolean modificarTransicionA(EstadoNF e1, EstadoNF e2, EstadoNF e3) {
        int i = this.estados.indexOf(e1);
        return estados.get(i).modificarTransicionA(estados.get(estados.indexOf(e1)), estados.get(estados.indexOf(e2)), estados.get(estados.indexOf(e3)));
    }

    /**
     * Agrega una transicion AFND
     *
     * @param e1
     * @param simbolo
     * @param e2
     * @return
     */
    public boolean agregarTransicion(EstadoNF e1, char simbolo, EstadoNF e2) {
        int indice = estados.indexOf(e1);
        return estados.get(indice).agregarTransicion(e1, simbolo, e2);
    }

    /**
     * Agrega una transicion A
     *
     * @param e1
     * @param e2
     * @return
     */
    public boolean agregarTransicionA(EstadoNF e1, EstadoNF e2) {
        int i = estados.indexOf(new EstadoNF(e1));
        return estados.get(i).agregarTransicionA(e1, e2);
    }

    /**
     * Comprueba si un estado AFND tiene transiciones A
     *
     * @param act
     * @return
     */
    public boolean haytransicionA(EstadoNF act) {
        return !act.transicionesA.e2.isEmpty();
    }

    /*
        Permite ver Aclausura dado un estado y un macroestado del automata
     */
    private ArrayList<EstadoNF> Aclausura(EstadoNF actual, HashMap<EstadoNF, Boolean> visitados, ArrayList<EstadoNF> macroestado) {
        if (!haytransicionA(actual)) {
            macroestado.add(actual);
            return macroestado;
        } else {
            macroestado.add(actual);
            visitados.put(actual, Boolean.TRUE);
            for (int i = 0; i < actual.transicionesA.e2.size(); i++) {
                EstadoNF siguiente = actual.transicionesA.e2.get(i);
                if (!macroestado.contains(siguiente) && !visitados.get(this.estados.get(this.estados.indexOf(siguiente))).booleanValue()) {
                    Aclausura(siguiente, visitados, macroestado);
                }
            }
        }
        return macroestado;
    }

    /*
        Permite hallar Aclausura dadu un etado del autómata
     */
    private ArrayList<EstadoNF> Aclausura(EstadoNF E) {
        ArrayList<EstadoNF> macroestado = new ArrayList<EstadoNF>();
        HashMap<EstadoNF, Boolean> visitados = new HashMap<EstadoNF, Boolean>();
        int i = 0;
        while (i < this.estados.size()) {
            visitados.put(this.estados.get(i), Boolean.FALSE);
            i++;
        }
        macroestado = Aclausura(E, visitados, macroestado);
        return macroestado;
    }

    private ArrayList<EstadoNF> Aclausura(ArrayList<EstadoNF> macroestado) {
        ArrayList<EstadoNF> dev = new ArrayList<EstadoNF>();
        for (int i = 0; i < macroestado.size(); i++) {
            dev.add(macroestado.get(i));
        }
        int i = 0;
        while (i < macroestado.size()) {
            ArrayList<EstadoNF> AclausuraE = new ArrayList<EstadoNF>();
            AclausuraE = Aclausura(macroestado.get(i));
            for (int j = 0; j < AclausuraE.size(); j++) {
                if (!dev.contains(AclausuraE.get(j))) {
                    dev.add(AclausuraE.get(j));
                }
            }
            i++;
        }
        return dev;
    }

    /**
     * Devuelve los estados destinos dado un estado origen y un simbolo
     *
     * @param e
     * @param simbolo
     * @return
     */
    public ArrayList<EstadoNF> transicion(EstadoNF e, char simbolo) {
        List<TransicionAFND> aux = this.estados.get(estados.indexOf(e)).transiciones;
        ArrayList<EstadoNF> dev = new ArrayList<EstadoNF>();
        boolean encontrado = false;
        int i = 0;
        while (i < aux.size() && !encontrado) {
            if (aux.get(i).simbolo == simbolo) {
                encontrado = true;
            } else {
                i++;
            }
        }
        if (encontrado) {
            TransicionAFND tr = aux.get(i);
            for (int j = 0; j < tr.e2.size(); j++) {
                dev.add(this.estados.get(this.estados.indexOf(tr.e2.get(j))));
            }
        }

        return dev;
    }

    /**
     *
     * @param estado
     * @param simbolo
     * @return
     */
    public ArrayList<EstadoNF> transicion(String estado, char simbolo) {
        List<TransicionAFND> aux = estados.get(estados.indexOf(new EstadoNF(estado))).transiciones;
        ArrayList<EstadoNF> dev = new ArrayList<EstadoNF>();
        boolean encontrado = false;
        int i = 0;
        while (i < aux.size() && !encontrado) {
            if (aux.get(i).simbolo == simbolo) {
                encontrado = true;
            } else {
                i++;
            }
        }
        if (encontrado) {
            TransicionAFND tr = aux.get(i);
            for (int j = 0; j < tr.e2.size(); j++) {
                dev.add(this.estados.get(this.estados.indexOf(tr.e2.get(j))));
            }
        }

        return dev;
    }

    /**
     *
     * @param macroestado
     * @param simbolo
     * @return
     */
    public ArrayList<EstadoNF> transicion(ArrayList<EstadoNF> macroestado, char simbolo) {
        ArrayList<EstadoNF> prima = new ArrayList<EstadoNF>();
        for (int i = 0; i < macroestado.size(); i++) {
            EstadoNF actual = macroestado.get(i);
            ArrayList<EstadoNF> aux = transicion(actual.nombre, simbolo);
            for (int j = 0; j < aux.size(); j++) {
                if (!prima.contains(aux.get(j))) {
                    prima.add(aux.get(j));
                }
            }
        }
        ArrayList<EstadoNF> E1 = new ArrayList<EstadoNF>();
        for (int i = 0; i < prima.size(); i++) {
            ArrayList<EstadoNF> clausuraprima = Aclausura(prima.get(i));
            for (int j = 0; j < clausuraprima.size(); j++) {
                E1.add(clausuraprima.get(j));
            }
        }
        return E1;
    }

    /**
     *
     * @param estado
     * @return
     */
    public TransicionA transicionA(String estado) {
        return estados.get(estados.indexOf(new EstadoNF(estado))).transicionesA;
    }

    private boolean esFinal(String estado) {
        return estados.get(estados.indexOf(new EstadoNF(estado))).esfinal;
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean esFinal(EstadoNF e) {
        return esFinal(e.nombre);
    }

    /**
     *
     * @param macroestado
     * @return
     */
    public boolean esFinal(ArrayList<EstadoNF> macroestado) {
        boolean hayfinal = false;
        int i = 0;

        while (i < macroestado.size() && !hayfinal) {
            if (esFinal(macroestado.get(i).nombre)) {
                hayfinal = true;
            } else {
                i++;
            }
        }
        return hayfinal;
    }

    public String toString() {
        String s = "Estados finales: \n";
        for (int i = 0; i < this.estados.size(); i++) {
            if (esFinal(this.estados.get(i))) {
                s += this.estados.get(i).nombre + "\n";
            }
        }
        s += "\n\nTransiciones: \n";
        for (int i = 0; i < this.estados.size(); i++) {
            for (int j = 0; j < this.estados.get(i).transiciones.size(); j++) {
                s += this.estados.get(i).transiciones.get(j);
            }
        }
        s += "\n\n----------------------------";
        return s;
    }

    /**
     *
     * @param cadena
     * @return
     */
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        ArrayList<EstadoNF> lambdas = new ArrayList<EstadoNF>(); //El estado inicial es el 0
        lambdas.add(this.estados.get(0));
        ArrayList<EstadoNF> macroestado = Aclausura(lambdas);
        int i = 0;
        for (; i < simbolo.length; i++) {
            System.out.println("Macroestado " + i + " " + macroestado);
            macroestado = transicion(macroestado, simbolo[i]);
        }
        System.out.println("Macroestado " + i + " " + macroestado);
        return esFinal(macroestado);
    }

    /**
     *
     * @param e
     */
    public void eliminarEstado(EstadoNF e) {
        this.estados.remove(e);
    }

    /**
     *
     * @param nombre
     */
    public void eliminarEstado(String nombre) {
        int n = this.estados.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < this.estados.get(i).transiciones.size();) {
                TransicionAFND aux = this.estados.get(i).transiciones.get(j);
                for (int k = 0; k < aux.e2.size();) {
                    if ((aux.e1.nombre).equals(nombre) || (aux.e2.get(k).nombre).equals(nombre)) {
                        eliminarTransicion(aux.e1.nombre, aux.simbolo, aux.e2.get(k).nombre);
                    } else {
                        k++;
                    }
                }
                if (aux.e2.size() != 0) {
                    j++;
                }
            }
            TransicionA aux = this.estados.get(i).transicionesA;
            for (int m = 0; m < aux.e2.size();) {
                if ((aux.e1.nombre).equals(nombre) || (aux.e2.get(m).nombre).equals(nombre)) {
                    eliminarTransicionA(new EstadoNF(aux.e1.nombre), new EstadoNF(aux.e2.get(m).nombre));
                } else {
                    m++;
                }
            }

        }
        this.estados.remove(new EstadoNF(nombre));
    }

    /**
     *
     * @param nombre
     */
    public void eliminarEstadoFinal(String nombre) {
        EstadoNF tmp = this.estados.get(this.estados.indexOf(new EstadoNF(nombre)));
        if (tmp.isFinal()) {
            eliminarEstado(nombre);
        }
    }

    /**
     *
     * @param nombre
     * @param esfinal
     */
    public void agregarEstado(String nombre, boolean esfinal) {
        this.estados.add(new EstadoNF(nombre, esfinal));
    }

    /**
     *
     * @param nombre
     */
    public void agregarEstado(String nombre) {
        this.estados.add(new EstadoNF(nombre));
    }

    /**
     *
     * @param e
     */
    public void agregarEstado(EstadoNF e) {
        this.estados.add(e);
    }
}
