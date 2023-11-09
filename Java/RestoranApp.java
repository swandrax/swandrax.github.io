import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class RestoranApp extends JFrame {
    private Map<String, String> userDatabase;
    private Pesanan pesanan;
    private JTextArea textArea;
    private double totalPembelian;
    private String currentUser;

    public RestoranApp() {
        userDatabase = new HashMap<>();
        pesanan = new Pesanan();
        totalPembelian = 0;

        setTitle("Aplikasi Restoran");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel untuk pendaftaran akun
        JPanel registerPanel = new JPanel();
        JTextField registerUsernameField = new JTextField(10);
        JPasswordField registerPasswordField = new JPasswordField(10);
        JButton registerButton = new JButton("Daftar");

        registerPanel.add(new JLabel("Buat Akun Baru"));
        registerPanel.add(new JLabel("Username: "));
        registerPanel.add(registerUsernameField);
        registerPanel.add(new JLabel("Password: "));
        registerPanel.add(registerPasswordField);
        registerPanel.add(registerButton);

        // Panel untuk login
        JPanel loginPanel = new JPanel();
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username: "));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password: "));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        // Panel untuk menampilkan menu dan pesanan
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JButton orderButton = new JButton("Pesan");
        textArea = new JTextArea();

        mainPanel.add(orderButton, BorderLayout.SOUTH);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Tambahkan panel pendaftaran akun
        add(registerPanel, BorderLayout.NORTH);

        // Event listener untuk tombol pendaftaran akun
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                daftarAkun(registerUsernameField.getText(), new String(registerPasswordField.getPassword()));
            }
        });

        // Event listener untuk tombol login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(usernameField.getText(), new String(passwordField.getPassword()));
            }
        });

        // Event listener untuk tombol pesan
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesanMenu();
            }
        });
    }

    // Metode untuk mendaftarkan akun baru
    private void daftarAkun(String username, String password) {
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, password);
            JOptionPane.showMessageDialog(this, "Akun berhasil dibuat!");
        } else {
            JOptionPane.showMessageDialog(this, "Username sudah digunakan. Coba lagi.");
        }
    }

    // Metode untuk menangani login
    private void login(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            currentUser = username;
            tampilkanMenuPesanan();
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal. Coba lagi.");
        }
    }

    // Metode untuk menampilkan panel menu pesanan setelah login berhasil
    private void tampilkanMenuPesanan() {
        // Hapus panel pendaftaran akun dan panel login
        remove(getComponent(0));
        remove(getComponent(0));
        revalidate();
        repaint();
        // Tampilkan panel menu pesanan
        getContentPane().add(new JScrollPane(textArea), BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    // Metode untuk menangani pemesanan menu
    private void pesanMenu() {
        String namaMenu = JOptionPane.showInputDialog("Masukkan nama menu:");
        if (namaMenu != null) {
            int harga = pesanan.hargaMenu(namaMenu);
            if (harga > 0) {
                totalPembelian += harga;
                textArea.append(namaMenu + "\tRp " + harga + "\n");

                // Tambahkan pemilihan metode pembayaran
                String[] metodePembayaran = {"Debit", "Credit"};
                String pilihanPembayaran = (String) JOptionPane.showInputDialog(this, "Pilih metode pembayaran:",
                        "Metode Pembayaran", JOptionPane.PLAIN_MESSAGE, null, metodePembayaran, metodePembayaran[0]);

                // Hitung diskon jika total pembelian melebihi 35 ribu
                if (totalPembelian > 35000) {
                    totalPembelian *= 0.9; // Diskon 10%
                    JOptionPane.showMessageDialog(this, "Diskon 10% diterapkan!");
                }

                // Cetak struk
                cetakStruk(pilihanPembayaran);
            } else {
                JOptionPane.showMessageDialog(this, "Menu tidak valid!");
            }
        }
    }

    // Metode untuk mencetak struk
    private void cetakStruk(String metodePembayaran) {
        JOptionPane.showMessageDialog(this, "Struk Pembayaran\n" +
                "Metode Pembayaran: " + metodePembayaran + "\n" +
                "Total Pembelian: Rp " + totalPembelian);
        // Reset total pembelian
        totalPembelian = 0;
        // Hapus isi JTextArea
        textArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestoranApp().setVisible(true);
            }
        });
    }
}

class Pesanan {
    private Map<String, Integer> daftarMenu;

    public Pesanan() {
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
