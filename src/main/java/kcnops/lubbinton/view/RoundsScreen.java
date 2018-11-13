package kcnops.lubbinton.view;

import kcnops.lubbinton.model.Setup;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class RoundsScreen extends JPanel {

	public RoundsScreen() {
		this.setLayout(new BorderLayout());
	}

	protected void thisRound(@Nonnull final Setup setup) {
		System.out.println(setup);
	}

	protected void nextRound(@Nonnull final Setup setup) {

	}

}
