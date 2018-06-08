package jp.co.stcinc.kotsuhiseisan.view;

import java.io.Serializable;
import javax.inject.Inject;
import jp.co.stcinc.kotsuhiseisan.common.Session;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractView implements Serializable {
 
    @Inject
    @Getter @Setter
    protected Session session;
    @Getter @Setter
    protected String message;
    
    public abstract void init();
    
    public String doLoginCheck() {
        if (!session.isAuth()) {
            return "login.xhtml?faces-redirect=true";
        }
        return null;
    }
    
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
}
