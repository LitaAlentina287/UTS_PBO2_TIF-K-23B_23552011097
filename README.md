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


