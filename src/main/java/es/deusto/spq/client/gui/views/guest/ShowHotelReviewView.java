package es.deusto.spq.client.gui.views.guest;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import javax.swing.JScrollPane;


import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.ReviewDTO;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowHotelReviewView extends View {

	private JInternalFrame frame;
    private Logger log;
    private HotelManagementController controller;
    private List<ReviewDTO> reviewsList;
    

	/**
	 * Create the application.
	 */
	public ShowHotelReviewView(ViewManager viewManager) {
        super(viewManager);

        log = ClientLogger.getLogger();
        this.controller = viewManager.getClient().getController();
	}

	 @Override
	    public ViewPermission getViewPermission() {
	        return ViewPermission.NOT_LOGGED_IN;
	    }

	    @Override
	    public ViewType getViewType() {
	        return ViewType.LOGIN;
	    }

	    @Override
	    public boolean isUnique() {
	        return true;
	    }

	    @Override
	    public @Nullable JInternalFrame getInternalFrame() {
	        return frame;
	    }

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JInternalFrame();
		frame.setBounds(100, 100, 492, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 453, 163);
		frame.getContentPane().add(scrollPane);
		
		JEditorPane editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
		JLabel lblNewLabel = new JLabel("Reviews");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(191, 11, 85, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblAverageScore = new JLabel("Average Score: 10");
		lblAverageScore.setBounds(191, 47, 108, 14);
		frame.getContentPane().add(lblAverageScore);
		
	}
}
