package rs.raf.demo.relations;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;


public class PreduzeceRelations<T> extends ForeignFieldRelations<T> {

    public PreduzeceRelations(Root<T> root, CriteriaBuilder builder, String lvl1key, String lvl2key, String val) {
        super(root, builder, lvl1key, val);

        String fieldKey = lvl2key == "" ? "preduzeceId":lvl2key;
        idExpression = root.get(key).get(fieldKey).as(Long.class);
    }

}
