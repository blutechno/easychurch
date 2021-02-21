/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl.GUI;

import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.view.JasperViewer;
import orm.DAOFactory;

/**
 *
 * @author jean pierre
 */
public class ReportPrinter {

    private static DAOFactory daoFactory = DAOFactory.getInstance();

    public static void reportPreviewer(String path, HashMap parameters) {
        try {
            JasperPrint jp = JasperFillManager.fillReport(path, parameters, daoFactory.getConnection());
            JasperViewer.viewReport(jp, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No Results Found");
        }
    }

    public static void excellPrinter(String path, HashMap parameters, String outputFile) {
        try {
            JasperPrint jp = JasperFillManager.fillReport(path, parameters, daoFactory.getConnection());
            JExcelApiExporter excelExporter = new JExcelApiExporter();
            excelExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            excelExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
            excelExporter.exportReport();
            JOptionPane.showMessageDialog(null, "Operation Done Successfully", "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getCause().toString());
        }
    }
}
