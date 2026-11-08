/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitor;

/**
 *
 * @author Cesar
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Estadisticas {
     private final AtomicInteger recibidas = new AtomicInteger();
    private final AtomicInteger asignadas = new AtomicInteger();
    private final AtomicLong sumaRespuesta = new AtomicLong();
    private final AtomicLong maxRespuesta = new AtomicLong();

    public void registrarLlamada() {
        recibidas.incrementAndGet();
    }

    public void registrarAsignacion(long segundosRespuesta) {
        asignadas.incrementAndGet();
        sumaRespuesta.addAndGet(segundosRespuesta);
        maxRespuesta.accumulateAndGet(segundosRespuesta, Math::max);
    }

    public int getRecibidas() { return recibidas.get(); }
    public int getAsignadas() { return asignadas.get(); }
    public long getMaxRespuesta() { return maxRespuesta.get(); }

    public double getPromedioRespuesta() {
        int n = asignadas.get();
        return n == 0 ? 0 : (double) sumaRespuesta.get() / n;
        
    }
}
