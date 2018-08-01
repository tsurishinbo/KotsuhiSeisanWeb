package jp.co.stcinc.kotsuhiseisan.common;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * アプリケーション設定情報クラス
 */
public class ApplicationConfig {

    /**
     * 設定値を取得する
     * @param key キー項目
     * @return 設定値
     */
    public static String getAppConfig(String key) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String value = externalContext.getInitParameter(key);
        return value;
    }
    
}
