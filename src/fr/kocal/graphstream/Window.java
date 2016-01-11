package fr.kocal.graphstream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;
import layout.TableLayout;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Hugo Alliaume on 04/01/16.
 */
public class Window extends JFrame {

    Window window;

    Graph graph;

    Viewer viewer;

    DefaultComboBoxModel<TypeGraph> comboBoxModel;

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
                comboBoxModel = new DefaultComboBoxModel(TypeGraph.values());
                this.comboBox = new JComboBox<TypeGraph>(comboBoxModel);

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

            JPanel self;

            JFileChooser fc;

            JButton buttonToPDF;

            JButton buttonPonderer;

            public JPanel make() {

                this.fc = new JFileChooser();
                this.self = this;

                this.setLayout(new BorderLayout());
                this.setBorder(BorderFactory.createTitledBorder("Actions"));
                this.makeButtonToPDF();
                this.makeButtonPonderer();

                return this;
            }

            private void makeButtonToPDF() {
                this.buttonToPDF = new JButton("Exporter en PDF");
                this.buttonToPDF.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        // La solution avec un OutputStream ne fonctionne pas encore (méthode pas implémentée :-))
                        //ByteArrayOutputStream stream = null;

                        if (graph == null) {
                            return;
                        }

                        int returnVal = fc.showSaveDialog(self);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {

                                FileSinkImages pic = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.VGA);
                                pic.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);

                                File file = fc.getSelectedFile();
                                String pdfFilename = file.getAbsolutePath();
                                String imageFilename = pdfFilename + ".png";

                                //pic.writeAll(graph, stream);
                                //Image png = PngImage.getImage(stream.toByteArray());

                                pic.writeAll(graph, imageFilename);
                                Image png = PngImage.getImage(imageFilename);

                                // Génération sur le PDF

                                Document document = new Document();
                                PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));

                                ArrayList<Node> degrees = Toolkit.degreeMap(graph);

                                Font fontTitle = new Font(Font.getFamily("TIMES_ROMAN"), 18, Font.BOLD);
                                Font fontParagraph = new Font(Font.getFamily("TIMES_ROMAN"), 14);

                                document.open();

                                document.addTitle("Génération de graphique");
                                document.addAuthor(System.getProperty("user.name"));
                                document.addCreator(System.getProperty("user.name"));

                                document.add(new Paragraph("Informations sur le graphe", fontTitle));
                                document.add(new Paragraph("Nom : " + comboBoxModel.getSelectedItem().toString(), fontParagraph));
                                document.add(new Paragraph("Degré moyen : " + Toolkit.averageDegree(graph) + "deg", fontParagraph));
                                document.add(new Paragraph("Degré maxi : " + degrees.get(degrees.size() - 1) + "deg", fontParagraph));
                                document.add(new Paragraph("Degré mini : " + degrees.get(0) + "deg", fontParagraph));
                                document.add(new Paragraph("Diamètre : " + Toolkit.diameter(graph) + "px", fontParagraph));
                                document.add(png);

                                document.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
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
                this.buttonArbreCourant = new JButton("Arbre courant");
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
