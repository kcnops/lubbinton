package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Side;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface IIncrementalDistributor {

	@Nonnull
	Round getNextRound(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds);

	default Round buildRound(@Nonnull List<Player> players) {
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

	default List<List<Player>> permutePlayers(@Nonnull final List<Player> input) {
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
