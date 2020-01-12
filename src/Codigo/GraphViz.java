package Codigo;

import java.io.IOException;

/**
 *
 * @author Alberto's PC
 */
public class GraphViz {

    public GraphViz(String org) throws IOException {
        /*
        ProcessBuilder pbuilder;
        pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", "src/Imagenes/arbol.gif",
                "Graphviz2.38/bin/dot.exe");
        pbuilder.redirectErrorStream(true);
        pbuilder.start();
*/
        String origen = org; 
        String ruta = "Graphviz2.38\\bin\\dot.exe " + 
                "-Tgif " + origen + " -o " + "src\\Imagenes\\automata.gif";
        Process cmd = Runtime.getRuntime().exec(ruta); 
    }
}
