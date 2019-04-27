package es.deusto.spq.client.gui.views.guest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
/**
 * The view where guest make the payment
 * @author egoes
 *
 */
public class UserPayView extends View{

	//TODO ADD THE LOSCALIZATION ON ALL THE TEXT

	private JFrame frame;

	private JRadioButton rdbtnMasterCard, rdbtnPaypal;
	private JButton btnMakePayment, btnCancelPayment;
	private JTextField tFUsername, tFPassword;
	private JLabel lblUsername, lblPassword;
	private JPanel formPaypal, formMasterCard;
	private HotelManagementController controller;

	/**
	 * Launch the application.
	 */
	//TODO DELETE THIS IS ONLY FOR TESTING REASONS
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final UserPayView window = new UserPayView(null);
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Class constructor
	 * @param viewManager
	 */
	public UserPayView(ViewManager viewManager) {
		super(viewManager);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 307, 300);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		final JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.LIGHT_GRAY);
		final JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.LIGHT_GRAY);

		formPaypal = new JPanel();
		formMasterCard = new JPanel(new SpringLayout());

		// Selectors of payment method
		rdbtnMasterCard = new JRadioButton("Pay with MasterCard");
		topPanel.add(rdbtnMasterCard);

		rdbtnMasterCard.addActionListener((final ActionEvent e) -> {
			payWithMasterCard();
		});

		rdbtnPaypal = new JRadioButton("Pay with Paypal");
		topPanel.add(rdbtnPaypal);

		rdbtnPaypal.addActionListener((final ActionEvent e) -> {
			payWithPaypal();
		});

		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);

		// Buttons at the bottom
		btnMakePayment = new JButton("Make Payment");
		bottomPanel.add(btnMakePayment);

		btnMakePayment.addActionListener((final ActionEvent e) -> {
			handleMakePayment();
		});

		btnCancelPayment = new JButton("Cancel Payment");
		bottomPanel.add(btnCancelPayment);

		btnCancelPayment.addActionListener((final ActionEvent e) -> {
			handleCancelPayment();
		});

		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

		frame.getContentPane().add(formPaypal);
		final SpringLayout sl_formPaypal = new SpringLayout();
		formPaypal.setLayout(sl_formPaypal);

		//MasterCard fields
		//TODO add the MasterCard fields

		// Paypal fields
		lblUsername = new JLabel("Username");
		sl_formPaypal.putConstraint(SpringLayout.NORTH, lblUsername, 20, SpringLayout.NORTH, formPaypal);
		sl_formPaypal.putConstraint(SpringLayout.WEST, lblUsername, 54, SpringLayout.WEST, formPaypal);
		lblUsername.setBounds(41, 73, 60, 14);
		formPaypal.add(lblUsername);

		tFUsername = new JTextField();
		sl_formPaypal.putConstraint(SpringLayout.NORTH, tFUsername, 0, SpringLayout.NORTH, lblUsername);
		sl_formPaypal.putConstraint(SpringLayout.WEST, tFUsername, 44, SpringLayout.EAST, lblUsername);
		tFUsername.setBounds(141, 70, 86, 20);
		tFUsername.setColumns(10);
		formPaypal.add(tFUsername);

		lblPassword = new JLabel("Password");
		sl_formPaypal.putConstraint(SpringLayout.NORTH, lblPassword, 26, SpringLayout.SOUTH, lblUsername);
		sl_formPaypal.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblUsername);
		lblPassword.setBounds(41, 116, 60, 14);
		formPaypal.add(lblPassword);

		tFPassword = new JTextField();
		sl_formPaypal.putConstraint(SpringLayout.NORTH, tFPassword, -3, SpringLayout.NORTH, lblPassword);
		sl_formPaypal.putConstraint(SpringLayout.WEST, tFPassword, 0, SpringLayout.WEST, tFUsername);
		tFPassword.setBounds(141, 113, 86, 20);
		tFPassword.setColumns(10);
		formPaypal.add(tFPassword);
		formPaypal.setVisible(false);

	}

	/**
	 * When paying with paypal shows teh fields
	 */
	private void payWithPaypal() {
		if (rdbtnPaypal.isSelected()) {
			rdbtnMasterCard.setSelected(false);
			formPaypal.setVisible(true);
			formMasterCard.setVisible(false);
		} else
			formPaypal.setVisible(false);
	}

	/**
	 * When paying with master card show the fields
	 */
	private void payWithMasterCard() {
		if (rdbtnMasterCard.isSelected()) {
			rdbtnPaypal.setSelected(false);
			formPaypal.setVisible(false);
		} else
			formMasterCard.setVisible(false);
	}

	/**
	 * The reasons why validation can fail
	 */
	private enum ValidationFailReason{
		/**
		 *A required field is empty
		 */
		REQUIRED_FIELD_EMPTY,

		/**
		 * Payment method not selected
		 */
		SELECT_PAYMENT_METHOD
	}

	/**
	 * Handles the make payment button to proceed to the payment
	 */
	private void handleMakePayment() {
		//Disable Fields
		toggleFields(false);

		final JTextComponent[] componentsPaypal = {
                tFUsername,
                tFPassword,
        };

		final JTextComponent[] componentsMasterCard = {
				//TODO Add the master card fields
        };
		//Check that all the required field are filled,if not fails
		if(rdbtnMasterCard.isSelected()) {
			for(final JTextComponent component : componentsMasterCard)
				if(component.getText().trim().isEmpty()) {
					notifyValidationFail(ValidationFailReason.REQUIRED_FIELD_EMPTY);
					toggleFields(true);
					return;
				}
		}else if(rdbtnPaypal.isSelected()){
			for(final JTextComponent component : componentsPaypal)
				if(component.getText().trim().isEmpty()) {
					notifyValidationFail(ValidationFailReason.REQUIRED_FIELD_EMPTY);
					toggleFields(true);
					return;
				}
		}else {
			 notifyValidationFail(ValidationFailReason.SELECT_PAYMENT_METHOD);
			 toggleFields(true);
			 return;
		}

		//TODO Call the paymentMethod
	}

	/**
	 * Closes the view when Cancel Payment is clicked
	 */
	private void handleCancelPayment() {
		frame.dispose();
	}

	/**
	 * This is called to enable or disable the fields
	 * @param enable to select if the fields are enabled or not
	 */
	private void toggleFields(boolean enable) {
		tFUsername.setEnabled(enable);
		tFPassword.setEnabled(enable);
		btnMakePayment.setEnabled(enable);
		btnCancelPayment.setEnabled(enable);
		rdbtnMasterCard.setEnabled(enable);
		rdbtnPaypal.setEnabled(enable);
		//TODO add the master card fields
	}

	/**
	 * Handles the notification if it fails
	 * @param reason The reason why it failed
	 */
	private void notifyValidationFail(ValidationFailReason reason) {

        String messageKey;
        int messageType;

        switch (reason) {
            case REQUIRED_FIELD_EMPTY:
                messageKey = "register.validation.errors.required";
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            case SELECT_PAYMENT_METHOD:
                messageKey = "register.validation.errors.password-confirmation";
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            default:
                messageKey = "register.validation.errors.unknown";
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
        }

        //TODO ADD THE LOCALIZATION
        /*JOptionPane.showMessageDialog(frame,
                getViewManager().getClient().getLocaleManager().getMessage(messageKey),
                getViewManager().getClient().getLocaleManager().getMessage("register.validation.title"),
                messageType);*/
    }

	@Override
	public void refresh() {
		onLocalChange();
	}

	/**
	 * Changes the localization
	 */
	private void onLocalChange() {
		//TODO ADD THE LOCALIZATION
		lblUsername.setText("");
		lblPassword.setText("");
		btnCancelPayment.setText("");
		btnMakePayment.setText("");
		rdbtnMasterCard.setText("");
		rdbtnPaypal.setText("");
	}
}
