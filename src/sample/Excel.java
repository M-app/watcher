package sample;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Excel {
    private String rutaArchivo = "";
    private String rutaConsolidado = "";
    private String nombreArchivo = "";

    Workbook consolidado;

    public Excel(String rutaArchivo, String rutaConsolidado, String nombreArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.rutaConsolidado = rutaConsolidado;
        this.nombreArchivo = nombreArchivo;
        crearConsolidado();
    }

    public void getLibro() {
        try {
            Workbook workbook = WorkbookFactory.create(new File(rutaArchivo));
            Sheet cSheet = consolidado.createSheet(nombreArchivo);
            DataFormatter dataFormatter = new DataFormatter();
            int rowNum=1;
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    Row cRow = cSheet.createRow(rowNum++);
                    for (Cell cell : row) {
                        cRow.createCell(0)
                                .setCellValue(dataFormatter.formatCellValue(cell));
                    }
                }
            }
            guardarConsolidado();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearConsolidado() {
        try {
            FileInputStream file = new FileInputStream(new File(rutaConsolidado + "/consolidated.xlsx"));
            consolidado = new XSSFWorkbook(file);
        } catch (Exception e) {
            consolidado = new XSSFWorkbook();
        }

    }

    public void llenarConsolidado(String nombreHoja) {

    }

    private void guardarConsolidado(){
        try {
            FileOutputStream fileOut = new FileOutputStream(rutaConsolidado + "/consolidated.xlsx");
            consolidado.write(fileOut);
            fileOut.close();
            consolidado.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
