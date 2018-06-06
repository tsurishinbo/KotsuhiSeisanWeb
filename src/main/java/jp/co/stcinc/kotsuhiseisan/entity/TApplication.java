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
    @NamedQuery(name = "TApplication.findAll", query = "SELECT t FROM TApplication t ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findById", query = "SELECT t FROM TApplication t WHERE t.id = :id ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findByStatus", query = "SELECT t FROM TApplication t WHERE t.status = :status ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findByApplyId", query = "SELECT t FROM TApplication t WHERE t.applyId = :applyId ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findByApplyDate", query = "SELECT t FROM TApplication t WHERE t.applyDate = :applyDate ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findByApproveId", query = "SELECT t FROM TApplication t WHERE t.approveId = :approveId ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findByApproveDate", query = "SELECT t FROM TApplication t WHERE t.approveDate = :approveDate ORDER BY t.id")
    , @NamedQuery(name = "TApplication.findByTotalFare", query = "SELECT t FROM TApplication t WHERE t.totalFare = :totalFare ORDER BY t.id")
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
    @Column(name = "approve_id")
    private Integer approveId;
    @Column(name = "approve_date")
    @Temporal(TemporalType.DATE)
    private Date approveDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_fare")
    private BigInteger totalFare;
    @OneToOne
    @JoinColumn(name = "apply_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee applyEmployee;
    @OneToOne
    @JoinColumn(name = "approve_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee approveEmployee;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    @OrderBy("sortNo asc")
    private List<TLine> lines;
    
    public TApplication() {
    }

    public TApplication(Integer id) {
        this.id = id;
    }

    public TApplication(Integer id, int status, int applyId, BigInteger totalFare) {
        this.id = id;
        this.status = status;
        this.applyId = applyId;
        this.totalFare = totalFare;
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

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public BigInteger getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigInteger totalFare) {
        this.totalFare = totalFare;
    }

    public MEmployee getApplyEmployee() {
        return applyEmployee;
    }

    public void setApplyEmployee(MEmployee applyEmployee) {
        this.applyEmployee = applyEmployee;
    }

    public MEmployee getApproveEmployee() {
        return approveEmployee;
    }

    public void setApproveEmployee(MEmployee approveEmployee) {
        this.approveEmployee = approveEmployee;
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
