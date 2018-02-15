/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gs.nom.mx.concurrent;

import gs.nom.mx.views.Principal;
import static gs.nom.mx.views.Principal.hideLoading;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Temporal
 */
public class ThreatChecarProceso extends Thread{
    
    private final Thread hiloSuperior;

    public ThreatChecarProceso(Thread hiloSuperior) {
        this.hiloSuperior = hiloSuperior;
    }
    
    @Override
    public void run(){
        while(hiloSuperior.isAlive()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hideLoading();
    }
            
}
