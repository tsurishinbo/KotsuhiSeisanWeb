package jp.co.stcinc.kotsuhiseisan.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "m_means")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MMeans.findAll", query = "SELECT m FROM MMeans m ORDER BY m.id")
    , @NamedQuery(name = "MMeans.findById", query = "SELECT m FROM MMeans m WHERE m.id = :id ORDER BY m.id")
    , @NamedQuery(name = "MMeans.findByMeans", query = "SELECT m FROM MMeans m WHERE m.means = :means ORDER BY m.id")})
public class MMeans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "means")
    private String means;

    public MMeans() {
    }

    public MMeans(Integer id) {
        this.id = id;
    }

    public MMeans(Integer id, String means) {
        this.id = id;
        this.means = means;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
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
        if (!(object instanceof MMeans)) {
            return false;
        }
        MMeans other = (MMeans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.MMeans[ id=" + id + " ]";
    }
    
}
