package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.common.LineDto;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;
import jp.co.stcinc.kotsuhiseisan.entity.MMeans;
import jp.co.stcinc.kotsuhiseisan.entity.MOrder;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;
import jp.co.stcinc.kotsuhiseisan.facade.MEmployeeFacade;
import jp.co.stcinc.kotsuhiseisan.facade.MMeansFacade;
import jp.co.stcinc.kotsuhiseisan.facade.MOrderFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TApplicationFacade;
import jp.co.stcinc.kotsuhiseisan.facade.TLineFacade;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;

@Named(value = "makeView")
@ViewScoped
public class MakeView extends AbstractView {

    int mode;
    private Integer applicationId;
    
    @Getter @Setter
    private Integer bossId;
    @Getter @Setter
    private LineDto editEntry;
    @Getter @Setter
    private LineDto updatingEntry;
    @Getter @Setter
    private List<LineDto> entryList;
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
    @EJB
    private MEmployeeFacade mEmployeeFacade;
    
    @PostConstruct
    @Override
    public void init() {
        editEntry = new LineDto();
        entryList = new ArrayList<>();
        updatingEntry = null;
        setBossList();
        setOrderList();
        setMeansList();

        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (flash.size() > 0) {
            initEdit((Integer)flash.get(Constant.PARAM_MAKE_ID));
            flash.clear();
        } else {
            initNew();
        }
    }

    public void doAdd() {
        entryList.add(editEntry);
        editEntry = new LineDto();
    }

    public void doUpdate() {
        copyEntry(editEntry, updatingEntry);
        updatingEntry = null;
        editEntry = new LineDto();
    }

    public void doUpdateCancel() {
        updatingEntry = null;
        editEntry = new LineDto();
    }
    
    public void doEdit(LineDto entry) {
        updatingEntry = entry;
        copyEntry(entry, editEntry);
    }
    
    public void doDelete(LineDto entry) {
        entry.setDeleted(true);
    }

    public void doCopy(LineDto entry) {
        LineDto newEntry = new LineDto();
        copyEntry(entry, newEntry);
        entryList.add(newEntry);
    }
    
    public String doSave() {
        if (mode == Constant.MAKE_MODE_NEW) {
            TApplication app = new TApplication();
            app.setStatus(Constant.STATUS_SAVE);
            app.setApplyId(session.getEmpNo());
            app.setBossApproveId(bossId);
            app.setTotalFare(getTotalFare());
            app.setLines(makeAddLines());
            tApplicationFacade.create(app);
        } else if (mode == Constant.MAKE_MODE_MODIFY) {
            TApplication app = tApplicationFacade.find(applicationId);
            app.setBossApproveId(bossId);
            app.setTotalFare(getTotalFare());
            tApplicationFacade.edit(app);
            this.updateLines();
        }
        return "top.xhtml?redirect=true";
    }
    
    public String doApply() {
        if (mode == Constant.MAKE_MODE_NEW) {
            TApplication app = new TApplication();
            app.setStatus(Constant.STATUS_WAIT_BOSS);
            app.setApplyId(session.getEmpNo());
            app.setApplyDate(getToday());
            app.setBossApproveId(bossId);
            app.setTotalFare(getTotalFare());
            app.setLines(makeAddLines());
            tApplicationFacade.create(app);
        } else if (mode == Constant.MAKE_MODE_MODIFY) {
            TApplication app = tApplicationFacade.find(applicationId);
            app.setStatus(Constant.STATUS_WAIT_BOSS);
            app.setApplyDate(getToday());
            app.setBossApproveId(bossId);
            app.setTotalFare(getTotalFare());
            tApplicationFacade.edit(app);
            this.updateLines();
        }
        return "top.xhtml?redirect=true";
    }
    
    public String getFare() {
        if (editEntry.getOneWayFee() != null) {
            Integer fare = (editEntry.isRoundtrip() ? editEntry.getOneWayFee() * 2 : editEntry.getOneWayFee());
            return String.format("%,d", fare) + " 円";
        } else {
            return null;
        }
    }
    
    public String getOrderName(String id) {
        MOrder order = mOrderFacade.find(id);
        if (order != null) {
            return order.getOrderName();
        } else {
            return null;
        }
    }
    
    public String getMeansName(Integer id) {
        MMeans means = mMeansFacade.find(id);
        if (means != null) {
            return means.getMeans();
        } else {
            return null;
        }
    }
    
    private void initNew() {
        mode = Constant.MAKE_MODE_NEW;
        applicationId = null;
        bossId = session.getBossId();
    }

    private void initEdit(Integer id) {
        mode = Constant.MAKE_MODE_MODIFY;
        applicationId = id;
        TApplication application = tApplicationFacade.find(id);
        bossId = application.getBossApproveId();
        for (TLine line : application.getLines()) {
            LineDto entry = new LineDto();
            entry.setId(line.getId());
            entry.setUsedDate(line.getUsedDate());
            entry.setOrderId(line.getOrderId());
            entry.setPlace(line.getPlace());
            entry.setPurpose(line.getPurpose());
            entry.setMeansId(line.getMeansId());
            entry.setSectionFrom(line.getSectionFrom());
            entry.setSectionTo(line.getSectionTo());
            entry.setRoundtrip(line.getIsRoundtrip() == 1);
            entry.setOneWayFee(line.getIsRoundtrip() == 1 ? line.getFare() / 2 : line.getFare());
            entry.setMemo(line.getMemo());
            entry.setDeleted(false);
            entryList.add(entry);
        }
    }
    
    private void setBossList() {
        bossList = new ArrayList<>();
        List<MEmployee> bossListDb = mEmployeeFacade.findBoss(session.getEmpNo());
        for (MEmployee boss : bossListDb) {
            SelectItem item = new SelectItem();
            item.setValue(boss.getId());
            item.setLabel(boss.getEmployeeName());
            bossList.add(item);
        }
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
    
    private int getTotalFare() {
        int totalFare = 0;
        for (LineDto lineDto : entryList) {
            if (!lineDto.isDeleted()) {
                totalFare += (lineDto.isRoundtrip() ? lineDto.getOneWayFee() * 2 : lineDto.getOneWayFee());
            }
        }
        return totalFare;
    }
    
    private Date getToday() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
        Calendar c = Calendar.getInstance(tz);
        Date today = DateUtils.truncate(c.getTime(), Calendar.DAY_OF_MONTH);
        return today;
    }
    
    private boolean checkNoLine() {
        int lineCnt = 0;
        for (LineDto lineDto : entryList) {
            if (!lineDto.isDeleted()) {
                lineCnt += 1;
            }
        }
        return lineCnt > 0;
    }
    
    private void copyEntry(LineDto srcEntry, LineDto dstEntry) {
        dstEntry.setUsedDate(srcEntry.getUsedDate());
        dstEntry.setOrderId(srcEntry.getOrderId());
        dstEntry.setPlace(srcEntry.getPlace());
        dstEntry.setPurpose(srcEntry.getPurpose());
        dstEntry.setMeansId(srcEntry.getMeansId());
        dstEntry.setSectionFrom(srcEntry.getSectionFrom());
        dstEntry.setSectionTo(srcEntry.getSectionTo());
        dstEntry.setRoundtrip(srcEntry.isRoundtrip());
        dstEntry.setOneWayFee(srcEntry.getOneWayFee());
        dstEntry.setMemo(srcEntry.getMemo());
        dstEntry.setDeleted(srcEntry.isDeleted());
    }
    
    private List<TLine> makeAddLines() {
        int sortNo = 1;
        List<TLine> addLines = new ArrayList<>();
        for (LineDto entry : entryList) {
            if (!entry.isDeleted()) {
                TLine line = new TLine();
                setEntryToLine(entry, line);
                line.setSortNo(sortNo++);
                addLines.add(line);
            }
        }
        return addLines;
    }
    
    private void updateLines() {
        int sortNo = 1;
        for (LineDto entry : entryList) {
            if (entry.getId() == null && !entry.isDeleted()) {
                // INSERT
                TLine line = new TLine();
                setEntryToLine(entry, line);
                line.setApplicationId(applicationId);
                line.setSortNo(sortNo++);
                tLineFacade.create(line);
            }
            if (entry.getId() != null && !entry.isDeleted()) {
                // UPDATE
                TLine line = tLineFacade.find(entry.getId());
                setEntryToLine(entry, line);
                line.setSortNo(sortNo++);
                tLineFacade.edit(line);
            }
            if (entry.getId() != null && entry.isDeleted()) {
                // DELETE
                TLine line = tLineFacade.find(entry.getId());
                tLineFacade.remove(line);
            }
        }
    }
    
    private void setEntryToLine(LineDto entry, TLine line) {
        line.setUsedDate(entry.getUsedDate());
        line.setOrderId(entry.getOrderId());
        line.setPlace(entry.getPlace());
        line.setPurpose(entry.getPurpose());
        line.setMeansId(entry.getMeansId());
        line.setSectionFrom(entry.getSectionFrom());
        line.setSectionTo(entry.getSectionTo());
        line.setIsRoundtrip(entry.isRoundtrip() ? 1: 0);
        line.setFare(entry.isRoundtrip() ? entry.getOneWayFee() * 2 : entry.getOneWayFee());
        line.setMemo(entry.getMemo());
    }
    
    public String getStyle(LineDto entry) {
        if (updatingEntry == entry) {
            return "background-color: #ffe4e1;";
        } else {
            return null;
        }
    }
    
    public boolean isDisabledButton() {
        return (updatingEntry != null);
    }

    public boolean isDisabledSaveButton() {
        if (updatingEntry != null) {
            return true;
        } else {
            int lineCount = 0;
            for (LineDto entry : entryList) {
                if (!entry.isDeleted()) {
                    lineCount += 1;
                }
            }
            return (lineCount == 0);
        }
    }
    
}
