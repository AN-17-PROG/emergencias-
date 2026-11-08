/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Cesar
 */
public enum Estado {
    PENDIENTE,  //llamo y esta esperando en la cola.
    ASIGNADA,   //un despachador le reservo recursos.
    EN_CAMINO,  //la ambulancia va hacia el lugar.
    ATENDIDA,   //ya termino.
    
}
