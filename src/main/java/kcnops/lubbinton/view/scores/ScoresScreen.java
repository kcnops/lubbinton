package kcnops.lubbinton.view.scores;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.view.LubbintonScreen;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoresScreen extends JPanel {

	private final LubbintonScreen frame;

	private final JPanel scorePanel;

	private final List<ScorePanel> scorePanels = new ArrayList<>();

	private final JButton calculateButton;

	public ScoresScreen(@Nonnull final LubbintonScreen frame) {
		this.frame = frame;
		this.setLayout(new BorderLayout());


		final JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(actionPanel, BorderLayout.SOUTH);

		calculateButton = new JButton();
		calculateButton.setText("Calculate");
		calculateButton.addActionListener(event -> calculatePressed());
		actionPanel.add(calculateButton);


		scorePanel = new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
		this.add(scorePanel, BorderLayout.CENTER);

	}

	private void calculatePressed() {
		scorePanels.forEach(ScorePanel::calculate);
	}

	public void setScores(@Nonnull final Map<Player, Integer> players) {
		players.forEach((player, score) -> {
			final ScorePanel scorePanel = new ScorePanel(player, score);
			scorePanels.add(scorePanel);
			this.scorePanel.add(scorePanel);
		});
	}
}
