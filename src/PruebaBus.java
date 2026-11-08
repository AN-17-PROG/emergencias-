/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Cesar
 */
import comunicacion.BusEventos;
import comunicacion.Evento;
import comunicacion.TipoaEvento;
import monitor.RegistroConsola;
import modelo.Emergencia;
import modelo.Gravedad;

public class PruebaBus {
    
     public static void main(String[] args) throws InterruptedException  {
          BusEventos bus = new BusEventos();
        Thread hiloBus = new Thread(bus);
        hiloBus.start();

        bus.suscribir(new RegistroConsola());
        bus.suscribir(e -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  (observador lento) termino con " + e.getTipo());
        });

        Emergencia em = new Emergencia(Gravedad.GRAVE, 3.0);
        long inicio = System.currentTimeMillis();
        bus.publicar(new Evento(TipoaEvento.LLAMADA_RECIBIDA, "Prueba", em, "3.0 km"));
        System.out.println("publicar() tardo " + (System.currentTimeMillis() - inicio) + " ms");

        Thread.sleep(4000);
        hiloBus.interrupt();
    }
     }
      
    
    

