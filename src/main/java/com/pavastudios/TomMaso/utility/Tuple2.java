package com.pavastudios.TomMaso.utility;

import java.util.Objects;

/**
 * Classe che modella il concetto di coppia
 *
 * @param <T1>
 * @param <T2>
 */
public class Tuple2<T1, T2> {
    private final T1 t1;
    private final T2 t2;

    /**
     * Metodo costruttore della classe Tuple2
     *
     * @param t1 primo valore della coppia
     * @param t2 secondo valore della coppia
     */
    public Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(t1, tuple2.t1) && Objects.equals(t2, tuple2.t2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t1, t2);
    }

    public T1 get1() {
        return t1;
    }

    public T2 get2() {
        return t2;
    }
}
