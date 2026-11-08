/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;

/**
 *
 * @author Cesar
 */

import comunicacion.Evento;
import comunicacion.Observador;
import comunicacion.TipoaEvento;
import java.util.concurrent.atomic.AtomicInteger;

public class Hospital  implements Observador {
    
    private final String nombre;
    private final AtomicInteger pacientesEsperados = new AtomicInteger();

    public Hospital(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void alRecibir(Evento evento) {
        if (evento.getTipo() != TipoaEvento.AMBULANCIA_EN_CAMINO) {
            return;
        }
        if (evento.getEmergencia().getGravedad().getPeso() >= 3) {
            int total = pacientesEsperados.incrementAndGet();
            System.out.println("  [" + nombre + "] se prepara para recibir ID "
                    + evento.getEmergencia().getId() + " (paciente #" + total + ")");
        }
    }
    
}
