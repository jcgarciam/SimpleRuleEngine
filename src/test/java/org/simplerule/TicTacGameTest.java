/*
 *
 *
 */

package org.simplerule;

import java.util.Scanner;

/**
 * Created by jcgarciam on 5/27/16.
 */
public class TicTacGameTest {

    public static void main(String[] args) {
        SimpleRule rule1 = SimpleRule
                .define("-GameStart-")
                .when(c -> c.has(TicTacTable.class, p -> p.getTableState().equals(TicTacTable.TicTacTableState.NOT_STARTED)))
                .then(ref -> {
                    System.out.println(ref.getRuleName());
                    final TicTacTable table = ref.first(TicTacTable.class);
                    table.startGame();
                });

        SimpleRule rule2 = SimpleRule
                .define("Time Slot for player")
                .allowLoop()
                .when(c -> c.has(TicTacTable.class, p -> p.getTableState().equals(TicTacTable.TicTacTableState.STARTED)))
                .then(ref -> {
                    System.out.println(ref.getRuleName());
                    final TicTacTable table = ref.first(TicTacTable.class);
                    final Player current = table.getCurrentPlayer();

                    int t = -1;
                    while (t < 0 || t > 8) {
                        System.out.println("[" + current.getName() + "] select your grid position: [0-8]?");
                        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
                        t = sc.nextInt();
                        if (!table.isAvailable(t)) {
                            System.out.println("position is already taken!");
                            t = -1;
                        }
                    }
                    table.mark(t);
                    table.switchUser();
                });


        SimpleRule rule30 = SimpleRule
                .define("Display Current Board")
                .allowLoop()
                .when(c -> c.has(TicTacTable.class, p -> p.getTableState().equals(TicTacTable.TicTacTableState.STARTED) && p.stillPendingMove()))
                .then(ref -> {
                    final TicTacTable table = ref.first(TicTacTable.class);
                    table.printBoard();
                });

        SimpleRule rule31 = SimpleRule
                .define("Check for Game Status")
                .allowLoop()
                .when(c -> c.has(TicTacTable.class, p -> p.getTableState().equals(TicTacTable.TicTacTableState.STARTED) && !p.stillPendingMove()))
                .then(ref -> {
                    System.out.println("Ending Game");
                    final TicTacTable table = ref.first(TicTacTable.class);
                    table.endGame();
                });


        SimpleRule rule4 = SimpleRule
                .define("EndGame")
                .when(c -> c.has(TicTacTable.class, p -> p.getTableState().equals(TicTacTable.TicTacTableState.FINISHED)))
                .then(ref -> {
                    System.out.println(ref.getRuleName());
                    final TicTacTable table = ref.first(TicTacTable.class);
                    table.printBoard();
                    System.out.println("The winner is: " + ref.first(TicTacTable.class).getWinner());
                });


        Player p1 = new Player("Juan Carlos", false);
        Player p2 = new Player("Other", false);
        TicTacTable table = new TicTacTable(p1, p2);
        SimpleRuleSession session = new SimpleRuleSession(new MatchingContextImpl());
        session.addObject(table);
        session.addObject(p1);
        session.addObject(p2);

        session.addRule(rule1);
        session.addRule(rule2);
//        session.addRule(rule3);
        session.addRule(rule30);
        session.addRule(rule31);
        session.addRule(rule4);

        //while(table.getTableState() != TicTacTable.TicTacTableState.FINISHED) {
        session.execute();
        //}
    }
}
