package jp.co.stcinc.kotsuhiseisan.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jp.co.stcinc.kotsuhiseisan.entity.MEmployee;

/**
 * 社員Facadeクラス
 */
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
    
    @Override
    public List<MEmployee> findAll() {
        Query query = em.createNamedQuery("MEmployee.findAll", MEmployee.class);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
    
    public MEmployee find(final Integer id, final String password) {
        Query query = em.createNamedQuery("MEmployee.findById&Password", MEmployee.class);
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
    
    public List<MEmployee> findBoss() {
        Query query = em.createNamedQuery("MEmployee.findByIsManager", MEmployee.class);
        query.setParameter("isManager", 1);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
    
    public List<MEmployee> findBoss(final Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("WITH RECURSIVE temp(id, employee_name, boss_id) AS ( ");
        sql.append("SELECT id, employee_name, boss_id ");
        sql.append("FROM m_employee ");
        sql.append("WHERE boss_id IS NULL ");
        sql.append("UNION ");
        sql.append("SELECT id, employee_name, boss_id ");
        sql.append("FROM m_employee ");
        sql.append("WHERE id = (SELECT boss_id FROM m_employee WHERE id = ?1) ");
        sql.append("UNION ");
        sql.append("SELECT m.id, m.employee_name, m.boss_id ");
        sql.append("FROM m_employee m, temp t ");
        sql.append("WHERE m.id = t.boss_id ");
        sql.append(") ");
        sql.append("SELECT id, employee_name FROM temp ORDER BY id ");
        Query query = em.createNativeQuery(sql.toString(), MEmployee.class);
        query.setParameter(1, id);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }

    public List<MEmployee> findBuka(final Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("WITH RECURSIVE temp(id, employee_name, boss_id) AS ( ");
        sql.append("SELECT id, employee_name, boss_id ");
        sql.append("FROM m_employee ");
        sql.append("WHERE id = ?1 ");
        sql.append("UNION ");
        sql.append("SELECT m.id, m.employee_name, m.boss_id ");
        sql.append("FROM m_employee m, temp t ");
        sql.append("WHERE m.boss_id = t.id ");
        sql.append(") ");
        sql.append("SELECT id, employee_name FROM temp ORDER BY id ");
        Query query = em.createNativeQuery(sql.toString(), MEmployee.class);
        query.setParameter(1, id);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
    
    public List<MEmployee> findBukaManager(final Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("WITH RECURSIVE temp(id, employee_name, boss_id) AS ( ");
        sql.append("SELECT id, employee_name, boss_id ");
        sql.append("FROM m_employee ");
        sql.append("WHERE id = ?1 ");
        sql.append("UNION ");
        sql.append("SELECT m.id, m.employee_name, m.boss_id ");
        sql.append("FROM m_employee m, temp t ");
        sql.append("WHERE m.boss_id = t.id ");
        sql.append("AND m.is_boss = 1 ");
        sql.append(") ");
        sql.append("SELECT id, employee_name FROM temp ORDER BY id ");
        Query query = em.createNativeQuery(sql.toString(), MEmployee.class);
        query.setParameter(1, id);
        List<MEmployee> employeeList = query.getResultList();
        return employeeList;
    }
}
