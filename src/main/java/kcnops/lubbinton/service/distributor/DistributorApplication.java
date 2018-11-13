package kcnops.lubbinton.service.distributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Setup;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DistributorApplication {

	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert"};
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie"};
	private static final int AMOUNT_OF_ROUNDS = 2;

	private final DistributorService1 distributorService1 = new DistributorService1();
	private final DistributorService2 distributorService = new DistributorService2();
	private final ScoringService scoringService = new ScoringService();

	private void run() {
		final long start = System.currentTimeMillis();

		final List<Player> players = Arrays.stream(NAMES).map(Player::new).collect(Collectors.toList());

		final Set<Setup> setups1 = distributorService1.distribute(players, AMOUNT_OF_ROUNDS);
		System.out.println("---------");
		final Set<Setup> setups = distributorService.distribute(players, AMOUNT_OF_ROUNDS);

//		final Map<Setup, Integer> scoredSetups = setups.stream()
//				.collect(Collectors.toMap(Function.identity(), setup -> scoringService.score(setup, players)));
////		System.out.println(scoredSetups.toString());
//
//		final Map<Setup, Integer> orderedScoredSetups = scoredSetups.entrySet().stream()
//				.sorted(Comparator.comparing(Map.Entry::getValue))
//				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//		System.out.println(orderedScoredSetups);
////		System.out.println("Selected: " + orderedScoredSetups);

		final long stop = System.currentTimeMillis();
		final long duration = stop - start;
		System.out.println("Duration: " + duration + "ms");
	}

	public static void main(String[] args) {
		new DistributorApplication().run();
	}

}
