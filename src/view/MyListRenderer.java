/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Session;
import controller.TeamManager;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import model.GiocatoreInRosa;
import model.Rosa;

/**
 *
 * @author enzov
 */
public class MyListRenderer extends DefaultListCellRenderer {
    
    private int indexList;

    public MyListRenderer(int indexList) {
  
        this.indexList = indexList;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        
        
        if (index == indexList) {
           
                setForeground(Color.GREEN);
            } 

        return c;
    }

}
