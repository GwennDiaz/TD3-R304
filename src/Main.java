import Location.InvasionTheater;

public class Main {
    public static void main(String[] args) {
        InvasionTheater theater = new InvasionTheater("Armorique -50 av. JC");
        theater.Initialize();
        theater.simulationLoop();
    }
}