package jp.co.stcinc.kotsuhiseisan.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jp.co.stcinc.kotsuhiseisan.common.ApplicationConfig;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import lombok.Getter;
import lombok.Setter;

/**
 * 交通費精算書画面のバッキングビーン
 */
@Named(value = "reportView")
@ViewScoped
public class ReportView extends AbstractView {

    /**
     * 交通費精算書クラス
     * 交通費精算書のリンクを作成するために使用する
     */
    public class ReportInfo {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private String url;
    }
    
    @Getter @Setter
    private List<ReportInfo> reports;

    /**
     * 初期処理
     */
    @PostConstruct
    @Override
    public void init() {
        String uri = ApplicationConfig.getAppConfig(Constant.CONFIG_REPORT_URI);
        String path = ApplicationConfig.getAppConfig(Constant.CONFIG_REPORT_PATH);
        File dir = new File(path);
        File[] files = dir.listFiles();
        reports = new ArrayList<>();
        for (File file : files) {
            if (file.getName().matches("^交通費精算書_[0-9]+.xlsx$")) {
                ReportInfo report = new ReportInfo();
                report.name = file.getName();
                report.url = uri + "/" + file.getName();
                reports.add(report);
            }
        }
    }
}
