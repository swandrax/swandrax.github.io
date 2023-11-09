import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, Integer> daftarMenu;

    public Menu() {
        daftarMenu = new HashMap<>();
        daftarMenu.put("Nasi Goreng", 15000);
        daftarMenu.put("Mie Goreng", 12000);
        daftarMenu.put("Es Teh", 5000);
        daftarMenu.put("Es Jeruk", 6000);
    }

    public int hargaMenu(String namaMenu) {
        return daftarMenu.getOrDefault(namaMenu, 0);
    }
}