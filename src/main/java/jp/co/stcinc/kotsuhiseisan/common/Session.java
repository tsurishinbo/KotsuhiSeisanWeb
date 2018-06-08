package jp.co.stcinc.kotsuhiseisan.common;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import lombok.Getter;
import lombok.Setter;

@SessionScoped
public class Session implements Serializable {

    @Getter @Setter
    private Integer empNo;
    @Getter @Setter
    private String empName;
    @Getter @Setter
    private Integer bossId;
    @Getter @Setter
    private String bossName;
    @Getter @Setter
    private boolean isBoss;
    @Getter @Setter
    private boolean isManager;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private boolean isAuth;

    public void login(
            final Integer empNo, 
            final String empName, 
            final Integer bossId, 
            final String bossName, 
            final boolean isBoss,
            final boolean isManager,
            final String email) {
        this.empNo = empNo;
        this.empName = empName;
        this.bossId = bossId;
        this.bossName = bossName;
        this.isBoss = isBoss;
        this.isManager = isManager;
        this.isAuth = true;
        this.email = email;
    }

    public void logout() {
        this.empNo = null;
        this.empName = null;
        this.bossId = null;
        this.bossName = null;
        this.isBoss = false;
        this.isManager = false;
        this.isAuth = false;
        this.email = null;
    }
}
