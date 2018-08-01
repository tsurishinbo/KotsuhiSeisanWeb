package jp.co.stcinc.kotsuhiseisan.common;

import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 交通費申請明細入力情報クラス
 */
public class LineDto {

    @Getter @Setter
    private Integer id;
    @NotNull(message = "利用日は必須項目です。")
    @Getter @Setter
    private Date usedDate;
    @Getter @Setter
    private String orderId;
    @NotNull(message = "出張場所は必須項目です。")
    @Size(min = 1, max = 40, message = "出張場所は40文字以内で入力してください。")
    @Getter @Setter
    private String place;
    @NotNull(message = "出張内容は必須項目です。")
    @Size(min = 1, max = 40, message = "出張内容は40文字以内で入力してください。")
    @Getter @Setter
    private String purpose;
    @NotNull(message = "交通手段は必須項目です。")
    @Getter @Setter
    private Integer meansId;
    @NotNull(message = "出発地は必須項目です。")
    @Size(min = 1, max = 40, message = "出発地は40文字以内で入力してください。")
    @Getter @Setter
    private String sectionFrom;
    @NotNull(message = "到着地は必須項目です。")
    @Size(min = 1, max = 40, message = "到着地は40文字以内で入力してください。")
    @Getter @Setter
    private String sectionTo;
    @Getter @Setter
    private boolean isRoundtrip;
    @NotNull(message = "片道料金は必須項目です。")
    @Min(value = 1, message="1円以上の金額を入力してください。")
    @Max(value = 999999, message="999999円以下の金額を入力してください。")
    @Getter @Setter
    private Integer oneWayFee;
    @Size(max = 400, message = "備考は400文字以内で入力してください。")
    @Getter @Setter
    private String memo;
    @Getter @Setter
    private boolean isDeleted;
}
