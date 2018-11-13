package kcnops.lubbinton.model;

import javax.annotation.Nonnull;

public class Player {

	private final String name;

	public Player(@Nonnull final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
