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

/**
 * 上司承認画面のバッキングビーン
 */
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
    
    /**
     * 初期処理
     */
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
    
    /**
     * 承認
     * @return 遷移先の画面
     */
    public String doApprove() {
        application.setStatus(Constant.STATUS_WAIT_MANAGER);
        application.setBossApproveDate(DateUtils.getToday());
        tApplicationFacade.edit(application);
        setFlash();
        return "boss_approve_list.xhtml?faces-rediect=true";
    }
    
    /**
     * 差戻
     * @return 遷移先の画面
     */
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
        application.setRejectCnt(application.getRejectCnt() + 1);
        application.setReject(rejectList);
        tApplicationFacade.edit(application);
        setFlash();
        return "boss_approve_list.xhtml?faces-rediect=true";
    }
    
    /**
     * 戻る
     * @return 遷移先の画面
     */
    public String doReturn() {
        setFlash();
        return "boss_approve_list.xhtml?faces-rediect=true";
    }
    
    /**
     * 画面間パラメータ(Flash)の設定
     */
    private void setFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_BOSSAPPROVE_BOSSID, bossId);
    }
}
