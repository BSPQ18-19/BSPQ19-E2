package es.deusto.spq.client.gui.views.guest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.logger.ClientLogger;
/**
 * The view where guest make the payment
 * @author egoes
 *
 */
public class UserPayView extends View{

	private JFrame frame;

	private JRadioButton rdbtnMasterCard, rdbtnPaypal;
	private JButton btnMakePayment, btnCancelPayment;
	private JTextField tFUsername, tFPassword, tFCreditCardNumber, tFSecurityCode;
	private JLabel lblUsername, lblPassword, lblCreditCardNumber, lblSecurityCode;
	private JPanel form, formMasterCard;
	private final HotelManagementController controller;

	/**
	 * Class constructor
	 * @param viewManager
	 */
	public UserPayView(ViewManager viewManager) {
		super(viewManager);
		controller = viewManager.getClient().getController();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 307, 201);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		final JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.LIGHT_GRAY);
		final JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.LIGHT_GRAY);

		form = new JPanel();
		frame.getContentPane().add(form, BorderLayout.CENTER);

		// Selectors of payment method
		rdbtnMasterCard = new JRadioButton(getViewManager().getClient().getLocaleManager().getMessage("pay.radioButton.mastercard"));
		topPanel.add(rdbtnMasterCard);

		rdbtnMasterCard.addActionListener((final ActionEvent e) -> {
			payWithMasterCard();
		});

		rdbtnPaypal = new JRadioButton(getViewManager().getClient().getLocaleManager().getMessage("pay.radioButton.paypal"));
		topPanel.add(rdbtnPaypal);

		rdbtnPaypal.addActionListener((final ActionEvent e) -> {
			payWithPaypal();
		});

		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);

		// Buttons at the bottom
		btnMakePayment = new JButton(getViewManager().getClient().getLocaleManager().getMessage("pay.button.makePayment"));
		bottomPanel.add(btnMakePayment);

		btnMakePayment.addActionListener((final ActionEvent e) -> {
			handleMakePayment();
		});

		btnCancelPayment = new JButton(getViewManager().getClient().getLocaleManager().getMessage("pay.button.cancelPayment"));
		bottomPanel.add(btnCancelPayment);

		btnCancelPayment.addActionListener((final ActionEvent e) -> {
			handleCancelPayment();
		});

		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		form.setLayout(null);

		//MasterCard fields
		lblCreditCardNumber = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("pay.label.cardNumber"), JLabel.TRAILING);
		lblCreditCardNumber.setBounds(12, 28, 111, 16);
		lblCreditCardNumber.setVisible(false);
		form.add(lblCreditCardNumber);

		tFCreditCardNumber = new JTextField();
		tFCreditCardNumber.setBounds(141, 26, 121, 20);
		tFCreditCardNumber.setVisible(false);
		form.add(tFCreditCardNumber);
		tFCreditCardNumber.setColumns(10);

		lblSecurityCode = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("pay.label.securityCode"), JLabel.TRAILING);
		lblSecurityCode.setBounds(44, 53, 79, 16);
		lblSecurityCode.setVisible(false);
		form.add(lblSecurityCode);

		tFSecurityCode = new JTextField();
		tFSecurityCode.setBounds(141, 51, 121, 20);
		form.add(tFSecurityCode);
		tFSecurityCode.setVisible(false);
		tFSecurityCode.setColumns(10);

		// Paypal fields
		lblUsername = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("pay.label.username"), JLabel.TRAILING);
		lblUsername.setBounds(64, 28, 59, 16);
		lblUsername.setVisible(false);
		form.add(lblUsername);

		tFUsername = new JTextField();
		form.add(tFUsername);
		tFUsername.setBounds(141, 26, 121, 20);
		tFUsername.setVisible(false);
		tFUsername.setColumns(10);

		lblPassword = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("pay.label.password"), JLabel.TRAILING);
		lblPassword.setBounds(64, 53, 59, 16);
		lblPassword.setVisible(false);
		form.add(lblPassword);

		tFPassword = new JTextField();
		tFPassword.setBounds(141, 51, 121, 20);
		tFPassword.setColumns(10);
		tFPassword.setVisible(false);
		form.add(tFPassword);
	}

	/**
	 * When paying with paypal shows the fields
	 */
	private void payWithPaypal() {
		if (rdbtnPaypal.isSelected()) {
			rdbtnMasterCard.setSelected(false);

			//To show
			lblUsername.setVisible(true);
			tFUsername.setVisible(true);
			lblPassword.setVisible(true);
			tFPassword.setVisible(true);

			//To hide
			lblCreditCardNumber.setVisible(false);
			tFCreditCardNumber.setVisible(false);
			lblSecurityCode.setVisible(false);
			tFCreditCardNumber.setVisible(false);

		} else {
			//To hide
			lblUsername.setVisible(false);
			tFUsername.setVisible(false);
			lblPassword.setVisible(false);
			tFPassword.setVisible(false);
		}
	}

	/**
	 * When paying with master card show the fields
	 */
	private void payWithMasterCard() {
		if (rdbtnMasterCard.isSelected()) {
			rdbtnPaypal.setSelected(false);

			//To show
			lblCreditCardNumber.setVisible(true);
			tFCreditCardNumber.setVisible(true);
			lblSecurityCode.setVisible(true);
			tFSecurityCode.setVisible(true);

			//To hide
			lblUsername.setVisible(false);
			tFUsername.setVisible(false);
			lblPassword.setVisible(false);
			tFPassword.setVisible(false);

		} else {
			//To hide
			lblCreditCardNumber.setVisible(false);
			tFCreditCardNumber.setVisible(false);
			lblSecurityCode.setVisible(false);
			tFSecurityCode.setVisible(false);
		}
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
		SELECT_PAYMENT_METHOD,

		/**
		 * 
		 */
		MASTERCARD_FIELDS_NOT_NUMBERS
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
				tFCreditCardNumber,
				tFSecurityCode,
        };
		//Check that all the required field are filled,if not fails
		if(rdbtnMasterCard.isSelected()) {
			for(final JTextComponent component : componentsMasterCard)
				if(component.getText().trim().isEmpty()) {
					notifyValidationFail(ValidationFailReason.REQUIRED_FIELD_EMPTY);
					toggleFields(true);
					return;
				}
			try {
				long cardNumber = Long.parseLong(tFCreditCardNumber.getText());
				int securityCode = Integer.parseInt(tFSecurityCode.getText());
			}catch (NumberFormatException e) {
				notifyValidationFail(ValidationFailReason.MASTERCARD_FIELDS_NOT_NUMBERS);
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

		//Call Payment
		if(rdbtnMasterCard.isSelected()) {
			String cardNumber = tFCreditCardNumber.getText();
			String securityCode = tFSecurityCode.getText();
			if(!controller.payReservation(cardNumber, securityCode, 0, false)) {
				ClientLogger.getLogger().fatal("Payment cant be done...");
	            JOptionPane.showMessageDialog(frame,
	                    getViewManager().getClient().getLocaleManager().getMessage("pay.validation.errors.unknown"),
	                    getViewManager().getClient().getLocaleManager().getMessage("pay.validation.errors.unknown.title"),
	                    JOptionPane.ERROR_MESSAGE);

	            frame.dispose();
	            return;
			}
		}else if(rdbtnPaypal.isSelected()) {
			String username = tFUsername.getText();
			String password = tFPassword.getText();
			//TODO AMOUNT
			if(!controller.payReservation(username, password, 0, true)) {
				ClientLogger.getLogger().fatal("Payment cant be done...");
	            JOptionPane.showMessageDialog(frame,
	                    getViewManager().getClient().getLocaleManager().getMessage("pay.validation.errors.unknown"),
	                    getViewManager().getClient().getLocaleManager().getMessage("pay.validation.errors.unknown.title"),
	                    JOptionPane.ERROR_MESSAGE);

	            frame.dispose();
	            return;
			}
		}

		// Success!
        JOptionPane.showMessageDialog(frame,
                getViewManager().getClient().getLocaleManager().getMessage("pay.success.body"),
                getViewManager().getClient().getLocaleManager().getMessage("pay.success.title"),
                JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
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
		lblCreditCardNumber.setEnabled(enable);
		tFCreditCardNumber.setEnabled(enable);
		lblSecurityCode.setEnabled(enable);
		tFSecurityCode.setEnabled(enable);
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
                messageKey = "pay.validation.errors.required";
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            case SELECT_PAYMENT_METHOD:
                messageKey = "pay.validation.errors.noMethod";
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            case MASTERCARD_FIELDS_NOT_NUMBERS:
            	messageKey = "pay.validation.errors.notNumbers";
            	messageType = JOptionPane.WARNING_MESSAGE;
            	break;
            default:
                messageKey = "pay.validation.errors.unknown";
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
        }

        JOptionPane.showMessageDialog(frame,
                getViewManager().getClient().getLocaleManager().getMessage(messageKey),
                getViewManager().getClient().getLocaleManager().getMessage("pay.validation.title"),
                messageType);
    }

	@Override
	public void refresh() {
		onLocalChange();
	}

	/**
	 * Changes the localization
	 */
	private void onLocalChange() {
		lblUsername.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.label.username"));
		lblPassword.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.label.password"));
		btnCancelPayment.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.button.cancelPayment"));
		btnMakePayment.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.button.makePayment"));
		rdbtnMasterCard.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.radioButton.mastercard"));
		rdbtnPaypal.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.radioButton.paypal"));
		lblCreditCardNumber.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.label.cardNumber"));
		lblSecurityCode.setText(getViewManager().getClient().getLocaleManager().getMessage("pay.label.securityCode"));
	}
}
