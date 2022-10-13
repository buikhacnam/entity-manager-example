package com.buinam.entitymanagerexample.repository;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.LinkedHashMap;
import java.util.Map;

public class AliasToEntityOrderedMapResultTransformer extends AliasedTupleSubsetResultTransformer {
    public static final AliasToEntityOrderedMapResultTransformer INSTANCE = new AliasToEntityOrderedMapResultTransformer();
    /**
     * Disallow instantiation of AliasToEntityOrderedMapResultTransformer .
     */
    private AliasToEntityOrderedMapResultTransformer () {
    }

    /**
     * {@inheritDoc}
     */
    public Object transformTuple(Object[] tuple, String[] aliases) {
        /* please note here LinkedHashMap is used so hopefully u ll get ordered key */
        Map result = new LinkedHashMap(tuple.length);
        for ( int i=0; i<tuple.length; i++ ) {
            String alias = aliases[i];
            if ( alias!=null ) {
                result.put( alias, tuple[i] );
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
