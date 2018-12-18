package kcnops.lubbinton;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.service.incrementalDistributor.IIncrementalDistributor;
import kcnops.lubbinton.service.incrementalDistributor.IncrementalDistributor;

import java.util.ArrayList;
import java.util.List;

public class TestApplication {

//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert"};
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny"}; // OK 37sec
//	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie"}; // NOK
	private static final String[] NAMES = new String[]{"Kristof","Thomas","Lucas", "Smets", "Geert", "Bart", "Arno", "Yannick", "Thierry", "Shauny", "Julie", "Esther"}; // NOK
	private static final int AMOUNT_OF_ROUNDS = 5;

	private static final IIncrementalDistributor DISTRIBUTOR = new IncrementalDistributor();

	public static void main(String[] args) {
		final List<Player> players = new ArrayList<>();
		boolean stop = false;
		for (int amountOfPlayers = 1; !stop;  amountOfPlayers++) {
			final Player newPlayer = new Player("player" + amountOfPlayers);
			players.add(newPlayer);

			System.out.println("Calculating for " + amountOfPlayers + " players...");

			final List<Round> rounds = new ArrayList<>();
			for (int i = 0; i < AMOUNT_OF_ROUNDS; i++) {
				long startTime = System.currentTimeMillis();
				rounds.add(DISTRIBUTOR.getNextRound(players, rounds));
				long endTime = System.currentTimeMillis();
				double duration = (endTime - startTime)/1000d;
				System.out.println("Time: " + duration);
				if(duration > 60) {
					System.out.println("Stopping as last duration was > 60, that's too damn long!");
					stop = true;
					break;
				}
			}
		}
	}

}
