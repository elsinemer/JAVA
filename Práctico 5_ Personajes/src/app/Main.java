package app;

import personajes.*;

public class Main {
    public static void main(String[] args) {
        Personaje[] party = new Personaje[] {
            new Mago("Sissu", 12),
            new Guerrero("Flor", 15),
            new Mago("Elsi", 9),
            new Guerrero("Tita", 10)
        };

        for (Personaje p : party) {
            p.accionEspecial();
        }
    }
}
