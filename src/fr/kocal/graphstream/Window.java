package fr.kocal.graphstream;

import layout.TableLayout;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hugo Alliaume on 04/01/16.
 */
public class Window extends JFrame {

    Window window;

    Graph graph;

    Viewer viewer;

    /**
     * Création d'un objet Window
     *
     * @param dimensions dimensions de la fenêtre
     */
    public Window(Dimension dimensions) {
        this.window = this;

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
        double border = 5;
        double size[][] = {
                {border, TableLayout.FILL, border, 200, border}, // colonnes
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

        this.add(panelChoice, "3, 1");
        this.add(panelActions, "3, 2");
        this.add(panelAlgos, "3, 3");
        this.add(new JPanel(), "3, 4");
    }

    private JPanel makePanelChoice() {
        return new JPanel() {

            DefaultComboBoxModel<TypeGraph> comboBoxModel;

            JComboBox<TypeGraph> comboBox;

            JButton buttonGenerate;

            View view;

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
                        TypeGraph typeGraph = (TypeGraph) comboBoxModel.getSelectedItem();

                        switch (typeGraph) {
                            case CHAIN:
                            case CYCLE:
                                params = ModalBox.renderForSummits(comboBoxModel);
                                break;

                            case NTREE:
                                params = ModalBox.renderForHeightAndChildren(comboBoxModel);
                                break;

                            case RANDOM:
                                params = ModalBox.renderForSummitsAndDegrees(comboBoxModel);
                                break;

                            case SQUARE_GRID:
                                params = ModalBox.renderForSideSize(comboBoxModel);
                                break;

                            case TORUS:
                                params = ModalBox.renderForSize(comboBoxModel);
                                break;

                            default:
                                System.out.println("???");
                        }

                        if (params != null) {
                            graph = GraphFactory.from(typeGraph, params);

                            if (graph != null) {
                                if (view != null) {
                                    window.remove(view);
                                }

                                viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
                                view = viewer.addDefaultView(false);
                                viewer.enableAutoLayout();

                                window.add(view, "1, 1, 1, 4");
                                window.revalidate();
                            }
                        }
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
