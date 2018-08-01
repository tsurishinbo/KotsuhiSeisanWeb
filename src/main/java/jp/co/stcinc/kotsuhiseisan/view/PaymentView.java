package jp.co.stcinc.kotsuhiseisan.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.common.DateUtils;
import jp.co.stcinc.kotsuhiseisan.common.PaymentApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 * 支払確定画面のバッキングビーン
 */
@Named(value = "paymentView")
@ViewScoped
public class PaymentView extends AbstractView {

    @Getter @Setter
    private PaymentApplication paymentApplication;
    @Getter @Setter
    private List<TApplication> selectedApplication;
    @EJB
    private TApplicationFacade tApplicationFacade;
    
    /**
     * 初期処理
     */
    @PostConstruct
    @Override
    public void init() {
        setPaymentApplication();
    }

    /**
     * 確定
     */
    public void doPayment() {
        if (selectedApplication.isEmpty()) {
            message = "入金を確定する申請を選択してください。";
        } else {
            for (TApplication app : selectedApplication) {
                app.setStatus(Constant.STATUS_PAID);
                app.setPaymentId(session.getEmpNo());
                app.setPaymentDate(DateUtils.getToday());
                tApplicationFacade.edit(app);
            }
            setPaymentApplication();
            message = null;
        }
    }
    
    /**
     * 明細ダイアログを開く
     * @param id 申請ID
     */
    public void doViewLine(Integer id) {
        // option
        Map<String,Object> options = new HashMap<>();
//        options.put("modal", true);
        options.put("width", 600);
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        // parameter
        Map<String, List<String>> params = new HashMap<>();
        params.put(Constant.PARAM_LINE_ID, Collections.singletonList(id.toString()));
        // dialog
        // Dialogを使うにはfaces-config.xmlに設定の追加が必要!!
        PrimeFaces.current().dialog().openDynamic("dialog.xhtml", options, params);
    }
    
    /**
     * 支払確定一覧の作成
     */
    private void setPaymentApplication() {
        paymentApplication = new PaymentApplication(tApplicationFacade.findPayment());
    }
}
