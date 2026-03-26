package hcmute.system.hotel.cknhom11qlhotel.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ReportExportUtil {

    public byte[] exportToExcel(String sheetName, List<String> headers, List<List<String>> rows) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet(sheetName);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                List<String> values = rows.get(rowIndex);
                for (int colIndex = 0; colIndex < values.size(); colIndex++) {
                    row.createCell(colIndex).setCellValue(values.get(colIndex));
                }
            }

            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Không thể export Excel", ex);
        }
    }

    public byte[] exportToPdf(String title, List<String> headers, List<List<String>> rows) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph heading = new Paragraph(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            heading.setSpacingAfter(12f);
            document.add(heading);

            PdfPTable table = new PdfPTable(headers.size());
            table.setWidthPercentage(100f);

            headers.forEach(header -> {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setPadding(6f);
                table.addCell(cell);
            });

            rows.forEach(row -> row.forEach(value -> {
                PdfPCell cell = new PdfPCell(new Phrase(value));
                cell.setPadding(5f);
                table.addCell(cell);
            }));

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (DocumentException | IOException ex) {
            throw new IllegalStateException("Không thể export PDF", ex);
        }
    }
}

