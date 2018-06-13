package jp.co.stcinc.kotsuhiseisan.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jp.co.stcinc.kotsuhiseisan.entity.MOrder;

@Stateless
public class MOrderFacade extends AbstractFacade<MOrder> {

    @PersistenceContext(unitName = "KotsuhiSeisanWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MOrderFacade() {
        super(MOrder.class);
    }
    
    @Override
    public List<MOrder> findAll() {
        Query query = em.createNamedQuery("MOrder.findAll", MOrder.class);
        List<MOrder> orderList = query.getResultList();
        return orderList;
    }
}
