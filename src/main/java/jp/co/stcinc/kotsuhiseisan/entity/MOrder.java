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
@Table(name = "m_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MOrder.findAll", query = "SELECT m FROM MOrder m ORDER BY m.id")
    , @NamedQuery(name = "MOrder.findById", query = "SELECT m FROM MOrder m WHERE m.id = :id ORDER BY m.id")
    , @NamedQuery(name = "MOrder.findByOrderName", query = "SELECT m FROM MOrder m WHERE m.orderName = :orderName ORDER BY m.id")})
public class MOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "order_name")
    private String orderName;

    public MOrder() {
    }

    public MOrder(String id) {
        this.id = id;
    }

    public MOrder(String id, String orderName) {
        this.id = id;
        this.orderName = orderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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
        if (!(object instanceof MOrder)) {
            return false;
        }
        MOrder other = (MOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.MOrder[ id=" + id + " ]";
    }
    
}
