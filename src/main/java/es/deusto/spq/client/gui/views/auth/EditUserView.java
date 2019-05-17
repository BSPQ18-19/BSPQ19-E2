package es.deusto.spq.client.gui.views.auth;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.util.SpringUtilities;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.UserDTO;

/**
 * The GUI for editing the user data
 *
 * @author egoes
 *
 */
public class EditUserView extends View {

	/**
	 *
	 * @param viewManager The class that manages the view
	 */
	public EditUserView(final ViewManager viewManager) {
		super(viewManager);
		hotelManagementController = viewManager.getClient().getController();
	}

	private JInternalFrame frame;
	private final HotelManagementController hotelManagementController;

	// All the form fields and buttons
	private JTextField nameTextField, emailTextField, phoneTextField, addressTextField;
	private JPasswordField passwordField, passwordConfirmationField;
	private JButton saveChanges, dontSave;
	 private JLabel nameLabel, emailLabel, passwordLabel, passwordConfirmationLabel, phoneLabel, addressLabel;

	// Logger
	private Logger log;

	@Override
	public ViewType getViewType() {
		return ViewType.EDIT_USER;
	}

	@Override
	public ViewPermission getViewPermission() {
		// TODO change for LOGGED_IN_GUEST when permisions working
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

	@Override
	public void dispose() {
        try {
            getInternalFrame().dispose();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * All the Swing code for showing the actual frame
	 */
	@Override
	public void initialize() {
		frame = new JInternalFrame();
		frame.setClosable(true);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// The container is using BorderLayout so that
		final JPanel container = new JPanel(new BorderLayout());

		final JPanel form = new JPanel(new SpringLayout());

		final JPanel bottomButtons = new JPanel(new BorderLayout());

		// Name field
		nameLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.name"), JLabel.TRAILING);
		form.add(nameLabel);
		nameTextField = new JTextField(10);
		nameLabel.setLabelFor(nameTextField);
		form.add(nameTextField);

		// Email field
		emailLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.email"), JLabel.TRAILING);
		form.add(emailLabel);
		emailTextField = new JTextField(10);
		emailLabel.setLabelFor(emailTextField);
		form.add(emailTextField);

		// Password field
		passwordLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.password"), JLabel.TRAILING);
		form.add(passwordLabel);
		passwordField = new JPasswordField(10);
		passwordLabel.setLabelFor(passwordField);
		form.add(passwordField);

		// Password confirmation field
		passwordConfirmationLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.password-confirmation"), JLabel.TRAILING);
		form.add(passwordConfirmationLabel);
		passwordConfirmationField = new JPasswordField(10);
		passwordConfirmationLabel.setLabelFor(passwordConfirmationField);
		form.add(passwordConfirmationField);

		// Phone field
		phoneLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.phone"), JLabel.TRAILING);
		form.add(phoneLabel);
		phoneTextField = new JTextField(10);
		phoneLabel.setLabelFor(phoneTextField);
		form.add(phoneTextField);

		// Address field
		addressLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.address"), JLabel.TRAILING);
		form.add(addressLabel);
		addressTextField = new JTextField(10);
		addressLabel.setLabelFor(addressTextField);
		form.add(addressTextField);

		// Lay out the form and make it a nice "responsive" grid
		SpringUtilities.makeCompactGrid(form, 6, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		// Add the form to the center slot
		container.add(form, BorderLayout.CENTER);

		// Save Changes button
		saveChanges = new JButton(getViewManager().getClient().getLocaleManager().getMessage("editUser.saveChanges"));
		bottomButtons.add(saveChanges, BorderLayout.PAGE_START); // add it to the bottom

		// Register the click function
		saveChanges.addActionListener((final ActionEvent e) -> {
			handleFormSubmission();
		});

		dontSave = new JButton(getViewManager().getClient().getLocaleManager().getMessage("editUser.dontSave"));
		bottomButtons.add(dontSave, BorderLayout.PAGE_END); // add it to the bottom

		// Register the click function
		dontSave.addActionListener((final ActionEvent e) -> {
			closeWindow();
		});

		container.add(bottomButtons, BorderLayout.PAGE_END);

		frame.add(container);

		frame.setVisible(true);
		frame.toFront();
		addDisposeEventHandler();
	}

	/**
	 * Closes the window
	 */
	private void closeWindow() {
		frame.dispose();
	}

	/**
	 * Show and bring to focus the window
	 */
	void show() {
		frame.setVisible(true);
		frame.requestFocus();
	}

	/**
	 * Represents a reason of why the validation might fail
	 */
	private enum ValidationFailReason {
		/**
		 * All fields are empty
		 */
		NO_CHANGES_MADE,

		/**
		 * The password confirmation is not the same as the password
		 */
		PASSWORD_CONFIRMATION_DIFFERENT
	}

	/**
	 * All the logic for edit User data: validates, sends data to server, checks
	 * response
	 */
	private void handleFormSubmission() {

		// Disable all fields
		toggleFields(false);

		// Fields
		final JTextComponent[] components = { nameTextField, emailTextField, phoneTextField, addressTextField,
				passwordField, passwordConfirmationField, };

		// Check that at least one field have been edited
		boolean allEmpty = true;
		for (final JTextComponent component : components)
			if (component.getText().trim().isEmpty())
				allEmpty = true;
			else {
				allEmpty = false;
				break;
			}
		if (allEmpty) {
			notifyValidationFail(ValidationFailReason.NO_CHANGES_MADE);
			// Re-enable fields
			toggleFields(true);
			return;
		}

		// Check if the password confirmation matches the password, and if not, fail
		if (!(new String(passwordField.getPassword())).equals(new String(passwordConfirmationField.getPassword()))
				&& (!passwordConfirmationField.getText().trim().isEmpty()
						| !passwordField.getText().trim().isEmpty())) {
			notifyValidationFail(ValidationFailReason.PASSWORD_CONFIRMATION_DIFFERENT);
			// Re-enable fields
			toggleFields(true);
			return;
		}

		// Send the new properties of the current user to the server
		UserDTO result = null;
		result = hotelManagementController.updateUser(nameTextField.getText(), emailTextField.getText(),
				new String(passwordField.getPassword()), phoneTextField.getText(), addressTextField.getText());

		// If it fails, tell the user and dispose the frame
		if (result == null) {
			ClientLogger.getLogger().fatal("User data not updated...");
			JOptionPane.showMessageDialog(frame, getViewManager().getClient().getLocaleManager().getMessage("editUser.validation.errors.unknown"),
					getViewManager().getClient().getLocaleManager().getMessage("editUser.validation.errors.unknown.title"), JOptionPane.ERROR_MESSAGE);

			frame.dispose();
			return;
		}

		// Success!
		JOptionPane.showMessageDialog(frame, getViewManager().getClient().getLocaleManager().getMessage("editUser.success.body"),
				getViewManager().getClient().getLocaleManager().getMessage("editUser.success.title"), JOptionPane.INFORMATION_MESSAGE);
		frame.dispose();

	}

	/**
	 * Show a dialog informing about a form validation failure
	 *
	 * @param reason the reason why the validation has failed
	 */
	private void notifyValidationFail(final ValidationFailReason reason) {

		String messageKey;
		int messageType;

		switch (reason) {
		case NO_CHANGES_MADE:
			messageKey = "editUser.validation.errors.noChanges";
			messageType = JOptionPane.WARNING_MESSAGE;
			break;
		case PASSWORD_CONFIRMATION_DIFFERENT:
			messageKey = "editUser.validation.errors.password-confirmation";
			messageType = JOptionPane.WARNING_MESSAGE;
			break;
		default:
			messageKey = "editUser.validation.errors.unknown";
			messageType = JOptionPane.ERROR_MESSAGE;
			break;
		}

		JOptionPane.showMessageDialog(frame, getViewManager().getClient().getLocaleManager().getMessage(messageKey),
				getViewManager().getClient().getLocaleManager().getMessage("editUser.validation.title"), messageType);
	}

	/**
	 * Enable or disable all the fields in the form
	 *
	 * @param enable whether or not to leave enabled the fields
	 */
	private void toggleFields(final boolean enable) {
		nameTextField.setEnabled(enable);
		emailTextField.setEnabled(enable);
		passwordConfirmationField.setEnabled(enable);
		passwordField.setEnabled(enable);
		phoneTextField.setEnabled(enable);
		addressTextField.setEnabled(enable);
		saveChanges.setEnabled(enable);
		dontSave.setEnabled(enable);
	}



	@Override
	public void bringToFront() {
		getInternalFrame().toFront();
	}

	@Override
    public void refresh() {
        onLocaleChange();
    }

	/**
	 * Apply localization changes
	 */
    private void onLocaleChange() {
        nameLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.name"));
        emailLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.email"));
        passwordLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.password"));
        passwordConfirmationLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.password-confirmation"));
        phoneLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.phone"));
        addressLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.label.address"));
        saveChanges.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.saveChanges"));
        dontSave.setText(getViewManager().getClient().getLocaleManager().getMessage("editUser.dontSave"));
    }
}
