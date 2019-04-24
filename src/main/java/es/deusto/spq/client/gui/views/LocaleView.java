package es.deusto.spq.client.gui.views;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LocaleView extends View {

    private JInternalFrame internalFrame;

    public LocaleView(ViewManager viewManager) {
        super(viewManager);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.LOCALE_SETTINGS;
    }

    @Override
    public ViewPermission getViewPermission() {
        return ViewPermission.NONE;
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Nullable
    @Override
    public JInternalFrame getInternalFrame() {
        return internalFrame;
    }

    @Override
    public void initialize() {
        internalFrame = new JInternalFrame();
        internalFrame.setClosable(true);
        internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        String[] locales = {"English", "Spanish", "Basque", "Czech"};
        JComboBox jComboBox = new JComboBox(locales);
        jComboBox.setSelectedIndex(1);
        // TODO add locale switch logic

        internalFrame.getContentPane().add(jComboBox);
        internalFrame.setSize(100, 100);
        internalFrame.pack();
        internalFrame.setVisible(true);

        addDisposeEventHandler();
    }

    @Override
    public void bringToFront() {
        getInternalFrame().toFront();
    }
}
