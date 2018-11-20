package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Round;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class RoundsScreen extends JPanel {

	private RoundPanel thisRoundPanel;
	private RoundPanel nextRoundPanel;

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
		roundsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(roundsPanel, BorderLayout.CENTER);

		thisRoundPanel = new RoundPanel("This round", true);
		roundsPanel.add(thisRoundPanel);

		nextRoundPanel = new RoundPanel("Next round", false);
		roundsPanel.add(nextRoundPanel);
	}

	protected void thisRound(@Nonnull final Round round) {
		thisRoundPanel.setGames(round);
	}


	protected void nextRound(@Nonnull final Round round) {
		nextRoundPanel.setGames(round);
	}

}
