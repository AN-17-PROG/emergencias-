/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilos;

/**
 *
 * @author Cesar
 */

import comunicacion.BusEventos;
import comunicacion.Evento;
import comunicacion.TipoaEvento;
import java.util.concurrent.ThreadLocalRandom;
import modelo.Recurso;
import recursos.GestorRecursos;

public class Averias  implements Runnable {
    
    private final GestorRecursos gestor;
    private final BusEventos bus;

    public Averias(GestorRecursos gestor, BusEventos bus) {
        this.gestor = gestor;
        this.bus = bus;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4000, 8000));
                Recurso r = gestor.averiarUno();
                if (r == null) {
                    continue;
                }
                bus.publicar(new Evento(TipoaEvento.RECURSO_AVERIADO, "Averias", null,
                        r + " fuera de servicio"));
                try {
                    Thread.sleep(3000);
                } finally {
                    gestor.reparar(r);
                    bus.publicar(new Evento(TipoaEvento.RECURSO_REPARADO, "Averias", null,
                            r + " vuelve a estar disponible"));
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Averias termino.");
    }
}
