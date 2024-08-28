package com.mycompany.oscarssoftware.clases;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte extends conexion{
    
    public Reporte(){}
    public void generarReporte(String ubicacion,String titulo){
              try {
                  // Ruta al archivo .jasper
                  String reportPath = getClass().getResource(ubicacion).getPath();

                  // Parámetros del informe
                  Map<String, Object> parameters = new HashMap<>();
                  // Agrega parámetros según sea necesario

                  // Llenar el informe
                  JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

                  // Mostrar el informe en una nueva ventana
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setTitle(titulo);
                viewer.setVisible(true);

              } catch (JRException ex) {
                  Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
              }
    }
        public void generarReporteParametros(String ubicacion, String titulo, Map<String, Object> parameters) {
        try {
            // Ruta al archivo .jasper
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
