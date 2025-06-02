package br.com.manager.repository;

import br.com.manager.exception.BusinessException;
import br.com.manager.interfaces.Repository;
import br.com.manager.util.Constants;
import br.com.manager.util.JPAUtil;
import br.com.manager.util.TextManager;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class GenericRepository<T, ID> implements Repository<T, ID> {

    private final Class<T> entityClass;

    protected GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            T mergedEntity = em.merge(entity);
            em.getTransaction().commit();
            return mergedEntity;
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback em caso de erro
            throw new BusinessException(TextManager.getLabel("error.saving.entity") + Constants.SPACE_BLANK + e.getMessage(), e);
        } finally {
            em.close();
        }

    }

    @Override
    public Optional<T> findById(ID id) {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(this.entityClass, id));
        } catch (Exception e) {
            throw new BusinessException(TextManager.getLabel("error.when.searching") + Constants.SPACE_BLANK + e.getMessage(), e);
        }

    }

    @Override
    public List<T> findAll() {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("FROM " + this.entityClass.getSimpleName(), this.entityClass).getResultList();
        } catch (Exception e) {
            throw new BusinessException(TextManager.getLabel("error.listing.entities") + Constants.SPACE_BLANK + e.getMessage(), e);
        }

    }

    @Override
    public void delete(ID id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            T entity = em.find(this.entityClass, id);

            if (Objects.nonNull(entity)) {
                em.remove(entity);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new BusinessException(TextManager.getLabel("error.deleting.entity") + Constants.SPACE_BLANK + e.getMessage(), e);
        } finally {
            em.close();
        }

    }

    public void isDatabaseOnline() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.createNativeQuery("SELECT 1").getSingleResult();
        } catch (Exception e) {
            throw new BusinessException(TextManager.getLabel("error.on.connect.db") + Constants.SPACE_BLANK + e.getMessage(), e);
        }
    }

}
