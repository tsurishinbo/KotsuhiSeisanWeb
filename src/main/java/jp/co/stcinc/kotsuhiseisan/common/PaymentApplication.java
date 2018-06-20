package jp.co.stcinc.kotsuhiseisan.common;

import java.util.List;
import java.util.Objects;
import javax.faces.model.ListDataModel;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;
import org.primefaces.model.SelectableDataModel;

public class PaymentApplication extends ListDataModel<TApplication> implements SelectableDataModel<TApplication> {

    public PaymentApplication(List<TApplication> data) {
        super(data);
    }
    
    @Override
    public Object getRowKey(TApplication application) {
        return application.getId();
    }

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
