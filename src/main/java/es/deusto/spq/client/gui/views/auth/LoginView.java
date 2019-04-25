package es.deusto.spq.client.gui.views.auth;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.locale.LocaleManager;

public class LoginView extends View {

    private JInternalFrame frame;
    private JTextField tFEmail;
    private JTextField tFPassword;
    private final JPanel panel_3 = new JPanel();
    private final Logger log;
    private final HotelManagementController controller;

    /**
     *
     * @param viewManager
     */
    public LoginView(ViewManager viewManager) {
        super(viewManager);

        log = ClientLogger.getLogger();
        this.controller = viewManager.getClient().getController();

    }

    @Override
    public ViewPermission getViewPermission() {
        return ViewPermission.NOT_LOGGED_IN;
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

    @Override
    public void initialize() {

        // TODO Remove code smells!!!
        frame = new JInternalFrame();
        frame.setBounds(100, 100, 223, 231);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

        final JPanel panel_2 = new JPanel();
        final GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.insets = new Insets(0, 0, 5, 0);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 3;
        gbc_panel_2.gridy = 1;
        frame.getContentPane().add(panel_2, gbc_panel_2);

        final JLabel lblLogin = new JLabel(LocaleManager.getMessage("login.title"));
        panel_2.add(lblLogin);

        final JPanel panel_1 = new JPanel();
        final GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.NORTH;
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.gridx = 3;
        gbc_panel_1.gridy = 2;
        frame.getContentPane().add(panel_1, gbc_panel_1);

        final JLabel lblEmail = new JLabel(LocaleManager.getMessage("login.label.email"));
        panel_1.add(lblEmail);

        tFEmail = new JTextField();
        panel_1.add(tFEmail);
        tFEmail.setColumns(10);

        final JPanel panel = new JPanel();
        final GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.anchor = GridBagConstraints.NORTH;
        gbc_panel.gridx = 3;
        gbc_panel.gridy = 3;
        frame.getContentPane().add(panel, gbc_panel);

        final JLabel lblPassword = new JLabel(LocaleManager.getMessage("login.label.password"));
        panel.add(lblPassword);

        tFPassword = new JPasswordField();
        panel.add(tFPassword);
        tFPassword.setColumns(10);
        final GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.gridx = 3;
        gbc_panel_3.gridy = 4;
        frame.getContentPane().add(panel_3, gbc_panel_3);


        final JButton btnLogin = new JButton(LocaleManager.getMessage("login.submit"));
        btnLogin.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent arg0) {
                //This trigers when login in
                final String email = tFEmail.getText();
                final String password = tFPassword.getText();

                UserDTO loggedUser = null;
                try {
                    loggedUser = controller.logIn(email, password);
                } catch (final RemoteException e) {
                    log.info("Remote exception trying to create a UserDTO");

                }

                if(loggedUser == null)
                    JOptionPane.showMessageDialog(frame, LocaleManager.getMessage("login.failed.body"), LocaleManager.getMessage("login.failed.title"), JOptionPane.ERROR_MESSAGE);
                else {
                    if(loggedUser.isGuest())
                        ;//TODO guest GUI
                    else
                        ;//TODO admin GUI
                    getViewManager().openView(ViewFactory.buildView(ViewType.ADMIN_HOTELS, getViewManager()));
                    dispose();
                    JOptionPane.showMessageDialog(frame, "Succesfull login", "Succesfull login", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panel_3.add(btnLogin);

        final JButton btnRegister = new JButton(LocaleManager.getMessage("login.register"));
        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                getViewManager().openView(ViewFactory.buildView(ViewType.REGISTRATION, getViewManager()));
                getViewManager().openView(ViewFactory.buildView(ViewType.EDIT_USER, getViewManager()));
            }
        });
        panel_3.add(btnRegister);

        frame.setVisible(true);
        addDisposeEventHandler();
    }

}
