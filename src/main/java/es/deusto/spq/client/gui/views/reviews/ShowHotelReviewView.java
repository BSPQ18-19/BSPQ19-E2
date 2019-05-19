package es.deusto.spq.client.gui.views.reviews;
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
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View for showing the Reviews of the previously selected Hotel
 * @author egoes
 *
 */
public class ShowHotelReviewView extends View {

	private JInternalFrame frame;
    private Logger log;
    private HotelManagementController controller;
    private List<ReviewDTO> reviewsList;
    private JScrollPane scrollPane;
    private JEditorPane editorPane;
    private JLabel lblTittle;
    private JLabel lblAverageScore;
    private double averageScore;
    private String hotelID;
    

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
		 //TODO Change it
	        return ViewPermission.NONE;
	    }

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
		displayReviews();
	}

	@Override
	public ViewType getViewType() {
		return ViewType.SHOW_REVIEWS;
	}

	@Override
	public boolean isUnique() {
		return true;
	}

	@Override
	public @Nullable JInternalFrame getInternalFrame() {
		return frame;
	}

	@Override
	public void bringToFront() {
		getInternalFrame().toFront();
	}

	@Override
	public void dispose() {
		try {
			getInternalFrame().dispose();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JInternalFrame();
		frame.setBounds(100, 100, 492, 312);
		frame.setClosable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 453, 163);
		frame.getContentPane().add(scrollPane);

		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setText("\n\n"+getViewManager().getClient().getLocaleManager().getMessage("showReviews.noReviews"));
		scrollPane.setViewportView(editorPane);
		
		lblTittle = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("showReviews.tittle"), JLabel.TRAILING);
		lblTittle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTittle.setBounds(191, 11, 85, 25);
		frame.getContentPane().add(lblTittle);
		
		//Displays the Average Score
		averageScore = 0;
		lblAverageScore = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("showReviews.averageScore")+" "+averageScore, JLabel.TRAILING);
		lblAverageScore.setBounds(191, 47, 85, 25);
		frame.getContentPane().add(lblAverageScore);
		
		frame.setVisible(true);
	}

	private void displayReviews() {

		reviewsList = new ArrayList<ReviewDTO>();
		String opinions = "";

		//Gets the review list
		reviewsList = controller.retrieveReviews(hotelID);
		averageScore = 0;

		if(reviewsList == null || reviewsList.isEmpty()) {
			//There are no reviews
			
			//TODO THIS MAKES AN ERROR
			editorPane.setText("\n\n"+getViewManager().getClient().getLocaleManager().getMessage("showReviews.noReviews"));
			return;
		}else {
			//There are reviews
			for(ReviewDTO r : reviewsList) {
				averageScore += r.getScore();
				opinions = opinions + r.getScore() +"-"+ r.getOpinion() +"\n\n";
			}
			
			//TODO THIS MAKES AN ERROR
			editorPane.setText(opinions);
			averageScore = averageScore/reviewsList.size();
			lblAverageScore.setText(getViewManager().getClient().getLocaleManager().getMessage("showReviews.averageScore")+averageScore);
			
		}
	}

	@Override
    public void refresh() {
        onLocaleChange();
    }

    /**
     * Update all the localized text
     */
    private void onLocaleChange() {
    	lblTittle.setText(getViewManager().getClient().getLocaleManager().getMessage("showReviews.tittle"));
    	lblAverageScore.setText(getViewManager().getClient().getLocaleManager().getMessage("showReviews.averageScore"));
    	if(reviewsList.isEmpty() || reviewsList == null) {
    		editorPane.setText("\n\n"+getViewManager().getClient().getLocaleManager().getMessage("showReviews.noReviews"));
    	}
    }
}
