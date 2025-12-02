import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Product {
    String id;
    String name;
    int quantity;

    Product(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}

class History {
    String action;   // MASUK / KELUAR
    String id;
    String name;
    int amount;

    History(String action, String id, String name, int amount) {
        this.action = action;
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}

public class ProductManager {

    public static void main(String[] args) {

        ArrayList<Product> products = new ArrayList<>();
        ArrayList<History> historyList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== MENU PRODUK ===");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Tampilkan Produk");
            System.out.println("3. Sorting Produk (A-Z)");
            System.out.println("4. Searching Produk");
            System.out.println("5. Tambah Quantity");
            System.out.println("6. Kurangi Quantity");
            System.out.println("7. Lihat Riwayat Masuk/Keluar");
            System.out.println("8. Keluar");
            System.out.print("Pilih menu: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                // (1) Tambah produk
                case 1:
                    System.out.print("ID produk: ");
                    String id = input.nextLine();

                    System.out.print("Nama produk: ");
                    String name = input.nextLine();

                    System.out.print("Quantity awal: ");
                    int qty = input.nextInt();

                    products.add(new Product(id, name, qty));
                    historyList.add(new History("MASUK", id, name, qty));

                    System.out.println("Produk berhasil ditambahkan!");
                    break;

                // (2) Tampilkan produk
                case 2:
                    if (products.isEmpty()) {
                        System.out.println("Belum ada produk.");
                        break;
                    }
                    System.out.println("\n=== DAFTAR PRODUK ===");
                    for (int i = 0; i < products.size(); i++) {
                        Product p = products.get(i);
                        System.out.println(
                            (i + 1) + ". [" + p.id + "] " + p.name + " - Qty: " + p.quantity
                        );
                    }
                    break;

                // (3) Sorting
                case 3:
                    Collections.sort(products, Comparator.comparing(p -> p.name.toLowerCase()));
                    System.out.println("Produk berhasil diurutkan A-Z!");
                    break;

                // (4) Searching
                case 4:
                    System.out.println("\n=== SEARCH PRODUK ===");
                    System.out.println("1. Cari berdasarkan ID");
                    System.out.println("2. Cari berdasarkan Nama");
                    System.out.print("Pilih: ");
                    int searchChoice = input.nextInt();
                    input.nextLine();

                    if (searchChoice == 1) {
                        // Cari by ID
                        System.out.print("Masukkan ID: ");
                        String findID = input.nextLine();

                        boolean foundID = false;
                        for (Product p : products) {
                            if (p.id.equalsIgnoreCase(findID)) {
                                System.out.println(
                                    "Ditemukan: [" + p.id + "] " + p.name + " - Qty: " + p.quantity
                                );
                                foundID = true;
                            }
                        }
                        if (!foundID) System.out.println("Produk dengan ID tersebut tidak ditemukan.");

                    } else if (searchChoice == 2) {
                        // Cari by Nama
                        System.out.print("Masukkan nama: ");
                        String findName = input.nextLine().toLowerCase();

                        boolean foundName = false;
                        for (Product p : products) {
                            if (p.name.toLowerCase().contains(findName)) {
                                System.out.println(
                                    "Ditemukan: [" + p.id + "] " + p.name + " - Qty: " + p.quantity
                                );
                                foundName = true;
                            }
                        }
                        if (!foundName) System.out.println("Produk dengan nama tersebut tidak ditemukan.");
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                    break;

                // (5) Tambah Quantity
                case 5:
                    if (products.isEmpty()) {
                        System.out.println("Belum ada produk.");
                        break;
                    }
                    System.out.print("Masukkan ID produk: ");
                    String addID = input.nextLine();

                    Product addTarget = null;
                    for (Product p : products) {
                        if (p.id.equalsIgnoreCase(addID)) {
                            addTarget = p;
                            break;
                        }
                    }

                    if (addTarget != null) {
                        System.out.print("Tambah berapa: ");
                        int addQty = input.nextInt();
                        addTarget.quantity += addQty;

                        historyList.add(new History("MASUK", addTarget.id, addTarget.name, addQty));
                        System.out.println("Quantity berhasil ditambahkan!");

                    } else {
                        System.out.println("ID tidak ditemukan.");
                    }
                    break;

                // (6) Kurangi Quantity
                case 6:
                    if (products.isEmpty()) {
                        System.out.println("Belum ada produk.");
                        break;
                    }
                    System.out.print("Masukkan ID produk: ");
                    String minID = input.nextLine();

                    Product minTarget = null;
                    for (Product p : products) {
                        if (p.id.equalsIgnoreCase(minID)) {
                            minTarget = p;
                            break;
                        }
                    }

                    if (minTarget != null) {
                        System.out.print("Kurangi berapa: ");
                        int minQty = input.nextInt();

                        if (minQty > minTarget.quantity) {
                            System.out.println("Quantity tidak mencukupi!");
                        } else {
                            minTarget.quantity -= minQty;

                            historyList.add(new History("KELUAR", minTarget.id, minTarget.name, minQty));

                            if (minTarget.quantity == 0) {
                                products.remove(minTarget);
                                System.out.println("Quantity 0 → produk dihapus!");
                            } else {
                                System.out.println("Quantity berhasil dikurangi!");
                            }
                        }
                    } else {
                        System.out.println("ID tidak ditemukan.");
                    }
                    break;

                // (7) Riwayat barang
                case 7:
                    if (historyList.isEmpty()) {
                        System.out.println("Belum ada riwayat.");
                        break;
                    }

                    System.out.println("\n=== RIWAYAT PRODUK ===");
                    for (History h : historyList) {
                        System.out.println(h.action + " | [" + h.id + "] " + h.name + " | " + h.amount);
                    }
                    break;

                case 8:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (choice != 8);
    }
}
