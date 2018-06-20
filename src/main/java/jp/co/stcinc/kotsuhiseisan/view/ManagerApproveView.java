package jp.co.stcinc.kotsuhiseisan.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.common.DateUtils;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TReject;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "managerApproveView")
@ViewScoped
public class ManagerApproveView extends AbstractView {

    private Integer id;
    
    @Getter @Setter
    private TApplication application;
    @NotNull(message = "差戻し理由は必須項目です。")
    @Getter @Setter
    private String comment;
    @EJB
    private TApplicationFacade tApplicationFacade;
    
    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            id = (Integer)flash.get(Constant.PARAM_MANAGERAPPROVE_ID);
            application = tApplicationFacade.find(id);
            flash.clear();
        }
    }
    
    public String doApprove() {
        application.setStatus(Constant.STATUS_WAIT_PAYMENT);
        application.setManagerApproveId(session.getEmpNo());
        application.setManagerApproveDate(DateUtils.getToday());
        tApplicationFacade.edit(application);
        return "manager_approve_list.xhtml?faces-redirect=true";
    }
    
    public String doReject() {
        List<TReject> rejectList = application.getReject();
        TReject reject = new TReject();
        reject.setApplicationId(id);
        reject.setRejectId(session.getEmpNo());
        reject.setRejectDate(DateUtils.getToday());
        reject.setComment(comment);
        rejectList.add(reject);
        application.setStatus(Constant.STATUS_SAVE);
        application.setApplyDate(null);
        application.setBossApproveDate(null);
        application.setRejectCnt(application.getRejectCnt() + 1);
        application.setReject(rejectList);
        tApplicationFacade.edit(application);
        return "manager_approve_list.xhtml?faces-redirect=true";
    }
    
    public String doReturn() {
        return "manager_approve_list.xhtml?faces-redirect=true";
    }
}
