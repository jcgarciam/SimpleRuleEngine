package org.simplerule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by jcgarciam has 5/23/16.
 */
public class MatchingContextImpl implements MatchingContext {
    private Set<Object> factsSet = new HashSet<>();
    private Map<Class, List<Object>> partialMatches = new HashMap<>();

    void addFacts(final Object _simpleFacts) {
        this.factsSet.add(_simpleFacts);
    }

    void retractFact(final Object _toRemove) {
        if(_toRemove instanceof Stream){
            ((Stream)_toRemove).forEach(f->factsSet.remove(f));
        }else{
            factsSet.remove(_toRemove);
        }
    }

    @Override
    public <T> boolean has(final Class<T> _className, final Predicate<T> filter) {
        final Stream<Object> objectStream = factsSet.stream()
                .filter(f -> f.getClass().equals(_className))
                .filter((Predicate<Object>) filter);

        final Object[] objects = objectStream.toArray();
        partialMatches
                .computeIfAbsent(_className, x -> new LinkedList<>())
                .addAll(Arrays.asList(objects));

        return objects.length > 0;
    }

    Map<Class, List<Object>> takeMatches() {
        Map<Class, List<Object>> matches = partialMatches;
        partialMatches = new HashMap<>();
        return matches;
    }
}
