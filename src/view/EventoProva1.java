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



public class EventoProva1 {
    
    private int id;
    private LocalDate data;
    private LocalTime orario;
    private int durata;
    private String luogo;
    boolean disponibilita;

    public EventoProva1(int id, LocalDate data, LocalTime orario, int durata, String luogo,boolean disponibilita ) {
        this.id = id;
        this.data = data;
        this.orario = orario;
        this.durata = durata;
        this.luogo = luogo;
        this.disponibilita = disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        this.disponibilita = disponibilita;
    }

    public boolean isDisponibilita() {
        return disponibilita;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public int getDurata() {
        return durata;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
    
    
    
}
