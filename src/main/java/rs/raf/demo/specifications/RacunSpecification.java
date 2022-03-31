package rs.raf.demo.specifications;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@AllArgsConstructor
public class RacunSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Expression<String> key = root.get(criteria.getKey());
        Class keyType = key.getJavaType();

        String value = criteria.getValue().toString();

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(key,value);
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(key,value);
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if ( keyType == String.class) {
                return builder.like(key, "%" + value + "%");
            }
            else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
