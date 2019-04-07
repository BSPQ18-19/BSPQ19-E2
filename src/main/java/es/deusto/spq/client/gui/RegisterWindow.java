package es.deusto.spq.client.gui;

import es.deusto.spq.client.gui.forms.SpringUtilities;
import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.locale.LocaleManager;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Logger;

/**
 * The Register window.
 * I know it's ugly, but I was in a hurry. Sorry about that :'(
 * Suggestions for improvement:
 * - Use an actual form system
 * - Make a validation system
 * @author Iñigo Apellániz, Iker Barriocanal
 */
public class RegisterWindow {

    private JFrame frame;
    private HotelManagementController hotelManagementController;

    // All the form fields and buttons
    private JTextField nameTextField, emailTextField, phoneTextField, addressTextField;
    private JPasswordField passwordField, passwordConfirmationField;
    private JButton submitButton;

    // Logger
    private Logger log;

    /**
     * Instantiates a new registration window and shows it
     * @param controller the HotelManagementController in use
     */
    public RegisterWindow(HotelManagementController controller) {
        log = ClientLogger.getLogger();
    	hotelManagementController = controller;
        initialize();
    }

    /**
     * All the Swing code for showing the actual frame
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle(LocaleManager.getMessage("register.title"));
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // The container is using BorderLayout so that
        JPanel container = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new SpringLayout());

        // I know this smells pretty badly. Sorry about that...
        // Name field
        JLabel nameLabel = new JLabel(LocaleManager.getMessage("register.label.name"), JLabel.TRAILING);
        form.add(nameLabel);
        nameTextField = new JTextField(10);
        nameLabel.setLabelFor(nameTextField);
        form.add(nameTextField);

        // Email field
        JLabel emailLabel = new JLabel(LocaleManager.getMessage("register.label.email"), JLabel.TRAILING);
        form.add(emailLabel);
        emailTextField = new JTextField(10);
        emailLabel.setLabelFor(emailTextField);
        form.add(emailTextField);

        // Password field
        JLabel passwordLabel = new JLabel(LocaleManager.getMessage("register.label.password"), JLabel.TRAILING);
        form.add(passwordLabel);
        passwordField = new JPasswordField(10);
        passwordLabel.setLabelFor(passwordField);
        form.add(passwordField);

        // Password confirmation field
        JLabel passwordConfirmationLabel = new JLabel(LocaleManager.getMessage("register.label.password-confirmation"), JLabel.TRAILING);
        form.add(passwordConfirmationLabel);
        passwordConfirmationField = new JPasswordField(10);
        passwordConfirmationLabel.setLabelFor(passwordConfirmationField);
        form.add(passwordConfirmationField);

        // Phone field
        JLabel phoneLabel = new JLabel(LocaleManager.getMessage("register.label.phone"), JLabel.TRAILING);
        form.add(phoneLabel);
        phoneTextField = new JTextField(10);
        phoneLabel.setLabelFor(phoneTextField);
        form.add(phoneTextField);

        // Address field
        JLabel addressLabel = new JLabel(LocaleManager.getMessage("register.label.address"), JLabel.TRAILING);
        form.add(addressLabel);
        addressTextField = new JTextField(10);
        addressLabel.setLabelFor(addressTextField);
        form.add(addressTextField);


        // Lay out the form and make it a nice "responsive" grid
        SpringUtilities.makeCompactGrid(form,
                6, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        // Add the form to the center slot
        container.add(form, BorderLayout.CENTER);


        // Submit button
        submitButton = new JButton(LocaleManager.getMessage("register.submit"));
        container.add(submitButton, BorderLayout.PAGE_END); // add it to the bottom

        // Register the click function
        submitButton.addActionListener((ActionEvent e) -> { handleFormSubmission(); });

        frame.add(container);

        frame.setVisible(true);
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
         * A required field was left empty
         */
        REQUIRED_FIELD_EMPTY,

        /**
         * The password confirmation is not the same as the password
         */
        PASSWORD_CONFIRMATION_DIFFERENT
    }

    /**
     * All the logic for registrations: validates, sends data to server, checks response
     */
    private void handleFormSubmission() {

        // Disable all fields
        toggleFields(false);

        // Required fields
        JTextComponent[] components = {
                nameTextField,
                emailTextField,
                phoneTextField,
                addressTextField,
                passwordField,
                passwordConfirmationField,
        };

        // Check that all required fields are filled, and if not, fail
        for (JTextComponent component : components) {
            if (component.getText().trim().isEmpty()) {
                notifyValidationFail(ValidationFailReason.REQUIRED_FIELD_EMPTY);
                // Re-enable fields
                toggleFields(true);
                return;
            }
        }

        // Check if the password confirmation matches the password, and if not, fail
        if (!(new String(passwordField.getPassword())).equals(new String(passwordConfirmationField.getPassword()))) {
            notifyValidationFail(ValidationFailReason.PASSWORD_CONFIRMATION_DIFFERENT);
            // Re-enable fields
            toggleFields(true);
            return;
        }

        // Create a new User and send it to the server
        UserDTO result = null;
		try {
			result = hotelManagementController.signInGuest(nameTextField.getText(),
                    emailTextField.getText(),
                    new String(passwordField.getPassword()),
                    phoneTextField.getText(),
                    addressTextField.getText());
		} catch (RemoteException e) {
			log.info("Remote exception trying to create a UserDTO");
		}

		// If it fails, tell the user and dispose the frame
        if (result == null) {
            ClientLogger.getLogger().severe("User not registered...");
            JOptionPane.showMessageDialog(frame,
                    LocaleManager.getMessage("register.validation.errors.unknown"),
                    LocaleManager.getMessage("register.validation.errors.unknown.title"),
                    JOptionPane.ERROR_MESSAGE);

            frame.dispose();
            return;
        }

        // Success!
        JOptionPane.showMessageDialog(frame,
                LocaleManager.getMessage("register.success.body"),
                LocaleManager.getMessage("register.success.title"),
                JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();

    }

    /**
     * Show a dialog informing about a form validation failure
     * @param reason the reason why the validation has failed
     */
    private void notifyValidationFail(ValidationFailReason reason) {

        String messageKey;
        int messageType;

        switch (reason) {
            case REQUIRED_FIELD_EMPTY:
                messageKey = "register.validation.errors.required";
                messageType = JOptionPane.WARNING_MESSAGE;
            break;
            case PASSWORD_CONFIRMATION_DIFFERENT:
                messageKey = "register.validation.errors.password-confirmation";
                messageType = JOptionPane.WARNING_MESSAGE;
            break;
            default:
                messageKey = "register.validation.errors.unknown";
                messageType = JOptionPane.ERROR_MESSAGE;
            break;
        }

        JOptionPane.showMessageDialog(frame,
                LocaleManager.getMessage(messageKey),
                LocaleManager.getMessage("register.validation.title"),
                messageType);
    }

    /**
     * Enable or disable all the fields in the form
     * @param enable whether or not to leave enabled the fields
     */
    private void toggleFields(boolean enable) {
        nameTextField.setEnabled(enable);
        emailTextField.setEnabled(enable);
        passwordConfirmationField.setEnabled(enable);
        passwordField.setEnabled(enable);
        phoneTextField.setEnabled(enable);
        addressTextField.setEnabled(enable);
        submitButton.setEnabled(enable);
    }

}

