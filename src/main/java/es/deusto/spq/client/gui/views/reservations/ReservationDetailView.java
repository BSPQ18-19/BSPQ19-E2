package es.deusto.spq.client.gui.views.reservations;

import es.deusto.spq.client.controller.HotelManagementController;
import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.views.admin.ScreenTypeAdmin;
import es.deusto.spq.client.gui.views.reviews.WriteReview;
import es.deusto.spq.server.data.dto.ReservationDTO;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class ReservationDetailView extends View {

    private JInternalFrame internalFrame;
    private ReservationDTO reservationDTO;

    // Fields
    JLabel detail;
    JButton cancelButton;
    JButton writeReview;

    private HotelManagementController controller;

    public ReservationDetailView(ViewManager viewManager) {
        super(viewManager);
        controller = viewManager.getClient().getController();
    }

    public ReservationDTO getReservationDTO() {
        return reservationDTO;
    }

    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
        refresh();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.RESERVATION_DETAIL;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Nullable
    @Override
    public JInternalFrame getInternalFrame() {
        return internalFrame;
    }

    public void initialize() {
        internalFrame = new JInternalFrame();
        internalFrame.setTitle(getViewManager().getClient().getLocaleManager().getMessage("reservation.detail.loading"));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        detail = new JLabel(getViewManager().getClient().getLocaleManager().getMessage("reservation.detail.loading"));
        panel.add(detail, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        cancelButton = new JButton(getViewManager().getClient().getLocaleManager().getMessage("reservation.detail.button.cancel"));
        buttonsPanel.add(cancelButton);

        writeReview = new JButton(getViewManager().getClient().getLocaleManager().getMessage("reservation.details.button.writteReview"));
        buttonsPanel.add(writeReview);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        refresh();

        internalFrame.add(panel);
        internalFrame.pack();
        internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        internalFrame.setClosable(true);
        internalFrame.setIconifiable(true);
        internalFrame.setVisible(true);
        addDisposeEventHandler();
    }

    @Override
    public void refresh() {

        // Don't refresh anything if we've not initialized yet
        if (getInternalFrame() == null) {
            return;
        }

        writeReview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				if(reservationDTO.getLastDay().before(timestamp)) {
					WriteReview view = null;
					getViewManager().openView(view = (WriteReview) ViewFactory.buildView(ViewType.WRITE_REVIEW, getViewManager()));
					view.setHotelID(reservationDTO.getRoomId());
					view.setUserID(reservationDTO.getGuestId());
				}else {
					JOptionPane.showMessageDialog(null, "NO PUEDES", "Write Now", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

        // Cancel button always gets updated (locale)
        cancelButton.setText(getViewManager().getClient().getLocaleManager().getMessage("reservation.detail.button.cancel"));

        // If there isn't a Reservation loaded yet, disable and wait for setter.
        if (getReservationDTO() == null) {
            internalFrame.setTitle(getViewManager().getClient().getLocaleManager().getMessage("reservation.detail.loading"));
            detail.setText(getViewManager().getClient().getLocaleManager().getMessage("reservation.detail.loading"));
            cancelButton.setEnabled(false);
            return;
        }

        internalFrame.setTitle(getViewManager().getClient().getLocaleManager()
                .getMessage("reservation.detail.title", reservationDTO.getId()));
        detail.setText(getViewManager().getClient().getLocaleManager()
                .getMessage(
                        "reservation.detail.information",
                        reservationDTO.getId(),
                        reservationDTO.getFirstDay(),
                        reservationDTO.getLastDay(),
                        reservationDTO.getRoomId(),
                        reservationDTO.getGuestId()
                ));
        cancelButton.setEnabled(true);
        internalFrame.pack();
        internalFrame.repaint();
    }

    @Override
    public void dispose() {
        getInternalFrame().dispose();
    }

    @Override
    public void bringToFront() {
        getInternalFrame().toFront();
    }
}
