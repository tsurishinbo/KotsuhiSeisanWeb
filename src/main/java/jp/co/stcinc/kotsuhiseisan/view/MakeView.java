package jp.co.stcinc.kotsuhiseisan.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.common.DateUtils;
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

/**
 * 交通費申請作成画面のバッキングビーン
 */
@Named(value = "makeView")
@ViewScoped
public class MakeView extends AbstractView {

    private static final int MAX_LINE = 100;
    
    private int mode;
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
    
    /**
     * 初期処理
     */
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

    /**
     * 明細追加
     */
    public void doAdd() {
        entryList.add(editEntry);
        editEntry = new LineDto();
    }

    /**
     * 明細更新
     */
    public void doUpdate() {
        copyEntry(editEntry, updatingEntry);
        updatingEntry = null;
        editEntry = new LineDto();
    }

    /**
     * 明細更新キャンセル
     */
    public void doUpdateCancel() {
        updatingEntry = null;
        editEntry = new LineDto();
    }
    
    /**
     * 編集
     * @param entry 対象の明細
     */
    public void doEdit(LineDto entry) {
        updatingEntry = entry;
        copyEntry(entry, editEntry);
    }
    
    /**
     * 削除
     * @param entry 対象の明細
     */
    public void doDelete(LineDto entry) {
        entry.setDeleted(true);
    }

    /**
     * コピー
     * @param entry 対象の明細
     */
    public void doCopy(LineDto entry) {
        LineDto newEntry = new LineDto();
        copyEntry(entry, newEntry);
        entryList.add(newEntry);
    }
    
    /**
     * 保存
     * @return 遷移先の画面
     */
    public String doSave() {
        if (getLineCount() > Constant.MAX_LINE) {
            message = "１度に申請できる明細は" + String.valueOf(MAX_LINE) + "件までです。";
            return null;
        }
        // 保存
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
    
    /**
     * 申請
     * @return 遷移先の画面
     */
    public String doApply() {
        if (getLineCount() > Constant.MAX_LINE) {
            message = "１度に申請できる明細は" + String.valueOf(MAX_LINE) + "件までです。";
            return null;
        }
        // 申請
        if (mode == Constant.MAKE_MODE_NEW) {
            TApplication app = new TApplication();
            app.setStatus(Constant.STATUS_WAIT_BOSS);
            app.setApplyId(session.getEmpNo());
            app.setApplyDate(DateUtils.getToday());
            app.setBossApproveId(bossId);
            app.setTotalFare(getTotalFare());
            app.setLines(makeAddLines());
            tApplicationFacade.create(app);
        } else if (mode == Constant.MAKE_MODE_MODIFY) {
            TApplication app = tApplicationFacade.find(applicationId);
            app.setStatus(Constant.STATUS_WAIT_BOSS);
            app.setApplyDate(DateUtils.getToday());
            app.setBossApproveId(bossId);
            app.setTotalFare(getTotalFare());
            tApplicationFacade.edit(app);
            this.updateLines();
        }
        return "top.xhtml?redirect=true";
    }
    
    /**
     * 申請料金を取得する
     * @return 申請料金
     */
    public String getFare() {
        // 往復がチェックされている場合は片道料金の２倍、チェックされていない場合は片道料金
        if (editEntry.getOneWayFee() != null) {
            Integer fare = (editEntry.isRoundtrip() ? editEntry.getOneWayFee() * 2 : editEntry.getOneWayFee());
            return String.format("%,d", fare) + " 円";
        } else {
            return null;
        }
    }
    
    /**
     * 作業名を取得する
     * @param id 作業コード
     * @return 作業名
     */
    public String getOrderName(String id) {
        MOrder order = mOrderFacade.find(id);
        if (order != null) {
            return order.getOrderName();
        } else {
            return null;
        }
    }
    
    /**
     * 交通手段名を取得する
     * @param id 交通手段コード
     * @return 交通手段名
     */
    public String getMeansName(Integer id) {
        MMeans means = mMeansFacade.find(id);
        if (means != null) {
            return means.getMeans();
        } else {
            return null;
        }
    }
    
    /**
     * スタイル（行の背景色）を取得する
     * @param entry 対象の明細
     * @return 
     */
    public String getStyle(LineDto entry) {
        // 対象の明細が編集中の場合は背景色を返却する
        if (updatingEntry == entry) {
            return "background-color: #ffe4e1;";
        } else {
            return null;
        }
    }
    
    /**
     * 各ボタンが無効であるかを取得する
     * （明細追加・明細更新・明細更新キャンセル・編集・削除・コピー）
     * @return true:無効 false:有効
     */
    public boolean isDisabledButton() {
        return (updatingEntry != null);
    }

    /**
     * 保存・申請ボタンが無効であるかを取得する
     * @return true:無効 false:有効
     */
    public boolean isDisabledSaveButton() {
        if (updatingEntry != null) {
            return true;
        } else {
            return (getLineCount() == 0);
        }
    }

    /**
     * 新規モードで画面を初期化する
     */
    private void initNew() {
        mode = Constant.MAKE_MODE_NEW;
        applicationId = null;
        bossId = session.getBossId();
    }

    /**
     * 編集モードで画面を初期化する
     * @param id 申請ID
     */
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
    
    /**
     * 承認者ドロップダウンリスト内容の設定
     */
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

    /**
     * 作業ドロップダウンリスト内容の設定
     */
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
    
    /**
     * 交通手段ドロップダウンリスト内容の設定
     */
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
    
    /**
     * 申請料金の合計を取得する
     * @return 申請料金の合計
     */
    private int getTotalFare() {
        int totalFare = 0;
        for (LineDto lineDto : entryList) {
            if (!lineDto.isDeleted()) {
                totalFare += (lineDto.isRoundtrip() ? lineDto.getOneWayFee() * 2 : lineDto.getOneWayFee());
            }
        }
        return totalFare;
    }
    
    /**
     * 申請明細の件数を取得する
     * @return 申請明細の件数
     */
    private int getLineCount() {
        int count = 0;
        for (LineDto entry : entryList) {
            if (!entry.isDeleted()) {
                count += 1;
            }
        }
        return count;
    }
    
    /**
     * 申請明細をコピーする
     * @param srcEntry コピー元の申請明細
     * @param dstEntry コピー先の申請明細
     */
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
    
    /**
     * 申請明細のリストを作成する
     * @return 申請明細のリスト
     */
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
    
    /**
     * 申請明細を更新する
     */
    private void updateLines() {
        int sortNo = 1;
        for (LineDto entry : entryList) {
            if (entry.getId() == null && !entry.isDeleted()) {
                // 追加
                TLine line = new TLine();
                setEntryToLine(entry, line);
                line.setApplicationId(applicationId);
                line.setSortNo(sortNo++);
                tLineFacade.create(line);
            }
            if (entry.getId() != null && !entry.isDeleted()) {
                // 更新
                TLine line = tLineFacade.find(entry.getId());
                setEntryToLine(entry, line);
                line.setSortNo(sortNo++);
                tLineFacade.edit(line);
            }
            if (entry.getId() != null && entry.isDeleted()) {
                // 削除
                TLine line = tLineFacade.find(entry.getId());
                tLineFacade.remove(line);
            }
        }
    }
    
    /**
     * DBから取得した申請明細を入力用のエンティティに設定する
     * @param entry 入力用のエンティティ
     * @param line DBから申請した申請明細
     */
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
}
