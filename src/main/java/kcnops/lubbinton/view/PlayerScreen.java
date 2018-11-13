package kcnops.lubbinton.view;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerScreen extends JPanel {

	private static final int PLAYER_BUTTON_HEIGHT = 50;
	private static final int PLAYER_BUTTON_WIDTH = 150;
	private static final int NEW_PLAYER_TEXTFIELD_WIDTH = 10;

	private List<JToggleButton> playerButtons = new ArrayList<>();

	public PlayerScreen(@Nonnull final LubbintonScreen frame, @Nonnull final List<String> playerNames) {

		this.setLayout(new BorderLayout());


		final JPanel playerButtonsPanel = new JPanel();
		this.add(playerButtonsPanel, BorderLayout.CENTER);
		playerButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		playerNames.forEach(playerName -> {
			final JToggleButton playerButton = new JToggleButton();
			playerButtons.add(playerButton);
			playerButton.setPreferredSize(new Dimension(PLAYER_BUTTON_WIDTH, PLAYER_BUTTON_HEIGHT));
			playerButton.setText(playerName);
			playerButtonsPanel.add(playerButton);
		});


		final JPanel playerActionPanel = new JPanel();
		this.add(playerActionPanel, BorderLayout.SOUTH);
		playerActionPanel.setLayout(new BorderLayout());

		final JPanel addPlayerPanel = new JPanel();
		playerActionPanel.add(addPlayerPanel, BorderLayout.WEST);

		final JTextField addPlayerTextField = new JTextField(NEW_PLAYER_TEXTFIELD_WIDTH);
		addPlayerPanel.add(addPlayerTextField);
		final JButton addPlayerButton = new JButton();
		addPlayerButton.setText("Add Player");
		addPlayerButton.addActionListener(e -> {
			final String newPlayerName = addPlayerTextField.getText();
			if(!newPlayerName.isEmpty()) {
				final JToggleButton playerButton = new JToggleButton();
				playerButtons.add(playerButton);
				playerButton.setPreferredSize(new Dimension(PLAYER_BUTTON_WIDTH, PLAYER_BUTTON_HEIGHT));
				playerButton.setText(newPlayerName);
				playerButtonsPanel.add(playerButton);
				addPlayerTextField.setText("");
				frame.pack();
			}
		});
		addPlayerPanel.add(addPlayerButton);

		final JPanel startPanel = new JPanel();
		playerActionPanel.add(startPanel, BorderLayout.EAST);
		final JButton startButton = new JButton();
		startButton.setText("Start");
		startButton.addActionListener(e -> {
			final List<String> selectedPlayers = playerButtons.stream()
					.filter(JToggleButton::isSelected)
					.map(JToggleButton::getText)
					.collect(Collectors.toList());
			frame.start(selectedPlayers);
		});

		startPanel.add(startButton);

	}


}
