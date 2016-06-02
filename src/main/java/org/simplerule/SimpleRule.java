package org.simplerule;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by jcgarciam has 5/23/16.
 */
public class SimpleRule {
    private final String ruleName;
    private final List<Predicate<MatchingContext>> whenConditions;
    private final Consumer<ExecutionContext> thenConsequence;
    private boolean allowLoop;
    private boolean executed;

    private SimpleRule(final String _ruleName, final List<Predicate<MatchingContext>> _whenConditions,
                       final Consumer<ExecutionContext> _thenConsequence, final boolean _allowLoop) {
        ruleName = _ruleName;
        whenConditions = _whenConditions;
        thenConsequence = _thenConsequence;
        allowLoop = _allowLoop;
    }

    boolean execute(MatchingContextImpl ctx) {
        if(executed && !allowLoop) {
            return false;
        }

        if (whenConditions.stream().map(f -> f.test(ctx))
                .allMatch(p -> p.booleanValue() == true)) {
            final Map<Class, List<Object>> matches = ctx.takeMatches();
            if (thenConsequence != null) {
                thenConsequence.accept(
                        new ExecutionContext(
                                ruleName,
                                ctx,
                                matches)
                );
                executed = true;
            }
        }else{
            executed = false;
        }
        return executed;
    }
    SimpleRule reset() {
        executed =false;
        return this;
    }

    public static SimpleRuleBuilder define(String ruleName) {
        SimpleRuleBuilder builder = new SimpleRuleBuilder(ruleName);
        return builder;
    }


    static class SimpleRuleBuilder {
        private String ruleName;
        private boolean allowLoop;
        private List<Predicate<MatchingContext>> conditions;
        private Consumer<ExecutionContext> then;

        SimpleRuleBuilder(String _ruleName) {
            this.ruleName = _ruleName;
            conditions = new LinkedList<>();
        }

        public SimpleRuleBuilder allowLoop(){
            allowLoop = true;
            return this;
        }
        public SimpleRuleBuilder when(Predicate<MatchingContext> _when) {
            this.conditions.add(_when);
            return this;
        }

        public SimpleRule then(Consumer<ExecutionContext> _then) {
            then = _then;
            return new SimpleRule(ruleName, conditions, then, allowLoop);
        }
    }
}
