//package ex4_tests.ex4_2_tests;
//
//import ascii_art.Algorithms;
//import org.junit.jupiter.api.Test;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * <B>Tests for the ascii_art.Algorithms section,</B>
// * featured in Exercise 4 part 2 of the new "Introduction to OOP" course,
// * HUJI, Winter 2021-2022 Semester.
// * <p>
// * Special thanks to Michal Iviansky for pointing me to this website for the unique morse representations
// * tests:
// * <a href="https://datagenetics.com/blog/march42012/results.html">
// * https://datagenetics.com/blog/march42012/results.html
// * </a>
// *
// * @author Erel Debel.
// */
//class AlgorithmsTest {
//
//	@Test
//	void findDuplicateCase0Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{2, 3, 2, 2}));
//	}
//
//	@Test
//	void findDuplicateCase1Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{1, 2, 2}));
//	}
//
//
//	@Test
//	void findDuplicateCase2Test() {
//		assertEquals(1, Algorithms.findDuplicate(new int[]{1, 2, 1}));
//	}
//
//	@Test
//	void findDuplicateCase3Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{1, 2, 3, 2}));
//	}
//
//	@Test
//	void findDuplicateCase4Test() {
//		assertEquals(1, Algorithms.findDuplicate(new int[]{1, 2, 3, 1}));
//	}
//
//	@Test
//	void findDuplicateCase5Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{1, 2, 3, 4, 2}));
//	}
//
//	@Test
//	void findDuplicateCase6Test() {
//		assertEquals(1, Algorithms.findDuplicate(new int[]{1, 2, 3, 4, 1}));
//	}
//
//	@Test
//	void findDuplicateCase7Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{1, 3, 2, 4, 2}));
//	}
//
//	@Test
//	void findDuplicateCase8Test() {
//		assertEquals(1, Algorithms.findDuplicate(new int[]{1, 3, 2, 4, 1}));
//	}
//
//	@Test
//	void findDuplicateCase9Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{1, 2, 3, 4, 2}));
//	}
//
//	@Test
//	void findDuplicateCase10Test() {
//		assertEquals(4, Algorithms.findDuplicate(new int[]{4, 2, 3, 6, 7, 5, 4, 1}));
//	}
//
//	@Test
//	void findDuplicateCase11Test() {
//		assertEquals(3, Algorithms.findDuplicate(new int[]{1, 2, 3, 4, 3}));
//	}
//
//	@Test
//	void findDuplicateCase12Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{2, 5, 2, 3, 4, 1}));
//	}
//
//	@Test
//	void findDuplicateCase13Test() {
//		assertEquals(1, Algorithms.findDuplicate(new int[]{1, 1, 2, 3}));
//	}
//
//	@Test
//	void findDuplicateCase14Test() {
//		assertEquals(2, Algorithms.findDuplicate(new int[]{1, 3, 4, 2, 2}));
//	}
//
//
//	@Test
//	void UniqueMorseRepresentations1Test() {
//		assertEquals(1, Algorithms.uniqueMorseRepresentations(new String[]{"p", "an"}));
//	}
//
//	@Test
//	void UniqueMorseRepresentations2Test() {
//		assertEquals(2, Algorithms.uniqueMorseRepresentations(new String[]{"p", "a"}));
//	}
//
//	@Test
//	void UniqueMorseRepresentations3Test() {
//		assertEquals(2, Algorithms.uniqueMorseRepresentations(new String[]{"abba", "abba", "aba"}));
//	}
//
//	@Test
//	void UniqueMorseRepresentations4Test() {
//		assertEquals(1, Algorithms.uniqueMorseRepresentations(new String[]{"grease", "greenie", "greens"}));
//	}
//
//	@Test
//	void UniqueMorseRepresentations5Test() {
//		assertEquals(2, Algorithms.uniqueMorseRepresentations(
//						new String[]{"grease", "green", "greens"}),
//				"{grease, greens}, {green}");
//	}
//
//	@Test
//	void UniqueMorseRepresentations6Test() {
//		assertEquals(2, Algorithms.uniqueMorseRepresentations(
//						new String[]{"nil", "wits", "bed", "wide", "bene", "testee", "dui", "pas"}),
//				"{nil, bed, bene, testee, dui}, {wits, wide, pas}");
//	}
//
//	@Test
//	void UniqueMorseRepresentations7Test() {
//		assertEquals(1, Algorithms.uniqueMorseRepresentations(new String[]{"wits", "wide", "pas"}));
//	}
//
//	@Test
//	void UniqueMorseRepresentations8Test() {
//		assertEquals(4, Algorithms.uniqueMorseRepresentations(
//						new String[]{"keeners", "gasts", "kerfs", "teepees", "abbe"}),
//				"{keeners, kerfs}, {gasts}, {teepees}, {abbe}");
//	}
//}
