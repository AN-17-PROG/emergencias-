/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilos;

/**
 *
 * @author Cesar
 */


import cola.ColaEmergencias;
import comunicacion.BusEventos;
import comunicacion.Evento;
import comunicacion.TipoaEvento;
import java.util.concurrent.ThreadLocalRandom;
import modelo.Emergencia;
import modelo.Gravedad;
import monitor.Estadisticas;

public class Operador  implements Runnable {
    
    private final String nombre;
    private final ColaEmergencias cola;
    private final Estadisticas estadisticas;
    private final BusEventos bus;

    public Operador(String nombre, ColaEmergencias cola, Estadisticas estadisticas, BusEventos bus) {
        this.nombre = nombre;
        this.cola = cola;
        this.estadisticas = estadisticas;
        this.bus = bus;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
                Emergencia e = generarLlamada();
estadisticas.registrarLlamada();
bus.publicar(new Evento(TipoaEvento.LLAMADA_RECIBIDA, nombre, e,
        String.format("%.1f km", e.getDistanciakm())));
cola.agregar(e);

            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println(nombre + " termino.");
    }

    private Emergencia generarLlamada() {
        ThreadLocalRandom azar = ThreadLocalRandom.current();
        Gravedad gravedad = Gravedad.values()[azar.nextInt(Gravedad.values().length)];
        double distancia = azar.nextDouble(0.5, 10.0);
        return new Emergencia(gravedad, distancia);
    }
}
