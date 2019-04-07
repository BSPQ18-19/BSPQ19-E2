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

/**
 * The Register window.
 * I know it's ugly, but I was in a hurry. Sorry about that :'(
 * Suggestions for improvement:
 * - Use an actual form system
 * - Make a validation system
 *
 */
public class RegisterWindow {

    private JFrame frame;
    private HotelManagementController hotelManagementController;
    private JTextField nameTextField, emailTextField, phoneTextField, addressTextField;
    private JPasswordField passwordField, passwordConfirmationField;
    private JButton submitButton;


    public RegisterWindow(HotelManagementController controller) {
        hotelManagementController = controller;
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setTitle(LocaleManager.getMessage("register.title"));
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());


        JPanel form = new JPanel(new SpringLayout());


        JLabel nameLabel = new JLabel(LocaleManager.getMessage("register.label.name"), JLabel.TRAILING);
        form.add(nameLabel);
        nameTextField = new JTextField(10);
        nameLabel.setLabelFor(nameTextField);
        form.add(nameTextField);

        JLabel emailLabel = new JLabel(LocaleManager.getMessage("register.label.email"), JLabel.TRAILING);
        form.add(emailLabel);
        emailTextField = new JTextField(10);
        emailLabel.setLabelFor(emailTextField);
        form.add(emailTextField);

        JLabel passwordLabel = new JLabel(LocaleManager.getMessage("register.label.password"), JLabel.TRAILING);
        form.add(passwordLabel);
        passwordField = new JPasswordField(10);
        passwordLabel.setLabelFor(passwordField);
        form.add(passwordField);

        JLabel passwordConfirmationLabel = new JLabel(LocaleManager.getMessage("register.label.password-confirmation"), JLabel.TRAILING);
        form.add(passwordConfirmationLabel);
        passwordConfirmationField = new JPasswordField(10);
        passwordConfirmationLabel.setLabelFor(passwordConfirmationField);
        form.add(passwordConfirmationField);

        JLabel phoneLabel = new JLabel(LocaleManager.getMessage("register.label.phone"), JLabel.TRAILING);
        form.add(phoneLabel);
        phoneTextField = new JTextField(10);
        phoneLabel.setLabelFor(phoneTextField);
        form.add(phoneTextField);

        JLabel addressLabel = new JLabel(LocaleManager.getMessage("register.label.address"), JLabel.TRAILING);
        form.add(addressLabel);
        addressTextField = new JTextField(10);
        addressLabel.setLabelFor(addressTextField);
        form.add(addressTextField);


        //Lay out the panel.
        SpringUtilities.makeCompactGrid(form,
                6, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        container.add(form, BorderLayout.CENTER);


        submitButton = new JButton(LocaleManager.getMessage("register.submit"));
        container.add(submitButton, BorderLayout.PAGE_END);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleFormSubmission();
            }
        });

        frame.add(container);

        frame.setVisible(true);
    }

    public void show() {
        frame.setVisible(true);
        frame.requestFocus();
    }

    private enum ValidationFailReason {
        REQUIRED_FIELD_EMPTY,
        PASSWORD_CONFIRMATION_DIFFERENT
    }

    private void handleFormSubmission() {
        JTextComponent[] components = {
                nameTextField,
                emailTextField,
                phoneTextField,
                addressTextField,
                passwordField,
                passwordConfirmationField,
        };

        for (int i = 0; i < components.length ; i++) {
            if (components[i].getText().trim().isEmpty()) {
                notifyValidationFail(ValidationFailReason.REQUIRED_FIELD_EMPTY);
                return;
            }
        }

        if (!(new String(passwordField.getPassword())).equals(new String(passwordConfirmationField.getPassword()))) {
            notifyValidationFail(ValidationFailReason.PASSWORD_CONFIRMATION_DIFFERENT);
            return;
        }

        // Signup logic
//        boolean result = hotelManagementController.signupGuest(emailTextField.getText(),
//                nameTextField.getText(),
//                new String(passwordField.getPassword()),
//                phoneTextField.getText(),
//                addressTextField.getText());
        UserDTO result = hotelManagementController.signInGuest("Pepe",
        		"pepe@gmail.com",
                "pepepassword",
                "1",
                "Avda. de la Sierra 23");

        if (result == null) {
            ClientLogger.getLogger().severe("User not registered...");
        }
    }

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

    private void toggleFields(boolean enable) {
        nameTextField.setEnabled(enable);
        emailTextField.setEnabled(enable);
        passwordConfirmationField.setEnabled(enable);
        passwordField.setEnabled(enable);
        phoneTextField.setEnabled(enable);
        addressTextField.setEnabled(enable);
        submitButton.setEnabled(false);
    }

}

