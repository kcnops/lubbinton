package kcnops.lubbinton.view.scores;

import kcnops.lubbinton.model.Player;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

	private JTextPane scorePanel;
	private JTextField pointsPanel;
	private JTextPane resultPanel;

	public ScorePanel(@Nonnull final Player player, @Nonnull final Integer score) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JTextPane namePanel = new JTextPane();
		namePanel.setText(player.toString());
		namePanel.setEditable(false);
		this.add(namePanel);

		scorePanel = new JTextPane();
		scorePanel.setText(score.toString());
		scorePanel.setEditable(false);
		this.add(scorePanel);

		pointsPanel = new JTextField();
		pointsPanel.setPreferredSize(new Dimension(30, 20));
		this.add(pointsPanel);

		resultPanel = new JTextPane();
		resultPanel.setEditable(false);
		this.add(resultPanel);
	}

	public void calculate() {
		if(scorePanel.getText() != null && pointsPanel != null) {
			int score =  Integer.parseInt(scorePanel.getText());
			int points =  Integer.parseInt(pointsPanel.getText());
			int result = score - points;
			resultPanel.setText("" + result);
		}
	}
}
