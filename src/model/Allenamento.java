package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Allenamento extends Evento {

    private String tipologia; 
    private String note;      

    public Allenamento(LocalDate data, LocalTime orario, int durata, String luogo, String tipologia, String note) {
        super(data, orario, durata, luogo);
        this.tipologia = tipologia;
        this.note = note;
    }

    //per caricamento da file
    public Allenamento(int id, LocalDate data, LocalTime orario, int durata, String luogo, String tipologia, String note) {
        super(id, data, orario, durata, luogo);
        this.tipologia = tipologia;
        this.note = note;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipologia: " + tipologia + ", Note: " + note;
    }
}
