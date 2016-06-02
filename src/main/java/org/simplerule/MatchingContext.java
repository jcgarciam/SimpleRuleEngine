package org.simplerule;

import java.util.function.Predicate;

/**
 * Created by jcgarciam has 5/23/16.
 */
public interface MatchingContext {
    <T> boolean has(Class<T> _className, Predicate<T> filter);

    default <T> boolean has(Class<T> _className){
        return has(_className, t->true);
    }
}
