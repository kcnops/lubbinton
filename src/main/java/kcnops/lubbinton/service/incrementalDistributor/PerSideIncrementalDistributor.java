package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.model.Side;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This incremental distributor will not find out the rest players by permutation but by counting who should rest.
 * The players playing will still be permuted, however per side and not per player, thus more intelligent and less time consuming.
 */
public class PerSideIncrementalDistributor implements IIncrementalDistributor {

	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets"};

	public static void main(String... args) {
		final List<Player> players = Arrays.stream(NAMES).map(Player::new).collect(Collectors.toList());
		PerSideIncrementalDistributor distributor = new PerSideIncrementalDistributor();
		distributor.getNextRound(players, Collections.emptyList());
	}


	@Nonnull
	@Override
	public Round getNextRound(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds) {
		final List<Player> restPlayers = getRestPlayers(players, previousRounds);
		final List<Player> playingPlayers = new ArrayList<>(players);
		playingPlayers.removeAll(restPlayers);
		final int amountOfGames = playingPlayers.size() / 4;
		final Set<Side> uniqueSides = getUniqueSides(playingPlayers);
		System.out.println("unique sides: " + uniqueSides);
		System.out.println("amount unique sides: " + uniqueSides.size());
		final Set<List<Side>> setups = getRounds(uniqueSides, amountOfGames);
		System.out.println("amount sideLists: " + setups.size());
		final Set<Round> rounds = buildRounds(setups, restPlayers);
		System.out.println("amount rounds: " + rounds.size());
		final Setup bestSetup = getBestSetup(players, previousRounds, rounds);
		return bestSetup.getRounds().get(bestSetup.getRounds().size()-1);
	}

	private Set<Round> buildRounds(@Nonnull final Set<List<Side>> setups, @Nonnull final List<Player> restPlayers) {
		if (setups.isEmpty()) {
			final Round singleResult = new Round(Collections.emptyList(), restPlayers);
			return Stream.of(singleResult).collect(Collectors.toSet());
		}
		return setups.stream().map(sideList -> {
			final List<Match> matches = new ArrayList<>();
			while(!sideList.isEmpty()) {
				final Side side1 = sideList.remove(0);
				final Side side2 = sideList.remove(0);
				final Match match = new Match(side1, side2);
				matches.add(match);
			}
			return new Round(matches, restPlayers);
		}).collect(Collectors.toSet());
	}

	private Set<List<Side>> getRounds(@Nonnull final Set<Side> uniqueSides, final int amountOfGames) {
		if(amountOfGames < 1) {
			return Collections.emptySet();
		}
		return getRoundsIterative(uniqueSides, amountOfGames * 2);
	}


	private Set<List<Side>> getRoundsIterative(@Nonnull final Set<Side> uniqueSides, final int amountOfSidesRemaining) {
		if (amountOfSidesRemaining == 1) {
			final Set<List<Side>> result = new HashSet<>();
			uniqueSides.forEach(side -> {
				final List<Side> sideList = new ArrayList<>();
				sideList.add(side);
				result.add(sideList);
			});
			return result;
		}
		final Set<List<Side>> setups = getRoundsIterative(uniqueSides, amountOfSidesRemaining - 1);
		final Set<List<Side>> newSetups = new HashSet<>();
		uniqueSides.forEach(side ->
			setups.forEach(setup -> {
				if(isNewSide(side, setup)) {
					final List<Side> newSetup = new ArrayList<>(setup);
					newSetup.add(side);
					newSetups.add(newSetup);
				}
			}));
		return newSetups;
	}

	private boolean isNewSide(@Nonnull final Side side, @Nonnull final List<Side> sides) {
		return isNewPlayerForSides(side.getOne(), sides) && isNewPlayerForSides(side.getTwo(), sides);
	}

	private boolean isNewPlayerForSides(@Nonnull final Player player, @Nonnull final List<Side> sides) {
		return sides.stream().noneMatch(side -> side.containsPlayer(player));
	}

	private Set<Side> getUniqueSides(@Nonnull final List<Player> players) {
		final Set<Side> uniqueSides = new HashSet<>();
		if (players.size() < 2) {
			return uniqueSides;
		}
		for (int first = 0; first < players.size()-1; first++) {
			for (int second = first+1; second < players.size(); second++) {
				uniqueSides.add(new Side(players.get(first), players.get(second)));
			}
		}
		return uniqueSides;
	}

	// Rest player methods

	private List<Player> getRestPlayers(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds) {
		final int amountOfRestPlayers = players.size() % 4;
		final Map<Integer,List<Player>> playersPerTimeRested = mapPlayersPerTimesRested(players, previousRounds);
		final List<Player> restPlayers = new ArrayList<>();
		while (restPlayers.size() < amountOfRestPlayers) {
			final Player nextRestPlayer = getNextRestPlayer(playersPerTimeRested, previousRounds.size());
			restPlayers.add(nextRestPlayer);
		}
		return restPlayers;
	}

	private Player getNextRestPlayer(@Nonnull final Map<Integer, List<Player>> playersPerTimeRested, final int amountOfRounds) {
		for (int timesRest = 0; timesRest < amountOfRounds+1; timesRest++) {
			final List<Player> playersForTimesRest = playersPerTimeRested.get(timesRest);
			if (playersForTimesRest != null && !playersForTimesRest.isEmpty()) {
				final Player result = playersForTimesRest.remove(0);
				playersPerTimeRested.put(timesRest, playersForTimesRest);
				return result;
			}
		}
		throw new RuntimeException("Could not find player to rest in map " + playersPerTimeRested.toString());
	}

	private Map<Integer, List<Player>> mapPlayersPerTimesRested(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds) {
		final Map<Integer, List<Player>> result = new HashMap<>();
		players.forEach(player -> {
			final int timesRested = getTimesRestedForPlayer(previousRounds, player);
			final List<Player> playersWithAmountRested = result.getOrDefault(timesRested, new ArrayList<>());
			playersWithAmountRested.add(player);
			result.put(timesRested, playersWithAmountRested);
		});
		return result;
	}

	private int getTimesRestedForPlayer(@Nonnull final List<Round> rounds, @Nonnull final Player player) {
		return Math.toIntExact(rounds.stream()
									  .map(Round::getRest)
									  .filter(restPlayers -> restPlayers.contains(player))
									  .count());
	}
}
