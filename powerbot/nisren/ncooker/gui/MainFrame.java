package com.powerbot.nisren.ncooker.gui;

import com.powerbot.nisren.ncooker.IO.Data;
import com.powerbot.nisren.ncooker.IO.Settings;
import com.powerbot.nisren.ncooker.wrappers.Fish;
import com.powerbot.nisren.ncooker.wrappers.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nisren
 * Date: 22/12/12
 * Time: 15:50
 */
public class MainFrame extends JFrame {

    public ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedLocation = locationCombo.getSelectedItem().toString();
            Data.location = Location.getLocation(selectedLocation);
            String selectedFish = fishCombo.getSelectedItem().toString();
            Data.fish = Fish.getFish(selectedFish);
            Data.started = true;
            dispose();
        }
    };
    private JPanel panel = new JPanel(null);
    private JLabel location = new JLabel("Location: ");
    private JLabel fish = new JLabel("Fish Type: ");
    private JComboBox locationCombo = new JComboBox();
    private JComboBox fishCombo = new JComboBox();
    private JButton start = new JButton("Start");

    public MainFrame() {
        init();
        setTitle("NCooker");
        setResizable(false);
    }

    public void init() {
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        {
            {
                panel.add(location);
                location.setBounds(10, 10, location.getPreferredSize().width, location.getPreferredSize().height);
            }
            {
                panel.add(fish);
                fish.setBounds(new Rectangle(new Point(10, 40), fish.getPreferredSize()));
            }
            {
                ComboBoxModel<String> model = new DefaultComboBoxModel<String>() {
                    @Override
                    public int getSize() {
                        return getLocationData().size();
                    }

                    @Override
                    public String getElementAt(int index) {
                        return getLocationData().get(index);
                    }
                };
                locationCombo.setModel(model);
                locationCombo.setSelectedItem(Data.settings.getValue(Settings.KEY_LOCATION)); //TODO change default location
                locationCombo.setEnabled(false);
                panel.add(locationCombo);
                locationCombo.setBounds(location.getPreferredSize().width + 15, 8, locationCombo.getPreferredSize().width, locationCombo.getPreferredSize().height);
            }

            {
                fishCombo.setModel(new DefaultComboBoxModel<Object>() {
                    @Override
                    public int getSize() {
                        return getFishData().size();
                    }

                    @Override
                    public Object getElementAt(int index) {
                        return getFishData().get(index);
                    }

                });
                fishCombo.setSelectedItem(Data.settings.getValue(Settings.KEY_FISH)); //TODO change default fish
                panel.add(fishCombo);
                fishCombo.setBounds(location.getPreferredSize().width + 15, 38, fishCombo.getPreferredSize().width, fishCombo.getPreferredSize().height);

            }
            {
                start.addActionListener(actionListener);
                panel.add(start);
                start.setBounds(5, 65, 176, start.getPreferredSize().height);
            }
        }
        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < panel.getComponentCount(); i++) {
                Rectangle bounds = panel.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = panel.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            panel.setMinimumSize(preferredSize);
            panel.setPreferredSize(preferredSize);
        }
        contentPane.add(panel);
        panel.setSize(180, 88);
        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
    }

    public List<String> getLocationData() {
        List<String> data = new ArrayList<>();
        for (Location location : Location.values()) {
            data.add(location.getName());
        }
        return data;
    }

    public List<String> getFishData() {
        List<String> data = new ArrayList<>();
        for (Fish fish : Fish.values()) {
            data.add(fish.getName());
        }
        return data;
    }
}
