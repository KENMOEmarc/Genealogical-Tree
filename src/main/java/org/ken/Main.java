package org.ken;

import org.ken.enums.Kinship;
import org.ken.enums.Sex;
import org.ken.models.Person;

public class Main {

    public static void main(String[] args) {

        // ==============================
        // 1️⃣ Create Grandparents
        // ==============================

        Person grandFather = new Person(Kinship.GRAND_PARENT, Sex.MALE, 78, "George");
        Person grandMother = new Person(Kinship.GRAND_PARENT, Sex.FEMALE, 72, "Martha");

        System.out.println("===== GRAND PARENTS CREATED =====");

        // ==============================
        // 2️⃣ Create Their 4 Children
        // ==============================

        Person child1 = new Person(Kinship.PARENT, Sex.MALE, 50, "John");
        Person child2 = new Person(Kinship.PARENT, Sex.FEMALE, 48, "Anna");
        Person child3 = new Person(Kinship.PARENT, Sex.MALE, 45, "David");
        Person child4 = new Person(Kinship.PARENT, Sex.FEMALE, 42, "Sarah");

        // Link children to grandparents
        grandFather.addChild(child1);
        grandMother.addChild(child1);

        grandFather.addChild(child2);
        grandMother.addChild(child2);

        grandFather.addChild(child3);
        grandMother.addChild(child3);

        grandFather.addChild(child4);
        grandMother.addChild(child4);

        System.out.println("===== 4 CHILDREN CREATED =====");

        // ==============================
        // 3️⃣ Each child has 3 children
        // ==============================

        createChildren(child1, "Chris", "Emma", "Lucas");
        createChildren(child2, "Olivia", "Noah", "Liam");
        createChildren(child3, "Sophia", "Mason", "James");
        createChildren(child4, "Isabella", "Ethan", "Mia");

        System.out.println("===== GRAND CHILDREN CREATED =====");

        // ==============================
        // 4️⃣ Display Informations
        // ==============================

        System.out.println("\n===== DISPLAY GRANDPARENTS INFO =====");
        grandFather.displayInformation();
        grandMother.displayInformation();

        System.out.println("\n===== DISPLAY CHILD INFO =====");
        child1.displayInformation();

        System.out.println("\nSiblings of John: " + child1.getSiblingsNames());

        System.out.println("\nChildren of John: " + child1.getChildrenNames());

        System.out.println("\n===== TEST COUSINS =====");
        System.out.println("Cousins of Chris: " +
                child1.getChildren().get(0).getCousinsNames());

        System.out.println("\n===== TEST UNCLES & AUNTS =====");
        System.out.println("Uncles/Aunts of Chris: " +
                child1.getChildren().get(0).getUnclesAndAuntNames());

        System.out.println("\n===== TEST NIBLINGS =====");
        System.out.println("Niblings of Anna: " + child2.getNiblingNames());

        System.out.println("\n===== TO STRING TEST =====");
        System.out.println(child1);
    }


    private static void createChildren(Person parent, String name1, String name2, String name3) {

        Person c1 = new Person(Kinship.GRAND_CHILD, Sex.MALE, 20, name1);
        Person c2 = new Person(Kinship.GRAND_CHILD, Sex.FEMALE, 18, name2);
        Person c3 = new Person(Kinship.GRAND_CHILD, Sex.MALE, 15, name3);

        parent.addChild(c1);
        parent.addChild(c2);
        parent.addChild(c3);

        System.out.println("Children created for " + parent.getName());
    }
}