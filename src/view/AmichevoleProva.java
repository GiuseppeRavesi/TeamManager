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



public class AmichevoleProva extends EventoProva {
    private String squadraAvversaria;

    public AmichevoleProva(int id, LocalDate data, LocalTime orario, int durata, String luogo,
                           String squadraAvversaria) {
        super(id, data, orario, durata, luogo);
        this.squadraAvversaria = squadraAvversaria;
    }

    public String getSquadraAvversaria() {
        return squadraAvversaria;
    }

    public void setSquadraAvversaria(String squadraAvversaria) {
        this.squadraAvversaria = squadraAvversaria;
    }

    @Override
    public String toString() {
        return super.toString() + ", squadraAvversaria='" + squadraAvversaria + '\'' + '}';
    }
}

