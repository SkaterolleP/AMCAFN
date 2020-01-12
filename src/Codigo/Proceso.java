package Codigo;

/**
 *
 * @author Alberto
 * @param <T>
 */
public interface Proceso<T> {

    /**
     *
     * @param estado
     * @return
     */
    public abstract boolean esFinal(T estado); //true si estado es un estado final

    /**
     *
     * @param cadena
     * @return
     */
    public abstract boolean reconocer(String cadena); //true si la cadena es reconocida

    /**
     *
     * @return
     */
    public abstract String toString(); //muestra las transiciones y estados finales
}
