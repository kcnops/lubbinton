package kcnops.lubbinton.service.scoring;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvenFasterScoringService extends ScoringService {

	private static final int PENALTY_REST = 99;
	private static final int PENALTY_WITH = 4;
	private static final int PENALTY_AGAINST = 3;

	@Override
	public int score(@Nonnull final Setup setup, @Nonnull final List<Player> players) {
		final Map<Player, Integer> rest = new HashMap<>();
		final Map<Player, Map<Player, Integer>> with = new HashMap<>();
		final Map<Player, Map<Player, Integer>> against = new HashMap<>();
		for (final Round round : setup.getRounds()) {
			// Build rest
//			for (final Player player : round.getRest()) {
//				final int oldTimesRest = rest.getOrDefault(player, 0);
//				final int newTimesRest = oldTimesRest + 1;
//				rest.put(player, newTimesRest);
//			}

			for (final Match match : round.getMatches()) {
				final Player player1 = match.getSideOne().getOne();
				final Player player2 = match.getSideOne().getTwo();
				final Player player3 = match.getSideTwo().getOne();
				final Player player4 = match.getSideTwo().getTwo();

				// Build with
				final Player with1Player1 = player1.compareTo(player2) <= 0 ? player1 : player2;
				final Player with1Player2 = player1.compareTo(player2) <= 0 ? player2 : player1;
				final Player with2Player1 = player3.compareTo(player4) <= 0 ? player3 : player4;
				final Player with2Player2 = player3.compareTo(player4) <= 0 ? player4 : player3;
				final Map<Player, Integer> with1 = with.getOrDefault(with1Player1, new HashMap<>());
				final Integer with1Times = with1.getOrDefault(with1Player2, 0);
				final Integer newWith1Times = with1Times + 1;
				with1.put(with1Player2, newWith1Times);
				with.put(with1Player1, with1);
				final Map<Player, Integer> with2 = with.getOrDefault(with2Player1, new HashMap<>());
				final Integer with2Times = with2.getOrDefault(with2Player2, 0);
				final Integer newWith2Times = with2Times + 1;
				with2.put(with2Player2, newWith2Times);
				with.put(with2Player1, with2);

				// Build against
				final Player against1Player1 = player1.compareTo(player3) <= 0 ? player1 : player3;
				final Player against1Player2 = player1.compareTo(player3) <= 0 ? player3 : player1;
				final Player against2Player1 = player1.compareTo(player4) <= 0 ? player1 : player4;
				final Player against2Player2 = player1.compareTo(player4) <= 0 ? player4 : player1;
				final Player against3Player1 = player2.compareTo(player3) <= 0 ? player1 : player3;
				final Player against3Player2 = player2.compareTo(player3) <= 0 ? player3 : player1;
				final Player against4Player1 = player2.compareTo(player4) <= 0 ? player1 : player4;
				final Player against4Player2 = player2.compareTo(player4) <= 0 ? player4 : player1;
				final Map<Player, Integer> against1 = against.getOrDefault(against1Player1, new HashMap<>());
				final Integer against1Times = against1.getOrDefault(against1Player2, 0);
				final Integer newAgainst1Times = against1Times + 1;
				against1.put(against1Player2, newAgainst1Times);
				against.put(against1Player1, against1);
				final Map<Player, Integer> against2 = against.getOrDefault(against2Player1, new HashMap<>());
				final Integer against2Times = against2.getOrDefault(against2Player2, 0);
				final Integer newAgainst2Times = against2Times + 1;
				against2.put(against2Player2, newAgainst2Times);
				against.put(against2Player1, against2);
				final Map<Player, Integer> against3 = against.getOrDefault(against3Player1, new HashMap<>());
				final Integer against3Times = against3.getOrDefault(against3Player2, 0);
				final Integer newAgainst3Times = against3Times + 1;
				against3.put(against3Player2, newAgainst3Times);
				against.put(against3Player1, against3);
				final Map<Player, Integer> against4 = against.getOrDefault(against4Player1, new HashMap<>());
				final Integer against4Times = against4.getOrDefault(against4Player2, 0);
				final Integer newAgainst4Times = against4Times + 1;
				against4.put(against4Player2, newAgainst4Times);
				against.put(against4Player1, against4);
			}
		}

		int penalty = 0;

		// Rest penalty
//		final int minTimesRest = setup.getRounds().size() / players.size();
//		for (int timesRest : rest.values()) {
//			final int moreRest = timesRest - minTimesRest;
//			penalty += moreRest == 0 ? 0 : Math.pow(PENALTY_REST, moreRest);
//		}

		// With penalty
		for (final Map<Player, Integer> withMap : with.values()) {
			for (final Integer withAmount : withMap.values()) {
				penalty += withAmount < 2 ? 0 : (int) Math.pow(PENALTY_WITH, withAmount - 1d);
			}
		}

		// Against penalty
		for (final Map<Player, Integer> againstMap : against.values()) {
			for (final Integer againstAmount : againstMap.values()) {
				penalty += againstAmount < 2 ? 0 : (int) Math.pow(PENALTY_AGAINST, againstAmount - 1d);
			}
		}

		return penalty;
	}

}