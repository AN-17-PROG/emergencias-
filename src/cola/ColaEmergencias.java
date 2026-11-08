/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cola;

/**
 *
 * @author Cesar
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import modelo.Emergencia;

public class ColaEmergencias {
    
    private final List<Emergencia> pendientes = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition hayEmergencias = lock.newCondition();

    public void agregar(Emergencia e) {
        lock.lock();
        try {
            pendientes.add(e);
            hayEmergencias.signal();
        } finally {
            lock.unlock();
        }
    }

    public Emergencia tomarMasUrgente() throws InterruptedException {
        lock.lock();
        try {
            while (pendientes.isEmpty()) {
                hayEmergencias.await();
            }
            Emergencia mejor = pendientes.get(0);
            double mejorPuntos = mejor.calcularPrioridad();
            for (Emergencia e : pendientes) {
                double puntos = e.calcularPrioridad();
                if (puntos > mejorPuntos) {
                    mejor = e;
                    mejorPuntos = puntos;
                }
            }
            pendientes.remove(mejor);
            return mejor;
        } finally {
            lock.unlock();
        }
        
    }
    public List<Emergencia> verPendientes() {
    lock.lock();
    try {
        return new ArrayList<>(pendientes);
    } finally {
        lock.unlock();
    }
}
    
}
