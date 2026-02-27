package org.ken;

import org.ken.enums.Sex;
import org.ken.models.Human;
import org.ken.models.Person;

public class Main {
    public static void main(String[] args) {
        Person father = new Person("John Smith", Sex.MALE, 37.5);
        Human mother = new Person("Elena Gomez", Sex.FEMALE, 34.0);

        Human jonathan;
        jonathan = new Person("Jonathan", Sex.MALE, 20.0);

        Human bernard = new Person("Bernard", Sex.MALE, 21);
        bernard.setMother(mother);
        bernard.setFather(father);

        mother.addChild(jonathan);
        //System.out.println(jonathan.getMother().getName());
        father.addChild(jonathan);

        father.displayInformation();
        mother.displayInformation();
        bernard.displayInformation();
    }
}