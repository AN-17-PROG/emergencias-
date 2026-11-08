/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

/**
 *
 * @author Cesar
 */


import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class BusEventos implements Runnable {
    
       private final List<Observador> observadores = new CopyOnWriteArrayList<>();
    private final BlockingQueue<Evento> buzon = new LinkedBlockingQueue<>();

    public void suscribir(Observador o) {
        observadores.add(o);
    }

    public void publicar(Evento evento) {
        buzon.offer(evento);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Evento evento = buzon.take();
                entregar(evento);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void entregar(Evento evento) {
        for (Observador o : observadores) {
            try {
                o.alRecibir(evento);
            } catch (RuntimeException ex) {
                System.err.println("Un observador fallo: " + ex);
            }
        }
    }
    
}
