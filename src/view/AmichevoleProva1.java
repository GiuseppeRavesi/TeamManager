/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelProva;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author enzov
 */
public class AmichevoleProva1 extends EventoProva1 {
    private String squadraAvversaria;

    public AmichevoleProva1(int id, LocalDate data, LocalTime orario, int durata, String luogo, boolean disponibilita,
                           String squadraAvversaria) {
        super(id, data, orario, durata, luogo, disponibilita);
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

