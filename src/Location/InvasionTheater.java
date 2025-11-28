package Location;

import Characters.Character;
import Characters.Roles.ClanChief;

import java.util.ArrayList;
import java.util.List;

public class InvasionTheater {
    private String name;
    private int maxLieux;
    private List<Location> place; //
    private List<ClanChief> chefs;

    public InvasionTheater(String nom) {
        this.name = name;
        this.maxLieux = 10;
        this.place = new ArrayList<>();
        this.chefs = new ArrayList<>();
    }

    public void showInformationTheatre() {
        System.out.println("Name of place: " + name);
        for (Location location : place) {
            //location.showAllStat();   VOIR CE QU'IL Y A DANS LOCATION
        }
    }

//    public void showAllStat() {
  //      int totalPopulation= 0;
    //    System.out.println("Informations :");
      //  for (Location l : place) {
        //    System.out.println("Place : " + l.getName());
          //  for (Character p : l.getPresentCharacters()) {
            //    System.out.println(" - " + p.getName() + " (" + p.getClass().getSimpleName() + ")");
              //  totalPopulation++;
            //}
    //    }
  //      System.out.println("Number of population : " + totalPopulation);
//    }

    public static void main(String[] args){
        InvasionTheater invasionTheater = new InvasionTheater("Gaule");
    }

    public static void simulationLoop(){

    }


}
