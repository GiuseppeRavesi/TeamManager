package model;

import java.util.Objects;

/**
 *
 * @author Giuseppe Ravesi
 */
public abstract class Statistica {

    protected int id;
    protected int idGiocatore;
    protected int idEvento;

     private static int contatore = 0;

    public Statistica(int idGiocatore, int idEvento) {
        this.id = ++contatore;
        this.idGiocatore = idGiocatore;
        this.idEvento = idEvento;
    }

    public Statistica(int id, int idGiocatore, int idEvento) {
        this.id = id;
        this.idGiocatore = idGiocatore;
        this.idEvento = idEvento;
    }

    public int getId() {
        return id;
    }

    public int getIdGiocatore() {
        return idGiocatore;
    }

    public int getIdEvento() {
        return idEvento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Statistica that = (Statistica) o;

        return id == that.id
                && idGiocatore == that.idGiocatore
                && idEvento == that.idEvento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idGiocatore, idEvento);
    }
}
