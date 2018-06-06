package jp.co.stcinc.kotsuhiseisan.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jp.co.stcinc.kotsuhiseisan.entity.MMeans;

@Stateless
public class MMeansFacade extends AbstractFacade<MMeans> {

    @PersistenceContext(unitName = "KotsuhiSeisanWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MMeansFacade() {
        super(MMeans.class);
    }
    
}
