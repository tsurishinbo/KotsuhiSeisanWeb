package jp.co.stcinc.kotsuhiseisan.view;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "loginView")
@ViewScoped
public class LoginView extends AbstractView {

    @NotNull(message = "社員番号は必須項目です。")
    @Getter @Setter
    private Integer empNo;
    @NotNull(message = "パスワードは必須項目です。")
    @Getter @Setter
    private String password;
    @EJB
    private MEmployeeFacade mEmployeeFacade;

    @PostConstruct
    @Override
    public void init() {
        session.logout();
    }
    
    public String doLogin() {
        MEmployee mEmployee = mEmployeeFacade.find(empNo, password);
        if (mEmployee != null) {
            session.login(mEmployee.getId(),
                    mEmployee.getEmployeeName(),
                    mEmployee.getBossId(), 
                    (mEmployee.getBossId() != null ? mEmployee.getBoss().getEmployeeName(): null),
                    mEmployee.getIsBoss() == 1,
                    mEmployee.getIsManager() == 1,
                    mEmployee.getEmail()
            );
            return "top.xhtml?faces-redirect=true";
        } else {
            message = "社員番号またはパスワードが違います。";
            return null;
        }
    }
}
