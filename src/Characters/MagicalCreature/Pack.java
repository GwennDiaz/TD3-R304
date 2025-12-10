package Characters.MagicalCreature;
import java.util.ArrayList;
import java.util.List;

public class Pack {
    private String name;
    private List<Lycanthrope> members;

    public Pack(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(Lycanthrope l) {
        members.add(l);
    }

    public void removeMember(Lycanthrope l) {
        members.remove(l);
    }

    public String getName() { return name; }
    public List<Lycanthrope> getMembers() { return members; }

    // Diffusion d'un hurlement à toute la meute
    public void broadcastHowl(Lycanthrope sender, String howlType) {
        System.out.println("--- La meute '" + name + "' résonne ---");
        for (Lycanthrope m : members) {
            if (m != sender) {
                m.entendreHurlement(sender, howlType);
            }
        }
    }
}