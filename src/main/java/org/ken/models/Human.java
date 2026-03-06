package org.ken.models;

import org.ken.contracts.Information;
import org.ken.enums.Kinship;
import org.ken.enums.Sex;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Human implements Information {

    protected String name;
    protected LocalDate birthDate;
    protected LocalDate deathDate; // null if alive
    protected Sex sex;
    protected Kinship kinship;

    protected Biography biography; // optional biographical details

    protected Human father;
    protected Human mother;
    protected List<Human> children;

    public Human() {
        this.sex = Sex.MALE;
        this.kinship = Kinship.CHILD;
        this.children = new ArrayList<>();
    }

    public Human(Kinship kinship, Sex sex, LocalDate birthDate, String name) {
        this(kinship, sex, birthDate, null, name);
    }

    public Human(Kinship kinship, Sex sex, LocalDate birthDate, LocalDate deathDate, String name) {
        validateParameters(name, birthDate, deathDate, sex, kinship);
        this.kinship = kinship;
        this.sex = sex;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.name = name;
        this.children = new ArrayList<>();
    }

    private void validateParameters(String name, LocalDate birthDate, LocalDate deathDate,
                                    Sex sex, Kinship kinship) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        if (deathDate != null) {
            if (deathDate.isBefore(birthDate)) {
                throw new IllegalArgumentException("Death date cannot be before birth date");
            }
            if (deathDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Death date cannot be in the future");
            }
        }

        if (sex == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }

        if (kinship == null) {
            throw new IllegalArgumentException("Family status cannot be null");
        }
    }

    //Family relationship methods

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
        var cousins = getUnclesAndAunts().stream()
                .map(Human::getChildren)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
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
        return getCousins().stream()
                .map(Human::getName)
                .toList();
    }

    public List<String> getNiblingNames() {
        return getNiblings().stream()
                .map(Human::getName)
                .toList();
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
        return getUnclesAndAunts().stream()
                .map(Human::getName)
                .toList();
    }

    public List<String> getSiblingsNames() {
        return getSiblings().stream()
                .map(Human::getName)
                .toList();
    }

    public void addChild(Human child) {
        linkParentChild(this, child);
    }

    private void linkParentChild(Human parent, Human child) {
        if (!parent.children.contains(child)) {
            parent.children.add(child);

            if (parent.sex == Sex.MALE) {
                child.father = parent;
            } else {
                child.mother = parent;
            }
        }
    }

    public List<Human> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public List<String> getChildrenNames() {
        return children.stream()
                .map(Human::getName)
                .toList();
    }

    public Human getFather() {
        return father;
    }

    protected void setFather(Human father) {
        this.father = father;
    }

    public Human getMother() {
        return mother;
    }

    protected void setMother(Human mother) {
        this.mother = mother;
    }

    public String getParentsNames() {
        String motherName = (mother != null) ? mother.getName() : "Inconnue";
        String fatherName = (father != null) ? father.getName() : "Inconnu";
        return motherName + " " + fatherName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        // Reuse validation logic
        validateParameters(this.name, birthDate, this.deathDate, this.sex, this.kinship);
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        validateParameters(this.name, this.birthDate, deathDate, this.sex, this.kinship);
        this.deathDate = deathDate;
    }

    public int getAgeAt(LocalDate referenceDate) {
        Objects.requireNonNull(referenceDate, "Reference date cannot be null");
        LocalDate end = (deathDate != null && deathDate.isBefore(referenceDate)) ? deathDate : referenceDate;
        return Period.between(birthDate, end).getYears();
    }

    public int getAge() {
        return getAgeAt(LocalDate.now());
    }

    public boolean isAlive() {
        return deathDate == null;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        if (sex == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }
        this.sex = sex;
    }

    public Kinship getKinship() {
        return kinship;
    }

    public void setKinship(Kinship kinship) {
        if (kinship == null) {
            throw new IllegalArgumentException("Family status cannot be null");
        }
        this.kinship = kinship;
    }


    @Override
    public void displayInformation() {
        switch (kinship) {
            case CHILD -> getChildInfo();
            case PARENT -> getParentInfo();
            case GRAND_CHILD -> {
                getChildInfo();
                // Add null checks for grandparents
                String paternalGrandfather = (father != null && father.getFather() != null) ? father.getFather().getName() : "Unknown";
                String paternalGrandmother = (father != null && father.getMother() != null) ? father.getMother().getName() : "Unknown";
                String maternalGrandfather = (mother != null && mother.getFather() != null) ? mother.getFather().getName() : "Unknown";
                String maternalGrandmother = (mother != null && mother.getMother() != null) ? mother.getMother().getName() : "Unknown";
                System.out.println("I have grandparents: " + paternalGrandfather + " " + paternalGrandmother +
                        " and " + maternalGrandfather + " " + maternalGrandmother);
            }
            case GRAND_PARENT -> {
                getParentInfo();
                System.out.println("I'm also a grand-parent");
            }
        }
    }

    private void getParentInfo() {
        System.out.println("Call me " + this.getName() + " I'm " + getAge() + " years old");
        System.out.println("I am the " + (getSex() == Sex.MALE ? "Father" : "Mother") + " of: " + getChildrenNames());
    }

    private void getChildInfo() {
        System.out.println("My name is " + this.getName() + ", I'm " + getAge() + " years old, sex = " + sex);
        System.out.println("I am a child.");
        if (!getSiblings().isEmpty()) {
            System.out.println("I've got some siblings: " + getSiblingsNames());
        } else {
            System.out.println("I don't have any sibling.");
        }
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                (deathDate != null ? ", deathDate=" + deathDate : ", alive") +
                ", sex=" + sex +
                ", kinship=" + kinship +
                ", parentsNames=" + getParentsNames() +
                '}';
    }
}