package kcnops.lubbinton.controller;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.service.distributor.DistributorService2;
import kcnops.lubbinton.service.distributor.IDistributorService;
import kcnops.lubbinton.view.LubbintonScreen;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainController {

	private static final List<String> NAMES = Arrays.asList("Kristof", "Thomas", "Lucas", "Christoph", "Geert");

	private LubbintonScreen mainScreen;
	private IDistributorService distributorService;

	public MainController() {
		mainScreen = new LubbintonScreen(this, NAMES);
		distributorService = new DistributorService2();
	}

	public void start(@Nonnull final List<String> playerNames) {
		final List<Player> players = playerNames.stream()
												 .map(Player::new)
												 .collect(Collectors.toList());
		final Set<Setup> rounds = distributorService.distribute(players, 1);
		final Setup randomRound = rounds.iterator().next();
		// TODO actual round(s)
		mainScreen.thisRound(randomRound);
	}
}
