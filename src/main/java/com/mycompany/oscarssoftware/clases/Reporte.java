package com.mycompany.oscarssoftware.clases;

import java.awt.Desktop;
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

            // Obtener la ruta dinámica del escritorio de OneDrive o el escritorio predeterminado
            String desktopPath = obtenerRutaEscritorio();

            // Generar el nombre del archivo
            String rutaPDF = desktopPath + File.separator + "Reporte_" + idPedido + ".pdf";

            // Exportar el reporte a PDF
            exportarAFilePDF(jasperPrint, rutaPDF);

            // Abrir el archivo generado
            abrirArchivoConRuntime(rutaPDF);

        } catch (Exception ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String obtenerRutaEscritorio() {
        // Verificar si OneDrive está habilitado
        String oneDrivePath = System.getenv("OneDrive");
        if (oneDrivePath != null) {
            String oneDriveDesktopPath = oneDrivePath + File.separator + "Desktop";
            File oneDriveDesktop = new File(oneDriveDesktopPath);
            if (oneDriveDesktop.exists()) {
                return oneDriveDesktopPath;  // Usar el escritorio de OneDrive si existe
            }
        }

        // Si no existe OneDrive, usar el escritorio predeterminado
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + "Desktop";
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
                // Abrir el archivo PDF con Runtime
                    Desktop.getDesktop().open(archivo);
            } else {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, "El archivo PDF no existe: " + rutaArchivo);
            }
        } catch (Exception ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, "Error al abrir el archivo PDF", ex);
        }
    }
}
