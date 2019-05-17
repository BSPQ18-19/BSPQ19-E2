package es.deusto.spq.client.gui.views.reviews;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jetbrains.annotations.Nullable;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(28, 74, 440, 147);
		frame.getContentPane().add(editorPane);
		
		JButton btnSendReview = new JButton("Summit review");
		btnSendReview.setBounds(186, 246, 161, 23);
		frame.getContentPane().add(btnSendReview);
		btnSendReview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Call the method that handles new Reviews
				frame.dispose();
			}
		});
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0,0,10,1));
		spinner.setBounds(73, 32, 43, 31);
		frame.getContentPane().add(spinner);
		
		JLabel lblNewLabel = new JLabel("Score");
		lblNewLabel.setBounds(73, 11, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblWritteYourReview = new JLabel("Writte your review");
		lblWritteYourReview.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWritteYourReview.setBounds(202, 40, 123, 23);
		frame.getContentPane().add(lblWritteYourReview);
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
	       
	    }
}
