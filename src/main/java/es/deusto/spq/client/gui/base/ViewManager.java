package es.deusto.spq.client.gui.base;

import es.deusto.spq.client.Client;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.locale.LocaleManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages the whole UI View system.
 * @author Iñigo Apellániz
 */
public class ViewManager {

    /**
     * The current Client class serving the app.
     */
    private Client client;

    /**
     * The current ViewPermission level.
     */
    private ViewPermission permission = ViewPermission.NOT_LOGGED_IN;

    /**
     * The base of the UI.
     */
    private JDesktopPane desktopPane;

    /**
     * The actual frame to hold all the UI.
     */
    private JFrame frame;

    /**
     * List of active views.
     */
    private List<View> views = new CopyOnWriteArrayList<View>();

    /**
     * Creates a new ViewManager with no Views.
     * @param client The current active Client class
     */
    public ViewManager(Client client) {
        this.client = client;
    }

    /**
     * Get the current Client class.
     * @return the Client the ViewManager is serving
     */
    public Client getClient() {
        return client;
    }

    /**
     * Closes all views.
     */
    public void closeAll() {
        for (View view : views) {
            // Call dispose for all active views
            view.dispose();
        }
    }

    /**
     * The ViewPermission represents the current permission level for the UI. The
     * @return the ViewPermission for the ViewManager
     */
    public ViewPermission getPermission() {
        return permission;
    }

    /**
     * @param permission the ViewPermission level to set as current
     */
    public void setPermission(ViewPermission permission) {
        this.permission = permission;

        List<View> viewsToDispose = new ArrayList<>();

        // Close all Views that don't meet the new permission level
        for (Iterator<View> iterator = views.iterator(); iterator.hasNext();) {
            View view = iterator.next();

            if (!ViewPermission.hasPermission(permission, view.getViewPermission())) {
                viewsToDispose.add(view);
            }
        }

        for (View view : views) {
            view.dispose();
        }


        repaintUI();
    }

    /**
     *
     */
    public void initialize() {

        // Only initialize if we're not already initialized
        if (frame == null) {
            frame = new JFrame(LocaleManager.getMessage("app.name"));
            desktopPane = new JDesktopPane();
            frame.getContentPane().add(desktopPane);

            frame.setSize(700, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else {
            ClientLogger.getLogger().warn("Tried to initialize when already initialized");
        }

        repaintUI();
        frame.toFront();
    }

    private void reloadMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Main menu item (about, close)
        JMenu menuMainItem = new JMenu(LocaleManager.getMessage("app.name"));
        menuMainItem.setFont(new Font("sans-serif", Font.BOLD, 14));

        JMenuItem aboutMenuSubitem = new JMenuItem(LocaleManager.getMessage("menu.about.title"));
        aboutMenuSubitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        frame,
                        LocaleManager.getMessage("menu.about.body"),
                        LocaleManager.getMessage("menu.about.title"),
                        JOptionPane.INFORMATION_MESSAGE
                );

            }
        });
        menuMainItem.add(aboutMenuSubitem);

        menuMainItem.addSeparator();

        JMenuItem quitMenuItem = new JMenuItem(LocaleManager.getMessage("menu.quit"));
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        quitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the program with code 0, no errors
                System.exit(0);

            }
        });
        menuMainItem.add(quitMenuItem);

        // Session menu ----------------------------
        String lblSesion;
        if(getPermission() == ViewPermission.LOGGED_IN) {
            if(getPermission() == ViewPermission.LOGGED_IN_ADMIN) {
                lblSesion = "User (ADMIN)";
            } else {
                lblSesion = "User";
            }
        } else {
            lblSesion = LocaleManager.getMessage("menu.session.none");
        }
        JMenu sesionMenu = new JMenu(lblSesion);
        if(getPermission() == ViewPermission.LOGGED_IN) {

            JMenuItem logInMenuItem = new JMenuItem(LocaleManager.getMessage("menu.session.logout"));
            logInMenuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mark as not logged in and open the login window
                    setPermission(ViewPermission.NOT_LOGGED_IN);
                    openView(ViewFactory.buildView(ViewType.LOGIN, getViewManager()));

                }
            });
            sesionMenu.add(logInMenuItem);
        } else {

            JMenuItem loginMenuItem = new JMenuItem(LocaleManager.getMessage("menu.session.login"));
            loginMenuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    openView(ViewFactory.buildView(ViewType.LOGIN, getViewManager()));
                }
            });
            sesionMenu.add(loginMenuItem);
        }

        menuBar.add(menuMainItem);
        menuBar.add(sesionMenu);

        frame.setJMenuBar(menuBar);
    }

    public ViewManager getViewManager() {
        return this;
    }


    /**
     * Repaint the whole base UI
     */
    private void repaintUI() {
        reloadMenu();
        frame.validate();
        frame.repaint();
    }

    /**
     * Called from a View whenever it's self-disposed
     * @param view
     */
    public void notifyDisposal(View view) {
        views.remove(view);
    }


    /**
     * Check if there's an active View with the specified ViewType.
     * @param viewType the ViewType to check
     * @return whether or not a View is active with the specified ViewType
     */
    private boolean isViewTypeActive(ViewType viewType) {

        // Iterate active views
        for (View view : views) {
            if (view.getViewType() == viewType) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get the first view with a specific ViewType.
     * @param viewType the type of the desired view
     * @return the first found view with the type, or null
     */
    @Nullable
    public View getFirstViewByViewType(ViewType viewType) {

        // Iterate active views
        for (View view : views) {
            if (view.getViewType() == viewType) {
                return view;
            }
        }

        // If not found return null
        return null;
    }

    private List<View> getViewsByViewPermission(ViewPermission viewPermission) {
        ArrayList<View> result = new ArrayList<>();

        for (View view : views) {
            if (view.getViewPermission() == viewPermission) {
                result.add(view);
            }
        }

        return result;
    }

    /**
     * Add a View and open it
     * @param view the view to open
     */
    public void openView(View view)  {

        // If we try to open an unique window, bring it to the front instead of creating a new one
        if (view.isUnique() && isViewTypeActive(view.getViewType())) {
            getFirstViewByViewType(view.getViewType()).bringToFront();
            return;
        }

        // Check if we have permission to open the window
        if (!ViewPermission.hasPermission(getPermission(), view.getViewPermission())) {
            // TODO log not enough permissions
            return;
        }

        // Add the view to the list and open it
        views.add(view);
        view.initialize();
        desktopPane.add(view.getInternalFrame());
        view.bringToFront();

    }
}
