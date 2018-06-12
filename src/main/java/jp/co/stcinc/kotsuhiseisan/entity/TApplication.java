package jp.co.stcinc.kotsuhiseisan.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "t_application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TApplication.findAll", query = "SELECT t FROM TApplication t")
    , @NamedQuery(name = "TApplication.findById", query = "SELECT t FROM TApplication t WHERE t.id = :id")
    , @NamedQuery(name = "TApplication.findByStatus", query = "SELECT t FROM TApplication t WHERE t.status = :status")
    , @NamedQuery(name = "TApplication.findByApplyId", query = "SELECT t FROM TApplication t WHERE t.applyId = :applyId")
    , @NamedQuery(name = "TApplication.findByApplyDate", query = "SELECT t FROM TApplication t WHERE t.applyDate = :applyDate")
    , @NamedQuery(name = "TApplication.findByBossApproveId", query = "SELECT t FROM TApplication t WHERE t.bossApproveId = :bossApproveId")
    , @NamedQuery(name = "TApplication.findByBossApproveDate", query = "SELECT t FROM TApplication t WHERE t.bossApproveDate = :bossApproveDate")
    , @NamedQuery(name = "TApplication.findByManagerApproveId", query = "SELECT t FROM TApplication t WHERE t.managerApproveId = :managerApproveId")
    , @NamedQuery(name = "TApplication.findByManagerApproveDate", query = "SELECT t FROM TApplication t WHERE t.managerApproveDate = :managerApproveDate")
    , @NamedQuery(name = "TApplication.findByPaymentId", query = "SELECT t FROM TApplication t WHERE t.paymentId = :paymentId")
    , @NamedQuery(name = "TApplication.findByPaymentDate", query = "SELECT t FROM TApplication t WHERE t.paymentDate = :paymentDate")
    , @NamedQuery(name = "TApplication.findByTotalFare", query = "SELECT t FROM TApplication t WHERE t.totalFare = :totalFare")
    , @NamedQuery(name = "TApplication.findByRejectCnt", query = "SELECT t FROM TApplication t WHERE t.rejectCnt = :rejectCnt")
    , @NamedQuery(name = "TApplication.getCountByStatus", query = "SELECT COUNT(t) FROM TApplication t WHERE t.applyId = :applyId AND t.status = :status")})
public class TApplication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apply_id")
    private int applyId;
    @Column(name = "apply_date")
    @Temporal(TemporalType.DATE)
    private Date applyDate;
    @Column(name = "boss_approve_id")
    private Integer bossApproveId;
    @Column(name = "boss_approve_date")
    @Temporal(TemporalType.DATE)
    private Date bossApproveDate;
    @Column(name = "manager_approve_id")
    private Integer managerApproveId;
    @Column(name = "manager_approve_date")
    @Temporal(TemporalType.DATE)
    private Date managerApproveDate;
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_fare")
    private BigInteger totalFare;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reject_cnt")
    private int rejectCnt;
    @OneToOne
    @JoinColumn(name = "apply_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee applicant;
    @OneToOne
    @JoinColumn(name = "boss_approve_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee boss;
    @OneToOne
    @JoinColumn(name = "manager_approve_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee manager;
    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee payer;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    @OrderBy("id")
    private List<TLine> lines;

    public TApplication() {
    }

    public TApplication(Integer id) {
        this.id = id;
    }

    public TApplication(Integer id, int status, int applyId, BigInteger totalFare, int rejectCnt) {
        this.id = id;
        this.status = status;
        this.applyId = applyId;
        this.totalFare = totalFare;
        this.rejectCnt = rejectCnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getBossApproveId() {
        return bossApproveId;
    }

    public void setBossApproveId(Integer bossApproveId) {
        this.bossApproveId = bossApproveId;
    }

    public Date getBossApproveDate() {
        return bossApproveDate;
    }

    public void setBossApproveDate(Date bossApproveDate) {
        this.bossApproveDate = bossApproveDate;
    }

    public Integer getManagerApproveId() {
        return managerApproveId;
    }

    public void setManagerApproveId(Integer managerApproveId) {
        this.managerApproveId = managerApproveId;
    }

    public Date getManagerApproveDate() {
        return managerApproveDate;
    }

    public void setManagerApproveDate(Date managerApproveDate) {
        this.managerApproveDate = managerApproveDate;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigInteger getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigInteger totalFare) {
        this.totalFare = totalFare;
    }

    public int getRejectCnt() {
        return rejectCnt;
    }

    public void setRejectCnt(int rejectCnt) {
        this.rejectCnt = rejectCnt;
    }

    public MEmployee getApplicant() {
        return applicant;
    }

    public void setApplicant(MEmployee applicant) {
        this.applicant = applicant;
    }

    public MEmployee getBoss() {
        return boss;
    }

    public void setBoss(MEmployee boss) {
        this.boss = boss;
    }

    public MEmployee getManager() {
        return manager;
    }

    public void setManager(MEmployee manager) {
        this.manager = manager;
    }

    public MEmployee getPayer() {
        return payer;
    }

    public void setPayer(MEmployee payer) {
        this.payer = payer;
    }

    public List<TLine> getLines() {
        return lines;
    }

    public void setLines(List<TLine> lines) {
        this.lines = lines;
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
        if (!(object instanceof TApplication)) {
            return false;
        }
        TApplication other = (TApplication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.TApplication[ id=" + id + " ]";
    }
    
}
