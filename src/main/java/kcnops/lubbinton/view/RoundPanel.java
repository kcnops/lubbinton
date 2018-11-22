package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Score;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoundPanel extends JPanel {

	private final boolean withScore;

	private JPanel bodyPanel;
	private List<MatchPanel> matchPanels = new ArrayList <>();

	protected RoundPanel(@Nonnull final String title,  final boolean withScore) {
		this.withScore = withScore;

		this.setLayout(new BorderLayout());

		final JTextField header = new JTextField(title);
		header.setEditable(false);
		this.add(header, BorderLayout.NORTH);

		emptyBody();
	}

	protected void emptyBody() {
		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BorderLayout());
		this.add(bodyPanel, BorderLayout.CENTER);

		bodyPanel.add(new JTextField("Loading..."));
	}

	protected void setGames(@Nonnull final Round round) {
		bodyPanel.removeAll();
		matchPanels = new ArrayList<>();

		final JPanel matchesPanel = new JPanel();
		matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.PAGE_AXIS));
		bodyPanel.add(matchesPanel, BorderLayout.CENTER);
		round.getMatches().forEach(match -> matchPanels.add(new MatchPanel(match, withScore)));
		matchPanels.forEach(matchesPanel::add);

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

	protected Map<Match, Score> getScores() {
		return matchPanels.stream().collect(Collectors.toMap(MatchPanel::getMatch, MatchPanel::getScore));
	}

	public boolean validateForNext() {
		return matchPanels.stream().allMatch(MatchPanel::validateForNext);
	}
}
