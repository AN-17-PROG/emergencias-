/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Cesar
 */
public class Asignacion {
     private final Recurso ambulancia;
    private final Recurso medico;
    private final Recurso equipo;

    public Asignacion(Recurso ambulancia, Recurso medico, Recurso equipo) {
        this.ambulancia = ambulancia;
        this.medico = medico;
        this.equipo = equipo;
    }

    public Recurso getAmbulancia() { return ambulancia; }
    public Recurso getMedico() { return medico; }
    public Recurso getEquipo() { return equipo; }

    @Override
    public String toString() {
        return "Asignacion[" + ambulancia + ", " + medico + ", " + equipo + "]";
    }
}
