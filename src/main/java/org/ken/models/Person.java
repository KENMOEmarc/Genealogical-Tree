package org.ken.models;

import org.ken.contracts.Information;
import org.ken.enums.Sex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person extends Human implements Information {

    public Person() {
        super();
    }

    public Person(String name, Sex sex, double age) {
        this();
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public void greetings() {
        System.out.println("Hello I am a person named " + getName());
    }

}
