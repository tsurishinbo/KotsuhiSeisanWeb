package jp.co.stcinc.kotsuhiseisan.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jp.co.stcinc.kotsuhiseisan.entity.TReject;

/**
 * 差戻Facadeクラス
 */
@Stateless
public class TRejectFacade extends AbstractFacade<TReject> {

    @PersistenceContext(unitName = "KotsuhiSeisanWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TRejectFacade() {
        super(TReject.class);
    }
    
}
