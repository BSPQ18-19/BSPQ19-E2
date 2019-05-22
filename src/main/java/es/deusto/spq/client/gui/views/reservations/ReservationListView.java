package es.deusto.spq.client.gui.views.reservations;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.dto.ReservationDTO;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ReservationListView extends View {

    private JLabel titleLabel;
    private JPanel resultsPanel;
    private List<ReservationDTO> reservations;

    public ReservationListView(ViewManager viewManager) {
        super(viewManager);
    }

    private JInternalFrame internalFrame;

    public List getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDTO> reservations) {
        this.reservations = reservations;
        refresh();
    }

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
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
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

        refresh();
        addDisposeEventHandler();
    }

    @Override
    public void refresh() {

        // Only refresh after we've initialized the frame
        if (getInternalFrame() == null) {
            return;
        }

        internalFrame.setTitle(getViewManager().getClient().getLocaleManager()
                .getMessage("reservations.title"));
        titleLabel.setText(getViewManager().getClient().getLocaleManager()
                .getMessage("reservations.heading", reservations.size()));

        resultsPanel.removeAll();
        for (ReservationDTO reservation : reservations) {
            JLabel label = new JLabel(
                    "<html><b>Reservation #"
                    + reservation.getId()
                    + "</b><br>" + reservation.getFirstDay()
                    + " to "
                    + reservation.getLastDay()
                    + "<hr></html>"
            );
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ClientLogger.getLogger().debug("Clicked on " + reservation.toString()); // TODO open reservation view
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            resultsPanel.add(label); // TODO localize
        }
        resultsPanel.validate();
        resultsPanel.repaint();

        try {
            getInternalFrame().validate();
            getInternalFrame().repaint();
            getInternalFrame().pack();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
}
