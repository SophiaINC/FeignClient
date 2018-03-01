/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.listener;

import gs.nom.mx.views.DialogLoading;
import gs.nom.mx.views.Principal;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author acruzb
 */
public class PrincipalEventsAdapter implements ComponentListener{
    
    private final Principal principal;
    private final DialogLoading loading;

    public PrincipalEventsAdapter(Principal principal, DialogLoading loading) {
        this.principal = principal;
        this.loading = loading;
    }    
    

    @Override
    public void componentResized(ComponentEvent e) {
        loading.setLocationRelativeTo(principal);
//        panelAprobado.setBounds(scroll.getWidth() - 200, scroll.getBounds().y + 50, 210, 210);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        loading.setLocationRelativeTo(principal);
//        panelAprobado.setBounds(scroll.getWidth() - 200, scroll.getBounds().y + 50, 210, 210);
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
    
}
