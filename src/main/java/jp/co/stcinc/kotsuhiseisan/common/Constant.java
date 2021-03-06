package jp.co.stcinc.kotsuhiseisan.common;

/**
 * 定数定義クラス
 */
public class Constant {

    /**
     * アプリケーション設定キー項目名
     */
    public static final String CONFIG_REPORT_URI = "ReportUri";
    public static final String CONFIG_REPORT_PATH = "ReportPath";
    public static final String CONFIG_REPORT_TEMPLATE = "ReportTemplate";

    /**
     * ステータス
     */
    public static final int STATUS_ALL = -1;
    public static final int STATUS_SAVE = 0;
    public static final int STATUS_WAIT_BOSS = 1;
    public static final int STATUS_WAIT_MANAGER = 2;
    public static final int STATUS_WAIT_PAYMENT = 3;
    public static final int STATUS_PAID = 4;
    
    /**
     * 申請作成モード
     */
    public static final int MAKE_MODE_NEW = 1;
    public static final int MAKE_MODE_MODIFY = 2;
    
    /**
     * 画面間パラメータ
     */
    public static final String PARAM_MAKE_ID = "param_make_id";
    public static final String PARAM_SEARCH_ID = "param_search_id";
    public static final String PARAM_SEARCH_APPLYDATEFROM = "param_search_applydatefrom";
    public static final String PARAM_SEARCH_APPLYDATETO = "param_search_applydateto";
    public static final String PARAM_SEARCH_APPLYID = "param_search_applyid";
    public static final String PARAM_SEARCH_APPROVEID = "param_search_approveid";
    public static final String PARAM_SEARCH_STATUS = "param_search_status";
    public static final String PARAM_BOSSAPPROVE_ID = "param_bossapprove_id";
    public static final String PARAM_BOSSAPPROVE_BOSSID = "param_bossapprove_bossid";
    public static final String PARAM_MANAGERAPPROVE_ID = "param_managerapprove_id";
    public static final String PARAM_LINE_ID = "param_line_id";
    
    /**
     * 最大明細数
     */
    public static final int MAX_LINE = 100;
   
}
