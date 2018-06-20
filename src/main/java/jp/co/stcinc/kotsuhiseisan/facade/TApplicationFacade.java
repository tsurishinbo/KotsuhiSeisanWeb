package jp.co.stcinc.kotsuhiseisan.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jp.co.stcinc.kotsuhiseisan.common.Constant;
import jp.co.stcinc.kotsuhiseisan.entity.TApplication;

@Stateless
public class TApplicationFacade extends AbstractFacade<TApplication> {

    @PersistenceContext(unitName = "KotsuhiSeisanWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TApplicationFacade() {
        super(TApplication.class);
    }
    
    @Override
    public List<TApplication> findAll() {
        Query query = em.createNamedQuery("TApplication.findAll", TApplication.class);
        List<TApplication> applicationList = query.getResultList();
        return applicationList;
    }

    public Long getCountByStatus(final Integer applyId, final Integer status) {
        Query query = em.createNamedQuery("TApplication.getCountByStatus", Long.class);
        query.setParameter("applyId", applyId);
        query.setParameter("status", status);
        Long count = (Long)query.getSingleResult();
        return count;
    }
    
    public List<TApplication> findByForm(
            final Date applyDateFrom,
            final Date applyDateTo,
            final Integer applyId,
            final Integer status) {
        
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT t ");
        jpql.append("FROM TApplication t ");
        jpql.append("WHERE 0 = 0 ");
        if (applyDateFrom != null) {
            jpql.append("AND t.applyDate >= :applyDateFrom ");
            parameters.put("applyDateFrom", applyDateFrom);
        }
        if (applyDateTo != null) {
            jpql.append("AND t.applyDate <= :applyDateTo ");
            parameters.put("applyDateTo", applyDateTo);
        }
        if (applyId != null) {
            jpql.append("AND t.applyId = :applyId ");
            parameters.put("applyId", applyId);
        }
        if (status != Constant.STATUS_ALL) {
            jpql.append("AND t.status = :status ");
            parameters.put("status", status);
        }
        jpql.append("ORDER BY t.id ASC ");
        
        Query query = em.createQuery(jpql.toString(), TApplication.class);
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
        List<TApplication> applicationList = query.getResultList();
        return applicationList;
    }
    
    public List<TApplication> findBossApprove(final Integer bossId) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT t ");
        jpql.append("FROM TApplication t ");
        jpql.append("WHERE t.status = :status ");
        jpql.append("AND t.bossApproveId = :bossApproveId ");
        jpql.append("ORDER BY t.applyDate, t.applyId ");
        
        Query query = em.createQuery(jpql.toString(), TApplication.class);
        query.setParameter("status", Constant.STATUS_WAIT_BOSS);
        query.setParameter("bossApproveId", bossId);
        List<TApplication> applicationList = query.getResultList();
        return applicationList;
    }
    
    public List<TApplication> findManagerApprove() {
        Query query = em.createNamedQuery("TApplication.findByStatus", TApplication.class);
        query.setParameter("status", Constant.STATUS_WAIT_MANAGER);
        List<TApplication> applicationList = query.getResultList();
        return applicationList;
    }

    public List<TApplication> findPayment() {
        Query query = em.createNamedQuery("TApplication.findByStatus", TApplication.class);
        query.setParameter("status", Constant.STATUS_WAIT_PAYMENT);
        List<TApplication> applicationList = query.getResultList();
        return applicationList;
    }
}
