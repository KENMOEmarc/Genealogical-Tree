package org.ken.models;

import org.ken.enums.Sex;
import org.ken.enums.Kinship;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonDTO {

    private final String name;
    private final LocalDate birthDate;
    private final LocalDate deathDate;
    private final int age;               // computed from dates
    private final boolean alive;
    private final Sex sex;
    private final Kinship kinship;

    private final List<Diploma> diplomas;   // year → diploma
    private final List<String> jobs;
    private final List<String> associations;
    private final String birthPlace;
    private final List<Map<String, String>> socialMediaAccounts;

    private final String fatherName;
    private final String motherName;
    private final List<String> childrenNames;
    private final List<String> siblingsNames;
    private final List<String> cousinsNames;
    private final List<String> niblingsNames;
    private final List<String> unclesAndAuntsNames;


    public PersonDTO(Human human, Biography bio) {

        this.name = human.getName();
        this.birthDate = human.getBirthDate();
        this.deathDate = human.getDeathDate();
        this.age = human.getAge();
        this.alive = human.isAlive();
        this.sex = human.getSex();
        this.kinship = human.getKinship();

        if (bio != null) {
            this.diplomas = bio.diplomas().stream()
                    .collect(Collectors.toUnmodifiableList());
            this.jobs = List.copyOf(bio.jobs());
            this.associations = List.copyOf(bio.associations());
            this.birthPlace = bio.birthPlace();
            this.socialMediaAccounts = bio.socialMediaAccounts().stream()
                    .map(Map::copyOf)
                    .collect(Collectors.toUnmodifiableList());
        } else {
            this.diplomas = List.of();
            this.jobs = List.of();
            this.associations = List.of();
            this.birthPlace = null;
            this.socialMediaAccounts = List.of();
        }

        this.fatherName = (human.getFather() != null) ? human.getFather().getName() : null;
        this.motherName = (human.getMother() != null) ? human.getMother().getName() : null;
        this.childrenNames = List.copyOf(human.getChildrenNames());
        this.siblingsNames = List.copyOf(human.getSiblingsNames());
        this.cousinsNames = List.copyOf(human.getCousinsNames());
        this.niblingsNames = List.copyOf(human.getNiblingNames());
        this.unclesAndAuntsNames = List.copyOf(human.getUnclesAndAuntNames());
    }

    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public LocalDate getDeathDate() { return deathDate; }
    public int getAge() { return age; }
    public boolean isAlive() { return alive; }
    public Sex getSex() { return sex; }
    public Kinship getKinship() { return kinship; }
    public List<Diploma> getDiplomas() { return diplomas; }
    public List<String> getJobs() { return jobs; }
    public List<String> getAssociations() { return associations; }
    public String getBirthPlace() { return birthPlace; }
    public List<Map<String, String>> getSocialMediaAccounts() { return socialMediaAccounts; }
    public String getFatherName() { return fatherName; }
    public String getMotherName() { return motherName; }
    public List<String> getChildrenNames() { return childrenNames; }
    public List<String> getSiblingsNames() { return siblingsNames; }
    public List<String> getCousinsNames() { return cousinsNames; }
    public List<String> getNiblingsNames() { return niblingsNames; }
    public List<String> getUnclesAndAuntsNames() { return unclesAndAuntsNames; }
}