package jp.co.stcinc.kotsuhiseisan.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
