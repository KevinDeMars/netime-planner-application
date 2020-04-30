package edu.baylor.csi3471.netime_planner.gui.screens;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.baylor.csi3471.netime_planner.gui.command.Command;
import edu.baylor.csi3471.netime_planner.gui.controllers.ProfileScreenController;
import edu.baylor.csi3471.netime_planner.util.Formatters;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;

public class ProfileScreen {
    private final ProfileScreenController controller;

    private JLabel dateLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel categoryLabel;
    private JLabel deadlinesLabel;
    private JLabel groupsLabel;
    private JLabel PHdate;
    private JLabel PHname;
    private JLabel PHemail;
    private JPanel profilePanel;
    private JButton addCategory;
    private JButton addGroup;
    private JButton addUpDeadline;
    private JButton viewScheduleButton;
    private LocalDateTime today;

    public ProfileScreen(String username) {
        controller = new ProfileScreenController(username);
        //Setup GUI
        $$$setupUI$$$();

        //Load pane with user data
        nameLabel.setText("Username: " + controller.getUsername());
        emailLabel.setText("Email: " + controller.getEmail());

        //Get todays date
        today = LocalDateTime.now();
        //Set todays date
        dateLabel.setText("Today: " + today.format(Formatters.LONG_DATE));

    }

    public JPanel getPanel() {
        return this.profilePanel;
    }

    public void setViewScheduleButtonCommand(Command cmd) {
        viewScheduleButton.addActionListener(e -> cmd.execute());
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayoutManager(8, 5, new Insets(0, 0, 0, 0), -1, -1));
        profilePanel.setBackground(new Color(-10394949));
        Font profilePanelFont = this.$$$getFont$$$("Castellar", Font.BOLD, 18, profilePanel.getFont(),1);
        if (profilePanelFont != null) profilePanel.setFont(profilePanelFont);
        profilePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        dateLabel = new JLabel();
        dateLabel.setEnabled(true);
        Font dateLabelFont = this.$$$getFont$$$("Alien Encounters", Font.BOLD | Font.ITALIC, 24, dateLabel.getFont(),1);
        if (dateLabelFont != null) dateLabel.setFont(dateLabelFont);
        dateLabel.setText("Today: ");
        profilePanel.add(dateLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailLabel = new JLabel();
        Font emailLabelFont = this.$$$getFont$$$("Lucida Sans Typewriter", Font.PLAIN, 12, emailLabel.getFont(),1);
        if (emailLabelFont != null) emailLabel.setFont(emailLabelFont);
        emailLabel.setText("Email: ");
        profilePanel.add(emailLabel, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        categoryLabel = new JLabel();
        categoryLabel.setBackground(new Color(-15090895));
        Font categoryLabelFont = this.$$$getFont$$$("Baskerville Old Face", Font.BOLD, 20, categoryLabel.getFont(),1);
        if (categoryLabelFont != null) categoryLabel.setFont(categoryLabelFont);
        categoryLabel.setText("My Categories: ");
        profilePanel.add(categoryLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        Font nameLabelFont = this.$$$getFont$$$("Lucida Sans Typewriter", Font.BOLD, 16, nameLabel.getFont(),1);
        if (nameLabelFont != null) nameLabel.setFont(nameLabelFont);
        nameLabel.setText("Username: ");
        profilePanel.add(nameLabel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        profilePanel.add(spacer1, new GridConstraints(3, 1, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        groupsLabel = new JLabel();
        Font groupsLabelFont = this.$$$getFont$$$("Baskerville Old Face", Font.BOLD, 20, groupsLabel.getFont(),1);
        if (groupsLabelFont != null) groupsLabel.setFont(groupsLabelFont);
        groupsLabel.setText("My Groups: ");
        profilePanel.add(groupsLabel, new GridConstraints(3, 2, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deadlinesLabel = new JLabel();
        Font deadlinesLabelFont = this.$$$getFont$$$("Baskerville Old Face", Font.BOLD, 20, deadlinesLabel.getFont(),1);
        if (deadlinesLabelFont != null) deadlinesLabel.setFont(deadlinesLabelFont);
        deadlinesLabel.setText("Upcoming Deadlines: ");
        profilePanel.add(deadlinesLabel, new GridConstraints(3, 4, 3, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        profilePanel.add(spacer2, new GridConstraints(3, 3, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        profilePanel.add(panel1, new GridConstraints(4, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("N/A");
        panel1.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addCategory = new JButton();
        addCategory.setBackground(new Color(-4514520));
        Font addCategoryFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 24, addCategory.getFont(),1);
        if (addCategoryFont != null) addCategory.setFont(addCategoryFont);
        addCategory.setText("+");
        addCategory.setToolTipText("Add new category");
        panel1.add(addCategory, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        profilePanel.add(panel2, new GridConstraints(5, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("N/A");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addGroup = new JButton();
        addGroup.setBackground(new Color(-4514520));
        Font addGroupFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 24, addGroup.getFont(),1);
        if (addGroupFont != null) addGroup.setFont(addGroupFont);
        addGroup.setText("+");
        addGroup.setToolTipText("Add New Group");
        panel2.add(addGroup, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        profilePanel.add(panel3, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("You have 0 upcoming deadlines");
        panel3.add(label3, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addUpDeadline = new JButton();
        addUpDeadline.setBackground(new Color(-4514520));
        Font addUpDeadlineFont = this.$$$getFont$$$("Source Code Pro", Font.BOLD, 36, addUpDeadline.getFont(),1);
        if (addUpDeadlineFont != null) addUpDeadline.setFont(addUpDeadlineFont);
        addUpDeadline.setText("+");
        addUpDeadline.setToolTipText("Add new upcoming deadline");
        panel3.add(addUpDeadline, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewScheduleButton = new JButton();
        viewScheduleButton.setText("View My Schedule");
        profilePanel.add(viewScheduleButton, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont, int i) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return profilePanel;
    }

}
