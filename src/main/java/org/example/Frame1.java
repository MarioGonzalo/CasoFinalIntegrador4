package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Frame1 extends JFrame {
    private JTextArea areaTexto;

    public Frame1() {
        setTitle("Editor de Texto");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        areaTexto = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        guardarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        archivoMenu.add(guardarItem);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(areaTexto.getText());
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

