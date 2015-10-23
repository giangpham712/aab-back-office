package com.aabplastic.backoffice.excel;

import org.jxls.command.CellRefGenerator;
import org.jxls.common.CellRef;
import org.jxls.common.Context;

public class ProductionSheetCellRefGenerator implements CellRefGenerator {

    private String[] sheetLabels;

    public ProductionSheetCellRefGenerator(String[] labels) {
        this.sheetLabels = labels;
    }

    @Override
    public CellRef generateCellRef(int index, Context context) {
        if (index == sheetLabels.length) {
            return null;
        }

        return new CellRef(sheetLabels[index] + "!A1");
    }
}
