/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alberto's PC
 */
public class Lector {

    private File fichero;
    private static final String HEADER = "digraph finite_state_machine {\n rankdir=LR;\n size=\"8,5\"\n  ";
    private Object transiciones;

    /**
     *
     * @param fichero
     */
    public Lector(String fichero) {
        this.fichero = new File(fichero);
    }

    /**
     *
     */
    public Lector() {

    }

    /**
     *
     * @return
     */
    public boolean resetFile() {
        boolean res = false;
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(fichero.getAbsolutePath());
            bw = new BufferedWriter(fw);
            bw.write("");
            bw.flush();
            res = true;
        } catch (IOException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param estados
     * @return
     */
    public boolean writeAFD(EstadoF[] estados) {
        boolean res = false;
        BufferedWriter bw = null;
        FileWriter fw = null;
        String content = HEADER + " node [shape = point]; start;\n";
        String contTr = "";
        try {
            for (int i = 0; i < estados.length; i++) {
                if (estados[i].esfinal) {
                    content = content + " node [shape = doublecircle, color=black, fontcolor=black]; " + estados[i].nombre + ";\n";
                } else {
                    content = content + "node [shape = circle] " + estados[i].nombre + ";\n";
                }
                for (int j = 0; j < estados[i].transiciones.size(); j++) {
                    TransicionAFD trafd = estados[i].transiciones.get(j);
                    contTr = contTr + trafd.getE1().nombre + " -> " + trafd.getE2().nombre + " [label = \"" + trafd.getSimbolo() + "\"];\n";
                }
            }
            content = content + contTr + "start -> " + estados[0].nombre + ";}";
            fw = new FileWriter(fichero.getAbsolutePath());
            bw = new BufferedWriter(fw);
            bw.append(content);
            bw.flush();
            res = true;
        } catch (IOException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param estados
     * @return
     */
    public boolean writeAFND(EstadoNF[] estados) {
        boolean res = false;
        BufferedWriter bw = null;
        FileWriter fw = null;
        String content = HEADER + " node [shape = point]; start;\n";
        String contTr = "";
        try {
            for (int i = 0; i < estados.length; i++) {
                if (estados[i].esfinal) {
                    content = content + " node [shape = doublecircle, color=black, fontcolor=black]; " + estados[i].nombre + ";\n";
                } else {
                    content = content + "node [shape = circle] " + estados[i].nombre + ";\n";
                }
                for (int j = 0; j < estados[i].transiciones.size(); j++) {
                    List<TransicionAFND> tr = estados[i].transiciones;
                    for (int k = 0; k < tr.get(j).e2.size(); k++) {
                        contTr = contTr + tr.get(j).getE1().nombre + " -> " + tr.get(j).getE2().get(k).nombre + " [label = \"" + tr.get(j).getSimbolo() + "\"];\n";
                    }
                }
                TransicionA tr1 = estados[i].getTransicionesA();
                for (int j = 0; j < tr1.getE2().size(); j++) {
                    contTr = contTr + tr1.getE1().nombre + " -> " + tr1.getE2().get(j).nombre + " [label = \"λ\"];\n";
                }
            }
            content = content + contTr + "start -> " + estados[0].nombre + ";}";
            fw = new FileWriter(fichero.getAbsolutePath());
            bw = new BufferedWriter(fw);
            bw.append(content);
            bw.flush();
            res = true;
        } catch (IOException e) {
            res = false;
            e.printStackTrace();
        }

        return res;
    }

    /**
     *
     * @param filename
     * @return
     */
    public Proceso readFile(String filename) {
        FileReader f;
        boolean isAFD = true;
        Proceso p;
        p = null;
        try {
            f = new FileReader(filename);
            BufferedReader b;
            b = new BufferedReader(f);
            String s;
            while (true) {
                s = b.readLine();
                if (s.equals("#EOF")) {
                    break;
                }
                if (s.startsWith("#AFD")) {
                    isAFD = true;
                    p = new AFD();
                } else if (s.startsWith("#AFND")) {
                    isAFD = false;
                    p = new AFND();
                }
                if (s.startsWith("#Estados")) {
                    if (isAFD) {
                        String[] read = s.split(" ");
                        for (int i = 0; i < read.length; i++) {
                            ((AFD) p).estados.add(new EstadoF(read[i]));
                        }
                    } else {
                        String[] read = s.split(" ");
                        for (int i = 0; i < read.length; i++) {
                            ((AFND) p).estados.add(new EstadoNF(read[i]));
                        }
                    }
                } else if (s.startsWith("#Finales")) {
                    if (isAFD) {
                        String[] read = s.split(" ");
                        for (int i = 1; i < read.length; i++) {
                            ((AFD) p).estados.add(new EstadoF(read[i], true));
                        }
                    } else {
                        String[] read = s.split(" ");
                        for (int i = 1; i < read.length; i++) {
                            ((AFND) p).estados.add(new EstadoNF(read[i], true));
                        }
                    }
                } else if (!s.startsWith("#")) {
                    if (isAFD) {
                        String[] read = s.split(",");
                        int i = ((AFD) p).estados.indexOf(new EstadoF(read[0]));
                        ((AFD) p).estados.get(i).transiciones.add(new TransicionAFD(read[0], read[1].charAt(0), read[2]));
                    } else {
                        String[] read = s.split(",");
                        int i = ((AFND) p).estados.indexOf(new EstadoNF(read[0]));
                        if (!read[1].equals("l")) {
                            ArrayList<EstadoNF> e2 = new ArrayList<EstadoNF>();
                            String[] strSplit;
                            if (read[2].startsWith("[")) {
                                strSplit = read[2].split(",");
                            } else {
                                strSplit = new String[1];
                                strSplit[0] = read[2];
                            }
                            for (int j = 0; j < strSplit.length; j++) {
                                char[] chrs = strSplit[j].toCharArray();
                                if (chrs.length > 2) {
                                    for (int k = 0; k < chrs.length; k++) {
                                        if (chrs[k] != '[' && chrs[k] != ']') {
                                            e2.add(new EstadoNF(String.valueOf(chrs[k])));
                                        }
                                    }
                                } else if (chrs.length > 0) {
                                    e2.add(new EstadoNF(String.valueOf(chrs[j])));
                                }
                            }
                            ((AFND) p).estados.get(i).transiciones.add(new TransicionAFND(new EstadoNF(read[0]), read[1].charAt(0), e2));
                        } else {
                            ArrayList<EstadoNF> e2 = new ArrayList<EstadoNF>();
                            String[] strSplit = read[2].split(",");
                            for (int j = 0; j < strSplit.length; j++) {
                                char[] chrs = strSplit[j].toCharArray();
                                if (chrs.length > 2) {
                                    for (int k = 0; k < chrs.length; k++) {
                                        if (chrs[k] != '[' && chrs[k] != ']') {
                                            ((AFND) p).estados.get(i).transicionesA.e2.add(new EstadoNF(String.valueOf(chrs[k])));
                                        }
                                    }
                                } else if (chrs.length > 0) {
                                    ((AFND) p).estados.get(i).transicionesA.e2.add(new EstadoNF(String.valueOf(chrs[j])));
                                }
                            }
                            EstadoNF visto = ((AFND) p).estados.get(i);
                            ((AFND) p).estados.get(i).transicionesA.e1 = new EstadoNF(read[0]);

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
