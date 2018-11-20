package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Match;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class MatchPanel extends JPanel {


	protected MatchPanel(@Nonnull final Match match,  final boolean withScore) {
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

	private void addScorePanel() {
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

		this.add(scorePanel, BorderLayout.EAST);
	}

}
