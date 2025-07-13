package model;


/**
 *
 * @author Giuseppe Ravesi
 */
public class Disponibilità {

    private int idGiocatore;
    private int idEvento;
    private boolean presenza;
    private String motivazione;
    private Statistica statistica;

    public Disponibilità(int idGiocatore, int idEvento, boolean presenza, String motivazione) {
        this.idGiocatore = idGiocatore;
        this.idEvento = idEvento;
        this.presenza = presenza;
        this.statistica = null;

        if (presenza) {
            this.motivazione = null;
        } else {
            this.motivazione = motivazione;
        }
    }

    public Disponibilità(int idGiocatore, int idEvento, boolean presenza, String motivazione, Statistica statistica) {
        this.idGiocatore = idGiocatore;
        this.idEvento = idEvento;
        this.presenza = presenza;
        this.statistica = statistica;

        if (presenza) {
            this.motivazione = null;
        } else {
            this.motivazione = motivazione;
        }
    }

    public int getIdGiocatore() {
        return idGiocatore;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public boolean isPresenza() {
        return presenza;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public Statistica getStatistica() {
        return statistica;
    }

    public void setStatistica(Statistica statistica) {
        this.statistica = statistica;
    }
    
    public void rimuoviStatistica(){
        this.statistica = null;
    }
    
    @Override
    public String toString() {
        return "Disponibilità{"
                + "idGiocatore=" + idGiocatore
                + ", idEvento=" + idEvento
                + ", presenza=" + presenza
                + ", motivazione='" + motivazione + '\''
                + '}';
    }
}
