package org.ken;

import org.ken.enums.Kinship;
import org.ken.enums.Sex;
import org.ken.models.Biography;
import org.ken.models.Diploma;
import org.ken.models.Person;
import org.ken.models.PersonDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // Create individuals
        Person john = new Person(Kinship.PARENT, Sex.MALE, LocalDate.of(1970, 5, 10), "John");
        Person jane = new Person(Kinship.PARENT, Sex.FEMALE, LocalDate.of(1972, 8, 15), "Jane");
        Person bob = new Person(Kinship.CHILD, Sex.MALE, LocalDate.of(1995, 3, 20), "Bob");
        Person alice = new Person(Kinship.CHILD, Sex.FEMALE, LocalDate.of(1998, 7, 12), "Alice");
        Person charlie = new Person(Kinship.CHILD, Sex.MALE, LocalDate.of(2000, 11, 5), "Charlie");

        // Build relationships
        john.addChild(bob);
        john.addChild(alice);
        jane.addChild(bob);
        jane.addChild(alice);
        bob.addChild(charlie);  // Charlie is Bob's child

        // Set kinship roles after children are added (for display)
        bob.setKinship(Kinship.PARENT);
        charlie.setKinship(Kinship.GRAND_CHILD);

        // Biographies
        Biography johnBio = new Biography(
                List.of(new Diploma("PhD Computer Science", "MIT", "Summa Cum Laude", 1995)),
                List.of("Professor at Stanford", "Researcher at Google"),
                List.of("ACM", "IEEE"),
                "New York",
                List.of(Map.of("platform", "LinkedIn", "url", "linkedin.com/in/john"))
        );
        john.setBiography(johnBio);

        Biography bobBio = new Biography(
                List.of(new Diploma("BSc Software Engineering", "University of Cambridge", "First Class", 2017)),
                List.of("Senior Developer at Microsoft"),
                List.of("Open Source Collective"),
                "London",
                List.of(Map.of("platform", "GitHub", "username", "bob-dev"))
        );
        bob.setBiography(bobBio);

        Biography charlieBio = new Biography(
                List.of(),
                List.of(),
                List.of("Chess Club"),
                "Seattle",
                List.of()
        );
        charlie.setBiography(charlieBio);

        // Test basic info
        System.out.println("=== Basic Information ===");
        System.out.println(john);
        System.out.println("John's age: " + john.getAge());
        System.out.println("Bob's age: " + bob.getAge());
        System.out.println("Charlie's alive? " + charlie.isAlive());

        // Family relationships
        System.out.println("\n=== Family Relationships ===");
        System.out.println("Bob's siblings: " + bob.getSiblingsNames());
        System.out.println("Bob's cousins: " + bob.getCousinsNames());
        System.out.println("Bob's uncles/aunts: " + bob.getUnclesAndAuntNames());
        System.out.println("Charlie's grandparents: " + charlie.getParentsNames());

        // Display information (polymorphic)
        System.out.println("\n=== Display Information ===");
        john.displayInformation();
        System.out.println();
        bob.displayInformation();
        System.out.println();
        charlie.displayInformation();

        // Biography access
        System.out.println("\n=== Biographies ===");
        System.out.println("John's diplomas: " + john.getBiography().diplomas());
        System.out.println("Bob's jobs: " + bob.getBiography().jobs());
        System.out.println("Charlie's associations: " + charlie.getBiography().associations());

        // DTO test
        System.out.println("\n=== Person DTO (John) ===");
        PersonDTO dto = new PersonDTO(john, john.getBiography());
        System.out.println("Name: " + dto.getName());
        System.out.println("Age: " + dto.getAge());
        System.out.println("Father: " + dto.getFatherName());
        System.out.println("Children: " + dto.getChildrenNames());
        System.out.println("Diplomas: " + dto.getDiplomas());
        System.out.println("Social Media: " + dto.getSocialMediaAccounts());

        // Validation demo (uncomment to see exceptions)
        // try {
        //     new Person(Kinship.CHILD, Sex.MALE, LocalDate.of(2050, 1, 1), "Future");
        // } catch (IllegalArgumentException e) {
        //     System.out.println("Validation caught: " + e.getMessage());
        // }
    }
}