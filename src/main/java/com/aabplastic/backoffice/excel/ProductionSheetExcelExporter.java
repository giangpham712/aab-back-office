package com.aabplastic.backoffice.excel;

import com.aabplastic.backoffice.excel.models.ProductionSheetOrder;
import com.aabplastic.backoffice.excel.models.ProductionSheetOrderItem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jxls.area.XlsArea;
import org.jxls.command.EachCommand;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.TransformerFactory;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ProductionSheetExcelExporter {

    public ProductionSheetExcelExporter() {

    }

    public String exportExcelFile(ProductionSheetOrder productionSheetOrder) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String outputFilename = MessageFormat.format("{0}.xlsx", productionSheetOrder.getOrderNumber());

        List<ProductionSheetOrderItem> productionSheetOrderItems = productionSheetOrder.getProductionSheetOrderItems();


        try (InputStream is = classLoader.getResourceAsStream("excels/production-order.xlsx")) {

            File outputFile = new File("temp", outputFilename);
            try (OutputStream os = new FileOutputStream(outputFile)) {

                Transformer transformer = TransformerFactory.createTransformer(is, os);

                XlsArea xlsArea = new XlsArea("Template!A1:N52", transformer);
                XlsArea orderArea = new XlsArea("Template!A1:N52", transformer);

                List<String> sheetLabels = productionSheetOrderItems.stream().map(x -> x.getProductName()).collect(Collectors.toList());

                EachCommand orderEachCommand = new EachCommand("orderItem", "orderItems", orderArea, new ProductionSheetCellRefGenerator(sheetLabels.toArray(new String[sheetLabels.size()])));

                xlsArea.addCommand(new AreaRef("Template!A1:N52"), orderEachCommand);
                Context context = new Context();

                context.putVar("orderItems", productionSheetOrderItems);

                xlsArea.applyAt(new CellRef("Sheet!A1"), context);
                xlsArea.processFormulas();

                orderEachCommand.setDirection(EachCommand.Direction.RIGHT);

                PoiTransformer poiTransformer = (PoiTransformer) transformer;

                Workbook workbook = poiTransformer.getWorkbook();
                XSSFSheet templateSheet = (XSSFSheet) workbook.getSheetAt(0);
                double leftMargin = templateSheet.getMargin(Sheet.LeftMargin);
                double rightMargin = templateSheet.getMargin(Sheet.RightMargin);
                double topMargin = templateSheet.getMargin(Sheet.TopMargin);
                double bottomMargin = templateSheet.getMargin(Sheet.BottomMargin);

                XSSFPrintSetup setup = (XSSFPrintSetup) templateSheet.getPrintSetup();


                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 1; i< numberOfSheets; i++) {
                    XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(i);
                    sheet.setMargin(Sheet.LeftMargin, leftMargin);
                    sheet.setMargin(Sheet.RightMargin, rightMargin);
                    sheet.setMargin(Sheet.TopMargin, topMargin);
                    sheet.setMargin(Sheet.BottomMargin, bottomMargin);
                }

                transformer.write();

            }

        }

        return outputFilename;
    }

}
