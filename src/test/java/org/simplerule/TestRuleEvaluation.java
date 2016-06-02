package org.simplerule;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Created by jcgarciam has 5/23/16.
 */
public class TestRuleEvaluation {

    @Test
    public void test1() {
        SimpleRule rule1 =
                SimpleRule.define("Person and Car")
                        .allowLoop()
                        .when(c->c.has(Person.class, p->p.getName().equals("carlos") ))
                        .when(c->c.has(Car.class, car->car.getDoorsCount() == 2))
                        .then(ref -> {
                            System.out.println(String.format("Rule '%s' executed", ref.getRuleName()));
                            ref.first(Person.class).setName("Jose");
                        });

        SimpleRule rule2 =
                SimpleRule.define("Do we have a boolean?")
                        .when(c->c.has(Boolean.class))
                        .then(ref -> {
                            System.out.println(String.format("Rule '%s' executed", ref.getRuleName()));
                            System.out.println("    yeah we have a boolean!!!");
                        });

        SimpleRuleSession session = new SimpleRuleSession(new MatchingContextImpl());
        session.addObject(new Person("carlos","juan", new Car(2),new Car(5)))
                .addObject(new Person("carlos","juan"))
                .addObject(new Person("pedro","garcia"))
                .addObject(new Person("Andrei","C."))
                .addObject(new Boolean(false))
                .addObject(new Boolean(true));

        IntStream.range(1, 5).forEach(i->
                session.addObject(new Car(i))
        );

        session.addRule(rule1);
        session.addRule(rule2);

        for(int i = 0; i < 20; i++) {
            System.out.println("---Preparing session.execute-----");
            final Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            Long freemem = runtime.freeMemory();
            Long time = System.currentTimeMillis();
            session.execute();
            time = System.currentTimeMillis() - time;
            Long freemem_after = runtime.freeMemory();
            System.out.println(String.format("Took: %s ms , MemBefore: [%s] kb , MemAfter[%s] kb, MemUsed: [%s] kb ", time,
                    freemem / 1024,
                    freemem_after / 1024,
                    Math.abs((freemem - freemem_after) / 1024)));

        }
    }
}
