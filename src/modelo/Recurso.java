/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Cesar
 */
public class Recurso {
    
      private final int id;
    private final TipoRecurso tipo;
    private volatile EstadoRecurso estado = EstadoRecurso.DISPONIBLE;

    public Recurso(int id, TipoRecurso tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public TipoRecurso getTipo() { return tipo; }
    public EstadoRecurso getEstado() { return estado; }
    public void setEstado(EstadoRecurso estado) { this.estado = estado; }

    @Override
    public String toString() {
        return tipo + "-" + id;
    }
}
