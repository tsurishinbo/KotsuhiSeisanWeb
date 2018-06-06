package jp.co.stcinc.kotsuhiseisan.view;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "topView")
@ViewScoped
public class TopView extends AbstractView {

    @Getter @Setter
    private Long cntSave;
    @Getter @Setter
    private Long cntWait;
    @Getter @Setter
    private Long cntApproved;
    @Getter @Setter
    private Long cntRejected;
    @EJB
    private TApplicationFacade tApplicationFacade;
    @EJB
    private MEmployeeFacade mEmployeeFacade;
    
    @PostConstruct
    public void init() {
        cntSave = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_SAVE);
        cntWait = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_WAIT);
        cntApproved = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_APPROVED);
        cntRejected = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_REJECTED);
    }
    
    public String doChangeEmail() {
        MEmployee employee = mEmployeeFacade.find(session.getEmpNo());
        employee.setEmail(session.getEmail());
        mEmployeeFacade.edit(employee);
        message = "メールアドレスを登録しました。";
        return null;
    }
    
    public String doTop() {
        return "top.xhtml?faces-redirect=true";
    }

    public String doSearch() {
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doMake() {
        return null;
    }
    
    public String doApprove() {
        return null;
    }
    
    public String doLogout() {
        session.logout();
        return "login.xhtml?faces-redirect=true";
    }
}
