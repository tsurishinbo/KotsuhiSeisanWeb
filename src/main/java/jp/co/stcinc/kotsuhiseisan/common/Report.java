package jp.co.stcinc.kotsuhiseisan.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.ejb.EJB;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 交通費精算書クラス
 * @author ykageyama
 */
public class Report {

    // 出力位置
    private static final int[] POSITION_ID = {2, 1};            // 申請ID
    private static final int[] POSITION_APPLY_ID = {2, 3};      // 申請者ID
    private static final int[] POSITION_APPLY_NAME = {2, 4};    // 申請者名
    private static final int[] POSITION_APPLY_DATE = {2, 5};    // 申請日
    private static final int[] POSITION_APPROVE_ID = {3, 3};    // 承認者ID
    private static final int[] POSITION_APPROVE_NAME = {3, 4};  // 承認者名
    private static final int[] POSITION_APPROVE_DATE = {3, 5};  // 承認日
    private static final int[] POSITION_TOTAL_FARE = {2, 7};    // 合計金額
    private static final int[] POSITION_USED_DATE = {7, 0};     // 利用日
    private static final int[] POSITION_ORDER_ID = {7, 1};      // 作業コード
    private static final int[] POSITION_PURPUSE = {7 ,2};       // 出張内容
    private static final int[] POSITION_PLACE = {8, 2};         // 出張場所
    private static final int[] POSITION_MEANS = {7, 5};         // 交通手段
    private static final int[] POSITION_SECTION = {8, 5};       // 区間
    private static final int[] POSITION_FARE = {7 ,9};          // 金額
    private static final int[] POSITION_MEMO = {7, 10};         // 備考
    // 明細行開始位置
    private static final int LINE_START = 7;

    @EJB
    ApplicationConfig appConfig;
    
    
    /**
     * コンストラクタ
     */
    public Report() { }
    
    /**
     * 精算書を作成する
     * @param application 申請情報
     */
    public static void make(TApplication application) {
        // テンプレートファイル
        String template = ApplicationConfig.getAppConfig(Constant.CONFIG_REPORT_TEMPLATE);
        // 精算書の出力先パス
        String path = ApplicationConfig.getAppConfig(Constant.CONFIG_REPORT_PATH);
       
        try {
            InputStream is = new FileInputStream(template);
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            // ヘッダ情報作成
            makeHeader(sheet, application);
            // 明細情報作成
            makeLine(sheet, application);
            // フッタ情報作成
            makeFooter(sheet, application.getId().toString());
            // ブック保存
            String filePath = path + "/交通費精算書_" + application.getId() + ".xlsx";
            write(wb, filePath);
        } catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
            
        }
    }

    /**
     * ヘッダ情報を作成する
     */
    private static void makeHeader(Sheet sheet, TApplication application) {
        int offset = 0;
        // 申請ID
        setCellValue(sheet, POSITION_ID, offset, application.getId());
        // 申請者ID
        setCellValue(sheet, POSITION_APPLY_ID, offset, application.getApplyId());
        // 申請者名
        setCellValue(sheet, POSITION_APPLY_NAME, offset, application.getApplicant().getEmployeeName());
        // 申請日
        setCellValue(sheet, POSITION_APPLY_DATE, offset, application.getApplyDate());
        // 承認者ID
        setCellValue(sheet, POSITION_APPROVE_ID, offset, application.getBossApproveId());
        // 承認者名
        setCellValue(sheet, POSITION_APPROVE_NAME, offset, application.getBoss().getEmployeeName());
        // 承認日
        setCellValue(sheet, POSITION_APPROVE_DATE, offset, application.getBossApproveDate());
        // 合計金額
        setCellValue(sheet, POSITION_TOTAL_FARE, offset, application.getTotalFare());
    }
    
    /**
     * 明細情報を作成する
     */
    private static void makeLine(Sheet sheet, TApplication application) {
        int offset = 0;
        // 行に値を設定＆セル結合
        for (TLine line : application.getLines()) {
            // 利用日
            setCellValue(sheet, POSITION_USED_DATE, offset, line.getUsedDate());
            cellMerge(sheet, POSITION_USED_DATE, offset, 1, 0);
            // 作業コード
            setCellValue(sheet, POSITION_ORDER_ID, offset, line.getOrderId());
            cellMerge(sheet, POSITION_ORDER_ID, offset, 1, 0);
            // 出張内容
            setCellValue(sheet, POSITION_PURPUSE, offset, line.getPurpose());
            cellMerge(sheet, POSITION_PURPUSE, offset, 0, 2);
            // 出張場所
            setCellValue(sheet, POSITION_PLACE, offset, line.getPlace());
            cellMerge(sheet, POSITION_PLACE, offset, 0, 2);
            // 交通手段
            setCellValue(sheet, POSITION_MEANS, offset, line.getMeans().getMeans());
            cellMerge(sheet, POSITION_MEANS, offset, 0, 3);
            // 区間
            String section = line.getSectionFrom();
            if (line.getIsRoundtrip() == 1) {
                section += " ⇔ ";
            } else {
                section += " ⇒ ";
            }
            section += line.getSectionTo();
            setCellValue(sheet, POSITION_SECTION, offset, section);
            cellMerge(sheet, POSITION_SECTION, offset, 0, 3);
            // 金額
            setCellValue(sheet, POSITION_FARE, offset, line.getFare());
            cellMerge(sheet, POSITION_FARE, offset, 1, 0);
            // 備考
            setCellValue(sheet, POSITION_MEMO, offset, line.getMemo());
            cellMerge(sheet, POSITION_MEMO, offset, 1, 0);
            
            offset += 2;
        }
        // 余分な行を削除
        for (int i = LINE_START + offset; i <= sheet.getLastRowNum() ; i++) {
            sheet.removeRow(sheet.getRow(i));
        }
    }
    
    /**
     * フッタ情報を作成する
     */
    private static void makeFooter(Sheet sheet, String id) {
        Footer footer = sheet.getFooter();
        footer.setLeft("申請No: " + id);
    }
    
    /**
     * セルを取得する
     * @param position セル位置
     * @param offset 行のオフセット
     * @return セル
     */
    private static Cell getCell(Sheet sheet, int[] position, int offset) {
        Row row = sheet.getRow(position[0] + offset);
        if (row != null) {
            Cell cell = row.getCell(position[1]);
            return cell;
        }
        return null;
    }
    
    /**
     * セルに値を設定する
     * @param position セル位置
     * @param offset 行のオフセット
     * @param value 設定値
     */
    private static void setCellValue(Sheet sheet, int[] position, int offset, Object value) {
        Cell cell = getCell(sheet, position, offset);
        if (cell != null) {
            CellStyle style = cell.getCellStyle();
            if (value != null) {
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Number) {
                    Number numValue = (Number) value;
                    if (numValue instanceof Float) {
                        Float floatValue = (Float) numValue;
                        numValue = new Double(String.valueOf(floatValue));
                    }
                    cell.setCellValue(numValue.doubleValue());
                } else if (value instanceof Date) {
                    Date dateValue = (Date) value;
                    cell.setCellValue(dateValue);
                } else if (value instanceof Boolean) {
                    Boolean boolValue = (Boolean) value;
                    cell.setCellValue(boolValue);
                }
            } else {
                cell.setCellType(CellType.BLANK);
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * セルを結合する
     * @param position 結合するセルの開始位置
     * @param offset 行のオフセット
     * @param rows 結合するセルの行数
     * @param cols 結合するセルの列数
     */
    private static void cellMerge(Sheet sheet, int[] position, int offset, int rows, int cols) {
        int firstRow = position[0] + offset;
        int firstCol = position[1];
        int lastRow = firstRow + rows;
        int lastCol = firstCol + cols;
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }
    
    /**
     * ワークブックを書き込む
     */
    private static void write(Workbook wb, String filePath) {
        try {
            OutputStream os = new FileOutputStream(filePath);
            wb.write(os);
        } catch (IOException e) {

        }
    }
}
