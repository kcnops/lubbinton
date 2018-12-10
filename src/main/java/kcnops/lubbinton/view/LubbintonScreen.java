package kcnops.lubbinton.view;

import kcnops.lubbinton.controller.MainController;
import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Score;
import kcnops.lubbinton.view.players.PlayerScreen;
import kcnops.lubbinton.view.rounds.RoundsScreen;
import kcnops.lubbinton.view.scores.ScoresScreen;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LubbintonScreen extends JFrame {

	private MainController mainController;

	private PlayerScreen playerPanel;
	private RoundsScreen roundPanel;
	private ScoresScreen scorePanel;

	public LubbintonScreen(@Nonnull final MainController mainController, @Nonnull final List<String> playerNames) {
		this.mainController = mainController;
		initializeScreen(playerNames);
	}

	private void initializeScreen(@Nonnull final List<String> playerNames) {
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.setVisible(true);
		this.getContentPane().setLayout(new BorderLayout(0, 0));

		final JTabbedPane tabbedPanel = new JTabbedPane();
		this.getContentPane().add(tabbedPanel, BorderLayout.CENTER);

		playerPanel = new PlayerScreen(this, playerNames);
		tabbedPanel.addTab("Players", playerPanel);

		roundPanel = new RoundsScreen(this);
		tabbedPanel.addTab("Rounds", roundPanel);

		scorePanel = new ScoresScreen(this);
		tabbedPanel.addTab("Score", scorePanel);
	}

	public void startPressed(@Nonnull final List<String> selectedPlayers) {
		System.out.println("Start pressed.");
		mainController.start(selectedPlayers);
	}

	public void nextPressed(@Nonnull final Map<Match, Score> scores) {
		System.out.println("Next pressed.");
		mainController.nextRound(scores);
	}

	public void finishPressed(@Nonnull final Optional<Map<Match, Score>> optionalScores) {
		System.out.println("Finish pressed.");
		mainController.finish(optionalScores);
	}

	public void thisRound(@Nonnull final Round round) {
		roundPanel.thisRound(round);
		this.pack();
	}

	public void nextRound(@Nonnull final Round round) {
		roundPanel.nextRound(round);
		this.pack();
	}

	public void emptyNextRound() {
		roundPanel.emptyNextRound();
	}

	public void setScores(@Nonnull final Map<Player, Integer> players) {
		scorePanel.setScores(players);
	}
}
