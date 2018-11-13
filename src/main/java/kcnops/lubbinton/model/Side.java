package kcnops.lubbinton.model;

import javax.annotation.Nonnull;

public class Side {

	private Player one;
	private Player two;

	public Side(
			@Nonnull final Player one,
			@Nonnull final Player two) {
		this.one = one;
		this.two = two;
	}

	public boolean doesPlayWith(@Nonnull final Player playerOne, @Nonnull final Player playerTwo) {
		return containsPlayer(playerOne) && containsPlayer(playerTwo);
	}

	public boolean containsPlayer(@Nonnull final Player player) {
		return one.equals(player) || two.equals(player);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Side side = (Side) o;

		return (one.equals(side.one) && two.equals(side.two)) || (one.equals(side.two) && two.equals(side.one));
	}

	@Override
	public int hashCode() {
		return one.hashCode() + two.hashCode();
	}

	@Override
	public String toString() {
		return one + " & " + two;
	}
}
