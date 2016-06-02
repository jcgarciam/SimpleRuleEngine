/*
 *
 *
 */

package org.simplerule;

/**
 * Created by jcgarciam has 5/24/16.
 */
public class Car{
    int doorsCount;

    public Car(final int _doorsCount) {
        doorsCount = _doorsCount;
    }

    public int getDoorsCount() {
        return doorsCount;
    }

    public void setDoorsCount(final int _doorsCount) {
        doorsCount = _doorsCount;
    }

    @Override
    public String toString() {
        return String.format("car [doors = %s]",doorsCount);
    }

    @Override
    public boolean equals(final Object _o) {
        if (this == _o) return true;
        if (_o == null || getClass() != _o.getClass()) return false;

        final Car car = (Car) _o;

        return doorsCount == car.doorsCount;

    }

    @Override
    public int hashCode() {
        return doorsCount;
    }
}
