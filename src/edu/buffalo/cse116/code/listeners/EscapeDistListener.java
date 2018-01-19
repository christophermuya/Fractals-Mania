package edu.buffalo.cse116.code.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.buffalo.cse116.code.view.UserInterface;

public class EscapeDistListener implements ActionListener {
private UserInterface ui;
	public EscapeDistListener(UserInterface ui){
		this.ui = ui;
	}
	public void actionPerformed(ActionEvent arg0) {
		ui.escapeDistDialog();
	}

}
