package kcnops.lubbinton.service.distributor;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.model.Side;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DistributorService1 implements IDistributorService {

	/**
	 * OLD stuff, goes out of memory just permutation list of 11+ players
	 */

	@Nonnull
	@Override
	public Set<Setup> distribute(@Nonnull final List<Player> players, final int amountOfRounds) {
		final List<List<Player>> playersPermutations = permutePlayers(players);
		System.out.println("Unique permutations including duplicate setups: " + playersPermutations.size());
//		System.out.println(playersPermutations.toString());
		final List<List<List<Player>>> setupPermutations = combineRounds(playersPermutations, amountOfRounds);
		System.out.println("Unique setup permutations including duplicate setups: " + setupPermutations.size());
//		System.out.println(setupPermutations.toString());
		final Set<Setup> setups = setupPermutations.stream()
				.map(this::buildSetup)
				.collect(Collectors.toSet());
		System.out.println("Unique setups: " + setups.size());
//		System.out.println(setups.toString());
		return setups;
	}

	private Setup buildSetup(@Nonnull final List<List<Player>> setupPermutations) {
		final List<Round> rounds = setupPermutations.stream()
				.map(this::buildRound)
				.collect(Collectors.toList());
		return new Setup(rounds);
	}

	private Round buildRound(@Nonnull List<Player> players) {
		final int amountOfPlayers = players.size();
		final int amountOfMatches = amountOfPlayers / 4;
		final List<Match> matches = new ArrayList<>();
		for (int i = 0; i < amountOfMatches; i++) {
			final Side sideOne = new Side(players.get(4*i), players.get(4*i+1));
			final Side sideTwo = new Side(players.get(4*i+2), players.get(4*i+3));
			matches.add(new Match(sideOne, sideTwo));
		}
		final List<Player> rest = players.subList(amountOfMatches*4, amountOfPlayers);
		return new Round(matches, rest);
	}

	private List<List<List<Player>>> combineRounds(@Nonnull final List<List<Player>> playerPermutations, final int amountOfRounds) {
		final List<List<List<Player>>> result = new ArrayList<>();
		if (amountOfRounds == 1) {
			for(List<Player> playerPermutation : playerPermutations) {
				final List<List<Player>> list = new ArrayList<>();
				list.add(playerPermutation);
				result.add(list);
			}
			return result;
		}
		final List<List<List<Player>>> subLists = combineRounds(playerPermutations, amountOfRounds - 1);
		for(List<List<Player>> subList : subLists) {
			for(List<Player> playerPermutation : playerPermutations) {
				// This doesn't work because lists aren't the same but they are as rounds
				final boolean alreadyContainsRound = subList.stream().anyMatch(sublist -> sublist.equals(playerPermutation));
				if (!alreadyContainsRound) {
					final List<List<Player>> newSubList = new ArrayList<>(subList);
					newSubList.add(playerPermutation);
					result.add(newSubList);
				}
			}
		}
		return result;
	}

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
