package br.portela.startuplogistica.repositories.user;

import br.portela.startuplogistica.dtos.user.input.FindUsersByFilters;
import br.portela.startuplogistica.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {
    private final EntityManager entityManager;
    private final UserJpaRepository repository;

    private Optional<User> findBy(String field, Object value) {
        if (Objects.isNull(field))
            return Optional.empty();

        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(User.class);
        final var user = query.from(User.class);

        // Remover a condição de "usuário ativo" temporariamente para depuração
        query.where(
                criteriaBuilder.equal(user.get(field), value)
        );

        final var results = entityManager.createQuery(query).getResultList();
        return results.stream().findFirst();
    }

    public Page<User> findAll(FindUsersByFilters filters) {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(User.class);
        final var user = query.from(User.class);
        final var pageLimit = filters.getLimit();
        final var pageNumber = filters.getPage();

        query.where(this.getFilterConditions(criteriaBuilder, user, filters)).orderBy(
                criteriaBuilder.asc(user.get("name")),
                criteriaBuilder.asc(user.get("createdAt"))
        );

        final var typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageLimit);
        typedQuery.setFirstResult(pageNumber * pageLimit);

        final var totalElements = getTotalUsersByFilters(criteriaBuilder, filters);

        final var pageable = PageRequest.of(pageNumber, pageLimit);
        return new PageImpl<>(typedQuery.getResultList(), pageable, totalElements);
    }

    private Predicate getFilterConditions(CriteriaBuilder criteriaBuilder, Root<User> user, FindUsersByFilters filters) {
        final var predicates = new ArrayList<Predicate>();

        Optional.ofNullable(filters.getName()).ifPresent(name -> {
                var userName = criteriaBuilder.lower(user.get("name"));
                predicates.add(criteriaBuilder.like(userName, "%"+ name.toLowerCase() +"%"));
        });

        Optional.ofNullable(filters.getEmail()).ifPresent(email ->
                predicates.add(criteriaBuilder.equal(user.get("email"), email))
        );

        Optional.ofNullable(filters.getRole()).ifPresent(role ->
                predicates.add(criteriaBuilder.equal(user.get("role"), role))
        );

        predicates.add(getUserStatusPredicate(criteriaBuilder, user, filters.getActive()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getUserStatusPredicate(CriteriaBuilder criteriaBuilder, Root<User> user, Boolean onlyActiveUsers) {
        Predicate condition;

        if (Objects.isNull(onlyActiveUsers))
            return criteriaBuilder.conjunction();

        condition = criteriaBuilder.or(
                criteriaBuilder.isNull(user.get("emailValidatedAt")),
                criteriaBuilder.isNotNull(user.get("disabledAt"))
        );

        if (onlyActiveUsers)
            condition = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(user.get("emailValidatedAt")),
                    criteriaBuilder.isNull(user.get("disabledAt"))
            );

        return condition;
    }

    private Long getTotalUsersByFilters(CriteriaBuilder criteriaBuilder, FindUsersByFilters filters) {
        var query = criteriaBuilder.createQuery(Long.class);
        var users = query.from(User.class);

        var countQuery = query.select(criteriaBuilder.count(users));
        countQuery.where(getFilterConditions(criteriaBuilder, users, filters));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public Optional<User> findByEmail(String email) {
        return this.findBy("email", email);
    }

    public Optional<User> findByEmailIncludeInactive(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> findById(UUID id) {
        return this.findBy("id", id);
    }

    public Optional<User> findByIdIncludeInactive(UUID id) {
        return repository.findById(id);
    }

    public User save(User entity) {
        return repository.save(entity);
    }

    public void delete(User user) {
        repository.delete(user);
    }

}
