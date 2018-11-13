package kcnops.lubbinton.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class SetupTest {

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

	private Round roundOne = new Round(Collections.singletonList(matchOne), Collections.singletonList(geert));
	private Round roundTwo = new Round(Collections.singletonList(matchTwo), Collections.singletonList(smets));

	@Test
	public void testEquals() {
		Setup setup = new Setup(Collections.singletonList(roundOne));
		assertThat(setup.hashCode()).isEqualTo(setup.hashCode());
		assertThat(setup).isEqualTo(setup);
	}

	@Test
	public void testEqualsSame() {
		Setup setupOne = new Setup(Collections.singletonList(roundOne));
		Setup setupTwo = new Setup(Collections.singletonList(roundOne));
		assertThat(setupOne.hashCode()).isEqualTo(setupTwo.hashCode());
		assertThat(setupOne).isEqualTo(setupTwo);
	}

	@Test
	public void testEqualsSwitchedRounds() {
		Setup setupOne = new Setup(Arrays.asList(roundOne, roundTwo));
		Setup setupTwo = new Setup(Arrays.asList(roundTwo, roundOne));
		assertThat(setupOne.hashCode()).isEqualTo(setupTwo.hashCode());
		assertThat(setupOne).isEqualTo(setupTwo);
	}

	@Test
	public void testEqualsMixedRoundsShouldBeEqual() {
		Setup setupOne = new Setup(Arrays.asList(roundOne, roundTwo, roundOne));
		Setup setupTwo = new Setup(Arrays.asList(roundOne, roundOne, roundTwo));
		assertThat(setupOne.hashCode()).isEqualTo(setupTwo.hashCode());
		assertThat(setupOne).isEqualTo(setupTwo);
	}

	@Test
	public void testEqualsDifferentAmountOfTheSameRoundShouldNotBeEqual() {
		Setup setupOne = new Setup(Arrays.asList(roundOne, roundOne, roundTwo));
		Setup setupTwo = new Setup(Arrays.asList(roundOne, roundTwo, roundTwo));
		assertThat(setupOne.hashCode()).isNotEqualTo(setupTwo.hashCode());
		assertThat(setupOne).isNotEqualTo(setupTwo);
	}

}
