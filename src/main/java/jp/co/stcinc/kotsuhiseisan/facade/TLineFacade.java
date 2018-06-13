package jp.co.stcinc.kotsuhiseisan.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jp.co.stcinc.kotsuhiseisan.entity.TLine;

@Stateless
public class TLineFacade extends AbstractFacade<TLine> {

    @PersistenceContext(unitName = "KotsuhiSeisanWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TLineFacade() {
        super(TLine.class);
    }
    
    @Override
    public List<TLine> findAll() {
        Query query = em.createNamedQuery("TLine.findAll", TLine.class);
        List<TLine> lineList = query.getResultList();
        return lineList;
    }
}
