package model;

/**
 *
 * @author Giuseppe Ravesi
 */
public abstract class Statistica {

    protected int id;
    protected int idGiocatore;
    protected int idEvento;
    
    private static int idCounter = 1;

    public Statistica(int idGiocatore, int idEvento) {
        this.id = generaId();
        this.idGiocatore = idGiocatore;
        this.idEvento = idEvento;
    }

    public Statistica(int id, int idGiocatore, int idEvento) {
        this.id = id;
        this.idGiocatore = idGiocatore;
        this.idEvento = idEvento;
    }

    protected int generaId() {
        return idCounter++;
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

}
