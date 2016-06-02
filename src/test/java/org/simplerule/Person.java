package org.simplerule;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jcgarciam has 5/23/16.
 */
public class Person {
    String name;
    String lastName;
    List<Car> owningCars;

    public Person(String _name, String _lastName, Car... cars){
        name = _name;
        lastName = _lastName;
        owningCars = Arrays.asList(cars);
    }
    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public List<Car> getOwningCars() {
        return owningCars;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object _o) {
        if (this == _o) return true;
        if (_o == null || getClass() != _o.getClass()) return false;

        final Person person = (Person) _o;

        if (!name.equals(person.name)) return false;
        return lastName.equals(person.lastName);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    public void setName(final String _name) {
        name = _name;
    }
}
