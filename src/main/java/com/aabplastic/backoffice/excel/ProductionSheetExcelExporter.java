package com.aabplastic.backoffice.excel;

import com.aabplastic.backoffice.excel.models.ProductionSheetOrder;
import com.aabplastic.backoffice.excel.models.ProductionSheetOrderItem;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionSheetExcelExporter {

    public ProductionSheetExcelExporter() {

    }

    public String exportExcelFile(ProductionSheetOrder productionSheetOrder) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String outputFilename = MessageFormat.format("{0}.xlsx", productionSheetOrder.getOrderNumber());

        List<ProductionSheetOrderItem> productionSheetOrderItems = productionSheetOrder.getProductionSheetOrderItems();

        int[] rollNumbers = new int[100];
        for (int i = 0; i < rollNumbers.length; i++) {
            rollNumbers[i] = i + 1;
        }

        final int[] index = {0};

        List<ProductionSheetOrderItem> oldList = productionSheetOrderItems.stream().filter(x -> x != null).collect(Collectors.toList());

        oldList.forEach(item -> {

            item.setRollNumbers(rollNumbers);

            int totalSheets = (Integer.parseInt(item.getTotalRolls()) - 1) / 100 + 1;

            if (totalSheets > 1) {

                for (int i = 1; i < totalSheets; i++) {

                    ProductionSheetOrderItem copy = copy(item);

                    productionSheetOrderItems.add(index[0] + i, copy);
                    copy.setRollNumbers(ArrayUtils.clone(item.getRollNumbers()));

                    for (int j = 0; j < copy.getRollNumbers().length; j++) {
                        copy.getRollNumbers()[j] = copy.getRollNumbers()[j] + 100 * i;
                    }
                }
            }

            index[0]++;
        });

        try (InputStream is = classLoader.getResourceAsStream("excels/production-order.xlsx")) {

            File file = new File("temp");
            if (!file.exists()) {
                file.mkdir();
            }

            File outputFile = new File("temp", outputFilename);
            try (OutputStream os = new FileOutputStream(outputFile)) {

                Transformer transformer = TransformerFactory.createTransformer(is, os);

                XlsArea xlsArea = new XlsArea("Template!A1:N52", transformer);
                XlsArea orderArea = new XlsArea("Template!A1:N52", transformer);

                Map<String, Integer> itemMap = new HashMap<>();

                List<String> sheetLabels = productionSheetOrderItems.stream().map(x -> {

                    String label = x.getProductName();
                    if (itemMap.containsKey(label)) {

                        int count = itemMap.get(label);
                        itemMap.put(label, count + 1);
                        label = MessageFormat.format("{0} ({1})", label, count + 1);
                    } else {
                        itemMap.put(label, 1);
                    }

                    return label;

                }).collect(Collectors.toList());

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

                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 1; i < numberOfSheets; i++) {
                    XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(i);
                    sheet.setMargin(Sheet.LeftMargin, leftMargin);
                    sheet.setMargin(Sheet.RightMargin, rightMargin);
                    sheet.setMargin(Sheet.TopMargin, topMargin);
                    sheet.setMargin(Sheet.BottomMargin, bottomMargin);

                    sheet.getOddHeader().setLeft(templateSheet.getOddHeader().getLeft());
                    sheet.getOddHeader().setCenter(templateSheet.getOddHeader().getCenter());
                    sheet.getOddHeader().setRight(templateSheet.getOddHeader().getRight());

                    sheet.getEvenHeader().setLeft(templateSheet.getOddHeader().getLeft());
                    sheet.getEvenHeader().setCenter(templateSheet.getOddHeader().getCenter());
                    sheet.getEvenHeader().setRight(templateSheet.getOddHeader().getRight());
                }

                transformer.write();

            }

        }

        return outputFilename;
    }

    private ProductionSheetOrderItem copy(ProductionSheetOrderItem productionSheetOrderItem) {
        ProductionSheetOrderItem copy = new ProductionSheetOrderItem();

        copy.setProductionSheetOrder(productionSheetOrderItem.getProductionSheetOrder());
        copy.setProductId(productionSheetOrderItem.getProductId());
        copy.setProductName(productionSheetOrderItem.getProductName());

        copy.setThickness(productionSheetOrderItem.getThickness());
        copy.setWidth(productionSheetOrderItem.getWidth());
        copy.setBlowingWidth(productionSheetOrderItem.getBlowingWidth());
        copy.setLength(productionSheetOrderItem.getLength());
        copy.setEmboss(productionSheetOrderItem.getEmboss());

        copy.setGusset(productionSheetOrderItem.getGusset());

        copy.setWeightPerLengthUnit(productionSheetOrderItem.getWeightPerLengthUnit());
        copy.setLengthPerRoll(productionSheetOrderItem.getLengthPerRoll());
        copy.setWeightPerRoll(productionSheetOrderItem.getWeightPerRoll());
        copy.setTotalWeight(productionSheetOrderItem.getTotalWeight());
        copy.setTotalBlowingWeight(productionSheetOrderItem.getTotalBlowingWeight());
        copy.setTotalRolls(productionSheetOrderItem.getTotalRolls());

        return copy;
    }


    private double calculateActualThickness(double thickness) {
        double tolerance;
        if (thickness < 0.013) {
            tolerance = 0;
        } else if (thickness < 0.014) {
            tolerance = 2;
        } else {
            tolerance = 5;
        }

        return (100 - tolerance) * thickness / 100;
    }

    private int getLengthPerRoll(double weightPerLengthUnit) {
        int lengthPerRoll = 4000;
        while (lengthPerRoll * weightPerLengthUnit / 1000 > 90) {
            lengthPerRoll = lengthPerRoll - 500;
        }

        return lengthPerRoll;
    }
}
