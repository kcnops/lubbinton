package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Round;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class RoundsScreen extends JPanel {

	private RoundPanel thisRoundPanel;
	private RoundPanel nextRoundPanel;
	private JPanel scorePanel;

	public RoundsScreen() {
		this.setLayout(new BorderLayout());

		final JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(actionPanel, BorderLayout.SOUTH);

		final JButton nextRoundButton = new JButton();
		nextRoundButton.setText("Next Round");
		actionPanel.add(nextRoundButton);

		final JButton finishButton = new JButton();
		finishButton.setText("Finish");
		actionPanel.add(finishButton);


		final JPanel roundsPanel = new JPanel();
		this.add(roundsPanel, BorderLayout.CENTER);

		thisRoundPanel = new RoundPanel("This round");
		roundsPanel.add(thisRoundPanel, BorderLayout.WEST);

		this.scorePanel = getScorePanel();
		roundsPanel.add(scorePanel, BorderLayout.CENTER);

		nextRoundPanel = new RoundPanel("Next round");
		roundsPanel.add(nextRoundPanel, BorderLayout.EAST);
	}

	protected void thisRound(@Nonnull final Round round) {
		thisRoundPanel.setGames(round);
	}


	protected void nextRound(@Nonnull final Round round) {
		nextRoundPanel.setGames(round);
	}

	private JPanel getScorePanel() {
		final JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new BorderLayout());

		final JTextField homeTeamScore = new JTextField();
		homeTeamScore.setPreferredSize(new Dimension(20, 10));
		scorePanel.add(homeTeamScore, BorderLayout.WEST);

		final JTextPane dashPanel = new JTextPane();
		scorePanel.add(dashPanel, BorderLayout.CENTER);
		dashPanel.setText("-");
		dashPanel.setEditable(false);

		final JTextField outTeamScore = new JTextField();
		scorePanel.add(outTeamScore, BorderLayout.EAST);
		outTeamScore.setPreferredSize(new Dimension(20, 10));

		return scorePanel;
	}

}
