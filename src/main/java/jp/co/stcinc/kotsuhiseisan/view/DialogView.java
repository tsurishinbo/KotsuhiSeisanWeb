package jp.co.stcinc.kotsuhiseisan.view;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

/**
 * 交通費申請照会ダイアログのバッキングビーン
 */
@Named(value = "dialogView")
@RequestScoped
public class DialogView extends AbstractView {

    @Getter @Setter
    private TApplication application;
    @EJB
    private TApplicationFacade tApplicationFacade;
    
    /**
     * 初期処理
     */
    @PostConstruct
    @Override
    public void init() {
        String id =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(Constant.PARAM_LINE_ID);
        if (id != null) {
            application = tApplicationFacade.find(Integer.valueOf(id));
        }
    }
}
