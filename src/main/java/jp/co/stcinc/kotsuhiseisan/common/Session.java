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
    private String email;
    @Getter @Setter
    private boolean isManager;
    @Getter @Setter
    private boolean isAuth;

    /**
     * 
     * @param empNo
     * @param empName
     * @param bossId
     * @param bossName
     * @param email 
     * @param isManager 
     */
    public void login(
            final Integer empNo, 
            final String empName, 
            final Integer bossId, 
            final String bossName, 
            final String email, 
            final boolean isManager) {
        this.empNo = empNo;
        this.empName = empName;
        this.bossId = bossId;
        this.bossName = bossName;
        this.email = email;
        this.isManager = isManager;
        this.isAuth = true;
    }

    /**
     * 
     */
    public void logout() {
        this.empNo = null;
        this.empName = null;
        this.bossId = null;
        this.bossName = null;
        this.email = null;
        this.isManager = false;
        this.isAuth = false;
    }
}
