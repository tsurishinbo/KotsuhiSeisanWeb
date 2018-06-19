package jp.co.stcinc.kotsuhiseisan.view;

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
import jp.co.stcinc.kotsuhiseisan.facade.TRejectFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "bossApproveView")
@ViewScoped
public class BossApproveView extends AbstractView {

    private Integer id;
    private Integer bossId;
    
    @Getter @Setter
    private TApplication application;
    @NotNull(message = "差戻し理由は必須項目です。")
    @Getter @Setter
    private String comment;
    @EJB
    private TApplicationFacade tApplicationFacade;
    @EJB
    private TRejectFacade tRejectFacade;
    
    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            id = (Integer)flash.get(Constant.PARAM_BOSSAPPROVE_ID);
            bossId = (Integer)flash.get(Constant.PARAM_BOSSAPPROVE_BOSSID);
            application = tApplicationFacade.find(id);
            flash.clear();
        }
    }
    
    public String doApprove() {
        application.setStatus(Constant.STATUS_WAIT_MANAGER);
        application.setBossApproveDate(DateUtils.getToday());
        tApplicationFacade.edit(application);
        setFlash();
        return "boss_approve_list.xhtml?faces-rediect=true";
    }
    
    public String doReject() {
        TReject reject = new TReject();
        reject.setApplicationId(id);
        reject.setRejectId(session.getEmpNo());
        reject.setRejectDate(DateUtils.getToday());
        reject.setComment(comment);
        tRejectFacade.create(reject);
        application.setStatus(Constant.STATUS_SAVE);
        application.setApplyDate(null);
        application.setRejectCnt(application.getRejectCnt() + 1);
        tApplicationFacade.edit(application);
        setFlash();
        return "boss_approve_list.xhtml?faces-rediect=true";
    }
    
    public String doReturn() {
        setFlash();
        return "boss_approve_list.xhtml?faces-rediect=true";
    }
    
    private void setFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_BOSSAPPROVE_BOSSID, bossId);
    }
}
