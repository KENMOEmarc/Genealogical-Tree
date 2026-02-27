package org.ken.models;

import org.ken.contracts.Information;
import org.ken.enums.Kinship;
import org.ken.enums.Sex;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Human implements Information {

    private static final int MAX_AGE = 150;
    private static final int MIN_AGE = 0;

    protected String name;
    protected double age;
    protected Sex sex;
    protected Kinship  kinship;

    protected Human father;
    protected Human mother;
    protected List<Human> children;

    public Human() {
        this.sex = Sex.MALE;
        this.kinship = Kinship.CHILD;
        children = new ArrayList<>();
    }

    public Human(Kinship kinship, Sex sex, double age, String name) {
        validateParameters(name, age, sex, kinship);
        this.kinship = kinship;
        this.sex = sex;
        this.age = age;
        this.name = name;
    }

    private void validateParameters(String name, double age, Sex sex, Kinship kinship) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException(
                    String.format("Age must be between %d and %d", MIN_AGE, MAX_AGE)
            );
        }

        if (sex == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }

        if (kinship == null) {
            throw new IllegalArgumentException("Family status cannot be null");
        }
    }


    public Set<Human> getSiblings() {
        Set<Human> siblings = new HashSet<>();
        if (father != null) {
            for (Human child : father.getChildren()) {
                if (child != this) {
                    siblings.add(child);
                }
            }
        }
        if (mother != null) {
            for (Human child : mother.getChildren()) {
                if (child != this) {
                    siblings.add(child);
                }
            }
        }
        return Collections.unmodifiableSet(siblings);
    }

    public Set<Human> getCousins() {
        var cousins = getUnclesAndAunts().stream().
                map(Human::getChildren).flatMap(Collection::stream).
                collect(Collectors.toSet() );
        return Collections.unmodifiableSet(cousins);
    }

    public List<Human> getNiblings() {
        List<Human> niblings = new ArrayList<>();
        for (Human sibling : getSiblings()) {
            niblings.addAll(sibling.getChildren());
        }
        return niblings;
    }

    public List<String> getCousinsNames() {
        return getCousins().stream().
                map(Human::getName).toList();
    }

    public List<String> getNiblingNames() {
        return getNiblings().stream().
                map(Human::getName).toList();
    }

    public Set<Human> getUnclesAndAunts() {
        Set<Human> unclesAndAunts = new HashSet<>();
        if (father != null) {
            unclesAndAunts.addAll(father.getSiblings());
        }
        if (mother != null) {
            unclesAndAunts.addAll(mother.getSiblings());
        }
        return Collections.unmodifiableSet(unclesAndAunts);
    }

    public List<String> getUnclesAndAuntNames() {
        return getUnclesAndAunts().stream().
                map(Human::getName).toList();
    }

    public List<String> getSiblingsNames() {
        List<String> siblingsNames = new ArrayList<>();
        for (Human person : getSiblings()) {
            siblingsNames.add(person.getName());
        }
        return siblingsNames;
    }

    public void  addChild(Human child) {
        children.add(child);
//        if (this.sex == Sex.MALE) {
//            child.setFather(this);
//        } else {
//            child.setMother(this);
//        }
    }

    public List<Human> getChildren() {
        return children;
    }

    public List<String> getChildrenNames() {
        List<String> childrenNames = new ArrayList<>();
        for (Human child : children) {
            childrenNames.add(child.getName());
        }
        return childrenNames;
    }

    public Human getFather() {
        return father;
    }

    public void setFather(Human father) {
        this.father = father;
        father.addChild(this);
    }

    public Human getMother() {
        return mother;
    }

    public void setMother(Human mother) {
        this.mother = mother;
        mother.addChild(this);
    }

    public String getParentsNames() {
        String motherName = (mother != null) ? mother.getName() : "Inconnue";
        String fatherName = (father != null) ? father.getName() : "Inconnu";
        return motherName + " " + fatherName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public double getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Kinship getKinship() {
        return kinship;
    }

    public void setKinship(Kinship kinship) {
        this.kinship = kinship;
    }

    @Override
    public void displayInformation() {

        switch (kinship) {
            case CHILD -> {
                getChildInfo();
            }
            case PARENT -> {
                getParentInfo();
            }
            case GRAND_CHILD -> {
                getChildInfo();
                System.out.println("I have grand-parents : " + father.getFather().getName() + "" + father.getMother().getName() +
                        " and " + mother.getFather().getName() + "" + mother.getMother().getName()
                );
            }
            case GRAND_PARENT -> {
                getParentInfo();
                System.out.println("I'm also a grand-parent");
            }
        }

    }

    private void getParentInfo() {
        System.out.println("Call me " + this.getName() + " I'm = " + age + " years old");
        System.out.println("I am the " +
                    ( (getSex() == Sex.MALE) ? "Father" : "Mother" ) + " of : " + getChildrenNames()
        );
    }

    private void getChildInfo() {

        System.out.println("My name is " + this.getName() + ", i'm = " + age + ", sex = " + sex );
        System.out.println("I am a child.");
        if (!getSiblings().isEmpty()) {
            System.out.println( "I've got some siblings : " + getSiblingsNames());
        } else {
            System.out.println("I don't have any sibling.");
        }
    }

    @Override
    public String toString() {
        return "Human{" +
                "name = '" + name + '\'' +
                ", age = " + age +
                ", sex = " + sex +
                ", kinship = " + kinship +
                ", parentsNames = " + getParentsNames() +
                '}';
    }
}
