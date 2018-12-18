package kcnops.lubbinton;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.service.incrementalDistributor.IIncrementalDistributor;
import kcnops.lubbinton.service.incrementalDistributor.IncrementalDistributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestApplication {

//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert"};
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick"};
	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie"};
	private static final int AMOUNT_OF_ROUNDS = 10;

	private static final IIncrementalDistributor DISTRIBUTOR = new IncrementalDistributor();

	public static void main(String[] args) {
		final List<Player> players = Arrays.stream(NAMES).map(Player::new).collect(Collectors.toList());
		final List<Round> rounds = new ArrayList<>();

		for (int i = 0; i < AMOUNT_OF_ROUNDS; i++) {
			final Round nextRound = DISTRIBUTOR.getNextRound(players, rounds);
			System.out.println(nextRound);
			rounds.add(nextRound);
		}

	}

}
