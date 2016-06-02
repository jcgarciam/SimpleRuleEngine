/*
 *
 *
 */

package org.simplerule;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jcgarciam on 5/25/16.
 */
public class SimpleRuleSession {
    private MatchingContext matchingContext;
    private List<SimpleRule> rules;

    public SimpleRuleSession(final MatchingContext _matchingContext) {
        matchingContext = _matchingContext;
        rules = new LinkedList<>();
    }

    public SimpleRuleSession addRule(SimpleRule _rules) {
        rules.add(_rules);
        return this;
    }
    public SimpleRuleSession addObject(Object _obj){
        ((MatchingContextImpl)matchingContext).addFacts(_obj);
        return this;
    }

    public synchronized void execute() {
        rules.stream().forEach(r->r.reset());

        boolean exec;
        do {
            exec = false;
            final Iterator<SimpleRule> iterator = rules.iterator();
            while (iterator.hasNext()) {
                SimpleRule rule = iterator.next();
                exec |= rule.execute((MatchingContextImpl) matchingContext);
            }
        } while (exec);
    }


}
