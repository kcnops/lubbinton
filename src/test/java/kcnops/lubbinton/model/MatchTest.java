package kcnops.lubbinton.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class MatchTest {

	private Player kristof = new Player("Kristof");
	private Player thomas = new Player("Thomas");
	private Player lucas = new Player("Lucas");
	private Player smets = new Player("Smets");
	private Player bart = new Player("Bart");
	private Player yannick = new Player("Yannick");

	private Side sideOne = new Side(kristof, thomas);
	private Side sideTwo = new Side(lucas, smets);
	private Side sideThree = new Side(bart, yannick);

	private Match matchOne = new Match(sideOne, sideTwo);
	private Match matchOneSwitched = new Match(sideTwo, sideOne);
	private Match matchTwo = new Match(sideOne, sideThree);

	@Test
	public void testEquals() {
		assertThat(matchOne).isEqualTo(matchOne);
	}

	@Test
	public void testEqualsSame() {
		assertThat(matchOne.hashCode()).isEqualTo(matchOne.hashCode());
		assertThat(matchOne).isEqualTo(matchOne);
	}

	@Test
	public void testEqualsSwitched() {
		assertThat(matchOne.hashCode()).isEqualTo(matchOneSwitched.hashCode());
		assertThat(matchOne).isEqualTo(matchOneSwitched);
	}

	@Test
	public void testEqualsNotEquals() {
		assertThat(matchOne.hashCode()).isNotEqualTo(matchTwo.hashCode());
		assertThat(matchOne).isNotEqualTo(matchTwo);
	}

	@Test
	public void testDoesPlayWith() {
		assertThat(matchOne.doesPlayWith(kristof, thomas)).isTrue();
		assertThat(matchOne.doesPlayWith(thomas, kristof)).isTrue();
		assertThat(matchOne.doesPlayWith(lucas, smets)).isTrue();
		assertThat(matchOne.doesPlayWith(smets, lucas)).isTrue();
		assertThat(matchOne.doesPlayWith(kristof, lucas)).isFalse();
		assertThat(matchOne.doesPlayWith(kristof, smets)).isFalse();
		assertThat(matchOne.doesPlayWith(thomas, lucas)).isFalse();
		assertThat(matchOne.doesPlayWith(thomas, smets)).isFalse();
		assertThat(matchOneSwitched.doesPlayWith(kristof, thomas)).isTrue();
		assertThat(matchOneSwitched.doesPlayWith(lucas, smets)).isTrue();
		assertThat(matchOneSwitched.doesPlayWith(kristof, lucas)).isFalse();
		assertThat(matchTwo.doesPlayWith(kristof, thomas)).isTrue();
		assertThat(matchTwo.doesPlayWith(bart, yannick)).isTrue();
		assertThat(matchTwo.doesPlayWith(kristof, bart)).isFalse();
	}

	@Test
	public void testDoesPlayAgainst() {
		assertThat(matchOne.doesPlayAgainst(kristof, lucas)).isTrue();
		assertThat(matchOne.doesPlayAgainst(kristof, smets)).isTrue();
		assertThat(matchOne.doesPlayAgainst(thomas, lucas)).isTrue();
		assertThat(matchOne.doesPlayAgainst(thomas, smets)).isTrue();
		assertThat(matchOne.doesPlayAgainst(lucas, kristof)).isTrue();
		assertThat(matchOne.doesPlayAgainst(lucas, thomas)).isTrue();
		assertThat(matchOne.doesPlayAgainst(smets, kristof)).isTrue();
		assertThat(matchOne.doesPlayAgainst(smets, thomas)).isTrue();
		assertThat(matchOne.doesPlayAgainst(kristof, thomas)).isFalse();
		assertThat(matchOne.doesPlayAgainst(thomas, kristof)).isFalse();
		assertThat(matchOne.doesPlayAgainst(smets, lucas)).isFalse();
		assertThat(matchOne.doesPlayAgainst(lucas, smets)).isFalse();
		assertThat(matchOneSwitched.doesPlayAgainst(kristof, lucas)).isTrue();
		assertThat(matchOneSwitched.doesPlayAgainst(kristof, smets)).isTrue();
		assertThat(matchOneSwitched.doesPlayAgainst(thomas, lucas)).isTrue();
		assertThat(matchOneSwitched.doesPlayAgainst(thomas, smets)).isTrue();
		assertThat(matchOneSwitched.doesPlayAgainst(kristof, thomas)).isFalse();
		assertThat(matchOneSwitched.doesPlayAgainst(smets, lucas)).isFalse();
		assertThat(matchTwo.doesPlayAgainst(kristof, bart)).isTrue();
		assertThat(matchTwo.doesPlayAgainst(kristof, yannick)).isTrue();
		assertThat(matchTwo.doesPlayAgainst(thomas, bart)).isTrue();
		assertThat(matchTwo.doesPlayAgainst(thomas, yannick)).isTrue();
		assertThat(matchTwo.doesPlayAgainst(kristof, thomas)).isFalse();
		assertThat(matchTwo.doesPlayAgainst(bart, yannick)).isFalse();
	}


}
