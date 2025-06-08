package br.com.fiap.aquasense.specification;

import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.model.LocalizacaoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LocalizacaoSpecification {
    public static Specification<Localizacao> withFilters(LocalizacaoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filter.statusLocalizacao() != null) {
                predicates.add(cb.equal(root.get("status"),filter.statusLocalizacao()));
            }
            if(filter.nomeLocal() != null) {
                predicates.add(cb.like(root.get("nome"), filter.nomeLocal()));
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };

    }
}
