package jp.co.stcinc.kotsuhiseisan.entity;

import java.io.Serializable;
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
@Table(name = "t_reject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TReject.findAll", query = "SELECT t FROM TReject t")
    , @NamedQuery(name = "TReject.findById", query = "SELECT t FROM TReject t WHERE t.id = :id")
    , @NamedQuery(name = "TReject.findByApplicationId", query = "SELECT t FROM TReject t WHERE t.applicationId = :applicationId")
    , @NamedQuery(name = "TReject.findByRejectId", query = "SELECT t FROM TReject t WHERE t.rejectId = :rejectId")
    , @NamedQuery(name = "TReject.findByRejectDate", query = "SELECT t FROM TReject t WHERE t.rejectDate = :rejectDate")
    , @NamedQuery(name = "TReject.findByComment", query = "SELECT t FROM TReject t WHERE t.comment = :comment")})
public class TReject implements Serializable {

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
    @Column(name = "reject_id")
    private int rejectId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reject_date")
    @Temporal(TemporalType.DATE)
    private Date rejectDate;
    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;
    @OneToOne
    @JoinColumn(name = "reject_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee rejecter;
    
    public TReject() {
    }

    public TReject(Integer id) {
        this.id = id;
    }

    public TReject(Integer id, int applicationId, int rejectId, Date rejectDate) {
        this.id = id;
        this.applicationId = applicationId;
        this.rejectId = rejectId;
        this.rejectDate = rejectDate;
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

    public int getRejectId() {
        return rejectId;
    }

    public void setRejectId(int rejectId) {
        this.rejectId = rejectId;
    }

    public Date getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MEmployee getRejecter() {
        return rejecter;
    }

    public void setRejecter(MEmployee rejecter) {
        this.rejecter = rejecter;
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
        if (!(object instanceof TReject)) {
            return false;
        }
        TReject other = (TReject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.TReject[ id=" + id + " ]";
    }
    
}
