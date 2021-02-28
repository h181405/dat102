package no.hvl.dat102;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat102.exception.EmptyCollectionException;
import no.hvl.dat102.mengde.adt.MengdeADT;

public abstract class MengdeADTTest {

	// Referanse til mengde 1 og 2
		private MengdeADT<String> m1;
		private MengdeADT<String> m2;
		private MengdeADT<String> m3;

		// Testdata
		private String e0 = "Bergen";
		private String e1 = "Oslo";
		private String e2 = "Trondheim";
		private String e3 = "Stavanger";
		private String e4 = "Kristiansand";

		protected abstract MengdeADT<String> reset();

		/**
		 * Hent en ny mengde for hver test.
		 * 
		 * @return
		 */
		@BeforeEach
		public void setup() {
			m1 = reset();
			m2 = reset();
			m3 = reset();
		}

		/**
		 * Test på at en ny mengde er tom.
		 */
		@Test
		public void nyStabelErTom() {
			assertTrue(m1.erTom());
			assertTrue(m2.erTom());
		}

		/**
		 * Test på union.
		 */
		@Test
		public void union() {

			m1.leggTil(e0);
			m1.leggTil(e1);
			m1.leggTil(e2);
			
			m2.leggTil(e2);
			m2.leggTil(e3);
			
			m3.leggTil(e0);
			m3.leggTil(e1);
			m3.leggTil(e2);
			m3.leggTil(e3);

			try {
				assertEquals(m1.union(m2), m3);
			} catch (EmptyCollectionException e) {
				fail("union feilet uventet " + e.getMessage());
			}
		}
		/**
		 * Test på snitt.
		 */
		@Test
		public void snitt() {

			m1.leggTil(e0);
			m1.leggTil(e1);
			m1.leggTil(e2);
			
			m2.leggTil(e2);
			m2.leggTil(e3);
			m2.leggTil(e4);
			
			m3.leggTil(e2);

			try {
				assertEquals(m1.snitt(m2), m3);
			} catch (EmptyCollectionException e) {
				fail("snitt feilet uventet " + e.getMessage());
			}
		}
		/**
		 * Test på differens.
		 */
		@Test
		public void differens() {

			m1.leggTil(e0);
			m1.leggTil(e1);
			m1.leggTil(e2);
			
			m2.leggTil(e2);
			m2.leggTil(e3);
			m2.leggTil(e4);
			
			m3.leggTil(e0);
			m3.leggTil(e1);

			try {
				assertEquals(m1.differens(m2), m3);
			} catch (EmptyCollectionException e) {
				fail("differens feilet uventet " + e.getMessage());
		}
	}
}
