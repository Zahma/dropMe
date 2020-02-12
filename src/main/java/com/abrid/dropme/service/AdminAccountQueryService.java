package com.abrid.dropme.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.abrid.dropme.domain.AdminAccount;
import com.abrid.dropme.domain.*; // for static metamodels
import com.abrid.dropme.repository.AdminAccountRepository;
import com.abrid.dropme.service.dto.AdminAccountCriteria;
import com.abrid.dropme.service.dto.AdminAccountDTO;
import com.abrid.dropme.service.mapper.AdminAccountMapper;

/**
 * Service for executing complex queries for {@link AdminAccount} entities in the database.
 * The main input is a {@link AdminAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdminAccountDTO} or a {@link Page} of {@link AdminAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdminAccountQueryService extends QueryService<AdminAccount> {

    private final Logger log = LoggerFactory.getLogger(AdminAccountQueryService.class);

    private final AdminAccountRepository adminAccountRepository;

    private final AdminAccountMapper adminAccountMapper;

    public AdminAccountQueryService(AdminAccountRepository adminAccountRepository, AdminAccountMapper adminAccountMapper) {
        this.adminAccountRepository = adminAccountRepository;
        this.adminAccountMapper = adminAccountMapper;
    }

    /**
     * Return a {@link List} of {@link AdminAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdminAccountDTO> findByCriteria(AdminAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdminAccount> specification = createSpecification(criteria);
        return adminAccountMapper.toDto(adminAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdminAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminAccountDTO> findByCriteria(AdminAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdminAccount> specification = createSpecification(criteria);
        return adminAccountRepository.findAll(specification, page)
            .map(adminAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdminAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdminAccount> specification = createSpecification(criteria);
        return adminAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link AdminAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdminAccount> createSpecification(AdminAccountCriteria criteria) {
        Specification<AdminAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdminAccount_.id));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildSpecification(criteria.getRole(), AdminAccount_.role));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(AdminAccount_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
