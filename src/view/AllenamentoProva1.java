/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author enzov
 */



public class AllenamentoProva1 extends EventoProva1 {
    private String tipologia;
    private String note;

    public AllenamentoProva1(int id, LocalDate data, LocalTime orario, int durata, String luogo, boolean disponibilita,
                            String tipologia, String note) {
        super(id, data, orario, durata, luogo,disponibilita);
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
        return super.toString() + ", tipologia='" + tipologia + '\'' + ", note='" + note + '\'' + '}';
    }
}

