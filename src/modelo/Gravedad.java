/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Cesar
 */
public  enum Gravedad {
    CRITICO(4),
    GRAVE(3),
    MODERADO(2),
    LEVE(1);

    private final int peso;

    Gravedad(int peso) { this.peso = peso; }

    public int getPeso() { return peso; }
}
    
    
    

