package com.hgsoft.modules.utils;

import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NewCallBackEcipEntityExcelView extends JeecgEntityExcelView {
    private Consumer<Workbook> callable;

    public NewCallBackEcipEntityExcelView(Consumer<Workbook> callable) {
        this.callable = callable;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codedFileName = "临时文件";
        Workbook workbook = null;
        String[] exportFields = null;
        Object exportFieldStr = model.get("exportFields");
        if (exportFieldStr != null && exportFieldStr != "") {
            exportFields = exportFieldStr.toString().split(",");
        }

        if (model.containsKey("mapList")) {
            List<Map<String, Object>> list = (List)model.get("mapList");
            if (list.size() == 0) {
                throw new RuntimeException("MAP_LIST IS NULL");
            }

            workbook = org.jeecgframework.poi.excel.ExcelExportUtil.exportExcel((org.jeecgframework.poi.excel.entity.ExportParams)((Map)list.get(0)).get("params"), (Class)((Map)list.get(0)).get("entity"), (Collection)((Map)list.get(0)).get("data"), exportFields);

            for(int i = 1; i < list.size(); ++i) {
                (new ExcelExportServer()).createSheet(workbook, (org.jeecgframework.poi.excel.entity.ExportParams)((Map)list.get(i)).get("params"), (Class)((Map)list.get(i)).get("entity"), (Collection)((Map)list.get(i)).get("data"), exportFields);
            }
        } else {
            workbook = ExcelExportUtil.exportExcel((ExportParams)model.get("params"), (Class)model.get("entity"), (Collection)model.get("data"), exportFields);
        }

        callable.accept(workbook);

        if (model.containsKey("fileName")) {
            codedFileName = (String)model.get("fileName");
        }

        if (workbook instanceof HSSFWorkbook) {
            codedFileName = codedFileName + ".xls";
        } else {
            codedFileName = codedFileName + ".xlsx";
        }

        if (this.isIE(request)) {
            codedFileName = URLEncoder.encode(codedFileName, "UTF8");
        } else {
            codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }
}
