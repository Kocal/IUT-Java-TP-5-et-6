package fr.kocal.graphstream;

/**
 * Created by Hugo Alliaume on 04/01/16.
 */
public enum TypeGraph {
    CYCLE("Cycle"),
    CHAINE("Chaine"),
    TORE("Tore"),
    GRILLE_CARRE("Grille carée"),
    ARBRE_UNAIRE("Arbre n-aire"),
    ALEATOIRE("Aléatoire");

    String name;

    TypeGraph(String chaine) {
        this.name = chaine;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
