package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.common.LineDto;
import jp.co.stcinc.kotsuhiseisan.entity.MMeans;
import jp.co.stcinc.kotsuhiseisan.entity.MOrder;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;
import jp.co.stcinc.kotsuhiseisan.facade.MMeansFacade;
import jp.co.stcinc.kotsuhiseisan.facade.MOrderFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TLineFacade;
import lombok.Getter;
import lombok.Setter;

@Named(value = "makeView")
@ViewScoped
public class MakeView extends AbstractView {

    int mode;
    
    @Getter @Setter
    private TApplication application;
    @Getter @Setter
    private List<LineDto> lineDtoList;
    @Getter @Setter
    private List<SelectItem> bossList;
    @Getter @Setter
    private List<SelectItem> orderList;
    @Getter @Setter
    private List<SelectItem> meansList;
    @EJB
    private TApplicationFacade tApplicationFacade;
    @EJB
    private TLineFacade tLineFacade;
    @EJB
    private MOrderFacade mOrderFacade;
    @EJB
    private MMeansFacade mMeansFacade;
    
    @PostConstruct
    @Override
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            initEdit((Integer)flash.get(Constant.PARAM_MAKE_ID));
            flash.clear();
        } else {
            initNew();
        }
        setBossList();
        setOrderList();
        setMeansList();
    }

    public String doAdd() {
        LineDto lineDto = new LineDto();
        lineDto.setLineKind(Constant.LINE_KIND_ADD);
        lineDto.setApplicationId(application.getId());
        lineDtoList.add(lineDto);
        return null;
    }
    
    public String doSave() {
        return null;
    }
    
    public String doApply() {
        return null;
    }
    
    public String doDelete(LineDto lineDto) {
        lineDto.setLineKind(Constant.LINE_KIND_DEL);
        return null;
    }
    
    public String doCopy(LineDto lineDto) {
        
        return null;
    }
    
    public boolean isEnabled(LineDto lineDto) {
        if (lineDto.getLineKind() == Constant.LINE_KIND_DEL) {
            return false;
        } else {
            return true;
        }
    }
    
    public String getFare(Integer oneWayFee, boolean isRoundtrip) {
        if (oneWayFee != null) {
            Integer fare = (isRoundtrip ? oneWayFee * 2 : oneWayFee);
            return String.format("%,d", fare) + " 円";
        } else {
            return null;
        }
    }
    
    private void initNew() {
        mode = Constant.MAKE_MODE_NEW;
        application = new TApplication();
        lineDtoList = new ArrayList<>();
    }

    private void initEdit(Integer id) {
        mode = Constant.MAKE_MODE_EDIT;
        application = tApplicationFacade.find(id);
        lineDtoList = new ArrayList<>();
        for (TLine line : application.getLines()) {
            LineDto lineDto = new LineDto();
            lineDto.setLineKind(Constant.LINE_KIND_UPD);
            lineDto.setId(line.getId());
            lineDto.setApplicationId(line.getApplicationId());
            lineDto.setUsedDate(line.getUsedDate());
            lineDto.setOrderId(line.getOrderId());
            lineDto.setPlace(line.getPlace());
            lineDto.setPurpose(line.getPurpose());
            lineDto.setMeansId(line.getMeansId());
            lineDto.setSectionFrom(line.getSectionFrom());
            lineDto.setSectionTo(line.getSectionTo());
            lineDto.setRoundtrip(line.getIsRoundtrip() == 1);
            lineDto.setOneWayFee(line.getIsRoundtrip() == 1 ? line.getFare() / 2 : line.getFare());
            lineDto.setMemo(line.getMemo());
            lineDtoList.add(lineDto);
        }
    }
    
    private void setBossList() {
        bossList = new ArrayList<>();
        
    }

    private void setOrderList() {
        orderList = new ArrayList<>();
        orderList.add(new SelectItem(null, ""));
        List<MOrder> orderListDb = mOrderFacade.findAll();
        for (MOrder order : orderListDb) {
            SelectItem item = new SelectItem();
            item.setValue(order.getId());
            item.setLabel(order.getId() + ":" + order.getOrderName());
            orderList.add(item);
        }
    }
    
    private void setMeansList() {
        meansList = new ArrayList<>();
        List<MMeans> meansListDb = mMeansFacade.findAll();
        for (MMeans means : meansListDb) {
            SelectItem item = new SelectItem();
            item.setValue(means.getId());
            item.setLabel(means.getMeans());
            meansList.add(item);
        }
    }
}
