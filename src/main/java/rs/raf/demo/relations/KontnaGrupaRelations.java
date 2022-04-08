package rs.raf.demo.relations;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class KontnaGrupaRelations<T> extends ForeignFieldRelations<T> {
    public KontnaGrupaRelations(Root<T> root, CriteriaBuilder builder, String lvl1key, String lvl2key, String val) {
        super(root, builder, lvl1key, val);

        String fieldKey = lvl2key == "" ? "brojKonta": lvl2key;
        idExpression = root.get(key).get(fieldKey).as(Long.class);
    }
}
