package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class RoundPanel extends JPanel {

	private final boolean withScore;

	private JPanel bodyPanel;

	protected RoundPanel(@Nonnull final String title,  final boolean withScore) {
		this.withScore = withScore;

		this.setLayout(new BorderLayout());

		final JTextField header = new JTextField(title);
		header.setEditable(false);
		this.add(header, BorderLayout.NORTH);

		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BorderLayout());
		this.add(bodyPanel, BorderLayout.CENTER);

		bodyPanel.add(new JTextField("Loading..."));
	}

	protected void setGames(@Nonnull final Round round) {
		bodyPanel.removeAll();

		final JPanel matchesPanel = new JPanel();
		matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.PAGE_AXIS));
		bodyPanel.add(matchesPanel, BorderLayout.CENTER);
		round.getMatches().forEach(match -> matchesPanel.add(new MatchPanel(match, withScore)));

		final JPanel thisRestPanel = new JPanel();
		bodyPanel.add(thisRestPanel, BorderLayout.SOUTH);
		final JPanel restPanel = getRestPanel(round);
		thisRestPanel.add(restPanel);
	}

	private JPanel getRestPanel(@Nonnull final Round round) {
		final JPanel restPanel = new JPanel();
		final JTextPane restPane = new JTextPane();
		restPane.setText("Rust: " + round.getRest().stream().map(Player::toString).collect(Collectors.joining(", ")));
		restPanel.add(restPane);
		return restPanel;
	}


}
