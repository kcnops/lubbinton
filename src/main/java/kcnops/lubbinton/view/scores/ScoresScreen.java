package kcnops.lubbinton.view.scores;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.view.LubbintonScreen;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ScoresScreen extends JPanel {

	private final LubbintonScreen frame;

	private final JPanel scorePanel;

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
		this.add(scorePanel, BorderLayout.CENTER);

	}

	private void calculatePressed() {

	}

	public void setScores(final Map<Player, Integer> players) {

	}
}
