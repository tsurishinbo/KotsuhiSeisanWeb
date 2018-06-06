package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
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
    private Integer approveId;
    @Getter @Setter
    private Integer status;
    @Getter @Setter
    private List<SelectItem> applyList;
    @Getter @Setter
    private List<SelectItem> approveList;
    @Getter @Setter
    private List<TApplication> applicationList;
    @EJB
    private MEmployeeFacade mEmployeeFacade;
    @EJB
    private TApplicationFacade tApplicationFacade;
    @PostConstruct
    public void init() {
        applyId = session.getEmpNo();
        approveId = session.getBossId();
        status = 0;
        applicationList = null;
        setApplyList();
        setApproveList();
    }
    
    public String doSearch() {
        applicationList = tApplicationFacade.findByForm(
                applyDateFrom, 
                applyDateTo, 
                applyId, 
                approveId, 
                status);
        return null;
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
        } else {
            applyList.add(new SelectItem(session.getEmpNo(), session.getEmpName()));
        }
    }
    
    private void setApproveList() {
        approveList = new ArrayList<>();
        approveList.add(new SelectItem(null, ""));
        List<MEmployee> employeeList = mEmployeeFacade.findManager();
        for (MEmployee employee : employeeList) {
            SelectItem item = new SelectItem();
            item.setValue(employee.getId());
            item.setLabel(employee.getEmployeeName());
            approveList.add(item);
        }
    }
}
