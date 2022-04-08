package rs.raf.demo.specifications;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import rs.raf.demo.exceptions.OperationNotSupportedException;
import rs.raf.demo.model.Dokument;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.model.Preduzece;
import rs.raf.demo.model.enums.RadnaPozicija;
import rs.raf.demo.model.enums.StatusZaposlenog;
import rs.raf.demo.model.enums.TipFakture;
import rs.raf.demo.relations.*;

import javax.persistence.criteria.*;

import java.util.Date;
import java.util.Objects;


@AllArgsConstructor
public class RacunSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;


    private RacunRelations<T> getRelations(Root<T> root, CriteriaBuilder builder, Class keyType, String lvl1key, String lvl2key, String val)
            throws OperationNotSupportedException {

        if (Date.class == keyType) {
            return new DateRelations<>(root, builder, lvl1key, val);
        }
        if (Long.class == keyType) {
            return new LongRelations<>(root, builder, lvl1key, val);
        }
        if (String.class == keyType) {
            return new StringRelations<>(root, builder, lvl1key, val);
        }
        if (Double.class == keyType) {
            return new DoubleRelations<>(root, builder, lvl1key, val);
        }

        if (Preduzece.class == keyType) {
            return new PreduzeceRelations<>(root, builder, lvl1key, lvl2key, val);
        }
        if (Dokument.class == keyType) {
            return new DokumentRelations<>(root, builder, lvl1key, lvl2key, val);
        }
        if (KontnaGrupa.class == keyType) {
            return new KontnaGrupaRelations<>(root, builder, lvl1key, lvl2key, val);
        }

        if (TipFakture.class == keyType) {
            return new TipFaktureRelations<>(root, builder, lvl1key, val);
        }
        if (RadnaPozicija.class == keyType) {
            return new RadnaPozicijaRelations<>(root, builder, lvl1key, val);
        }
        if (StatusZaposlenog.class == keyType) {
            return new StatusZaposlenogRelations<>(root, builder, lvl1key, val);
        }

        throw new OperationNotSupportedException(String.format("Josuvek nije podrzano filtriranje po tipu %s(%s)", lvl1key, keyType));
    }

    @Override
    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        String orgKey = criteria.getKey(); // sa leve str :
        String lvl1key = orgKey;
        String lvl2key = "";

        if(orgKey.contains("_")){

            String[] keys = orgKey.split("_");

            if(keys.length != 2){
                throw new RuntimeException("kljuc ne sme da sadrzi vise od jednog '_' ("+orgKey+")");
            }
            lvl1key = keys[0];
            lvl2key = keys[1];
        }

        Class keyType = root.get(orgKey).getJavaType();

        RacunRelations<T> relations = getRelations(root,builder,keyType,lvl1key,lvl2key,criteria.getValue().toString());

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return relations.greaterThanOrEqualTo();
        }
        if (criteria.getOperation().equalsIgnoreCase("<")) {
            return relations.lessThanOrEqualTo();
        }
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            return relations.equalTo();
        }
        throw new OperationNotSupportedException(String.format("Nepoznata operacija \"%s\"",criteria.getOperation()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RacunSpecification<?> that = (RacunSpecification<?>) o;
        return Objects.equals(criteria, that.criteria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteria);
    }
}
