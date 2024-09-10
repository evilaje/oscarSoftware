package com.mycompany.oscarssoftware.clases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class Reporte extends conexion {

    public Reporte() {
    }

    public void generarReporteConImagen(String ubicacion, String titulo, Map<String, Object> parameters, String rutaImagen, int idPedido) {
        try {
            InputStream imageStream = getClass().getResourceAsStream(rutaImagen);
            parameters.put("logo", imageStream);

            JasperPrint jasperPrint = generarReporteConParametros(ubicacion, parameters);

            // Generar un nombre de archivo único basado en el ID del pedido
            String rutaPDF = "C:\\Users\\Orne\\Desktop\\Reportes\\Reporte_" + idPedido + ".pdf";
            exportarAFilePDF(jasperPrint, rutaPDF);

            abrirArchivoConRuntime(rutaPDF);

        } catch (Exception ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JasperPrint generarReporteConParametros(String ubicacion, Map<String, Object> parameters) throws JRException {
        String reportPath = getClass().getResource(ubicacion).getPath();
        return JasperFillManager.fillReport(reportPath, parameters, getCon());
    }

    private void exportarAFilePDF(JasperPrint jasperPrint, String rutaArchivo) throws JRException {
        try (FileOutputStream outputStream = new FileOutputStream(new File(rutaArchivo))) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, "Error al exportar el reporte a PDF", ex);
        }
    }

    private void abrirArchivoConRuntime(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                // Abrir usando Runtime
                Runtime.getRuntime().exec("cmd /c start " + archivo.getAbsolutePath());
            } else {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, "El archivo PDF no existe: " + rutaArchivo);
            }
        } catch (Exception ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, "Error al abrir el archivo PDF", ex);
        }
    }
}