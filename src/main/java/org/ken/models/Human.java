package org.ken.models;

import org.ken.contracts.Information;
import org.ken.enums.Kinship;
import org.ken.enums.Sex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Human implements Information {
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
        return siblings;
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
        return getMother().getName() + " " + getFather().getName();
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

    public void setKinship(Kinship kinship) {}

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
        if (getSiblings() != null) {
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
                ", father = " + father.getName() +
                ", mother = " + mother.getName() +
                '}';
    }
}
