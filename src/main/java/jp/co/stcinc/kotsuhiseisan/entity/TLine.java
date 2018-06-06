package jp.co.stcinc.kotsuhiseisan.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "t_line")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TLine.findAll", query = "SELECT t FROM TLine t ORDER BY t.id")
    , @NamedQuery(name = "TLine.findById", query = "SELECT t FROM TLine t WHERE t.id = :id ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByApplicationId", query = "SELECT t FROM TLine t WHERE t.applicationId = :applicationId ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByUsedDate", query = "SELECT t FROM TLine t WHERE t.usedDate = :usedDate ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByOrderId", query = "SELECT t FROM TLine t WHERE t.orderId = :orderId ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByPlace", query = "SELECT t FROM TLine t WHERE t.place = :place ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByPurpose", query = "SELECT t FROM TLine t WHERE t.purpose = :purpose ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByMeansId", query = "SELECT t FROM TLine t WHERE t.meansId = :meansId ORDER BY t.id")
    , @NamedQuery(name = "TLine.findBySectionFrom", query = "SELECT t FROM TLine t WHERE t.sectionFrom = :sectionFrom ORDER BY t.id")
    , @NamedQuery(name = "TLine.findBySectionTo", query = "SELECT t FROM TLine t WHERE t.sectionTo = :sectionTo ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByIsRoundtrip", query = "SELECT t FROM TLine t WHERE t.isRoundtrip = :isRoundtrip ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByFare", query = "SELECT t FROM TLine t WHERE t.fare = :fare ORDER BY t.id")
    , @NamedQuery(name = "TLine.findByMemo", query = "SELECT t FROM TLine t WHERE t.memo = :memo ORDER BY t.id")
    , @NamedQuery(name = "TLine.findBySortNo", query = "SELECT t FROM TLine t WHERE t.sortNo = :sortNo ORDER BY t.id")})
public class TLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "application_id")
    private int applicationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "used_date")
    @Temporal(TemporalType.DATE)
    private Date usedDate;
    @Size(max = 7)
    @Column(name = "order_id")
    private String orderId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "place")
    private String place;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "purpose")
    private String purpose;
    @Basic(optional = false)
    @NotNull
    @Column(name = "means_id")
    private int meansId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "section_from")
    private String sectionFrom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "section_to")
    private String sectionTo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_roundtrip")
    private int isRoundtrip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fare")
    private BigInteger fare;
    @Size(max = 2147483647)
    @Column(name = "memo")
    private String memo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sort_no")
    private int sortNo;
    @OneToOne
    @JoinColumn(name = "means_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMeans means;
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MOrder order;

    public TLine() {
    }

    public TLine(Integer id) {
        this.id = id;
    }

    public TLine(Integer id, int applicationId, Date usedDate, String place, String purpose, int meansId, String sectionFrom, String sectionTo, int isRoundtrip, BigInteger fare, int sortNo) {
        this.id = id;
        this.applicationId = applicationId;
        this.usedDate = usedDate;
        this.place = place;
        this.purpose = purpose;
        this.meansId = meansId;
        this.sectionFrom = sectionFrom;
        this.sectionTo = sectionTo;
        this.isRoundtrip = isRoundtrip;
        this.fare = fare;
        this.sortNo = sortNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getMeansId() {
        return meansId;
    }

    public void setMeansId(int meansId) {
        this.meansId = meansId;
    }

    public String getSectionFrom() {
        return sectionFrom;
    }

    public void setSectionFrom(String sectionFrom) {
        this.sectionFrom = sectionFrom;
    }

    public String getSectionTo() {
        return sectionTo;
    }

    public void setSectionTo(String sectionTo) {
        this.sectionTo = sectionTo;
    }

    public int getIsRoundtrip() {
        return isRoundtrip;
    }

    public void setIsRoundtrip(int isRoundtrip) {
        this.isRoundtrip = isRoundtrip;
    }

    public BigInteger getFare() {
        return fare;
    }

    public void setFare(BigInteger fare) {
        this.fare = fare;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public MMeans getMeans() {
        return means;
    }

    public void setMeans(MMeans means) {
        this.means = means;
    }

    public MOrder getOrder() {
        return order;
    }

    public void setOrder(MOrder order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TLine)) {
            return false;
        }
        TLine other = (TLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.TLine[ id=" + id + " ]";
    }
    
}
