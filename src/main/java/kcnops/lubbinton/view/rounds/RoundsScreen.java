package kcnops.lubbinton.view.rounds;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Score;
import kcnops.lubbinton.view.LubbintonScreen;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

public class RoundsScreen extends JPanel {

	private final LubbintonScreen frame;

	private final RoundPanel thisRoundPanel;
	private final RoundPanel nextRoundPanel;

	private final JButton nextRoundButton;
	private final JButton finishButton;


	public RoundsScreen(@Nonnull final LubbintonScreen frame) {
		this.frame = frame;

		this.setLayout(new BorderLayout());

		final JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(actionPanel, BorderLayout.SOUTH);

		nextRoundButton = new JButton();
		nextRoundButton.setText("Next Round");
		nextRoundButton.addActionListener(event -> nextPressed());
		actionPanel.add(nextRoundButton);

		finishButton = new JButton();
		finishButton.setText("Finish");
		finishButton.addActionListener(event -> finishPressed());
		actionPanel.add(finishButton);


		final JPanel roundsPanel = new JPanel();
		roundsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(roundsPanel, BorderLayout.CENTER);

		thisRoundPanel = new RoundPanel("This round", true);
		roundsPanel.add(thisRoundPanel);

		nextRoundPanel = new RoundPanel("Next round", false);
		roundsPanel.add(nextRoundPanel);
	}

	private void nextPressed() {
		nextRoundButton.setEnabled(false);
		if (!thisRoundPanel.validateForNext()) {
			nextRoundButton.setEnabled(true);
			return;
		}
		final Map<Match, Score> scores = thisRoundPanel.getScores();
		frame.nextPressed(scores);
	}

	private void finishPressed() {
		finishButton.setEnabled(false);
		if (!thisRoundPanel.validateForNext()) {
			finishButton.setEnabled(true);
			frame.finishPressed(Optional.empty());
		} else {
			finishButton.setEnabled(true);
			final Map<Match, Score> scores = thisRoundPanel.getScores();
			frame.finishPressed(Optional.of(scores));
		}
	}

	public void thisRound(@Nonnull final Round round) {
		thisRoundPanel.setGames(round);
	}


	public void nextRound(@Nonnull final Round round) {
		nextRoundPanel.setGames(round);
		nextRoundButton.setEnabled(true);
	}

	public void emptyNextRound() {
		nextRoundPanel.emptyBody();
	}
}
