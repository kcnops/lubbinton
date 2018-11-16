package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class RoundsScreen extends JPanel {

	private JPanel thisRoundPanel;
	private JPanel nextRoundPanel;

	public RoundsScreen() {
		this.setLayout(new BorderLayout());

		thisRoundPanel = new JPanel();
		thisRoundPanel.setLayout(new BorderLayout());
		thisRoundPanel.add(new JTextField("Loading..."), BorderLayout.CENTER);
		this.add(thisRoundPanel, BorderLayout.WEST);

		nextRoundPanel = new JPanel();
		nextRoundPanel.setLayout(new BorderLayout());
		nextRoundPanel.add(new JTextField("Loading..."), BorderLayout.CENTER);
		this.add(nextRoundPanel, BorderLayout.EAST);
	}

	protected void thisRound(@Nonnull final Round round) {
		thisRoundPanel.removeAll();

		final JPanel thisMatchesPanel = new JPanel();
		thisMatchesPanel.setLayout(new BoxLayout(thisMatchesPanel, BoxLayout.PAGE_AXIS));
		thisRoundPanel.add(thisMatchesPanel, BorderLayout.NORTH);
		round.getMatches().forEach(match -> thisRoundPanel.add(getMatchPanel(match, true)));


		final JPanel thisRestPanel = new JPanel();
		thisRoundPanel.add(thisRestPanel, BorderLayout.SOUTH);
		final JPanel restPanel = getRestPanel(round);
		thisRestPanel.add(restPanel);
	}


	protected void nextRound(@Nonnull final Round round) {
		nextRoundPanel.removeAll();

		final JPanel thisMatchesPanel = new JPanel();
		thisMatchesPanel.setLayout(new BoxLayout(thisMatchesPanel, BoxLayout.PAGE_AXIS));
		nextRoundPanel.add(thisMatchesPanel, BorderLayout.NORTH);
		round.getMatches().forEach(match -> nextRoundPanel.add(getMatchPanel(match, false)));


		final JPanel thisRestPanel = new JPanel();
		nextRoundPanel.add(thisRestPanel, BorderLayout.SOUTH);
		final JPanel restPanel = getRestPanel(round);
		thisRestPanel.add(restPanel);
	}

	private JPanel getMatchPanel(@Nonnull final Match match, final boolean withScore) {
		final JPanel matchPanel = new JPanel();
		matchPanel.setLayout(new BorderLayout());


		final JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		matchPanel.add(gamePanel, BorderLayout.WEST);

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


		if (withScore) {
			final JPanel scorePanel = new JPanel();
			scorePanel.setLayout(new BorderLayout());
			matchPanel.add(scorePanel, BorderLayout.EAST);

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
		}

		return matchPanel;
	}

	private JPanel getRestPanel(@Nonnull final Round round) {
		final JPanel restPanel = new JPanel();
		final JTextPane restPane = new JTextPane();
		restPane.setText("Rust: " + round.getRest().stream().map(Player::toString).collect(Collectors.joining(", ")));
		restPanel.add(restPane);
		return restPanel;
	}

}
