# UTS Pemrograman Berorientasi Obyek 2

**Mata Kuliah:** Pemrograman Berorientasi Obyek 2  
**Dosen Pengampu:** Muhammad Ikhwan Fathulloh

## Profil

- **Nama:** {Lita Alentina}  
- **NIM:** {23552011097}  
- **Studi Kasus:** Sistem Kasir Restoran

---

## Judul Studi Kasus

**Penerapan Konsep OOP dan CRUD dalam Sistem Kasir Restoran Berbasis Java dan MySQL**

---

## Penjelasan Studi Kasus

Studi kasus ini bertujuan membangun aplikasi kasir untuk restoran yang mampu mencatat menu makanan dan minuman, membuat pesanan dari pelanggan, serta mencatat status pembayaran. Aplikasi dikembangkan menggunakan bahasa Java, database MySQL, dan menerapkan prinsip Pemrograman Berorientasi Objek (OOP) seperti inheritance, encapsulation, polymorphism, dan abstraction.

---

## Penjelasan 4 Pilar OOP dalam Studi Kasus

---

## 1️⃣ Inheritance (Pewarisan)

Pewarisan digunakan pada kelas `Makanan` dan `Minuman`, yang mewarisi atribut dan method dari kelas abstrak `ItemMakanan`.

```java
// Superclass Abstrak
public abstract class ItemMakanan {
    protected int id;
    protected String nama;
    protected double harga;

    public ItemMakanan(int id, String nama, double harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public abstract double hitungHarga(int jumlah);
}

// Subclass Makanan
public class Makanan extends ItemMakanan {
    public Makanan(int id, String nama, double harga) {
        super(id, nama, harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
}

// Subclass Minuman
public class Minuman extends ItemMakanan {
    public Minuman(int id, String nama, double harga) {
        super(id, nama, harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
}





## 2️⃣ Encapsulation (Enkapsulasi)
Data disembunyikan menggunakan modifier private dan hanya bisa diakses melalui getter/setter untuk menjaga keamanan data.

```java
// Class Pesanan
public class Pesanan {
    private int id;
    private int meja;
    private String status;
    private List<DetailPesanan> detailList = new ArrayList<>();

    public Pesanan(int id, int meja, String status) {
        this.id = id;
        this.meja = meja;
        this.status = status;
    }

    public int getId() { return id; }
    public int getMeja() { return meja; }
    public String getStatus() { return status; }
    public List<DetailPesanan> getDetailList() { return detailList; }

    public void tambahDetail(DetailPesanan detail) {
        detailList.add(detail);
    }
}





// Class DetailPesanan
public class DetailPesanan {
    private ItemMakanan menu;
    private int jumlah;

    public DetailPesanan(ItemMakanan menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public ItemMakanan getMenu() { return menu; }
    public int getJumlah() { return jumlah; }

    public double getTotalHarga() {
        return menu.hitungHarga(jumlah);
    }
}





## 3️⃣ Polymorphism (Polimorfisme)
Polimorfisme ditunjukkan dengan method hitungHarga() yang memiliki implementasi berbeda di setiap subclass, tetapi dipanggil menggunakan referensi ItemMakanan.

```java
// Superclass
public abstract class ItemMakanan {
    public abstract double hitungHarga(int jumlah);
}

// Subclass Makanan
@Override
public double hitungHarga(int jumlah) {
    return harga * jumlah;
}

// Subclass Minuman
@Override
public double hitungHarga(int jumlah) {
    return harga * jumlah;
}





## 4️⃣ Abstraction (Abstraksi)
Kelas ItemMakanan merupakan abstract class yang tidak bisa diinstansiasi secara langsung. Method hitungHarga dideklarasikan secara abstrak agar wajib diimplementasikan oleh subclass.

```java
// Abstract Class
public abstract class ItemMakanan {
    protected int id;
    protected String nama;
    protected double harga;

    public ItemMakanan(int id, String nama, double harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public abstract double hitungHarga(int jumlah);
}





🔗 Tautan Proyek
📁 GitHub Repository: UTS_PBO2_TIF-K-23B_23552011097

🎥 YouTube Demo: https://youtu.be/tODA-1ikwcg?si=T0TffNRm8WglQ2sT




