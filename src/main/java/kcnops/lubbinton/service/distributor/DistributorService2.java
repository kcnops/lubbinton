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

public class DistributorService2 implements IDistributorService {

	@Nonnull
	@Override
	public Set<Setup> distribute(@Nonnull final List<Player> players, final int amountOfRounds) {
		final List<List<Player>> playersPermutations = permutePlayers(players);
		System.out.println("Unique permutations including duplicates: " + playersPermutations.size());
		final Set<Round> roundPermutations = playersPermutations.stream().map(this::buildRound).collect(Collectors.toSet());
		System.out.println("Unique rounds: " + roundPermutations.size());
//		System.out.println(playersPermutations.toString());
		final List<List<Round>> setupPermutations = combineRounds(roundPermutations, amountOfRounds);
		System.out.println("Unique setup permutations including duplicates: " + setupPermutations.size());
//		System.out.println(setupPermutations.toString());
		final Set<Setup> setups = setupPermutations.stream()
				.map(Setup::new)
				.collect(Collectors.toSet());
		System.out.println("Unique setups: " + setups.size());
//		System.out.println(setups.toString());
		return setups;
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

	private List<List<Round>> combineRounds(@Nonnull final Set<Round> roundPermutations, final int amountOfRounds) {
		final List<List<Round>> result = new ArrayList<>();
		if (amountOfRounds == 1) {
			for(Round round : roundPermutations) {
				final List<Round> list = new ArrayList<>();
				list.add(round);
				result.add(list);
			}
			return result;
		}
		final List<List<Round>> subLists = combineRounds(roundPermutations, amountOfRounds - 1);
		for(List<Round> subList : subLists) {
			for(Round playerPermutation : roundPermutations) {
				final boolean alreadyContainsRound = subList.stream().anyMatch(sublist -> sublist.equals(playerPermutation));
				if (!alreadyContainsRound) {
					final List<Round> newSubList = new ArrayList<>(subList);
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
