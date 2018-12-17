package kcnops.lubbinton.service.incrementalDistributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.service.scoring.ScoringService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IncrementalDistributor implements IIncrementalDistributor {

	private ScoringService scoringService = new ScoringService();

	@Nonnull
	@Override
	public Round getNextRound(@Nonnull List<Player> players, @Nonnull List<Round> previousRounds) {
		final List<List<Player>> permutePlayers = permutePlayers(players);

		Set<Round> rounds = permutePlayers.stream().map(this::buildRound).collect(Collectors.toSet());

		final Set<Setup> setups = new HashSet<>();
		rounds.forEach(round -> {
			List<Round> fullRoundsList = new ArrayList<>(previousRounds);
			fullRoundsList.add(round);
			Setup setup = new Setup(fullRoundsList);
			setups.add(setup);
		});

		Setup bestSetup = setups.iterator().next();
		int bestScore = scoringService.score(bestSetup, players);
		for (Setup setup : setups) {
			int score = scoringService.score(setup, players);
			if (score < bestScore) {
				bestScore = score;
				bestSetup = setup;
			}
		}

		final List<Round> bestSetupRounds = bestSetup.getRounds();
		return bestSetupRounds.get(bestSetupRounds.size()-1);
	}
}
