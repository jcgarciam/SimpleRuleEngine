/*
 *
 *
 */

package org.simplerule;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by jcgarciam on 5/27/16.
 */
public class ExecutionContext {

    private String ruleName;
    private MatchingContextImpl ctx;
    private Map<Class, List<Object>> matches;

    public ExecutionContext(final String _ruleName, final MatchingContextImpl _ctx, final Map<Class, List<Object>> _matches) {
        ruleName = _ruleName;
        ctx = _ctx;
        matches = _matches;
    }

    public String getRuleName(){
        return ruleName;
    }
    public <T> T first(Class<T> _clazz){
        return (T) matches.get(_clazz).stream().findFirst().get();
    }
    public <T> Stream<T> all(Class<T> _clazz){
        return (Stream<T>) matches.get(_clazz).stream();
    }
    public <T> Stream<T> all(Class<T> _clazz, Predicate<T> _filter){
        return (Stream<T>) matches.get(_clazz).stream().filter((Predicate<Object>) _filter);
    }
    public <T> void retract(T _element){
        ctx.retractFact(_element);
    }
}
