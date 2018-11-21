package kcnops.lubbinton.controller;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Score;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.service.distributor.DistributorService2;
import kcnops.lubbinton.service.distributor.IDistributorService;
import kcnops.lubbinton.view.LubbintonScreen;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainController {

	private static final List<String> NAMES = Arrays.asList("Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie");

	private final LubbintonScreen mainScreen;
	private final IDistributorService distributorService;

	private Map<Player, Integer> players;
	private Round nextRound;

	public MainController() {
		mainScreen = new LubbintonScreen(this, NAMES);
		distributorService = new DistributorService2();
	}

	public void start(@Nonnull final List<String> playerNames) {
		players = playerNames.stream()
				.map(Player::new)
				.collect(Collectors.toMap(Function.identity(), player -> 0));

		final Round round = getRound();
		mainScreen.thisRound(round);

		new Thread(this::newNextRound).start();
	}

	public void nextRound(final Map<Match, Score> scores) {
		addScores(scores);

		mainScreen.thisRound(nextRound);

		mainScreen.emptyNextRound();

		new Thread(this::newNextRound).start();
	}

	private void addScores(final Map<Match, Score> scores) {
		// TODO
	}

	private void newNextRound() {
		try {
			System.out.println("Loading next round...");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		nextRound = getRound();
		System.out.println("Round loaded");
		mainScreen.nextRound(nextRound);
	}

	@Nonnull
	private Round getRound() {
		final Set<Setup> rounds = distributorService.distribute(new ArrayList<>(players.keySet()), 1);
		final Setup randomSetup = rounds.iterator().next();
		return randomSetup.getRounds().get(0);
	}

}
