package Codigo;

/**
 *
 * @author Alberto
 */
public interface Proceso<T> {

    public abstract boolean esFinal(T estado); //true si estado es un estado final

    public abstract boolean reconocer(String cadena); //true si la cadena es reconocida

    public abstract String toString(); //muestra las transiciones y estados finales
}
