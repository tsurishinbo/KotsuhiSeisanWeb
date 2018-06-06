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
    
    public String doLoginCheck() {
        if (!session.isAuth()) {
            return "login.xhtml?faces-redirect=true";
        }
        return null;
    }
}
