package com.chat.common;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
 
/**
 * Custom renderer to display a country's flag alongside its name
 *
 * @author wwww.codejava.net
 */
public class UserDTORenderer extends JLabel implements ListCellRenderer<UserDTO> {
 
    @Override
    public Component getListCellRendererComponent(JList<? extends UserDTO> list, UserDTO user, int index,
        boolean isSelected, boolean cellHasFocus) {
          
       
        setText(user.getUserName() + "(" + user.getEmail() + ")");
         
        return this;
    }
     
}