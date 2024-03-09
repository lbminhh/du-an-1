/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author LE MINH
 */
public class LineCustom extends JPanel{

    public LineCustom() {
        Graphics g = this.getGraphics();
        g.drawLine(0, 0, 200, 200);
        setOpaque(false);
    }
    
}
