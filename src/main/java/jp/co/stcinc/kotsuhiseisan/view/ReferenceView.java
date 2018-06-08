package jp.co.stcinc.kotsuhiseisan.view;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "referenceView")
@ViewScoped
public class ReferenceView extends AbstractView {

    private Date applyDateFrom;
    private Date applyDateTo;
    private Integer applyId;
    private Integer status;

    @Getter @Setter
    private TApplication application;
    @EJB
    private TApplicationFacade tApplicationFacade;
    
    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            applyDateFrom = (Date)flash.get(Constant.SEARCHKEY_APPLYDATE_FROM);
            applyDateTo = (Date)flash.get(Constant.SEARCHKEY_APPLYDATE_TO);
            applyId = (Integer)flash.get(Constant.SEARCHKEY_APPLY_ID);
            status = (Integer)flash.get(Constant.SEARCHKEY_STATUS);
            Integer id = (Integer)flash.get(Constant.SEARCHKEY_ID);
            application = tApplicationFacade.find(id);
            flash.clear();
        }
    }
    
    public String doCopy() {
        
        return null;
    }
    
    public String doEdit() {
        return null;
    }
    
    public String doDelete() {
        tApplicationFacade.remove(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doApplyCancel() {
        application.setStatus(Constant.STATUS_SAVE);
        application.setApplyDate(null);
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doApproveCancel() {
        if (application.getStatus() == Constant.STATUS_WAIT_MANAGER) {
            application.setStatus(Constant.STATUS_WAIT_BOSS);
            application.setBossApproveDate(null);
        }    
        if (application.getStatus() == Constant.STATUS_WAIT_PAYMENT) {
            application.setStatus(Constant.STATUS_WAIT_MANAGER);
            application.setManagerApproveId(null);
            application.setManagerApproveDate(null);
        }
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doReport() {
        return null;
    }
    
    public String doReturn() {
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public boolean isEnabledCopy() {
        if (application.getApplyId() == session.getEmpNo()) {
            return true;
        }
        return false;
    }
    
    public boolean isEnabledEdit() {
        if (application.getApplyId() == session.getEmpNo()
                && application.getStatus() == Constant.STATUS_SAVE) {
            return true;
        }
        return false;
    }
    
    public boolean isEnabledDelete() {
        if (application.getApplyId() == session.getEmpNo()
                && application.getStatus() == Constant.STATUS_SAVE) {
            return true;
        }
        return false;
    }
    
    public boolean isEnabledCancelApply() {
        if (application.getApplyId() == session.getEmpNo()
                && application.getStatus() == Constant.STATUS_WAIT_BOSS) {
            return true;
        }
        return false;
    }

    public boolean isEnabledCancelApprove() {
        if (application.getBossApproveId().equals(session.getEmpNo())
                && application.getStatus() == Constant.STATUS_WAIT_MANAGER) {
            return true;
        }
        if (application.getManagerApproveId().equals(session.getEmpNo())
                && application.getStatus() == Constant.STATUS_WAIT_PAYMENT) {
            return true;
        }
        return false;
    }
    
    public boolean isEnabledReport() {
        if (session.isManager()
                && (application.getStatus() == Constant.STATUS_WAIT_PAYMENT || 
                    application.getStatus() == Constant.STATUS_WAIT_PAID)) {
            return true;
        }
        return false;
    }
    
    private void setFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.SEARCHKEY_APPLYDATE_FROM, applyDateFrom);
        flash.put(Constant.SEARCHKEY_APPLYDATE_TO, applyDateTo);
        flash.put(Constant.SEARCHKEY_APPLY_ID, applyId);
        flash.put(Constant.SEARCHKEY_STATUS, status);
    }
}
