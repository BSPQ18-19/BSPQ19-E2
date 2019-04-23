package es.deusto.spq.client.gui.views.auth;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.util.SpringUtilities;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.locale.LocaleManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class EditUserView extends View {

	//The user that is actually logged in and we want to edit
	private UserDTO loggedUser;
	
    public EditUserView(ViewManager viewManager) {
        super(viewManager);
        hotelManagementController = viewManager.getClient().getController();
        loggedUser = hotelManagementController.getLoggedUser();
    }

    private JInternalFrame frame;
    private HotelManagementController hotelManagementController;

    // All the form fields and buttons
    private JTextField nameTextField, emailTextField, phoneTextField, addressTextField;
    private JPasswordField passwordField, passwordConfirmationField;
    private JButton submitButton;

    // Logger
    private Logger log;
   

    @Override
    public ViewType getViewType() {
        return ViewType.EDIT_USER;
    }

    @Override
    public ViewPermission getViewPermission() {
        return ViewPermission.LOGGED_IN;
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
     * All the Swing code for showing the actual frame
     */
    public void initialize() {
        frame = new JInternalFrame();
        frame.setClosable(true);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // The container is using BorderLayout so that
        JPanel container = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new SpringLayout());

        // Name field
        JLabel nameLabel = new JLabel(LocaleManager.getMessage("editUser.label.name"), JLabel.TRAILING);
        form.add(nameLabel);
        nameTextField = new JTextField(10);
        nameLabel.setLabelFor(nameTextField);
        form.add(nameTextField);
        nameTextField.setText(loggedUser.getName());

        // Email field
        JLabel emailLabel = new JLabel(LocaleManager.getMessage("editUser.label.email"), JLabel.TRAILING);
        form.add(emailLabel);
        emailTextField = new JTextField(10);
        emailLabel.setLabelFor(emailTextField);
        form.add(emailTextField);
        emailTextField.setText(loggedUser.getEmail());

        // Password field
        JLabel passwordLabel = new JLabel(LocaleManager.getMessage("editUser.label.password"), JLabel.TRAILING);
        form.add(passwordLabel);
        passwordField = new JPasswordField(10);
        passwordLabel.setLabelFor(passwordField);
        form.add(passwordField);
        passwordField.setText(loggedUser.getPassword());

        // Password confirmation field
        JLabel passwordConfirmationLabel = new JLabel(LocaleManager.getMessage("editUser.label.password-confirmation"), JLabel.TRAILING);
        form.add(passwordConfirmationLabel);
        passwordConfirmationField = new JPasswordField(10);
        passwordConfirmationLabel.setLabelFor(passwordConfirmationField);
        form.add(passwordConfirmationField);
        passwordConfirmationField.setText(loggedUser.getPassword());

        // Phone field
        JLabel phoneLabel = new JLabel(LocaleManager.getMessage("editUser.label.phone"), JLabel.TRAILING);
        form.add(phoneLabel);
        phoneTextField = new JTextField(10);
        phoneLabel.setLabelFor(phoneTextField);
        form.add(phoneTextField);
        phoneTextField.setText("phone");

        // Address field
        JLabel addressLabel = new JLabel(LocaleManager.getMessage("editUser.label.address"), JLabel.TRAILING);
        form.add(addressLabel);
        addressTextField = new JTextField(10);
        addressLabel.setLabelFor(addressTextField);
        form.add(addressTextField);
        addressTextField.setText("address");


        // Lay out the form and make it a nice "responsive" grid
        SpringUtilities.makeCompactGrid(form,
                6, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        // Add the form to the center slot
        container.add(form, BorderLayout.CENTER);


        // Submit button
        submitButton = new JButton(LocaleManager.getMessage("editUser.saveChanges"));
        container.add(submitButton, BorderLayout.PAGE_END); // add it to the bottom

        // Register the click function
        submitButton.addActionListener((ActionEvent e) -> { handleFormSubmission(); });

        frame.add(container);

        frame.setVisible(true);
        frame.toFront();
        addDisposeEventHandler();
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

        // Send the new properties of the current user to the server
        UserDTO result = null;
        result = hotelManagementController.editUser(nameTextField.getText(),
		        emailTextField.getText(),
		        new String(passwordField.getPassword()),
		        phoneTextField.getText(),
		        addressTextField.getText());

        // If it fails, tell the user and dispose the frame
        if (result == null) {
            ClientLogger.getLogger().fatal("User not registered...");
            JOptionPane.showMessageDialog(frame,
                    LocaleManager.getMessage("editUser.validation.errors.unknown"),
                    LocaleManager.getMessage("editUser.validation.errors.unknown.title"),
                    JOptionPane.ERROR_MESSAGE);

            frame.dispose();
            return;
        }

        // Success!
        JOptionPane.showMessageDialog(frame,
                LocaleManager.getMessage("editUser.success.body"),
                LocaleManager.getMessage("editUser.success.title"),
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
                messageKey = "editUser.validation.errors.required";
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

        JOptionPane.showMessageDialog(frame,
                LocaleManager.getMessage(messageKey),
                LocaleManager.getMessage("editUser.validation.title"),
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

    @Override
    public void bringToFront() {
        getInternalFrame().toFront();
    }
}
