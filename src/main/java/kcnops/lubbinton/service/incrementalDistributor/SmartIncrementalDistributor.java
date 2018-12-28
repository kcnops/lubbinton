package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.model.Side;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This incremental distributor will not find out the rest players by permutation but by counting who should rest.
 * The players playing will still be permuted. This is to improve.
 */
public class SmartIncrementalDistributor implements IIncrementalDistributor {

	@Nonnull
	@Override
	public Round getNextRound(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds) {
		final List<Player> restPlayers = getRestPlayers(players, previousRounds);
		final List<Player> playingPlayers = new ArrayList<>(players);
		playingPlayers.removeAll(restPlayers);
		final List<List<Player>> permutations = permutePlayers(playingPlayers);
		Set<Round> rounds = permutations.stream()
										 .map(this::buildRound)
										 .map(round -> new Round(round.getMatches(), restPlayers))
										 .collect(Collectors.toSet());
		final Setup bestSetup = getBestSetup(players, previousRounds, rounds);
		return bestSetup.getRounds().get(bestSetup.getRounds().size()-1);
	}

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

	@Nonnull
	private Round buildRound(@Nonnull List<Player> players) {
		final int amountOfPlayers = players.size();
		final int amountOfMatches = amountOfPlayers / 4;
		final List<Match> matches = new ArrayList<>();
		for (int i = 0; i < amountOfMatches; i++) {
			final Side sideOne = new Side(players.get(4 * i), players.get(4 * i + 1));
			final Side sideTwo = new Side(players.get(4 * i + 2), players.get(4 * i + 3));
			matches.add(new Match(sideOne, sideTwo));
		}
		final List<Player> rest = players.subList(amountOfMatches * 4, amountOfPlayers);
		return new Round(matches, rest);
	}

	@Nonnull
	private List<List<Player>> permutePlayers(@Nonnull final List<Player> input) {
		final List<List<Player>> output = new ArrayList<>();
		if (input.isEmpty()) {
			output.add(new ArrayList<>());
			return output;
		}
		final List<Player> list = new ArrayList<>(input);
		final Player head = list.get(0);
		final List<Player> rest = list.subList(1, list.size());
		for (List<Player> permutations : permutePlayers(rest)) {
			final List<List<Player>> subLists = new ArrayList<>();
			for (int i = 0; i <= permutations.size(); i++) {
				final List<Player> subList = new ArrayList<>(permutations);
				subList.add(i, head);
				subLists.add(subList);
			}
			output.addAll(subLists);
		}
		return output;
	}

}
