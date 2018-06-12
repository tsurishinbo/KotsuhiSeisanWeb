package jp.co.stcinc.kotsuhiseisan.view;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TLineFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "makeView")
@ViewScoped
public class MakeView extends AbstractView {

    int mode;
    
    @Getter @Setter
    TApplication application;
    @EJB
    TApplicationFacade tApplicationFacade;
    @EJB
    TLineFacade tLineFacade;
    
    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            mode = Constant.MAKE_MODE_EDIT;
            application = tApplicationFacade.find(flash.get(Constant.PARAM_MAKE_ID));
        } else {
            mode = Constant.MAKE_MODE_NEW;
            application = new TApplication();
            application.setLines(new ArrayList<TLine>());
        }
    }
    
    public String doAdd() {
        return null;
    }
    
    public String doEdit() {
        return null;
    }

    public String doDelete(TLine line) {
        application.getLines().remove(line);
        return null;
    }
    
    public String doCopy(Integer lineId) {
        TLine newLine = tLineFacade.find(lineId);
        application.getLines().add(newLine);
        return null;
    }
    
//    public String doCopy(TLine line) {
//        line.setMemo("コピーしたもの");
//        application.getLines().add(line);
//        return null;
//    }

}
