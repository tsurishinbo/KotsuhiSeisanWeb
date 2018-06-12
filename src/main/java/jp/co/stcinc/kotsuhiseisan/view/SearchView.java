package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "searchView")
@ViewScoped
public class SearchView extends AbstractView {

    @Getter @Setter
    private Date applyDateFrom;
    @Getter @Setter
    private Date applyDateTo;
    @Getter @Setter    
    private Integer applyId;
    @Getter @Setter
    private Integer status;
    @Getter @Setter
    private List<SelectItem> applyList;
    @Getter @Setter
    private List<TApplication> applicationList;
    @EJB
    private MEmployeeFacade mEmployeeFacade;
    @EJB
    private TApplicationFacade tApplicationFacade;

    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            applyDateFrom = (Date)flash.get(Constant.PARAM_SEARCH_APPLYDATEFROM);
            applyDateTo = (Date)flash.get(Constant.PARAM_SEARCH_APPLYDATETO);
            applyId = (Integer)flash.get(Constant.PARAM_SEARCH_APPLYID);
            status = (Integer)flash.get(Constant.PARAM_SEARCH_STATUS);
            setApplicationList();
            flash.clear();
        } else {
            applyDateFrom = null;
            applyDateTo = null;
            applyId = session.getEmpNo();
            status = -1;
            applicationList = null;
        }
        setApplyList();
    }
    
    public String doSearch() {
        setApplicationList();
        return null;
    }
    
    public String doReference(Integer id) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(Constant.PARAM_SEARCH_ID, id);
        flash.put(Constant.PARAM_SEARCH_APPLYDATEFROM, applyDateFrom);
        flash.put(Constant.PARAM_SEARCH_APPLYDATETO, applyDateTo);
        flash.put(Constant.PARAM_SEARCH_APPLYID, applyId);
        flash.put(Constant.PARAM_SEARCH_STATUS, status);        
        return "reference.xhtml?faces-redirect=true";
    }
    
    private void setApplyList() {
        applyList = new ArrayList<>();
        if (session.isManager()) {
            applyList.add(new SelectItem(null, ""));
            List<MEmployee> employeeList = mEmployeeFacade.findAll();
            for (MEmployee employee : employeeList) {
                SelectItem item = new SelectItem();
                item.setValue(employee.getId());
                item.setLabel(employee.getEmployeeName());
                applyList.add(item);
            }
        } else if (session.isBoss()) {
            applyList.add(new SelectItem(null, ""));
            List<MEmployee> employeeList = mEmployeeFacade.findBuka(session.getEmpNo());
            for (MEmployee employee : employeeList) {
                SelectItem item = new SelectItem();
                item.setValue(employee.getId());
                item.setLabel(employee.getEmployeeName());
                applyList.add(item);
            }
        } else {
            applyList.add(new SelectItem(session.getEmpNo(), session.getEmpName()));
        }
    }
    
    private void setApplicationList() {
        applicationList = tApplicationFacade.findByForm(
                applyDateFrom, 
                applyDateTo, 
                applyId, 
                status);
    }
}
