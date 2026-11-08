/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Cesar
 */
import cola.ColaEmergencias;
import comunicacion.BusEventos;
import hilos.Averias;
import hilos.Despachador;
import hilos.Operador;
import hospital.Hospital;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import modelo.Recurso;
import modelo.TipoRecurso;
import monitor.Estadisticas;
import monitor.Monitor;
import monitor.RegistroConsola;
import recursos.GestorRecursos;

public class Main {
    public static void main(String[]args) throws InterruptedException{
        
      int numOperadores = 2;
        int numDespachadores = 2;
        int numRecursos = 2;
        int segundos = 20;
        boolean conAverias = true;

        ColaEmergencias cola = new ColaEmergencias();
        GestorRecursos gestor = new GestorRecursos();
        Estadisticas estadisticas = new Estadisticas();

        BusEventos bus = new BusEventos();
        bus.suscribir(new RegistroConsola());
        bus.suscribir(new Hospital("Hospital Central"));

        for (int i = 1; i <= numRecursos; i++) {
            gestor.agregar(new Recurso(i, TipoRecurso.AMBULANCIA));
            gestor.agregar(new Recurso(i, TipoRecurso.MEDICO));
            gestor.agregar(new Recurso(i, TipoRecurso.EQUIPO));
        }

        int hilos = numOperadores + numDespachadores + 2 + (conAverias ? 1 : 0);
        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        pool.submit(bus);
        for (int i = 1; i <= numOperadores; i++) {
            pool.submit(new Operador("Operador-" + i, cola, estadisticas, bus));
        }
        for (int i = 1; i <= numDespachadores; i++) {
            pool.submit(new Despachador("Despachador-" + i, cola, gestor, estadisticas, bus));
        }
        pool.submit(new Monitor(cola, gestor, estadisticas));
        if (conAverias) {
            pool.submit(new Averias(gestor, bus));
        }

        Thread.sleep(segundos * 1000L);
        pool.shutdownNow();
    } 

    }

