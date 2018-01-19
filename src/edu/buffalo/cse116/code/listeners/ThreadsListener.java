package edu.buffalo.cse116.code.listeners;
import edu.buffalo.cse116.code.view.UserInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ThreadsListener implements ActionListener {
	private UserInterface ui;
	public ThreadsListener(UserInterface ui){
		this.ui = ui;
	}
	public void actionPerformed(ActionEvent arg0) {
		ui.threadsDialog();
	}
}
