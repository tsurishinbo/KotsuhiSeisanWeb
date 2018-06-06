package jp.co.stcinc.kotsuhiseisan.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
