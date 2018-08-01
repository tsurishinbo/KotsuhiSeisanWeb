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

/**
 * トップ画面のバッキングビーン
 */
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

    /**
     * 初期処理
     */
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
    
    /**
     * メールアドレス変更
     */
    public void doChangeEmail() {
        MEmployee employee = mEmployeeFacade.find(session.getEmpNo());
        employee.setEmail(session.getEmail());
        mEmployeeFacade.edit(employee);
        message = "メールアドレスを登録しました。";
    }
    
    /**
     * トップ
     * @return 遷移先の画面
     */
    public String doTop() {
        return "top.xhtml?faces-redirect=true";
    }

    /**
     * 申請作成
     * @return 遷移先の画面
     */
    public String doMake() {
        return "make.xhtml?faces-redirect=true";
    }
    
    /**
     * 申請検索
     * @return 遷移先の画面
     */
    public String doSearch() {
        return "search.xhtml?faces-redirect=true";
    }
    
    /**
     * 上司承認
     * @return 遷移先の画面
     */
    public String doBossApprove() {
        return "boss_approve_list.xhtml?faces-redirect=true";
    }
    
    /**
     * 管理部承認
     * @return 遷移先の画面 
     */
    public String doManagerApprove() {
        return "manager_approve_list.xhtml?faces-redirect=true";
    }
    
    /**
     * 支払確定
     * @return 遷移先の画面
     */
    public String doPayment() {
        return "payment.xhtml?faces-redirect=true";
    }
    
    /**
     * 精算書
     * @return 遷移先の画面
     */
    public String doReport() {
        return "report.xhtml?faces-redirect=true";
    }
    
    /**
     * 月次締め
     * @return 遷移先の画面
     */
    public String doMonthly() {
        return null;
    }
    
    /**
     * ログアウト
     * @return 遷移先の画面
     */
    public String doLogout() {
        session.logout();
        return "login.xhtml?faces-redirect=true";
    }
    
    /**
     * 上司承認待ち申請件数を取得する
     * @return 上司承認待ち申請件数
     */
    public Long getBossApproveCount() {
        Long count = tApplicationFacade.getBossApproveCount(session.getEmpNo());
        return count;
    }
    
    /**
     * 管理部承認待ち申請件数を取得する
     * @return 管理部承認待ち申請件数
     */
    public Long getManagerApproveCount() {
        Long count;
        if (session.isManager()) {
            count = tApplicationFacade.getManagerApproveCount();
        } else {
            count = 0L;
        }
        return count;
    }
}
