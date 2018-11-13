package kcnops.lubbinton.service.distributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewDistributorService {

	public List<Side> distribute(@Nonnull final String[] playerNames) {
		final List<Player> players = Arrays.stream(playerNames).map(Player::new).collect(Collectors.toList());
		final List<Side> uniqueSides = getUniqueSides(players);
		final int amountOfUniqueSides = uniqueSides.size();
		System.out.println(uniqueSides);
		System.out.println("Unique sides: " + amountOfUniqueSides);
		return uniqueSides;
	}

	private List<Side> getUniqueSides(@Nonnull final List<Player> players) {
		return getUniqueSides(players, null);
	}

	private List<Side> getUniqueSides(@Nonnull final List<Player> players, @Nullable final Player first) {
		if (players.isEmpty()) {
			return new ArrayList<>();
		}
		final List<Player> rest = new ArrayList<>(players);
		final Player head = rest.remove(0);
		if (first != null) {
			final List<Side> sides = getUniqueSides(new ArrayList<>(rest), first);
			sides.add(new Side(first, head));
			return sides;
		}
		else {
			final List<Side> sidesWithHead = getUniqueSides(new ArrayList<>(rest), head);
			final List<Side> sidesWithoutHead = getUniqueSides(new ArrayList<>(rest), null);
			final List<Side> result = new ArrayList<>(sidesWithHead);
			result.addAll(sidesWithoutHead);
			return result;
		}
	}

}
