package org.ken;

import org.ken.enums.Sex;
import org.ken.models.Human;
import org.ken.models.Person;

public class Main {
    public static void main(String[] args) {
        Person father = new Person("John Smith", Sex.MALE, 37.5);
        Human mother = new Person("Elena Gomez", Sex.FEMALE, 34.0);

        Human human;
        human = new Person("Jonathan", Sex.MALE, 20.0);

        Human child = new Person("Bernard", Sex.MALE, 21);
        child.setMother(mother);
        child.setFather(father);

        mother.addChild(human);
        father.addChild(human);

        father.displayInformation();
        mother.displayInformation();
        child.displayInformation();
    }
}