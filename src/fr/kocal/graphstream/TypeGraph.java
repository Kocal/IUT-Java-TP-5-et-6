package fr.kocal.graphstream;

/**
 * Created by Hugo Alliaume on 04/01/16.
 */
public enum TypeGraph {
    CYCLE("Cycle"),
    CHAIN("Chaîne"),
    TORUS("Tore"),
    SQUARE_GRID("Grille carée"),
    NTREE("Arbre n-aire"),
    RANDOM("Aléatoire");

    String name;

    TypeGraph(String chaine) {
        this.name = chaine;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
