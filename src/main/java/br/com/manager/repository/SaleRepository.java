package br.com.manager.repository;


import br.com.manager.exception.BusinessException;
import br.com.manager.model.Sales;
import br.com.manager.util.Constants;
import br.com.manager.util.JPAUtil;
import br.com.manager.util.TextManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class SaleRepository extends GenericRepository<Sales, Long> {

    public SaleRepository() {
        super(Sales.class);
    }

    public List<Sales> findByClientId(Long clientId) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<Sales> query = em.createQuery("SELECT s FROM Sales s WHERE s.client.id = :id", Sales.class);
            query.setParameter("id", clientId);
            return query.getResultList();
        } catch (Exception e) {
            throw new BusinessException(TextManager.getLabel("error.listing.entities") + Constants.SPACE_BLANK + e.getMessage(), e);
        }
    }

    public List<Sales> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<Sales> query = em.createQuery("SELECT s FROM Sales s WHERE s.dateSale BETWEEN :startDate AND :endDate", Sales.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            throw new BusinessException(TextManager.getLabel("error.listing.entities") + Constants.SPACE_BLANK + e.getMessage(), e);
        }
    }

}
