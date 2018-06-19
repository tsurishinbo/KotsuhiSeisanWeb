package jp.co.stcinc.kotsuhiseisan.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "managerApproveListView")
@ViewScoped
public class ManagerApproveListView extends AbstractView {
    
    @Getter @Setter
    private List<TApplication> applicationList;
    @EJB
    private TApplicationFacade tApplicationFacade;
    
    @PostConstruct
    @Override
    public void init() {
        setApplicationList();
    }

    public String doReference(Integer id) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_MANAGERAPPROVE_ID, id);
        return "manager_approve.xhtml?faces-redirect";
    }
    
    private void setApplicationList() {
        applicationList = tApplicationFacade.findManagerApprove();
    }
}
