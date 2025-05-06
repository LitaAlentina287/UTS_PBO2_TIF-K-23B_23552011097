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

### 1. **Inheritance**
Kelas `Makanan` dan `Minuman` merupakan turunan dari kelas abstrak `ItemMakanan`. Keduanya mewarisi atribut dan method dari `ItemMakanan` seperti `id`, `nama`, `harga`, serta metode `hitungHarga`.

```java
public class Makanan extends ItemMakanan { ... }
public class Minuman extends ItemMakanan { ... }

