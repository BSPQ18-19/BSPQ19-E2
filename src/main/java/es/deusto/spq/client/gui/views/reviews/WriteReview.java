package es.deusto.spq.client.gui.views.reviews;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.server.data.dto.ReviewDTO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class WriteReview extends View{
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public WriteReview(ViewManager viewManager) {
        super(viewManager);
        hotelManagementController = viewManager.getClient().getController();
    }

	private JInternalFrame frame;
    private HotelManagementController hotelManagementController;
    
    // Logger
    private Logger log;
    
    //View Components
    private JEditorPane editorPane;
    private JButton btnSendReview ;
    private JSpinner spinner;
    private JLabel lblScore;
    private JLabel lblWritteYourReview ;
    private String hotelID, userID;

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
    public ViewType getViewType() {
        return ViewType.WRITE_REVIEW;
    }

    @Override
    public ViewPermission getViewPermission() {
    	//TODO Change
        return ViewPermission.NONE;
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
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		frame = new JInternalFrame();
		frame.setBounds(100, 100, 517, 337);
		frame.setClosable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(28, 74, 440, 147);
		frame.getContentPane().add(editorPane);
		
		btnSendReview = new JButton(getViewManager().getClient().getLocaleManager().getMessage("writeReview.summit"));
		btnSendReview.setBounds(186, 246, 161, 23);
		frame.getContentPane().add(btnSendReview);
		btnSendReview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(editorPane.getText().equals("")) {
					JOptionPane.showMessageDialog(frame,
		                    getViewManager().getClient().getLocaleManager().getMessage("writeReview.noText"),
		                    getViewManager().getClient().getLocaleManager().getMessage("writeReview.noText.tittle"),
		                    JOptionPane.ERROR_MESSAGE);
					return;
				}
				int score = (int) spinner.getValue();
				try {
					ReviewDTO review= hotelManagementController.createReview(editorPane.getText(), score, "", "");
					if(review == null) {
						JOptionPane.showMessageDialog(frame,
			                    getViewManager().getClient().getLocaleManager().getMessage("writeReview.error"),
			                    getViewManager().getClient().getLocaleManager().getMessage("writeReview.error.title"),
			                    JOptionPane.ERROR_MESSAGE);
						frame.dispose();
					}else {
						 JOptionPane.showMessageDialog(frame,
					                getViewManager().getClient().getLocaleManager().getMessage("writeReview.success"),
					                getViewManager().getClient().getLocaleManager().getMessage("writeReview.success.title"),
					                JOptionPane.INFORMATION_MESSAGE);
					        frame.dispose();
					}
				}catch (Exception ex) {
					log.fatal("Error creating a new resevation: " + ex.getMessage());
				}
			}
		});
		
		spinner = new JSpinner(new SpinnerNumberModel(0,0,10,1));
		spinner.setBounds(73, 32, 43, 31);
		frame.getContentPane().add(spinner);
		
		lblScore = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("writeReview.score"), JLabel.TRAILING);
		lblScore.setBounds(73, 11, 46, 14);
		frame.getContentPane().add(lblScore);
		
		lblWritteYourReview = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("writeReview.descrition"), JLabel.TRAILING);
		lblWritteYourReview.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWritteYourReview.setBounds(202, 40, 123, 23);
		frame.getContentPane().add(lblWritteYourReview);
		frame.setVisible(true);
	}

	 @Override
	    public void bringToFront() {
	        getInternalFrame().toFront();
	    }

	    @Override
	    public void refresh() {
	        onLocaleChange();
	    }

	    private void onLocaleChange() {
	       btnSendReview.setText(getViewManager().getClient().getLocaleManager().getMessage("writeReview.summit"));
	       lblScore.setText(getViewManager().getClient().getLocaleManager().getMessage("writeReview.score"));
	       lblWritteYourReview.setText(getViewManager().getClient().getLocaleManager().getMessage("writeReview.descrition"));
	    }
}
