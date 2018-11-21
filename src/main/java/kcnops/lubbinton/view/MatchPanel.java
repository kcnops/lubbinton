package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Score;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class MatchPanel extends JPanel {

	private Match match;
	private JTextField homeTeamScore;
	private JTextField outTeamScore;


	protected MatchPanel(@Nonnull final Match match,  final boolean withScore) {
		this.match = match;

		this.setLayout(new BorderLayout());

		final JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		this.add(gamePanel, BorderLayout.CENTER);

		final JTextPane homeTeamPanel = new JTextPane();
		gamePanel.add(homeTeamPanel, BorderLayout.WEST);
		homeTeamPanel.setText(match.getSideOne().toString());
		homeTeamPanel.setEditable(false);

		final JTextPane vsPanel = new JTextPane();
		gamePanel.add(vsPanel, BorderLayout.CENTER);
		vsPanel.setText("vs.");
		vsPanel.setEditable(false);

		final JTextPane outTeamPanel = new JTextPane();
		gamePanel.add(outTeamPanel, BorderLayout.EAST);
		outTeamPanel.setText(match.getSideTwo().toString());
		outTeamPanel.setEditable(false);

		if(withScore) {
			addScorePanel();
		}
	}

	public Match getMatch() {
		return match;
	}

	public Score getScore() {
		return new Score(Integer.parseInt(homeTeamScore.getText()), Integer.parseInt(outTeamScore.getText()));
	}

	private void addScorePanel() {
		final JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new BorderLayout());

		homeTeamScore = new JTextField();
		homeTeamScore.setPreferredSize(new Dimension(20, 10));
		scorePanel.add(homeTeamScore, BorderLayout.WEST);

		final JTextPane dashPanel = new JTextPane();
		scorePanel.add(dashPanel, BorderLayout.CENTER);
		dashPanel.setText("-");
		dashPanel.setEditable(false);

		outTeamScore = new JTextField();
		scorePanel.add(outTeamScore, BorderLayout.EAST);
		outTeamScore.setPreferredSize(new Dimension(20, 10));

		this.add(scorePanel, BorderLayout.EAST);
	}

	protected boolean validateForNext() {
		try {
			Integer.parseInt(homeTeamScore.getText());
			Integer.parseInt(outTeamScore.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
