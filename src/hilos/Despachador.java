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
import modelo.Asignacion;
import modelo.Emergencia;
import modelo.Estado;
import modelo.EstadoRecurso;
import monitor.Estadisticas;
import recursos.GestorRecursos;

public class Despachador implements Runnable{
    
  
    private final String nombre;
    private final ColaEmergencias cola;
    private final GestorRecursos gestor;
    private final Estadisticas estadisticas;
    private final BusEventos bus;

    public Despachador(String nombre, ColaEmergencias cola, GestorRecursos gestor,
                       Estadisticas estadisticas, BusEventos bus) {
        this.nombre = nombre;
        this.cola = cola;
        this.gestor = gestor;
        this.estadisticas = estadisticas;
        this.bus = bus;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Emergencia e = cola.tomarMasUrgente();
                Asignacion asignacion = gestor.reservar(200);

                if (asignacion == null) {
                    bus.publicar(new Evento(TipoaEvento.SIN_RECURSOS, nombre, e, "la devuelve a la cola"));
                    cola.agregar(e);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 150));
                } else {
                    atender(e, asignacion);
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println(nombre + " termino.");
    }

    private void atender(Emergencia e, Asignacion asignacion) throws InterruptedException {
        try {
            estadisticas.registrarAsignacion(e.segundosEsperando());
            e.setEstado(Estado.ASIGNADA);
            bus.publicar(new Evento(TipoaEvento.RECURSOS_ASIGNADOS, nombre, e, asignacion.toString()));

            asignacion.getAmbulancia().setEstado(EstadoRecurso.EN_CAMINO);
            e.setEstado(Estado.EN_CAMINO);
            bus.publicar(new Evento(TipoaEvento.AMBULANCIA_EN_CAMINO, nombre, e,
                    asignacion.getAmbulancia().toString()));
            Thread.sleep(1000);

            asignacion.getAmbulancia().setEstado(EstadoRecurso.OCUPADO);
            Thread.sleep(1500);

            e.setEstado(Estado.ATENDIDA);
            bus.publicar(new Evento(TipoaEvento.EMERGENCIA_ATENDIDA, nombre, e, "atencion terminada"));
        } finally {
            gestor.liberar(asignacion);
        }
    }
}
