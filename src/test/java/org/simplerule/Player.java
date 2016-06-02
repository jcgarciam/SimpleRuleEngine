/*
 *
 *
 */

package org.simplerule;

/**
 * Created by jcgarciam on 5/27/16.
 */
public class Player {
    private String name;
    private boolean ai;
    private char symbol;

    public Player(final String _name, final boolean _ai) {
        ai = _ai;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public boolean isAi() {
        return ai;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(final char _symbol) {
        symbol = _symbol;
    }
}
