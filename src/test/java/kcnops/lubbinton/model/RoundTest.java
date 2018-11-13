package kcnops.lubbinton.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class RoundTest {

	private Player kristof = new Player("Kristof");
	private Player thomas = new Player("Thomas");
	private Player lucas = new Player("Lucas");
	private Player smets = new Player("Smets");
	private Player geert = new Player("Geert");

	private Side sideOne = new Side(kristof, thomas);
	private Side sideTwo = new Side(lucas, smets);
	private Side sideThree = new Side(lucas, geert);

	private Match matchOne = new Match(sideOne, sideTwo);
	private Match matchTwo = new Match(sideOne, sideThree);
	private Match matchOneSwitched = new Match(sideTwo, sideOne);

	@Test
	public void testEquals() {
		Round roundOne = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
		assertThat(roundOne.hashCode()).isEqualTo(roundOne.hashCode());
		assertThat(roundOne).isEqualTo(roundOne);
	}

	@Test
	public void testEqualsSame() {
		Round roundOne = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
		Round roundTwo = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
		assertThat(roundOne.hashCode()).isEqualTo(roundTwo.hashCode());
		assertThat(roundOne).isEqualTo(roundTwo);
	}

	@Test
	public void testEqualsSwitchedSides() {
		Round roundOne = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
		Round roundTwo = new Round(Collections.singletonList(matchOneSwitched), Collections.singletonList(geert));
		assertThat(roundOne.hashCode()).isEqualTo(roundTwo.hashCode());
		assertThat(roundOne).isEqualTo(roundTwo);
	}

	@Test
	public void testEqualsSwitchedMatches() {
		Round roundOne = new Round(Arrays.asList(matchOne, matchTwo), Collections.singletonList(smets));
		Round roundTwo = new Round(Arrays.asList(matchTwo, matchOne), Collections.singletonList(smets));
		assertThat(roundOne.hashCode()).isEqualTo(roundTwo.hashCode());
		assertThat(roundOne).isEqualTo(roundTwo);
	}

	@Test
	public void testEqualsSwitchedRest() {
		Round roundOne = new Round(Collections.singletonList(matchOne), Arrays.asList(smets, geert));
		Round roundTwo = new Round(Collections.singletonList(matchOne), Arrays.asList(geert, smets));
		assertThat(roundOne.hashCode()).isEqualTo(roundTwo.hashCode());
		assertThat(roundOne).isEqualTo(roundTwo);
	}

	@Test
	public void testEqualsNotEqualMatches() {
		Round roundOne = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
		Round roundTwo = new Round(Collections.singletonList(matchTwo), Collections.singletonList(geert));
		assertThat(roundOne.hashCode()).isNotEqualTo(roundTwo.hashCode());
		assertThat(roundOne).isNotEqualTo(roundTwo);
	}

	@Test
	public void testEqualsNotEqualRest() {
		Round roundOne = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
		Round roundTwo = new Round(Collections.singletonList(matchOne), Collections.singletonList(smets));
		assertThat(roundOne.hashCode()).isNotEqualTo(roundTwo.hashCode());
		assertThat(roundOne).isNotEqualTo(roundTwo);
	}

}
