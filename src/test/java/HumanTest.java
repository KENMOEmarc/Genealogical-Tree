import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ken.enums.Kinship;
import org.ken.enums.Sex;
import org.ken.models.Human;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    private TestHuman grandFather;
    private TestHuman grandMother;

    private TestHuman child1;
    private TestHuman child2;
    private TestHuman child3;
    private TestHuman child4;

    private TestHuman grandChild1;

    // Concrete class for testing
    static class TestHuman extends Human {
        public TestHuman(Kinship kinship, Sex sex, double age, String name) {
            super(kinship, sex, age, name);
        }
    }

    @BeforeEach
    void setUp() {

        // Grandparents
        grandFather = new TestHuman(Kinship.GRAND_PARENT, Sex.MALE, 75, "George");
        grandMother = new TestHuman(Kinship.GRAND_PARENT, Sex.FEMALE, 70, "Martha");

        // 4 children
        child1 = new TestHuman(Kinship.PARENT, Sex.MALE, 50, "John");
        child2 = new TestHuman(Kinship.PARENT, Sex.FEMALE, 48, "Anna");
        child3 = new TestHuman(Kinship.PARENT, Sex.MALE, 45, "David");
        child4 = new TestHuman(Kinship.PARENT, Sex.FEMALE, 42, "Sarah");

        // Link children to grandparents
        grandFather.addChild(child1);
        grandMother.addChild(child1);

        grandFather.addChild(child2);
        grandMother.addChild(child2);

        grandFather.addChild(child3);
        grandMother.addChild(child3);

        grandFather.addChild(child4);
        grandMother.addChild(child4);

        // Add grandchildren to child1
        grandChild1 = new TestHuman(Kinship.GRAND_CHILD, Sex.MALE, 20, "Chris");
        child1.addChild(grandChild1);
    }

    // ===============================
    // Constructor & Validation Tests
    // ===============================

    @Test
    void shouldThrowExceptionIfNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, Sex.MALE, 20, ""));
    }

    @Test
    void shouldThrowExceptionIfAgeInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, Sex.MALE, 200, "Test"));
    }

    @Test
    void shouldThrowExceptionIfSexIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(Kinship.CHILD, null, 20, "Test"));
    }

    @Test
    void shouldThrowExceptionIfKinshipIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new TestHuman(null, Sex.MALE, 20, "Test"));
    }

    // ===============================
    // Relationship Tests
    // ===============================

    @Test
    void testAddChildSetsParentCorrectly() {
        assertEquals(grandFather, child1.getFather());
        assertEquals(grandMother, child1.getMother());
    }

    @Test
    void testGetChildren() {
        assertEquals(4, grandFather.getChildren().size());
        assertTrue(grandFather.getChildren().contains(child1));
    }

    @Test
    void testGetSiblings() {
        Set<Human> siblings = child1.getSiblings();
        assertEquals(3, siblings.size());
        assertTrue(siblings.contains(child2));
        assertTrue(siblings.contains(child3));
        assertTrue(siblings.contains(child4));
    }

    @Test
    void testGetUnclesAndAunts() {
        Set<Human> uncles = grandChild1.getUnclesAndAunts();
        assertEquals(3, uncles.size());
    }

    @Test
    void testGetCousins() {

        // Add children to child2 so Chris has cousins
        TestHuman cousin1 = new TestHuman(Kinship.GRAND_CHILD, Sex.FEMALE, 18, "Olivia");
        child2.addChild(cousin1);

        Set<Human> cousins = grandChild1.getCousins();

        assertEquals(1, cousins.size());
        assertTrue(cousins.contains(cousin1));
    }

    @Test
    void testGetNiblings() {

        // Add children to child2
        TestHuman nephew = new TestHuman(Kinship.GRAND_CHILD, Sex.MALE, 15, "Noah");
        child2.addChild(nephew);

        assertEquals(1, child1.getNiblings().size());
        assertTrue(child1.getNiblings().contains(nephew));
    }

    @Test
    void testGetParentsNames() {
        String parents = child1.getParentsNames();
        assertTrue(parents.contains("George"));
        assertTrue(parents.contains("Martha"));
    }

    @Test
    void testToString() {
        String result = child1.toString();
        assertTrue(result.contains("John"));
        assertTrue(result.contains("PARENT"));
    }

    @Test
    void testDisplayInformationDoesNotThrow() {
        assertDoesNotThrow(() -> child1.displayInformation());
    }

    @Test
    void siblingsSetShouldBeUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () ->
                child1.getSiblings().add(child2)
        );
    }

    @Test
    void shouldReturnEmptySiblingsIfNoParents() {
        TestHuman orphan = new TestHuman(Kinship.CHILD, Sex.MALE, 10, "Orphan");
        assertTrue(orphan.getSiblings().isEmpty());
    }

    @Test
    void shouldNotAddSameChildTwice() {
        grandFather.addChild(child1);
        grandFather.addChild(child1);

        assertEquals(4, grandFather.getChildren().size());
    }

    @Test
    void shouldReturnCorrectChildrenNames() {
        assertTrue(grandFather.getChildrenNames().contains("John"));
    }
}