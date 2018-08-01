package jp.co.stcinc.kotsuhiseisan.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jp.co.stcinc.kotsuhiseisan.entity.MMeans;

/**
 * 交通手段Facadeクラス
 */
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
    
    @Override
    public List<MMeans> findAll() {
        Query query = em.createNamedQuery("MMeans.findAll", MMeans.class);
        List<MMeans> meansList = query.getResultList();
        return meansList;
    }
}
