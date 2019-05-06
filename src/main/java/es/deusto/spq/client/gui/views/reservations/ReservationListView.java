package es.deusto.spq.client.gui.views.reservations;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReservationListView extends View {

    private JLabel titleLabel;
    private JPanel resultsPanel;

    public ReservationListView(ViewManager viewManager) {
        super(viewManager);
    }

    private JInternalFrame internalFrame;

    @Override
    public ViewType getViewType() {
        return ViewType.RESERVATION_LIST;
    }

    @Override
    public ViewPermission getViewPermission() {
        // TODO change for a dynamic permission level
        return ViewPermission.NONE;
    }

    @Nullable
    @Override
    public JInternalFrame getInternalFrame() {
        return internalFrame;
    }

    @Override
    public void bringToFront() {
        internalFrame.toFront();
    }

    @Override
    public void dispose() {
        internalFrame.dispose();
    }

    @Override
    public void initialize() {
        internalFrame = new JInternalFrame();
        internalFrame.setTitle(getViewManager().getClient().getLocaleManager().getMessage("reservations.title"));

        // Heading panel
        JPanel panel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        panel.setLayout(borderLayout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("reservations.heading"));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center part
        resultsPanel = new JPanel();
        JLabel label =  new JLabel(getViewManager().getClient().getLocaleManager().getMessage("reservations.no-data"));
        resultsPanel.add(label);

        // Finishing touches
        internalFrame.add(panel);
        panel.add(resultsPanel, BorderLayout.CENTER);

        internalFrame.pack(); // fix size to components
        internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        internalFrame.setClosable(true);
        internalFrame.setIconifiable(true);
        internalFrame.setVisible(true);

        addDisposeEventHandler();
    }

    @Override
    public void refresh() {
        internalFrame.setTitle(getViewManager().getClient().getLocaleManager().getMessage("reservations.title"));
        titleLabel.setText(getViewManager().getClient().getLocaleManager().getMessage("reservations.heading"));
    }
}
