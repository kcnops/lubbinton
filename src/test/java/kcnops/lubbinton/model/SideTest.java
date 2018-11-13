package kcnops.lubbinton.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SideTest {

	private Player kristof = new Player("Kristof");
	private Player thomas = new Player("Thomas");
	private Player lucas = new Player("Lucas");

	private Side sideOne = new Side(kristof, thomas);
	private Side sideOneSwitched = new Side(thomas, kristof);
	private Side sideTwo = new Side(kristof, lucas);


	@Test
	public void testEquals() {
		assertThat(sideOne).isEqualTo(sideOne);
	}

	@Test
	public void testEqualsSame() {
		assertThat(sideOne.hashCode()).isEqualTo(sideOne.hashCode());
		assertThat(sideOne).isEqualTo(sideOne);
	}

	@Test
	public void testEqualsSwitched() {
		assertThat(sideOne.hashCode()).isEqualTo(sideOneSwitched.hashCode());
		assertThat(sideOne).isEqualTo(sideOneSwitched);
	}

	@Test
	public void testEqualsNotEquals() {
		assertThat(sideOne.hashCode()).isNotEqualTo(sideTwo.hashCode());
		assertThat(sideOne).isNotEqualTo(sideTwo);
	}

	@Test
	public void testDoesPlayWith() {
		assertThat(sideOne.doesPlayWith(kristof, thomas)).isTrue();
		assertThat(sideOne.doesPlayWith(thomas, kristof)).isTrue();
		assertThat(sideOne.doesPlayWith(kristof, lucas)).isFalse();
		assertThat(sideOne.doesPlayWith(thomas, lucas)).isFalse();
		assertThat(sideOneSwitched.doesPlayWith(kristof, thomas)).isTrue();
		assertThat(sideOneSwitched.doesPlayWith(thomas, kristof)).isTrue();
		assertThat(sideOneSwitched.doesPlayWith(kristof, lucas)).isFalse();
		assertThat(sideOneSwitched.doesPlayWith(thomas, lucas)).isFalse();
		assertThat(sideTwo.doesPlayWith(kristof, lucas)).isTrue();
		assertThat(sideTwo.doesPlayWith(lucas, kristof)).isTrue();
		assertThat(sideTwo.doesPlayWith(kristof, thomas)).isFalse();
		assertThat(sideTwo.doesPlayWith(lucas, thomas)).isFalse();
	}

	@Test
	public void testContainsPlayer() {
		assertThat(sideOne.containsPlayer(kristof)).isTrue();
		assertThat(sideOne.containsPlayer(thomas)).isTrue();
		assertThat(sideOne.containsPlayer(lucas)).isFalse();
		assertThat(sideOneSwitched.containsPlayer(kristof)).isTrue();
		assertThat(sideOneSwitched.containsPlayer(thomas)).isTrue();
		assertThat(sideOneSwitched.containsPlayer(lucas)).isFalse();
		assertThat(sideTwo.containsPlayer(kristof)).isTrue();
		assertThat(sideTwo.containsPlayer(lucas)).isTrue();
		assertThat(sideTwo.containsPlayer(thomas)).isFalse();
	}

}
