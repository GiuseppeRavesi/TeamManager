package model;

import model.enums.Ruolo;
import model.enums.Status;

import java.time.LocalDate;
import java.util.Objects;

public class GiocatoreInRosa {

    private Giocatore giocatore;
    private Ruolo ruolo;               
    private Status status;
    private int numMaglia;
    private LocalDate dataInserimento;

    public GiocatoreInRosa(Giocatore giocatore, Ruolo ruolo, Status status,int numMaglia, LocalDate dataInserimento) {
        this.giocatore = giocatore;
        this.ruolo = ruolo;
        this.status = status;
        this.numMaglia = numMaglia;
        this.dataInserimento = dataInserimento;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDataInserimento() {
        return dataInserimento;
    }

    public int getNumMaglia() {
        return numMaglia;
    }

    public void setNumMaglia(int numMaglia) {
        this.numMaglia = numMaglia;
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
               + " | " + ruolo.name() + " | " + status.name();
    }
    
    //metodo to string di enzov
    public String toString2() {
        return String.format("%-12s %-12s %-4s %-20s %-10s", giocatore.getNome(), giocatore.getCognome(), "#" + numMaglia, ruolo, status);
    }
}
