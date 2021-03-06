package es.deusto.spq.client.gui.views.auth;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
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
 * The register admit view
 *
 */
public class RegisterAdminView extends View {

	/**
	 *
	 * @param viewManager The class that manages the views
	 */
    public RegisterAdminView(ViewManager viewManager) {
        super(viewManager);
        hotelManagementController = viewManager.getClient().getController();
    }

    private JInternalFrame frame;
    private final HotelManagementController hotelManagementController;

    // All the form fields and buttons
    private JTextField nameTextField, emailTextField, addressTextField;
    private JPasswordField passwordField, passwordConfirmationField;
    private JButton submitButton;
    private JLabel nameLabel, emailLabel, passwordLabel, passwordConfirmationLabel, addressLabel;

    // Logger
    private Logger log;

    @Override
    public ViewType getViewType() {
    	return ViewType.REGISTER_ADMINISTRATOR;
    }

    @Override
    public ViewPermission getViewPermission() {
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
     * All the Swing code for showing the actual frame
     */
    @Override
	public void initialize() {
        frame = new JInternalFrame();
        frame.setClosable(true);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // The container is using BorderLayout so that
        JPanel container = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new SpringLayout());

        // Name field
        nameLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.name"), JLabel.TRAILING);
        form.add(nameLabel);
        nameTextField = new JTextField(10);
        nameLabel.setLabelFor(nameTextField);
        form.add(nameTextField);

        // Email field
        emailLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.email"), JLabel.TRAILING);
        form.add(emailLabel);
        emailTextField = new JTextField(10);
        emailLabel.setLabelFor(emailTextField);
        form.add(emailTextField);

        // Password field
        passwordLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.password"), JLabel.TRAILING);
        form.add(passwordLabel);
        passwordField = new JPasswordField(10);
        passwordLabel.setLabelFor(passwordField);
        form.add(passwordField);

        // Password confirmation field
        passwordConfirmationLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.password-confirmation"), JLabel.TRAILING);
        form.add(passwordConfirmationLabel);
        passwordConfirmationField = new JPasswordField(10);
        passwordConfirmationLabel.setLabelFor(passwordConfirmationField);
        form.add(passwordConfirmationField);

        // Address field
        addressLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.address"), JLabel.TRAILING);
        form.add(addressLabel);
        addressTextField = new JTextField(10);
        addressLabel.setLabelFor(addressTextField);
        form.add(addressTextField);

        // Lay out the form and make it a nice "responsive" grid
        SpringUtilities.makeCompactGrid(form,
                5, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        
        // Add the form to the center slot
        container.add(form, BorderLayout.CENTER);

        // Submit button
        submitButton = new JButton(getViewManager().getClient().getLocaleManager().getMessage("register.admin.submit"));
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
        final JTextComponent[] components = {
                nameTextField,
                emailTextField,
                addressTextField,
                passwordField,
                passwordConfirmationField,
        };

        // Check that all required fields are filled, and if not, fail
        for (final JTextComponent component : components)
			if (component.getText().trim().isEmpty()) {
                notifyValidationFail(ValidationFailReason.REQUIRED_FIELD_EMPTY);
                // Re-enable fields
                toggleFields(true);
                return;
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
            result = hotelManagementController.signInAdmin(nameTextField.getText(),
                    emailTextField.getText(),
                    new String(passwordField.getPassword()),
                    addressTextField.getText());
        } catch (final RemoteException e) {
            log.info("Remote exception trying to create a UserDTO");
        }

        // If it fails, tell the user and dispose the frame
        if (result == null) {
            ClientLogger.getLogger().fatal("User not registered...");
            JOptionPane.showMessageDialog(frame,
                    getViewManager().getClient().getLocaleManager().getMessage("register.admin.validation.errors.unknown"),
                    getViewManager().getClient().getLocaleManager().getMessage("register.admin.validation.errors.unknown.title"),
                    JOptionPane.ERROR_MESSAGE);

            frame.dispose();
            return;
        }

        // Success!
        JOptionPane.showMessageDialog(frame,
                getViewManager().getClient().getLocaleManager().getMessage("register.admin.success.body"),
                getViewManager().getClient().getLocaleManager().getMessage("register.admin.success.title"),
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
                messageKey = "register.admin.validation.errors.required";
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            case PASSWORD_CONFIRMATION_DIFFERENT:
                messageKey = "register.admin.validation.errors.password-confirmation";
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            default:
                messageKey = "register.admin.validation.errors.unknown";
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
        }

        JOptionPane.showMessageDialog(frame,
                getViewManager().getClient().getLocaleManager().getMessage(messageKey),
                getViewManager().getClient().getLocaleManager().getMessage("register.admin.validation.title"),
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
        addressTextField.setEnabled(enable);
        submitButton.setEnabled(enable);
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
        nameLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.name"));
        emailLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.email"));
        passwordLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.password"));
        passwordConfirmationLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.password-confirmation"));
        addressLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("register.admin.label.address"));
        submitButton.setText(getViewManager().getClient().getLocaleManager().getMessage("register.admin.submit"));
    }
}
