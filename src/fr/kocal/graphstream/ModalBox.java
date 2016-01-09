package fr.kocal.graphstream;

import layout.TableLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hugo Alliaume on 09/01/16.
 */
public abstract class ModalBox {

    public static double sizeForOneRowTwoColumns[][] = {
            {5, TableLayout.PREFERRED, TableLayout.FILL, 5},
            {5, TableLayout.FILL, 5}
    };

    public static double sizeForTwoRowsTwoColumns[][] = {
            {5, TableLayout.PREFERRED, TableLayout.FILL, 5},
            {5, TableLayout.FILL, TableLayout.FILL, 5}
    };

    public static int[] renderForSummits(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
        int params[] = new int[1];

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, panel.getHeight()));
        panel.setLayout(new TableLayout(ModalBox.sizeForOneRowTwoColumns));

        JLabel label = new JLabel("Nombre de sommets : ");
        JTextField nbSommets = new JTextField();

        panel.add(label, "1, 1");
        panel.add(nbSommets, "2, 1");

        int result = JOptionPane.showConfirmDialog(null, panel, comboBoxModel.getSelectedItem().toString(), JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                params[0] = Integer.parseInt(nbSommets.getText());
            } catch (Exception e) {
                e.printStackTrace();
                return renderForSummits(comboBoxModel);
            }
        }

        return params;
    }

        public static int[] renderForChain(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
            int params =
        }
}
