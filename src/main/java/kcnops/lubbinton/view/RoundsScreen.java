package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Score;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class RoundsScreen extends JPanel {

	private final LubbintonScreen frame;

	private final RoundPanel thisRoundPanel;
	private final RoundPanel nextRoundPanel;

	private final JButton nextRoundButton;


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

		final JButton finishButton = new JButton();
		finishButton.setText("Finish");
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
		Map<Match, Score> scores = thisRoundPanel.getScores();
		frame.nextPressed(scores);
	}

	protected void thisRound(@Nonnull final Round round) {
		thisRoundPanel.setGames(round);
	}


	protected void nextRound(@Nonnull final Round round) {
		nextRoundPanel.setGames(round);
		nextRoundButton.setEnabled(true);
	}

	protected void emptyNextRound() {
		nextRoundPanel.emptyBody();
	}
}
