import io.mysticcraft.mystvale.MystVale;

public class TestMystVale {
    public static void main(String[] args) {
        System.out.println("=== Testing MystVale ===");
        MystVale.ready();
        System.out.println("Response: " + MystVale.getLastResponse());
        System.out.println("=== Done ===");
    }
}
