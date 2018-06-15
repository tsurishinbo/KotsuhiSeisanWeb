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
import org.apache.commons.lang3.StringUtils;

@Named(value = "topView")
@ViewScoped
public class TopView extends AbstractView {

    @Getter @Setter
    private Long cntWaitBoss;
    @Getter @Setter
    private Long cntWaitManager;
    @Getter @Setter
    private Long cntWaitPayment;
    @EJB
    private TApplicationFacade tApplicationFacade;
    @EJB
    private MEmployeeFacade mEmployeeFacade;
    
    @PostConstruct
    @Override
    public void init() {
        if (StringUtils.isEmpty(session.getEmail())) {
            message = "メールアドレスを登録してください。";
        }
        cntWaitBoss = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_WAIT_BOSS);
        cntWaitManager = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_WAIT_MANAGER);
        cntWaitPayment = tApplicationFacade.getCountByStatus(session.getEmpNo(), Constant.STATUS_WAIT_PAYMENT);
    }
    
    public void doChangeEmail() {
        MEmployee employee = mEmployeeFacade.find(session.getEmpNo());
        employee.setEmail(session.getEmail());
        mEmployeeFacade.edit(employee);
        message = "メールアドレスを登録しました。";
    }
    
    public String doTop() {
        return "top.xhtml?faces-redirect=true";
    }

    public String doSearch() {
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doMake() {
        return "make.xhtml?faces-redirect=true";
    }
    
    public String doBossApprove() {
        return null;
    }
    
    public String doManagerApprove() {
        return null;
    }
    
    public String doPayment() {
        return null;
    }
    
    public String doReport() {
        return null;
    }
    
    public String doMonthly() {
        return null;
    }
    
    public String doLogout() {
        session.logout();
        return "login.xhtml?faces-redirect=true";
    }
}
