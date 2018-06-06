package jp.co.stcinc.kotsuhiseisan.view;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "loginView")
@ViewScoped
public class LoginView extends AbstractView {

    @Getter @Setter
    private Integer empNo;
    @Getter @Setter
    private String password;
    @EJB
    private MEmployeeFacade mEmployeeFacade;

    public String doLogin() {
        MEmployee mEmployee = mEmployeeFacade.find(empNo, password);
        if (mEmployee != null) {
            session.login(mEmployee.getId(),
                    mEmployee.getEmployeeName(),
                    mEmployee.getBossId(), 
                    (mEmployee.getBossId() != null ? mEmployee.getBoss().getEmployeeName(): null),
                    mEmployee.getEmail(),
                    mEmployee.getManager() == 1
            );
            return "top.xhtml?faces-redirect=true";
        } else {
            message = "社員番号またはパスワードが違います。";
            return null;
        }
    }
}
