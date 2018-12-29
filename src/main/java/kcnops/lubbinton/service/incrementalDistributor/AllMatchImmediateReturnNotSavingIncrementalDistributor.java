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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This incremental distributor will not find out the rest players by permutation but by counting who should rest.
 * The players playing will still be permuted, however per side and not per player, thus more intelligent and less time consuming.
 *
 * Does not build all possibilities, to then transform them to a complete setup to then calculate all scores and get the best one,
 * but builds, transforms, calculates and if not better start next one.
 * This way it does not need to save all possibilities.
 */
public class AllMatchImmediateReturnNotSavingIncrementalDistributor implements IIncrementalDistributor {

	private static final String[] NAMES = new String[]{"1","2","3", "4"};

	public static void main(String... args) {
		final List<Player> players = Arrays.stream(NAMES).map(Player::new).collect(Collectors.toList());
		AllMatchImmediateReturnNotSavingIncrementalDistributor distributor = new AllMatchImmediateReturnNotSavingIncrementalDistributor();
		distributor.getNextRound(players, Collections.emptyList());
	}

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
		permuteTransformScoreAndReturnBestSetup(allPlayers, restPlayers, previousRounds, new ArrayList<>(), playingPlayers);
		return bestSetup;
	}

	private void permuteTransformScoreAndReturnBestSetup(@Nonnull final List<Player> allPlayers, @Nonnull final List<Player> restPlayers, @Nonnull final List<Round> previousRounds, @Nonnull final List<Match> matchList, @Nonnull final List<Player> remainingPlayers) {
		if (remainingPlayers.isEmpty()) {
			final Round round = new Round(matchList, restPlayers);
			final Setup setup = buildSetup(previousRounds, round);
			handleNewSetup(setup, allPlayers);
			return;
		}
		for (int first = 0; first < remainingPlayers.size()-1; first++) {
			for (int second = first+1; second < remainingPlayers.size(); second++) {
				final Player playerOne = remainingPlayers.get(first);
				final Player playerTwo = remainingPlayers.get(second);
				final Side sideOne = new Side(playerOne, playerTwo);

				final List<Player> remainingPlayersCopy = new ArrayList<>(remainingPlayers);
				remainingPlayersCopy.remove(playerOne);
				remainingPlayersCopy.remove(playerTwo);

				for (int third = 0; third < remainingPlayersCopy.size()-1; third++) {
					for (int forth = third + 1; forth < remainingPlayersCopy.size(); forth++) {
						final Player playerThree = remainingPlayersCopy.get(third);
						final Player playerFour = remainingPlayersCopy.get(forth);
						final Side sideTwo = new Side(playerThree, playerFour);
						final Match match = new Match(sideOne, sideTwo);
						final List<Match> newMatchList = new ArrayList<>(matchList);
						newMatchList.add(match);

						final List<Player> remainingPlayersCopyCopy = new ArrayList<>(remainingPlayersCopy);
						remainingPlayersCopyCopy.remove(playerThree);
						remainingPlayersCopyCopy.remove(playerFour);

						permuteTransformScoreAndReturnBestSetup(allPlayers, restPlayers, previousRounds, newMatchList, remainingPlayersCopyCopy);
					}
				}

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
	private Setup buildSetup(@Nonnull final List<Round> previousRounds, @Nonnull final Round round) {
		final List<Round> allRounds = new ArrayList<>(previousRounds);
		allRounds.add(round);
		return new Setup(allRounds);
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
