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
import jp.co.stcinc.kotsuhiseisan.common.Report;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

/**
 * 交通費申請照会画面のバッキングビーン
 */
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
    
    /**
     * 初期処理
     */
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
    
    /**
     * 編集
     * @return 遷移先の画面
     */
    public String doEdit() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_MAKE_ID, application.getId());
        return "make.xhtml?faces-redirect=true";
    }
    
    /**
     * 削除
     * @return 遷移先の画面
     */
    public String doDelete() {
        tApplicationFacade.remove(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * コピー
     * @return 遷移先の画面
     */
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
    
    /**
     * 申請取消
     * @return 遷移先の画面
     */
    public String doCancelApply() {
        application.setStatus(Constant.STATUS_SAVE);
        application.setApplyDate(null);
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 上司承認取消
     * @return 遷移先の画面
     */
    public String doCancelBossApprove() {
        application.setStatus(Constant.STATUS_WAIT_BOSS);
        application.setBossApproveDate(null);
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 管理部承認取消
     * @return 遷移先の画面
     */
    public String doCancelManagerApprove() {
        application.setStatus(Constant.STATUS_WAIT_MANAGER);
        application.setManagerApproveId(null);
        application.setManagerApproveDate(null);
        tApplicationFacade.edit(application);
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 精算書出力
     * @return 遷移先の画面 
     */
    public String doReport() {
        Report.make(application);
        message = "精算書を出力しました。";
        return null;
    }
    
    /**
     * 戻る
     * @return 遷移先の画面
     */
    public String doReturn() {
        setFlash();
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 編集ボタンが有効であるかを取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledEdit() {
        if (application.getApplyId() == session.getEmpNo()
                && application.getStatus() == Constant.STATUS_SAVE) {
            return true;
        }
        return false;
    }
    
    /**
     * 削除ボタンが有効であるかを取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledDelete() {
        if (application.getApplyId() == session.getEmpNo()
                && application.getStatus() == Constant.STATUS_SAVE) {
            return true;
        }
        return false;
    }
    
    /**
     * コピーボタンが有効であるかを取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledCopy() {
        if (application.getApplyId() == session.getEmpNo()) {
            return true;
        }
        return false;
    }
    
    /**
     * 申請取消ボタンが有効であるかを取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledCancelApply() {
        if (application.getApplyId() == session.getEmpNo()
                && application.getStatus() == Constant.STATUS_WAIT_BOSS) {
            return true;
        }
        return false;
    }

    /**
     * 上司承認取消ボタンが有効であるかを取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledCancelBossApprove() {
        if (Objects.equals(application.getBossApproveId(), session.getEmpNo())
                && application.getStatus() == Constant.STATUS_WAIT_MANAGER) {
            return true;
        }
        return false;
    }

    /**
     * 管理部承認取消ボタンが有効であるか取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledCancelManagerApprove() {
        if (Objects.equals(application.getManagerApproveId(), session.getEmpNo())
                && application.getStatus() == Constant.STATUS_WAIT_PAYMENT) {
            return true;
        }
        return false;
    }
    
    /**
     * 精算書出力ボタンが有効であるか取得する
     * @return true:有効 false:無効
     */
    public boolean isEnabledReport() {
        if (session.isManager()
                && (application.getStatus() == Constant.STATUS_WAIT_PAYMENT || 
                    application.getStatus() == Constant.STATUS_PAID)) {
            return true;
        }
        return false;
    }
    
    /**
     * 画面間パラメータ(Flash)の設定
     */
    private void setFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_SEARCH_APPLYDATEFROM, applyDateFrom);
        flash.put(Constant.PARAM_SEARCH_APPLYDATETO, applyDateTo);
        flash.put(Constant.PARAM_SEARCH_APPLYID, applyId);
        flash.put(Constant.PARAM_SEARCH_STATUS, status);
    }
}
