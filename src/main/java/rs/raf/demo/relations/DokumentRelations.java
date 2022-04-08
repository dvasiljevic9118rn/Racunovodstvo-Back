package rs.raf.demo.relations;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class DokumentRelations<T> extends ForeignFieldRelations<T> {
    public DokumentRelations(Root<T> root, CriteriaBuilder builder, String lvl1key, String lvl2key, String val) {
        super(root, builder, lvl1key, val);

        String fieldKey = lvl2key == "" ? "dokumentId":lvl2key;
        idExpression = root.get(key).get(fieldKey).as(Long.class);
    }
}
