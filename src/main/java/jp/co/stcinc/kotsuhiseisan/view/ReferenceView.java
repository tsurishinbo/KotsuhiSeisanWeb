package jp.co.stcinc.kotsuhiseisan.view;

import java.util.Date;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
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
    @EJB
    private MEmployeeFacade mEmployeeFacade;
    
    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            applyDateFrom = (Date)flash.get(Constant.PARAM_SEARCH_APPLYDATEFROM);
            applyDateTo = (Date)flash.get(Constant.PARAM_SEARCH_APPLYDATETO);
            applyId = (Integer)flash.get(Constant.PARAM_SEARCH_APPLYID);
            status = (Integer)flash.get(Constant.PARAM_SEARCH_STATUS);
            Integer id = (Integer)flash.get(Constant.PARAM_SEARCH_ID);
            application = tApplicationFacade.find(id);
            flash.clear();
        }
    }
    
    public String doEdit() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_MAKE_ID, application.getId());
        return "make.xhtml?faces-redirect=true";
    }
    
    public String doDelete() {
        tApplicationFacade.remove(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doCopy() {
        TApplication newApplication = tApplicationFacade.find(application.getId());
        newApplication.setId(null);
        newApplication.setStatus(Constant.STATUS_SAVE);
        newApplication.setApplyDate(null);
        newApplication.setBossApproveId(session.getBossId());
        newApplication.setBossApproveDate(null);
        newApplication.setManagerApproveId(null);
        newApplication.setManagerApproveDate(null);
        newApplication.setPaymentId(null);
        newApplication.setPaymentDate(null);
        newApplication.setRejectCnt(0);
        newApplication.setBoss(mEmployeeFacade.find(session.getBossId()));
        newApplication.setManager(null);
        newApplication.setPayer(null);
        for (TLine newLine : newApplication.getLines()) {
            newLine.setId(null);
            newLine.setApplicationId(0);
            newLine.setUsedDate(new Date());
        }
        tApplicationFacade.create(newApplication);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doCancelApply() {
        application.setStatus(Constant.STATUS_SAVE);
        application.setApplyDate(null);
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doCancelBossApprove() {
        application.setStatus(Constant.STATUS_WAIT_BOSS);
        application.setBossApproveDate(null);
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    public String doCancelManagerApprove() {
        application.setStatus(Constant.STATUS_WAIT_MANAGER);
        application.setManagerApproveId(null);
        application.setManagerApproveDate(null);
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
    
    public boolean isEnabledCopy() {
        if (application.getApplyId() == session.getEmpNo()) {
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

    public boolean isEnabledCancelBossApprove() {
        if (Objects.equals(application.getBossApproveId(), session.getEmpNo())
                && application.getStatus() == Constant.STATUS_WAIT_MANAGER) {
            return true;
        }
        return false;
    }

    public boolean isEnabledCancelManagerApprove() {
        if (Objects.equals(application.getManagerApproveId(), session.getEmpNo())
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
        flash.put(Constant.PARAM_SEARCH_APPLYDATEFROM, applyDateFrom);
        flash.put(Constant.PARAM_SEARCH_APPLYDATETO, applyDateTo);
        flash.put(Constant.PARAM_SEARCH_APPLYID, applyId);
        flash.put(Constant.PARAM_SEARCH_STATUS, status);
    }
}
