package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Amichevole extends Evento {
    private String squadraAvversaria;

    public Amichevole(LocalDate data, LocalTime orario, int durata, String luogo, String squadraAvversaria) {
        super(data, orario, durata, luogo);
        this.squadraAvversaria = squadraAvversaria;
    }

    public Amichevole(int id, LocalDate data, LocalTime orario, int durata, String luogo, String squadraAvversaria) {
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
        return super.toString() + ", Avversario: " + squadraAvversaria;
    }
}

