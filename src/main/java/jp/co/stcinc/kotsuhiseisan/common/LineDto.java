package jp.co.stcinc.kotsuhiseisan.common;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class LineDto {

    @Getter @Setter
    private int lineKind;
    @Getter @Setter
    private Integer id;
    @Getter @Setter
    private Integer applicationId;
    @Getter @Setter
    private Date usedDate;
    @Getter @Setter
    private String orderId;
    @Getter @Setter
    private String place;
    @Getter @Setter
    private String purpose;
    @Getter @Setter
    private Integer meansId;
    @Getter @Setter
    private String sectionFrom;
    @Getter @Setter
    private String sectionTo;
    @Getter @Setter
    private boolean isRoundtrip;
    @Getter @Setter
    private Integer oneWayFee;
    @Getter @Setter
    private String memo;
}
