package Location;

import java.util.ArrayList;

public class InvasionTheater {
    private String nom;
    private int maxLieux;
    //private List<Lieu> lieux; //
    //private List<ChefDeClan> chefs;

    public InvasionTheater(String nom) {
        this.nom = nom;
        this.maxLieux = 10;
//        this.lieux = new ArrayList<>();
//        this.chefs = new ArrayList<>();
    }



    public static void main(String[] args){
        InvasionTheater invasionTheater = new InvasionTheater("Gaule");
    }

    public static void simulationLoop(){

    }


}
