/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

/**
 *
 * @author Cesar
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import modelo.Asignacion;
import modelo.EstadoRecurso;
import modelo.Recurso;

public class GestorRecursos {
   
      private final List<Recurso> ambulancias = new ArrayList<>();
    private final List<Recurso> medicos = new ArrayList<>();
    private final List<Recurso> equipos = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition cambioDeRecursos = lock.newCondition();

    public void agregar(Recurso r) {
        lock.lock();
        try {
            switch (r.getTipo()) {
                case AMBULANCIA: ambulancias.add(r); break;
                case MEDICO: medicos.add(r); break;
                case EQUIPO: equipos.add(r); break;
            }
            cambioDeRecursos.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Asignacion reservar(long timeoutMs) throws InterruptedException {
        long restante = TimeUnit.MILLISECONDS.toNanos(timeoutMs);
        lock.lock();
        try {
            while (!hayTodoDisponible()) {
                if (restante <= 0) {
                    return null;
                }
                restante = cambioDeRecursos.awaitNanos(restante);
            }
            Recurso a = tomar(ambulancias);
            Recurso m = tomar(medicos);
            Recurso e = tomar(equipos);
            return new Asignacion(a, m, e);
        } finally {
            lock.unlock();
        }
    }

    public void liberar(Asignacion asignacion) {
        lock.lock();
        try {
            asignacion.getAmbulancia().setEstado(EstadoRecurso.DISPONIBLE);
            asignacion.getMedico().setEstado(EstadoRecurso.DISPONIBLE);
            asignacion.getEquipo().setEstado(EstadoRecurso.DISPONIBLE);
            cambioDeRecursos.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public List<Recurso> verRecursos() {
        lock.lock();
        try {
            List<Recurso> todos = new ArrayList<>();
            todos.addAll(ambulancias);
            todos.addAll(medicos);
            todos.addAll(equipos);
            return todos;
        } finally {
            lock.unlock();
        }
    }

    public Recurso averiarUno() {
        lock.lock();
        try {
            List<Recurso> libres = new ArrayList<>();
            for (List<Recurso> lista : List.of(ambulancias, medicos, equipos)) {
                for (Recurso r : lista) {
                    if (r.getEstado() == EstadoRecurso.DISPONIBLE) {
                        libres.add(r);
                    }
                }
            }
            if (libres.isEmpty()) {
                return null;
            }
            Recurso elegido = libres.get(ThreadLocalRandom.current().nextInt(libres.size()));
            elegido.setEstado(EstadoRecurso.FUERA_DE_SERVICIO);
            return elegido;
        } finally {
            lock.unlock();
        }
    }

    public void reparar(Recurso r) {
        lock.lock();
        try {
            r.setEstado(EstadoRecurso.DISPONIBLE);
            cambioDeRecursos.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private boolean hayTodoDisponible() {
        return buscarDisponible(ambulancias) != null
            && buscarDisponible(medicos) != null
            && buscarDisponible(equipos) != null;
    }

    private Recurso buscarDisponible(List<Recurso> lista) {
        for (Recurso r : lista) {
            if (r.getEstado() == EstadoRecurso.DISPONIBLE) {
                return r;
            }
        }
        return null;
    }

    private Recurso tomar(List<Recurso> lista) {
        Recurso r = buscarDisponible(lista);
        r.setEstado(EstadoRecurso.OCUPADO);
        return r;
    }
}
