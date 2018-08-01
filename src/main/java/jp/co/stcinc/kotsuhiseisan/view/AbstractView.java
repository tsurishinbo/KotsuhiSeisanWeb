package jp.co.stcinc.kotsuhiseisan.view;

import java.io.Serializable;
import javax.inject.Inject;
import jp.co.stcinc.kotsuhiseisan.common.Session;
import lombok.Getter;
import lombok.Setter;

/**
 * Viewバッキングビーンの基底クラス
 */
public abstract class AbstractView implements Serializable {
 
    @Inject
    @Getter @Setter
    protected Session session;
    @Getter @Setter
    protected String message;

    /**
     * 初期処理
     */
    public abstract void init();
    
    /**
     * 未ログインの場合にログイン画面に強制遷移する
     * @return 遷移先画面
     */
    public String doLoginCheck() {
        if (!session.isAuth()) {
            return "login.xhtml?faces-redirect=true";
        }
        return null;
    }
    
    /**
     * ステータス区分からステータス名を取得する
     * @param status ステータス区分
     * @return ステータス名
     */
    public String getStatusName(final int status) {
        switch (status) {
            case 0:
                return "未申請";
            case 1:
                return "上司承認待ち";
            case 2:
                return "管理部承認待ち";
            case 3:
                return "支払待ち";
            case 4:
                return "支払済";
            default:
                return null;
        }
    }
    
    /**
     * 改行コードをbrタグに変換する
     * @param str 変換前文字列
     * @return 返還後文字列
     */
    public String br(String str) {
        return str.replace("\n", "<br/>");
    }
}
