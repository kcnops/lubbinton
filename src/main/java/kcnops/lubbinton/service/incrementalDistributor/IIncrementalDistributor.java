package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.service.scoring.EvenFasterScoringService;
import kcnops.lubbinton.service.scoring.ScoringService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FunctionalInterface
public interface IIncrementalDistributor {

	ScoringService SCORING_SERVICE = new EvenFasterScoringService();

	@Nonnull
	Round getNextRound(@Nonnull final List<Player> players, @Nonnull final List<Round> previousRounds);

	default Setup getBestSetup(@Nonnull List<Player> players, @Nonnull List<Round> previousRounds, Set<Round> rounds) {
		final Set<Setup> setups = new HashSet<>();
		rounds.forEach(round -> {
			List<Round> fullRoundsList = new ArrayList<>(previousRounds);
			fullRoundsList.add(round);
			Setup setup = new Setup(fullRoundsList);
			setups.add(setup);
		});

		Setup bestSetup = setups.iterator().next();
		int bestScore = SCORING_SERVICE.score(bestSetup, players);
		for (Setup setup : setups) {
			int score = SCORING_SERVICE.score(setup, players);
			if (score < bestScore) {
				bestScore = score;
				bestSetup = setup;
			}
		}
		return bestSetup;
	}

}
