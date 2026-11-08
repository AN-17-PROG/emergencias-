/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitor;

/**
 *
 * @author Cesar
 */
import cola.ColaEmergencias;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Emergencia;
import modelo.Recurso;
import recursos.GestorRecursos;

public class Monitor  implements Runnable {
    
    private final ColaEmergencias cola;
    private final GestorRecursos gestor;
    private final Estadisticas estadisticas;

    public Monitor(ColaEmergencias cola, GestorRecursos gestor, Estadisticas estadisticas) {
        this.cola = cola;
        this.gestor = gestor;
         this.estadisticas = estadisticas;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(2000);
                mostrarTablero();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void mostrarTablero() {
        List<Emergencia> pendientes = cola.verPendientes();
        List<Recurso> recursos = gestor.verRecursos();

        Map<Emergencia, Double> puntos = new HashMap<>();
        for (Emergencia e : pendientes) {
            puntos.put(e, e.calcularPrioridad());
        }
        pendientes.sort((a, b) -> Double.compare(puntos.get(b), puntos.get(a)));

        StringBuilder sb = new StringBuilder();
        sb.append("\n========== MONITOR ==========\n");
        
        long maxEspera = 0;
for (Emergencia e : pendientes) {
    maxEspera = Math.max(maxEspera, e.segundosEsperando());
}
sb.append("Emergencias pendientes: ").append(pendientes.size())
  .append(" | espera maxima: ").append(maxEspera).append(" s\n");
        

        int mostradas = 0;
        for (Emergencia e : pendientes) {
            if (mostradas == 5) {
                sb.append("  ...\n");
                break;
            }
            sb.append(String.format("  ID %d | %s | prioridad %.0f | espera %d s%n",
                    e.getId(), e.getGravedad(), puntos.get(e), e.segundosEsperando()));
            mostradas++;
        }
        
        sb.append(String.format("Llamadas: %d | Asignadas: %d | Respuesta promedio: %.1f s | maxima: %d s%n",
        estadisticas.getRecibidas(), estadisticas.getAsignadas(),
        estadisticas.getPromedioRespuesta(), estadisticas.getMaxRespuesta()));

        sb.append("Recursos:\n");
        for (Recurso r : recursos) {
            sb.append("  ").append(r).append(" -> ").append(r.getEstado()).append("\n");
        }
        sb.append("=============================");
        System.out.println(sb);
    }
}
