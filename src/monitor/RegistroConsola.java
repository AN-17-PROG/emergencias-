/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitor;

/**
 *
 * @author Cesar
 */
import comunicacion.Evento;
import comunicacion.Observador;

public class RegistroConsola implements Observador {
    
   
    @Override
    public void alRecibir(Evento evento) {
        System.out.println(evento);
    }
    
}
