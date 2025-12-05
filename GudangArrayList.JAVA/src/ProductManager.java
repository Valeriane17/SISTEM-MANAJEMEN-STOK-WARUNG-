import java.util.*;

class Product {
    String id, name; 
    int quantity;
    Product(String id, String name, int quantity) {
        this.id = id; this.name = name; this.quantity = quantity;
    }
}

class History {
    String action, id, name;
    int amount;
    History(String action, String id, String name, int amount) {
        this.action = action; this.id = id; this.name = name; this.amount = amount;
    }
}

public class ProductManager {

    // ========== RECURSIVE BINARY SEARCH (O(log n)) ==========
    static int binarySearchID(ArrayList<Product> p, String target, int L, int R) {
        if (L > R) return -1;
        int mid = (L + R) / 2;
        int cmp = p.get(mid).id.compareToIgnoreCase(target);

        if (cmp == 0) return mid;
        return (cmp > 0)
                ? binarySearchID(p, target, L, mid - 1)
                : binarySearchID(p, target, mid + 1, R);
    }

    public static void main(String[] args) {

        ArrayList<Product> products = new ArrayList<>();
        ArrayList<History> history = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int c;

        do {
            System.out.println("\n=== MENU PRODUK ===");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Tampilkan Produk");
            System.out.println("3. Sorting Produk (A-Z)");
            System.out.println("4. Searching Produk");
            System.out.println("5. Tambah Quantity");
            System.out.println("6. Kurangi Quantity");
            System.out.println("7. Lihat Riwayat");
            System.out.println("8. Keluar");
            System.out.print("Pilih: ");
            c = in.nextInt(); in.nextLine();

            switch (c) {

                // ===== 1. TAMBAH PRODUK =====
                case 1: 
                    System.out.print("ID: "); String id = in.nextLine();
                    System.out.print("Nama: "); String nm = in.nextLine();
                    System.out.print("Qty: "); int q = in.nextInt();
                    products.add(new Product(id, nm, q));
                    history.add(new History("MASUK", id, nm, q));
                    System.out.println("Produk ditambahkan.");
                    break;

                // ===== 2. TAMPILKAN PRODUK =====
                case 2:
                    if (products.isEmpty()) { System.out.println("Belum ada produk."); break; }
                    int no = 1;
                    for (Product p : products)
                        System.out.println(no++ + ". ["+p.id+"] "+p.name+" - Qty: "+p.quantity);
                    break;

                // ===== 3. SORTING (A-Z) - COLLECTION SORT (O(n log n)) =====
                case 3:
                    products.sort(Comparator.comparing(p -> p.name.toLowerCase()));
                    System.out.println("Produk diurutkan A-Z.");
                    break;

                // ===== 4. SEARCHING =====
                case 4:
                    System.out.println("1. Cari ID (Binary Search)");
                    System.out.println("2. Cari Nama (Linear Search)");
                    System.out.print("Pilih: ");
                    int sc = in.nextInt(); in.nextLine();

                    if (sc == 1) {
                        // === SEARCHING by ID → Binary Search (REKURSIF) ===
                        System.out.print("ID: ");
                        String sid = in.nextLine();

                        // binary search requires sorting by ID
                        products.sort(Comparator.comparing(p -> p.id.toLowerCase()));

                        int index = binarySearchID(products, sid, 0, products.size() - 1);
                        if (index != -1) {
                            Product p = products.get(index);
                            System.out.println("Ditemukan: ["+p.id+"] "+p.name+" - Qty: "+p.quantity);
                        } else System.out.println("Tidak ditemukan.");

                    } else if (sc == 2) {
                        // === SEARCHING by NAME → Linear Search (O(n)) ===
                        System.out.print("Nama: ");
                        String sn = in.nextLine().toLowerCase();
                        boolean f = false;

                        for (Product p : products)
                            if (p.name.toLowerCase().contains(sn)) {
                                System.out.println("[" + p.id + "] " + p.name + " - Qty: " + p.quantity);
                                f = true;
                            }

                        if (!f) System.out.println("Nama tidak ditemukan.");
                    }
                    break;

                // ===== 5. TAMBAH QUANTITY =====
                case 5:
                    System.out.print("ID: ");
                    String addID = in.nextLine();
                    Product add = products.stream()
                            .filter(p -> p.id.equalsIgnoreCase(addID))
                            .findFirst().orElse(null);

                    if (add == null) { System.out.println("ID tidak ditemukan."); break; }

                    System.out.print("Tambah: ");
                    int aq = in.nextInt();
                    add.quantity += aq;

                    history.add(new History("MASUK", add.id, add.name, aq));
                    System.out.println("Quantity ditambahkan.");
                    break;

                // ===== 6. KURANGI QUANTITY =====
                case 6:
                    System.out.print("ID: ");
                    String mid = in.nextLine();
                    Product min = products.stream()
                            .filter(p -> p.id.equalsIgnoreCase(mid))
                            .findFirst().orElse(null);

                    if (min == null) { System.out.println("ID tidak ditemukan."); break; }

                    System.out.print("Kurangi: ");
                    int mq = in.nextInt();

                    if (mq > min.quantity) { System.out.println("Tidak cukup stok!"); break; }

                    min.quantity -= mq;
                    history.add(new History("KELUAR", min.id, min.name, mq));

                    if (min.quantity == 0) {
                        products.remove(min);
                        System.out.println("Produk qty = 0 → dihapus.");
                    } else System.out.println("Quantity dikurangi.");
                    break;

                // ===== 7. RIWAYAT =====
                case 7:
                    if (history.isEmpty()) { System.out.println("Belum ada riwayat."); break; }
                    for (History h : history)
                        System.out.println(h.action+" | ["+h.id+"] "+h.name+" | "+h.amount);
                    break;

                case 8:
                    System.out.println("Program selesai.");
                    break;
            }
        } while (c != 8);
    }
}