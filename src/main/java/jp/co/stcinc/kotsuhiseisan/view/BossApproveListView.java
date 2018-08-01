package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

/**
 * 上司承認一覧画面のバッキングビーン
 */
@Named(value = "bossApproveListView")
@ViewScoped
public class BossApproveListView extends AbstractView {

    @Getter @Setter
    private Integer bossId;
    @Getter @Setter
    private List<TApplication> applicationList;
    @Getter @Setter
    private List<SelectItem> approverList;
    @EJB
    private MEmployeeFacade mEmployeeFacade;
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
            bossId = (Integer)flash.get(Constant.PARAM_BOSSAPPROVE_BOSSID);
            flash.clear();
        } else {
            bossId = session.getEmpNo();
        }
        setApproverList();
        setApplicationList();
    }

    /**
     * 上司承認待ちデータの検索
     * @param e 
     */
    public void doSearch(ValueChangeEvent e) {
        bossId = (Integer)e.getNewValue();
        setApplicationList();
    }
    
    /**
     * 詳細
     * @param id 申請ID
     * @return 遷移先の画面
     */
    public String doReference(Integer id) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_BOSSAPPROVE_ID, id);
        flash.put(Constant.PARAM_BOSSAPPROVE_BOSSID, bossId);
        return "boss_approve.xhtml?faces-redirect=true";
    }
    
    /**
     * 承認者ドロップダウンリスト内容の設定
     */
    private void setApproverList() {
        approverList = new ArrayList<>();
        List<MEmployee> employeeList = mEmployeeFacade.findBukaManager(session.getEmpNo());
        for (MEmployee employee : employeeList) {
            SelectItem item = new SelectItem();
            item.setValue(employee.getId());
            item.setLabel(employee.getEmployeeName());
            approverList.add(item);
        }
    }

    /**
     * 上司承認待ち一覧の作成
     */
    private void setApplicationList() {
        applicationList = tApplicationFacade.findBossApprove(bossId);
    }
}
