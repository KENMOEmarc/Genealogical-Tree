//package org.ken.models;

import org.junit.jupiter.api.Test;
import org.ken.enums.Kinship;
import org.ken.enums.Sex;
import org.ken.models.Human;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    // Concrete subclass for testing
    static class TestHuman extends Human {
        public TestHuman(Kinship kinship, Sex sex, double age, String name) {
            super(kinship, sex, age, name);
        }
        public TestHuman() {
            super();
        }
    }

    // ---------- Constructor validation ----------
    @Test
    void constructor_ValidParameters_ShouldCreateInstance() {
        TestHuman human = new TestHuman(Kinship.CHILD, Sex.MALE, 25, "John");
        assertAll(
                () -> assertEquals("John", human.getName()),
                () -> assertEquals(25, human.getAge()),
                () -> assertEquals(Sex.MALE, human.getSex()),
                () -> assertEquals(Kinship.CHILD, human.getKinship())
        );
    }

    @Test
    void constructor_EmptyName_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, Sex.MALE, 25, "   "));
        assertEquals("Name cannot be empty", ex.getMessage());
    }

    @Test
    void constructor_NullName_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, Sex.MALE, 25, null));
        assertEquals("Name cannot be empty", ex.getMessage());
    }

    @Test
    void constructor_AgeBelowMin_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, Sex.MALE, -1, "John"));
        assertEquals("Age must be between 0 and 150", ex.getMessage());
    }

    @Test
    void constructor_AgeAboveMax_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, Sex.MALE, 151, "John"));
        assertEquals("Age must be between 0 and 150", ex.getMessage());
    }

    @Test
    void constructor_NullSex_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, null, 25, "John"));
        assertEquals("Sex cannot be null", ex.getMessage());
    }

    @Test
    void constructor_NullKinship_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(null, Sex.MALE, 25, "John"));
        assertEquals("Family status cannot be null", ex.getMessage());
    }

    // ---------- addChild() ----------
    @Test
    void addChild_WithFather_ShouldSetFatherAndAddToChildren() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Son");

        father.addChild(child);

        assertAll(
                () -> assertTrue(father.getChildren().contains(child)),
                () -> assertEquals(father, child.getFather()),
                () -> assertNull(child.getMother())
        );
    }

    @Test
    void addChild_WithMother_ShouldSetMotherAndAddToChildren() {
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.FEMALE, 8, "Daughter");

        mother.addChild(child);

        assertAll(
                () -> assertTrue(mother.getChildren().contains(child)),
                () -> assertEquals(mother, child.getMother()),
                () -> assertNull(child.getFather())
        );
    }

    @Test
    void addChild_NullChild_ShouldThrowException() {
        TestHuman parent = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> parent.addChild(null));
        assertEquals("The children cannot be null", ex.getMessage());
    }

    @Test
    void addChild_DuplicateChild_ShouldNotAddAgain() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Son");

        father.addChild(child);
        father.addChild(child); // duplicate

        assertEquals(1, father.getChildren().size());
    }

    // ---------- setFather() / setMother() ----------
    @Test
    void setFather_ShouldSetBidirectionalRelation() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Son");

        child.setFather(father);

        assertAll(
                () -> assertEquals(father, child.getFather()),
                () -> assertTrue(father.getChildren().contains(child))
        );
    }

    @Test
    void setMother_ShouldSetBidirectionalRelation() {
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.FEMALE, 8, "Daughter");

        child.setMother(mother);

        assertAll(
                () -> assertEquals(mother, child.getMother()),
                () -> assertTrue(mother.getChildren().contains(child))
        );
    }

    // ---------- getSiblings() ----------
    @Test
    void getSiblings_WithFullSiblings_ShouldReturnAll() {
        // Father and mother have two children
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman child1 = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child1");
        TestHuman child2 = new TestHuman(Kinship.CHILD, Sex.FEMALE, 8, "Child2");

        child1.setFather(father);
        child1.setMother(mother);
        child2.setFather(father);
        child2.setMother(mother);

        Set<Human> siblings = child1.getSiblings();
        assertEquals(1, siblings.size());
        assertTrue(siblings.contains(child2));
    }

    @Test
    void getSiblings_WithHalfSiblings_ShouldReturnAll() {
        // Father has two children with two different mothers
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 45, "Dad");
        TestHuman mother1 = new TestHuman(Kinship.PARENT, Sex.FEMALE, 40, "Mom1");
        TestHuman mother2 = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom2");
        TestHuman child1 = new TestHuman(Kinship.CHILD, Sex.MALE, 15, "Child1");
        TestHuman child2 = new TestHuman(Kinship.CHILD, Sex.FEMALE, 12, "Child2");

        child1.setFather(father);
        child1.setMother(mother1);
        child2.setFather(father);
        child2.setMother(mother2);

        Set<Human> siblings = child1.getSiblings();
        assertEquals(1, siblings.size());
        assertTrue(siblings.contains(child2));
    }

    @Test
    void getSiblings_OnlyChild_ShouldReturnEmptySet() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman onlyChild = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Only");

        onlyChild.setFather(father);
        onlyChild.setMother(mother);

        assertTrue(onlyChild.getSiblings().isEmpty());
    }

    @Test
    void getSiblings_ReturnsUnmodifiableSet() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman child1 = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child1");
        TestHuman child2 = new TestHuman(Kinship.CHILD, Sex.FEMALE, 8, "Child2");

        child1.setFather(father);
        child1.setMother(mother);
        child2.setFather(father);
        child2.setMother(mother);

        Set<Human> siblings = child1.getSiblings();
        assertThrows(UnsupportedOperationException.class, () -> siblings.add(child1));
    }

    // ---------- getUnclesAndAunts() ----------
    @Test
    void getUnclesAndAunts_ShouldReturnParentsSiblings() {
        // Grandparents have two children: Father and Uncle
        TestHuman grandpa = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 70, "Grandpa");
        TestHuman grandma = new TestHuman(Kinship.GRAND_PARENT, Sex.FEMALE, 68, "Grandma");
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman uncle = new TestHuman(Kinship.PARENT, Sex.MALE, 38, "Uncle");

        father.setFather(grandpa);
        father.setMother(grandma);
        uncle.setFather(grandpa);
        uncle.setMother(grandma);

        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child");
        child.setFather(father);

        Set<Human> unclesAunts = child.getUnclesAndAunts();
        assertEquals(1, unclesAunts.size());
        assertTrue(unclesAunts.contains(uncle));
    }

    @Test
    void getUnclesAndAunts_NoParentSiblings_ShouldReturnEmpty() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child");
        child.setFather(father);
        child.setMother(mother);

        assertTrue(child.getUnclesAndAunts().isEmpty());
    }

    // ---------- getCousins() ----------
    @Test
    void getCousins_ShouldReturnChildrenOfUnclesAndAunts() {
        // Grandparents: Grandpa, Grandma
        // Their children: Father, Aunt
        // Aunt's child: Cousin
        TestHuman grandpa = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 70, "Grandpa");
        TestHuman grandma = new TestHuman(Kinship.GRAND_PARENT, Sex.FEMALE, 68, "Grandma");
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman aunt = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Aunt");
        TestHuman cousin = new TestHuman(Kinship.CHILD, Sex.MALE, 12, "Cousin");

        father.setFather(grandpa);
        father.setMother(grandma);
        aunt.setFather(grandpa);
        aunt.setMother(grandma);
        cousin.setMother(aunt);  // aunt is mother

        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child");
        child.setFather(father);

        Set<Human> cousins = child.getCousins();
        assertEquals(1, cousins.size());
        assertTrue(cousins.contains(cousin));
    }

    // ---------- getNiblings() ----------
    @Test
    void getNiblings_ShouldReturnChildrenOfSiblings() {
        // Parents: Father, Mother
        // Their children: Child1 (aunt/uncle), Child2 (parent of niblings)
        // Child2's children: Nephew
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 50, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 48, "Mom");
        TestHuman child1 = new TestHuman(Kinship.PARENT, Sex.MALE, 25, "Uncle");
        TestHuman child2 = new TestHuman(Kinship.PARENT, Sex.FEMALE, 22, "MomOfNephew");
        TestHuman nephew = new TestHuman(Kinship.CHILD, Sex.MALE, 2, "Nephew");

        child1.setFather(father);
        child1.setMother(mother);
        child2.setFather(father);
        child2.setMother(mother);
        nephew.setMother(child2);

        List<Human> niblings = child1.getNiblings();
        assertEquals(1, niblings.size());
        assertTrue(niblings.contains(nephew));
    }

    // ---------- getParentsNames() ----------
    @Test
    void getParentsNames_BothParentsPresent_ShouldReturnBoth() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "John Sr.");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Jane");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "John Jr.");
        child.setFather(father);
        child.setMother(mother);

        assertEquals("Jane John Sr.", child.getParentsNames());
    }

    @Test
    void getParentsNames_MotherMissing_ShouldReturnUnknown() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "John Sr.");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "John Jr.");
        child.setFather(father);

        assertEquals("Inconnue John Sr.", child.getParentsNames());
    }

    @Test
    void getParentsNames_FatherMissing_ShouldReturnUnknown() {
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Jane");
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "John Jr.");
        child.setMother(mother);

        assertEquals("Jane Inconnu", child.getParentsNames());
    }

    // ---------- getChildrenNames() ----------
    @Test
    void getChildrenNames_ShouldReturnListOfNames() {
        TestHuman parent = new TestHuman(Kinship.PARENT, Sex.MALE, 45, "Dad");
        TestHuman child1 = new TestHuman(Kinship.CHILD, Sex.MALE, 15, "Son");
        TestHuman child2 = new TestHuman(Kinship.CHILD, Sex.FEMALE, 12, "Daughter");
        parent.addChild(child1);
        parent.addChild(child2);

        List<String> names = parent.getChildrenNames();
        assertTrue(names.containsAll(List.of("Son", "Daughter")));
        assertEquals(2, names.size());
    }

    // ---------- getSiblingsNames() ----------
    @Test
    void getSiblingsNames_ShouldReturnListOfNames() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        TestHuman child1 = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child1");
        TestHuman child2 = new TestHuman(Kinship.CHILD, Sex.FEMALE, 8, "Child2");
        child1.setFather(father);
        child1.setMother(mother);
        child2.setFather(father);
        child2.setMother(mother);

        List<String> names = child1.getSiblingsNames();
        assertEquals(1, names.size());
        assertEquals("Child2", names.get(0));
    }

    // ---------- getUnclesAndAuntNames() ----------
    @Test
    void getUnclesAndAuntNames_ShouldReturnNames() {
        TestHuman grandpa = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 70, "Grandpa");
        TestHuman grandma = new TestHuman(Kinship.GRAND_PARENT, Sex.FEMALE, 68, "Grandma");
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman aunt = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Aunt");
        father.setFather(grandpa);
        father.setMother(grandma);
        aunt.setFather(grandpa);
        aunt.setMother(grandma);
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child");
        child.setFather(father);

        List<String> names = child.getUnclesAndAuntNames();
        assertEquals(1, names.size());
        assertEquals("Aunt", names.get(0));
    }

    // ---------- getCousinsNames() ----------
    @Test
    void getCousinsNames_ShouldReturnNames() {
        TestHuman grandpa = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 70, "Grandpa");
        TestHuman grandma = new TestHuman(Kinship.GRAND_PARENT, Sex.FEMALE, 68, "Grandma");
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman aunt = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Aunt");
        TestHuman cousin = new TestHuman(Kinship.CHILD, Sex.MALE, 12, "Cousin");
        father.setFather(grandpa);
        father.setMother(grandma);
        aunt.setFather(grandpa);
        aunt.setMother(grandma);
        cousin.setMother(aunt);
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child");
        child.setFather(father);

        List<String> names = child.getCousinsNames();
        assertEquals(1, names.size());
        assertEquals("Cousin", names.get(0));
    }

    // ---------- getNiblingNames() ----------
    @Test
    void getNiblingNames_ShouldReturnNames() {
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 50, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 48, "Mom");
        TestHuman sibling = new TestHuman(Kinship.PARENT, Sex.FEMALE, 22, "Aunt");
        TestHuman nibling = new TestHuman(Kinship.CHILD, Sex.MALE, 2, "Nephew");
        sibling.setFather(father);
        sibling.setMother(mother);
        nibling.setMother(sibling);
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 25, "Uncle");
        child.setFather(father);
        child.setMother(mother);

        List<String> names = child.getNiblingNames();
        assertEquals(1, names.size());
        assertEquals("Nephew", names.get(0));
    }

    // ---------- toString() ----------
    @Test
    void toString_ShouldContainName() {
        TestHuman human = new TestHuman(Kinship.CHILD, Sex.MALE, 25, "John");
        String str = human.toString();
        assertTrue(str.contains("John"));
        assertTrue(str.contains("CHILD"));
        assertTrue(str.contains("MALE"));
    }

    // ---------- displayInformation() ----------
    @Test
    void displayInformation_ShouldNotThrow() {
        TestHuman child = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Child");
        assertDoesNotThrow(() -> child.displayInformation());

        TestHuman parent = new TestHuman(Kinship.PARENT, Sex.FEMALE, 40, "Parent");
        assertDoesNotThrow(() -> parent.displayInformation());

        TestHuman grandChild = new TestHuman(Kinship.GRAND_CHILD, Sex.MALE, 5, "GrandChild");
        // Need grandparents to avoid NPE in grandChild display (father.getFather() etc.)
        TestHuman grandpa = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 70, "Grandpa");
        TestHuman grandma = new TestHuman(Kinship.GRAND_PARENT, Sex.FEMALE, 68, "Grandma");
        TestHuman father = new TestHuman(Kinship.PARENT, Sex.MALE, 40, "Dad");
        TestHuman mother = new TestHuman(Kinship.PARENT, Sex.FEMALE, 38, "Mom");
        father.setFather(grandpa);
        father.setMother(grandma);
        mother.setFather(grandpa);
        mother.setMother(grandma);
        grandChild.setFather(father);
        grandChild.setMother(mother);
        assertDoesNotThrow(() -> grandChild.displayInformation());

        TestHuman grandParent = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 70, "Grandpa");
        assertDoesNotThrow(() -> grandParent.displayInformation());
    }
}