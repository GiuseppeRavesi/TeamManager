/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author Giuseppe Ravesi
 */
public class RosaCompletaException extends Exception {
   
    public RosaCompletaException() {
        super("Non puoi aggiungere altri giocatori: la rosa ha raggiunto il limite massimo di 22 giocatori.");
    }
}
