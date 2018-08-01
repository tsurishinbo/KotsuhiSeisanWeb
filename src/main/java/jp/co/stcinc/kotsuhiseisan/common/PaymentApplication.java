package jp.co.stcinc.kotsuhiseisan.common;

import java.util.List;
import java.util.Objects;
import javax.faces.model.ListDataModel;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import org.primefaces.model.SelectableDataModel;

/**
 * 支払確定一覧用データモデルクラス
 * 
 */
public class PaymentApplication extends ListDataModel<TApplication> implements SelectableDataModel<TApplication> {

    /**
     * コンストラクタ
     * @param data 申請リスト
     */
    public PaymentApplication(List<TApplication> data) {
        super(data);
    }
    
    /**
     * 行を識別するキーを取得する
     * @param application 申請
     * @return 行を識別するキー
     */
    @Override
    public Object getRowKey(TApplication application) {
        return application.getId();
    }

    /**
     * 行を取得する
     * @param rowKey 行を識別するキー
     * @return 行
     */
    @Override
    public TApplication getRowData(String rowKey) {
        Integer id = Integer.parseInt(rowKey);
        List<TApplication> applicationList = (List<TApplication>)getWrappedData();
        for (TApplication app : applicationList) {
            if (Objects.equals(id, app.getId())) {
                return app;
            }
        }
        return null;
    }
    
}
