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
import jp.co.stcinc.kotsuhiseisan.common.Report;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

@Named(value = "reportView")
@ViewScoped
public class ReportView extends AbstractView {

    
    @PostConstruct
    @Override
    public void init() {
    }

}
