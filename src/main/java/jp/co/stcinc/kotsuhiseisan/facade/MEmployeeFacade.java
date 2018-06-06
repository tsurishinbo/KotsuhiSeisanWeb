package jp.co.stcinc.kotsuhiseisan.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;

@Stateless
public class MEmployeeFacade extends AbstractFacade<MEmployee> {

    @PersistenceContext(unitName = "KotsuhiSeisanWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MEmployeeFacade() {
        super(MEmployee.class);
    }
    
    public MEmployee find(final Integer id, final String password) {
        Query query = em.createNamedQuery("MEmployee.findById&Password");
        query.setParameter("id", id);
        query.setParameter("password", password);
        MEmployee employee;
        try {
            employee = (MEmployee)query.getSingleResult();
        } catch (NoResultException e) {
            employee = null;
        }
        return employee;
    }
    
    public List<MEmployee> findManager() {
        Query query = em.createNamedQuery("MEmployee.findByManager");
        query.setParameter("manager", 1);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
}
