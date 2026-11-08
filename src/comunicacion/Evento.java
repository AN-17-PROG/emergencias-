/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

/**
 *
 * @author Cesar
 */
import modelo.Emergencia;

public class Evento {
    
     private final TipoaEvento tipo;
    private final String origen;
    private final Emergencia emergencia;
    private final String detalle;

    public Evento(TipoaEvento tipo, String origen, Emergencia emergencia, String detalle) {
        this.tipo = tipo;
        this.origen = origen;
        this.emergencia = emergencia;
        this.detalle = detalle;
    }

    public TipoaEvento getTipo() { return tipo; }
    public String getOrigen() { return origen; }
    public Emergencia getEmergencia() { return emergencia; }
    public String getDetalle() { return detalle; }

    @Override
    public String toString() {
        String id = (emergencia == null) ? "-"
                : "ID " + emergencia.getId() + " (" + emergencia.getGravedad() + ")";
        return origen + " | " + tipo + " | " + id + " | " + detalle;
    }
}
