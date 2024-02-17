package org.basics;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.*;
import javax.swing.*;

public class JLayeredPaneTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JLayeredPane Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 530);
        JLayeredPane jLayeredPane = new JLayeredPane();
        JButton jButton1 = new JButton("Open");
        jButton1.setBackground(Color.green);
        jButton1.setBounds(175, 50, 150, 100);
        jLayeredPane.add(jButton1, JLayeredPane.DEFAULT_LAYER);
        JButton jButton2 = new JButton("Open");
        JButton jButton3 = new JButton("Close");
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton2.setBackground(Color.RED);
        jButton3.setBackground(Color.RED);
        jButton2.setBounds(75, 125, 150, 100);
        jButton3.setBounds(275, 125, 150, 100);
        jLayeredPane.add(jButton2, JLayeredPane.PALETTE_LAYER);
        jLayeredPane.add(jButton3, JLayeredPane.PALETTE_LAYER);
        JButton jButton4 = new JButton("Open");
        JButton jButton5 = new JButton("Close");
        jButton4.setVisible(false);
        jButton5.setVisible(false);
        jButton4.setBackground(Color.MAGENTA);
        jButton5.setBackground(Color.MAGENTA);
        jButton4.setBounds(75, 125, 150, 100);
        jButton5.setBounds(275, 125, 150, 100);
        jLayeredPane.add(jButton4, JLayeredPane.MODAL_LAYER);
        jLayeredPane.add(jButton5, JLayeredPane.MODAL_LAYER);
        JButton jButton6 = new JButton("Open");
        JButton jButton7 = new JButton("Close");
        jButton6.setVisible(false);
        jButton7.setVisible(false);
        jButton6.setBackground(Color.CYAN);
        jButton7.setBackground(Color.CYAN);
        jButton6.setBounds(75, 125, 150, 100);
        jButton7.setBounds(275, 125, 150, 100);
        jLayeredPane.add(jButton6, JLayeredPane.POPUP_LAYER);
        jLayeredPane.add(jButton7, JLayeredPane.POPUP_LAYER);
        JButton jButton8 = new JButton("Close");
        jButton8.setVisible(false);
        jButton8.setBackground(Color.GRAY);
        jButton8.setBounds(175, 350, 150, 100);
        jLayeredPane.add(jButton8, JLayeredPane.DRAG_LAYER);
        frame.add(jLayeredPane);
        frame.setVisible(true);
        jButton1.addActionListener(e -> {
            jButton2.setVisible(true);
            jButton3.setVisible(true);
        });
        jButton2.addActionListener(e -> {
            jButton4.setVisible(true);
            jButton5.setVisible(true);
        });
        jButton3.addActionListener(e -> {
            jButton2.setVisible(false);
            jButton3.setVisible(false);
        });
        jButton4.addActionListener(e -> {
            jButton6.setVisible(true);
            jButton7.setVisible(true);
        });
        jButton5.addActionListener(e -> {
            jButton4.setVisible(false);
            jButton5.setVisible(false);
        });
        jButton6.addActionListener(e -> {
            jButton8.setVisible(true);
        });
        jButton7.addActionListener(e -> {
            jButton6.setVisible(false);
            jButton7.setVisible(false);
        });
        jButton8.addActionListener(e -> {
            jButton8.setVisible(false);
        });
    }
}
