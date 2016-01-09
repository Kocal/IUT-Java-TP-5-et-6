package fr.kocal.graphstream;

import layout.TableLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hugo Alliaume on 09/01/16.
 */
public abstract class ModalBox {

    private static double sizeForOneRowTwoColumns[][] = {
            {5, TableLayout.PREFERRED, TableLayout.FILL, 5},
            {5, TableLayout.FILL, 5}
    };

    private static double sizeForTwoRowsTwoColumns[][] = {
            {5, TableLayout.PREFERRED, TableLayout.FILL, 5},
            {5, TableLayout.FILL, TableLayout.FILL, 5}
    };

    private static JSpinner generateSpinner() {
        return new JSpinner(
                new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1)
        );
    }

    private static int[] renderOneRowsTwoColumns(DefaultComboBoxModel<TypeGraph> comboBoxModel, String label, JSpinner spinner) {
        int params[] = new int[1];

        JPanel panel = new JPanel(new TableLayout(sizeForOneRowTwoColumns));

        panel.add(new JLabel(label), "1, 1");
        panel.add(spinner, "2, 1");

        int result = JOptionPane.showConfirmDialog(null, panel, comboBoxModel.getSelectedItem().toString(), JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            params[0] = (int) spinner.getValue();

            return params;
        }

        return null;
    }

    private static int[] renderTwoRowsTwoColumns(DefaultComboBoxModel<TypeGraph> comboBoxModel, String label1, JSpinner spinner1, String label2, JSpinner spinner2) {
        int params[] = new int[2];

        JPanel panel = new JPanel(new TableLayout(sizeForTwoRowsTwoColumns));

        panel.add(new JLabel(label1), "1, 1");
        panel.add(spinner1, "2, 1");

        panel.add(new JLabel(label2), "1, 2");
        panel.add(spinner2, "2, 2");

        int result = JOptionPane.showConfirmDialog(null, panel, comboBoxModel.getSelectedItem().toString(), JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            params[0] = (int) spinner1.getValue();
            params[1] = (int) spinner2.getValue();

            return params;
        }

        return null;
    }

    public static int[] renderForSummits(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
        return renderOneRowsTwoColumns(comboBoxModel, "Nb sommets : ", ModalBox.generateSpinner());
    }

    public static int[] renderForSize(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
        return renderOneRowsTwoColumns(comboBoxModel, "Taille : ", generateSpinner());
    }

    public static int[] renderForSideSize(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
        return renderOneRowsTwoColumns(comboBoxModel, "Taille du côté: ", generateSpinner());
    }

    public static int[] renderForHeightAndChildren(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
        return renderTwoRowsTwoColumns(comboBoxModel, "Hauteur : ", generateSpinner(), "Nb fils/noeud : ", generateSpinner());
    }

    public static int[] renderForSummitsAndDegrees(DefaultComboBoxModel<TypeGraph> comboBoxModel) {
        return renderTwoRowsTwoColumns(comboBoxModel, "Nb sommets : ", generateSpinner(), "Degré moyen : ", generateSpinner());
    }
}
