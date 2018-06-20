/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.stcinc.kotsuhiseisan.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kageyamay
 */
@Entity
@Table(name = "t_auth")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TAuth.findAll", query = "SELECT t FROM TAuth t")
    , @NamedQuery(name = "TAuth.findByToken", query = "SELECT t FROM TAuth t WHERE t.token = :token")
    , @NamedQuery(name = "TAuth.findByEmpNo", query = "SELECT t FROM TAuth t WHERE t.empNo = :empNo")
    , @NamedQuery(name = "TAuth.findByIssued", query = "SELECT t FROM TAuth t WHERE t.issued = :issued")
    , @NamedQuery(name = "TAuth.findByExpire", query = "SELECT t FROM TAuth t WHERE t.expire = :expire")})
public class TAuth implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emp_no")
    private int empNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "issued")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issued;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expire")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    public TAuth() {
    }

    public TAuth(String token) {
        this.token = token;
    }

    public TAuth(String token, int empNo, Date issued, Date expire) {
        this.token = token;
        this.empNo = empNo;
        this.issued = issued;
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public Date getIssued() {
        return issued;
    }

    public void setIssued(Date issued) {
        this.issued = issued;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (token != null ? token.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TAuth)) {
            return false;
        }
        TAuth other = (TAuth) object;
        if ((this.token == null && other.token != null) || (this.token != null && !this.token.equals(other.token))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.stcinc.kotsuhiseisan.entity.TAuth[ token=" + token + " ]";
    }
    
}
