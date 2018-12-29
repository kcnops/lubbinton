package kcnops.lubbinton;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.service.incrementalDistributor.AllMatchImmediateReturnNotSavingIncrementalDistributor;
import kcnops.lubbinton.service.incrementalDistributor.IIncrementalDistributor;

import java.util.ArrayList;
import java.util.List;

public class TestApplication {

//	8-11: 0.4sec

//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert"};
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny"};
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie"};
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie", "Esther"};
	private static final int AMOUNT_OF_ROUNDS = 5;

	private static final IIncrementalDistributor DISTRIBUTOR = new AllMatchImmediateReturnNotSavingIncrementalDistributor();

	public static void main(String[] args) throws InterruptedException {
		final List<Player> players = new ArrayList<>();
		boolean stop = false;
		for (int amountOfPlayers = 1; !stop;  amountOfPlayers++) {
			final Player newPlayer = new Player("player" + amountOfPlayers);
			players.add(newPlayer);

			System.out.println("Calculating for " + amountOfPlayers + " players...");

			final List<Round> rounds = new ArrayList<>();
			for (int i = 0; i < AMOUNT_OF_ROUNDS; i++) {
				long startTime = System.currentTimeMillis();
				final Round nextRound = DISTRIBUTOR.getNextRound(players, rounds);
				long endTime = System.currentTimeMillis();
				double duration = (endTime - startTime)/1000d;
				System.out.println("Time: " + duration);
				System.out.println(nextRound);
				rounds.add(nextRound);
				Thread.sleep(1000);
			}
		}
	}

}
