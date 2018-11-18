package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class RoundPanel extends JPanel {

	private JPanel bodyPanel;

	protected RoundPanel(@Nonnull final String title) {
		this.setLayout(new BorderLayout());

		final JTextField header = new JTextField(title);
		header.setEditable(false);
		this.add(header, BorderLayout.NORTH);

		bodyPanel = new JPanel();
		this.add(bodyPanel, BorderLayout.CENTER);

		bodyPanel.add(new JTextField("Loading..."));
	}

	protected void setGames(@Nonnull final Round round) {
		bodyPanel.removeAll();

		final JPanel thisMatchesPanel = new JPanel();
		thisMatchesPanel.setLayout(new BoxLayout(thisMatchesPanel, BoxLayout.PAGE_AXIS));
		bodyPanel.add(thisMatchesPanel, BorderLayout.CENTER);
		round.getMatches().forEach(match -> this.add(getMatchPanel(match)));

		final JPanel thisRestPanel = new JPanel();
		bodyPanel.add(thisRestPanel, BorderLayout.SOUTH);
		final JPanel restPanel = getRestPanel(round);
		thisRestPanel.add(restPanel);
	}

	private JPanel getMatchPanel(@Nonnull final Match match) {
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
