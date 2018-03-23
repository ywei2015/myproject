package com.talkweb.common;

import java.io.OutputStream;
import org.apache.poi.hssf.usermodel.*;

/**
 * TODO(描述这个类的作用) 
 * @author: 
 * 2012-9-29，Administrator，（描述修改内容）
 */
public class ExportExcel
{

    public ExportExcel()
    {
    }

    public void printExcel(OutputStream out, String head[], String data[][])
    {
        try
        {
            HSSFWorkbook hssfworkbook = new HSSFWorkbook();
            HSSFSheet hssfSheet = hssfworkbook.createSheet();
            hssfworkbook.setSheetName(0, "sheet1");
            HSSFRow hssfRowHead = hssfSheet.createRow(0);
            for(int j = 0; j < head.length; j++)
            {
                String h = head[j];
                HSSFCell hssfCell = hssfRowHead.createCell((short)j);
                hssfCell.setCellValue(h);
            }

            for(int i = 0; i < data.length; i++)
            {
                HSSFRow hssfRowData = hssfSheet.createRow(i + 1);
                String row[] = data[i];
                for(int j = 0; j < row.length; j++)
                {
                    HSSFCell hssfCell = hssfRowData.createCell((short)j);
                    hssfCell.setCellValue(row[j]);
                }

            }

            for(int j = 0; j < head.length; j++)
                hssfSheet.autoSizeColumn((short)j);

            hssfworkbook.write(out);
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void printExcel(OutputStream out, String head[][], String data[][])
    {
        try
        {
            HSSFWorkbook hssfworkbook = new HSSFWorkbook();
            HSSFSheet hssfSheet = hssfworkbook.createSheet();
            hssfworkbook.setSheetName(0, "sheet1");
            HSSFRow hssfRowHead = hssfSheet.createRow(0);
            for(int j = 0; j < head.length; j++)
            {
                String h = head[j][0];
                HSSFCell hssfCell = hssfRowHead.createCell(j);
                hssfSheet.autoSizeColumn(j);
                if("0".equals(head[j][1]))
                {
                    HSSFCellStyle cellStyle = hssfworkbook.createCellStyle();
                    HSSFFont font = hssfworkbook.createFont();
                    font.setColor((short)10);
                    cellStyle.setFont(font);
                    hssfCell.setCellStyle(cellStyle);
                }
                hssfCell.setCellValue(h);
            }

            for(int i = 0; i < data.length; i++)
            {
                HSSFRow hssfRowData = hssfSheet.createRow(i + 1);
                String row[] = data[i];
                for(int j = 0; j < row.length; j++)
                {
                    HSSFCell hssfCell = hssfRowData.createCell((short)j);
                    hssfCell.setCellValue(row[j]);
                }

            }

            for(int j = 0; j < head.length; j++)
                hssfSheet.autoSizeColumn((short)j);

            hssfworkbook.write(out);
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void printExcel(OutputStream out, String head[], String data[][], int isNull[])
    {
        try
        {
            HSSFWorkbook hssfworkbook = new HSSFWorkbook();
            HSSFSheet hssfSheet = hssfworkbook.createSheet();
            hssfworkbook.setSheetName(0, "sheet1");
            HSSFRow hssfRowHead = hssfSheet.createRow(0);
            for(int j = 0; j < head.length; j++)
            {
                String h = head[j];
                HSSFCell hssfCell = hssfRowHead.createCell(j);
                hssfSheet.autoSizeColumn(j);
                if(isNull[j] == 0)
                {
                    HSSFCellStyle cellStyle = hssfworkbook.createCellStyle();
                    HSSFFont font = hssfworkbook.createFont();
                    font.setColor((short)10);
                    cellStyle.setFont(font);
                    hssfCell.setCellStyle(cellStyle);
                }
                hssfCell.setCellValue(h);
            }

            for(int i = 0; i < data.length; i++)
            {
                HSSFRow hssfRowData = hssfSheet.createRow(i + 1);
                String row[] = data[i];
                for(int j = 0; j < row.length; j++)
                {
                    HSSFCell hssfCell = hssfRowData.createCell((short)j);
                    hssfCell.setCellValue(row[j]);
                }

            }

            for(int j = 0; j < head.length; j++)
                hssfSheet.autoSizeColumn((short)j);

            hssfworkbook.write(out);
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static final long serialVersionUID = 1L;
}
