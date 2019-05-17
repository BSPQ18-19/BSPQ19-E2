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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowHotelReviewView extends View {

	private JInternalFrame frame;
    private Logger log;
    private HotelManagementController controller;
    private List<ReviewDTO> reviewsList;
    private JScrollPane scrollPane;
    private JEditorPane editorPane;
    private JLabel lblTittle;
    private JLabel lblAverageScore;
    private int averageScore;
    

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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 453, 163);
		frame.getContentPane().add(scrollPane);
		
		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
		lblTittle = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("showReviews.tittle"));
		lblTittle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTittle.setBounds(191, 11, 85, 25);
		frame.getContentPane().add(lblTittle);
		
		lblAverageScore = new JLabel();
		lblAverageScore.setBounds(191, 47, 108, 14);
		frame.getContentPane().add(lblAverageScore);

		reviewsList = new ArrayList<ReviewDTO>();
		//TODO Add the actual hotelID
		reviewsList = controller.retrieveReviews("");
		averageScore = 0;
		if(reviewsList.isEmpty() || reviewsList == null) {
			editorPane.setText("\n\n"+getViewManager().getClient().getLocaleManager().getMessage("showReviews.noReviews"));
		}else if(reviewsList.size() == 1) {
			editorPane.setText(reviewsList.get(0).getScore()+"-"+reviewsList.get(0).getOpinion());
			averageScore = reviewsList.get(0).getScore();
		}else {
			String opinion;
			String previousText;

			opinion = reviewsList.get(0).getScore() +"-"+reviewsList.get(0).getOpinion();
			averageScore = reviewsList.get(0).getScore();
			for(int n = 1; n < reviewsList.size(); n++) {
				averageScore += reviewsList.get(n).getScore();
				previousText = editorPane.getText();
				opinion = reviewsList.get(n).getScore() +"-"+ reviewsList.get(n).getOpinion();
				editorPane.setText(previousText+"\n\n"+opinion);
			}
			averageScore = averageScore/reviewsList.size();
		}
		lblAverageScore.setText(getViewManager().getClient().getLocaleManager().getMessage("showReviews.averageScore")+averageScore);
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
