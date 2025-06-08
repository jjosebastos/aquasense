package br.com.fiap.aquasense.specification;

import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.model.AreaRiscoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AreaRiscoSpecification {
    public static Specification<AreaRisco> withFilters(AreaRiscoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filter.tipoRisco() != null) {
                predicates.add(cb.equal(root.get("tipoRisco"),filter.tipoRisco()));
            }
            if(filter.dataAtualizacaoInicial() != null && filter.dataAtualizacaoFinal() != null) {
                predicates.add(cb.between(root.get("date"), filter.dataAtualizacaoInicial(), filter.dataAtualizacaoInicial()));
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };

    }
}
