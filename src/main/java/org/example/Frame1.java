package org.example;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Frame1 extends JFrame {
    private JTextArea areaTexto;
    private JComboBox<String> listaDocumentos;
    private ArrayList<String> nombresArchivos;

    public Frame1() {
        setTitle("Editor de Texto");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        areaTexto = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        listaDocumentos = new JComboBox<>();
        listaDocumentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDocumentoSeleccionado();
            }
        });

        JPanel panelBotones = new JPanel();
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        panelBotones.add(botonGuardar);

        getContentPane().add(listaDocumentos, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        cargarListaDocumentos();
    }

    private void cargarListaDocumentos() {
        nombresArchivos = new ArrayList<>();
        File carpetaDocumentos = new File("documentos");
        if (!carpetaDocumentos.exists() || !carpetaDocumentos.isDirectory()) {
            carpetaDocumentos.mkdir();
        } else {
            File[] archivos = carpetaDocumentos.listFiles();
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    nombresArchivos.add(archivo.getName());
                }
            }
            actualizarListaDocumentos();
        }
    }

    private void actualizarListaDocumentos() {
        listaDocumentos.removeAllItems();
        for (String nombreArchivo : nombresArchivos) {
            listaDocumentos.addItem(nombreArchivo);
        }
    }

    private void cargarDocumentoSeleccionado() {
        String nombreArchivo = (String) listaDocumentos.getSelectedItem();
        if (nombreArchivo != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader("documentos/" + nombreArchivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                areaTexto.setText(contenido.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(areaTexto.getText());
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.");
                cargarListaDocumentos();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

