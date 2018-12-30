package kcnops.lubbinton.model;

import javax.annotation.Nonnull;

public class Player {

	private final String name;

	public Player(@Nonnull final String name) {
		this.name = name;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	public int compareTo(@Nonnull final Player otherPlayer) {
		return this.name.compareTo(otherPlayer.getName());
	}

	@Override
	public String toString() {
		return name;
	}
}
