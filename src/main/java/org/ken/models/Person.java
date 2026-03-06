package org.ken.models;

import org.ken.contracts.Information;
import org.ken.enums.Kinship;
import org.ken.enums.Sex;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person extends Human implements Information {

    public Person(Kinship kinship, Sex sex, LocalDate birthDate, String name) {
        super(kinship, sex,  birthDate, name);
    }
}
