package model;

import java.time.LocalDate;
import java.util.Objects;

public class GiocatoreInRosa {

    private Giocatore giocatore;          
    private String ruolo;
    private String status;                
    private LocalDate dataInserimento;

    public GiocatoreInRosa(Giocatore giocatore, String ruolo, String status, LocalDate dataInserimento) {
        this.giocatore = giocatore;
        this.ruolo = ruolo;
        this.status = status;
        this.dataInserimento = dataInserimento;
    }

    
    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(LocalDate dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiocatoreInRosa)) return false;
        GiocatoreInRosa that = (GiocatoreInRosa) o;
        return Objects.equals(giocatore, that.giocatore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giocatore);
    }

    @Override
    public String toString() {
        return giocatore.getNome() + " " + giocatore.getCognome()
               + " | " + ruolo + " | " + status;
    }
}

