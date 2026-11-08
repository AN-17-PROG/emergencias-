/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Cesar
 */
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class Emergencia {
    
    private static final AtomicInteger contador =new AtomicInteger(0);
    
    
    private final int id;
    private final Gravedad gravedad;
    private final double distanciakm;
    private final Instant horaLlegada;
    private volatile Estado estado;
    
    private static final double PESO_GRAVEDAD = 100;
    private static final double PESO_ESPERA = 10;
    private static final double PESO_DISTANCIA = 2;
    
    public Emergencia(Gravedad gravedad, double distanciakm){
        this.id = contador.incrementAndGet();
        this.gravedad = gravedad;
        this.distanciakm = distanciakm;
        this.horaLlegada = Instant.now();
        this.estado = Estado.PENDIENTE;
    }
    
    
    public long segundosEsperando() {
        return Duration.between(horaLlegada, Instant.now()).getSeconds();
    }
    
    public double calcularPrioridad() {
    return gravedad.getPeso() * PESO_GRAVEDAD
         + segundosEsperando() * PESO_ESPERA
         - distanciakm * PESO_DISTANCIA;
}
       
    public int getId() {return id; }
    public Gravedad getGravedad() { return gravedad; }
    public double getDistanciakm() { return distanciakm; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado;}
} 
