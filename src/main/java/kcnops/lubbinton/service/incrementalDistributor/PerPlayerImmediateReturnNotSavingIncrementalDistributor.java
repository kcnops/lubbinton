package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.model.Side;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This incremental distributor will not find out the rest players by permutation but by counting who should rest.
 * The players playing will still be permuted, however per side and not per player, thus more intelligent and less time consuming.
 *
 * Does not build all possibilities, to then transform them to a complete setup to then calculate all scores and get the best one,
 * but builds, transforms, calculates and if not better start next one.
 * This way it does not need to save all possibilities.
 */
public class PerPlayerImmediateReturnNotSavingIncrementalDistributor implements IIncrementalDistributor {

	private Integer bestScore;
	private Setup bestSetup;

	@Nonnull
	@Override
	public Round getNextRound(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds) {
		final List<Player> restPlayers = getRestPlayers(players, previousRounds);
		final List<Player> playingPlayers = new ArrayList<>(players);
		playingPlayers.removeAll(restPlayers);
		final Setup bestSetup = permuteTransformScoreAndReturnBestSetup(players, playingPlayers, restPlayers, previousRounds);
		return bestSetup.getRounds().get(bestSetup.getRounds().size()-1);
	}

	@Nonnull
	private Setup permuteTransformScoreAndReturnBestSetup(@Nonnull final List<Player> allPlayers, @Nonnull final List<Player> playingPlayers, @Nonnull final List<Player> restPlayers, @Nonnull final List<Round> previousRounds) {
		bestScore = Integer.MAX_VALUE;
		bestSetup = null;
		permuteTransformScoreAndReturnBestSetup(allPlayers, playingPlayers, restPlayers, previousRounds, new ArrayList<>());
		return bestSetup;
	}

	private void permuteTransformScoreAndReturnBestSetup(@Nonnull final List<Player> allPlayers, @Nonnull final List<Player> playingPlayers, @Nonnull final List<Player> restPlayers, @Nonnull final List<Round> previousRounds, @Nonnull final List<Player> playerList) {
		if (playerList.size() == playingPlayers.size()) {
			final Round round = buildRound(playerList, restPlayers);
			final Setup setup = buildSetup(previousRounds, round);
			handleNewSetup(setup, allPlayers);
			return;
		}
		for (final Player player : playingPlayers) {
			if (!playerList.contains(player)) {
				final List<Player> newPlayerList = new ArrayList<>(playerList);
				newPlayerList.add(player);
				permuteTransformScoreAndReturnBestSetup(allPlayers, playingPlayers, restPlayers, previousRounds, newPlayerList);
			}
		}
	}

	private void handleNewSetup(@Nonnull final Setup setup, @Nonnull final List<Player> allPlayers) {
		final int score = SCORING_SERVICE.score(setup, allPlayers);
		if (score < bestScore) {
			bestScore = score;
			bestSetup = setup;
		}
	}

	@Nonnull
	private Round buildRound(@Nonnull List<Player> playingPlayers, @Nonnull final List<Player> restPlayers) {
		final int amountOfPlayers = playingPlayers.size();
		final int amountOfMatches = amountOfPlayers / 4;
		final List<Match> matches = new ArrayList<>();
		for (int i = 0; i < amountOfMatches; i++) {
			final Side sideOne = new Side(playingPlayers.get(4 * i), playingPlayers.get(4 * i + 1));
			final Side sideTwo = new Side(playingPlayers.get(4 * i + 2), playingPlayers.get(4 * i + 3));
			matches.add(new Match(sideOne, sideTwo));
		}
		return new Round(matches, restPlayers);
	}

	@Nonnull
	private Setup buildSetup(@Nonnull final List<Round> previousRounds, @Nonnull final Round round) {
		final List<Round> allRounds = new ArrayList<>(previousRounds);
		allRounds.add(round);
		return new Setup(allRounds);
	}


	private boolean isNewSide(@Nonnull final Side side, @Nonnull final List<Side> sides) {
		return isNewPlayerForSides(side.getOne(), sides) && isNewPlayerForSides(side.getTwo(), sides);
	}

	private boolean isNewPlayerForSides(@Nonnull final Player player, @Nonnull final List<Side> sides) {
		return sides.stream().noneMatch(side -> side.contains(player));
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
