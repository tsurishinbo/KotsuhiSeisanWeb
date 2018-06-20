package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
import org.primefaces.context.RequestContext;

@Named(value = "paymentView")
@ViewScoped
public class PaymentView extends AbstractView {

    @Getter @Setter
    private PaymentApplication paymentApplication;
    @Getter @Setter
    private List<TApplication> selectedApplication;
    @EJB
    private TApplicationFacade tApplicationFacade;
    
    @PostConstruct
    @Override
    public void init() {
        setPaymentApplication();
    }

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
    
    public void doViewLine(Integer id) {
//        // option
//        Map<String,Object> options = new HashMap<>();
//        options.put("modal", true);
//        options.put("width", 640);
//        options.put("height", 340);
//        options.put("contentWidth", "100%");
//        options.put("contentHeight", "100%");
//        // parameter
//        Map<String, List<String>> params = new HashMap<>();
//        params.put("id", Collections.singletonList(id.toString()));
//        // dialog
//        PrimeFaces.current().dialog().openDynamic("dialog.html", options, params);

//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");
//        PrimeFaces.current().dialog().showMessageDynamic(msg);
        
        PrimeFaces.current().dialog().openDynamic("dialog");
    }
    
    private void setPaymentApplication() {
        paymentApplication = new PaymentApplication(tApplicationFacade.findPayment());
    }
    
}
