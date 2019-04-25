package es.deusto.spq.client.gui.views;

import es.deusto.spq.client.gui.base.View;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewPermission;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.locale.AllowedLocale;
import es.deusto.spq.client.gui.locale.LocaleManager;
import es.deusto.spq.client.logger.ClientLogger;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        /*
            Information label
         */

        JLabel label = new JLabel("<html><b>Locale settings</b><br>Please select your desired language:</html>");
        label.setBorder(new EmptyBorder(6, 6, 6, 6));
        panel.add(label, BorderLayout.PAGE_START);

        /*
            Combo box
         */
        JComboBox jComboBox = new JComboBox();

        // Fill the combo box
        for (int i = 0; i < AllowedLocale.ALLOWED_LOCALES.length; i++) {
            String item;

            // Generate the String for the AllowedLocale
            if (AllowedLocale.ALLOWED_LOCALES[i].getEnglishName().equals(AllowedLocale.ALLOWED_LOCALES[i].getLocalizedName())) {
                // If the language its the same as the localized, avoid repetitions
                item = AllowedLocale.ALLOWED_LOCALES[i].getEnglishName();
            } else {
                // Fot other languages, append the localized language between parentheses
                item = AllowedLocale.ALLOWED_LOCALES[i].getEnglishName() + " (" + AllowedLocale.ALLOWED_LOCALES[i].getLocalizedName() + ")";
            }
            jComboBox.addItem(item); // add the String to the combo box

            // Check if it's the current locale, and if so, select it
            if (AllowedLocale.ALLOWED_LOCALES[i].getLocale().toLanguageTag().equals(getViewManager().getClient().getLocaleManager().getLocale().toLanguageTag())) {
                jComboBox.setSelectedIndex(i);
            }
        }

        panel.add(jComboBox, BorderLayout.CENTER);

        /*
            Save button
         */
        JButton button = new JButton("Save changes");
        panel.add(button, BorderLayout.PAGE_END);

        // Save Locale change
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (AllowedLocale allowedLocale : AllowedLocale.ALLOWED_LOCALES) {
                    /*
                    Check if the name of the selected locale matches an AllowedLocale constant
                    We have to check 2 things: first, the name just in English, because if there's no
                    translation we don't include the localized name between parentheses.
                    Then, we check including the name in English and then between parentheses, such as Spanish (Español)
                     */
                    if (jComboBox.getSelectedItem().equals(allowedLocale.getEnglishName())
                            || jComboBox.getSelectedItem()
                            .equals(allowedLocale.getEnglishName() + " (" + allowedLocale.getLocalizedName() + ")")) {

                        // We only want to trigger a locale set if the selected Locale is different than the current one
                        if (!getViewManager().getClient().getLocaleManager().getLocale().equals(allowedLocale.getLocale())) {
                            // Disable inputs while we change Locale
                            jComboBox.setEnabled(false);
                            button.setEnabled(false);
                            ClientLogger.getLogger().info("Switching locale to " + allowedLocale.getCode());
                            getViewManager().getClient().getLocaleManager().setLocale(allowedLocale.getLocale());
                            getViewManager().repaintAll();  // this will trigger a global repaint
                        }

                        // Finally, dispose this window.
                        dispose();
                    }
                }
            }
        });

        internalFrame.add(panel);
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
