package fr.kocal.graphstream;

import layout.TableLayout;
import sun.awt.HorizBagLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hugo Alliaume on 04/01/16.
 */
public class Window extends JFrame {

    /**
     * Création d'un objet Window
     *
     * @param dimensions dimensions de la fenêtre
     */
    public Window(Dimension dimensions) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Graphstream");
        this.setSize(dimensions);
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        this.setupPanels();
    }

    /**
     * Création d'un objet Window ayant pour dimensions 1000x600
     */
    public Window() {
        this(new Dimension(1100, 600));
    }

    private void setupPanels() {
        double border = 10;
        double size[][] = {
            { border, TableLayout.FILL, 200, border}, // colonnes
            { // Lignes
                border,
                TableLayout.PREFERRED,
                TableLayout.PREFERRED,
                TableLayout.PREFERRED,
                TableLayout.FILL,
                border
            }
        };

        this.setLayout(new TableLayout(size));

        JPanel panelChoice = this.makePanelChoice();
        JPanel panelActions = this.makePanelActions();
        JPanel panelAlgos = this.makePanelAlgos();

        JPanel canvas = new JPanel();
        canvas.setBackground(Color.RED);

        this.add(canvas, "1, 1, 1, 4");
        this.add(panelChoice, "2, 1");
        this.add(panelActions, "2, 2");
        this.add(panelAlgos, "2, 3");
        this.add(new JPanel(), "2, 4");

    }

    private JPanel makePanelChoice() {
        return new JPanel() {

            DefaultComboBoxModel<TypeGraph> comboBoxModel;

            JComboBox<TypeGraph> comboBox;

            JButton buttonGenerate;

            public JPanel make() {
                this.setLayout(new BorderLayout());
                this.setBorder(BorderFactory.createTitledBorder("Choix du graphe"));
                this.makeComboBox();
                this.makeButtonGenerate();

                return this;
            }

            private void makeComboBox() {
                this.comboBoxModel = new DefaultComboBoxModel(TypeGraph.values());
                this.comboBox = new JComboBox<TypeGraph>(this.comboBoxModel);

                this.add(this.comboBox, BorderLayout.NORTH);
            }

            private void makeButtonGenerate() {
                this.buttonGenerate = new JButton("Générer");
                this.buttonGenerate.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        int params[] = new int[0];

                        switch ((TypeGraph) comboBoxModel.getSelectedItem()) {
                            case CYCLE:
                            case CHAINE:
                                params = ModalBox.renderForSummits(comboBoxModel);
                                break;

                            case TORE:

                                break;

                            case GRILLE_CARRE:
                                break;

                            case ARBRE_UNAIRE:
                                break;

                            case ALEATOIRE:
                                break;

                            default:
                                System.out.println("???");
                        }

                        System.out.println(params);
                    }
                });

                this.add(this.buttonGenerate, BorderLayout.SOUTH);
            }

        }.make();
    }

    private JPanel makePanelActions() {
        return (new JPanel() {

            JButton buttonToPDF;

            JButton buttonPonderer;

            public JPanel make() {
                this.setLayout(new BorderLayout());
                this.setBorder(BorderFactory.createTitledBorder("Actions"));
                this.makeButtonToPDF();
                this.makeButtonPonderer();

                return this;
            }

            private void makeButtonToPDF() {
                this.buttonToPDF = new JButton("Exporter en PDF");
                this.add(this.buttonToPDF, BorderLayout.NORTH);
            }

            private void makeButtonPonderer() {
                this.buttonPonderer = new JButton("Pondérer");
                this.add(this.buttonPonderer, BorderLayout.SOUTH);
            }
        }).make();
    }

    private JPanel makePanelAlgos() {
        return (new JPanel() {

            JButton buttonArbreCourant;

            JButton buttonWelshPowell;

            JButton buttonDSat;

            public JPanel make() {
                this.setLayout(new BorderLayout());
                this.setBorder(BorderFactory.createTitledBorder("Algorithme"));
                this.makeButtonArbreCourant();
                this.makeButtonWelshPowell();
                this.makeButtonDSat();

                return this;
            }

            private void makeButtonArbreCourant() {
                this.buttonArbreCourant = new JButton("Exporter en PDF");
                this.add(this.buttonArbreCourant, BorderLayout.NORTH);
            }

            private void makeButtonWelshPowell() {
                this.buttonWelshPowell = new JButton("Welsh Powell");
                this.add(this.buttonWelshPowell, BorderLayout.CENTER);
            }

            private void makeButtonDSat() {
                this.buttonDSat = new JButton("D Sat");
                this.add(this.buttonDSat, BorderLayout.SOUTH);
            }

        }).make();
    }
}
