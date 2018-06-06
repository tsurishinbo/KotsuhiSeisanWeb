package jp.co.stcinc.kotsuhiseisan.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "m_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MEmployee.findAll", query = "SELECT m FROM MEmployee m ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findById", query = "SELECT m FROM MEmployee m WHERE m.id = :id ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findByPassword", query = "SELECT m FROM MEmployee m WHERE m.password = :password ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findByEmployeeName", query = "SELECT m FROM MEmployee m WHERE m.employeeName = :employeeName ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findByBossId", query = "SELECT m FROM MEmployee m WHERE m.bossId = :bossId ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findByManager", query = "SELECT m FROM MEmployee m WHERE m.manager = :manager ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findByEmail", query = "SELECT m FROM MEmployee m WHERE m.email = :email ORDER BY m.id")
    , @NamedQuery(name = "MEmployee.findById&Password", query = "SELECT m FROM MEmployee m WHERE m.id = :id AND m.password = :password ORDER BY m.id")})
public class MEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "boss_id")
    private Integer bossId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "manager")
    private int manager;
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @OneToOne
    @JoinColumn(name = "boss_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MEmployee boss;

    public MEmployee() {
    }

    public MEmployee(Integer id) {
        this.id = id;
    }

    public MEmployee(Integer id, String password, String employeeName, int manager) {
        this.id = id;
        this.password = password;
        this.employeeName = employeeName;
        this.manager = manager;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public MEmployee getBoss() {
        return boss;
    }

    public void setBoss(MEmployee boss) {
        this.boss = boss;
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
        if (!(object instanceof MEmployee)) {
            return false;
        }
        MEmployee other = (MEmployee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.MEmployee[ id=" + id + " ]";
    }
    
}
