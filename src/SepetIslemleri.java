import java.sql.*;
import java.util.Scanner;

public class SepetIslemleri {
//ürün ekleme güncelleme silme işlemlerini ekle


    public static double sepetToplam = 0;
    static double fiyat = 0;
    static double paraustu = 0;
    static int secim = 0;
    static int barkod = 0;

    static int stok = 0;
    static String urunAdi;
    static String reyon;

    static Scanner klavye = new Scanner(System.in);


    public static void urunEkle() { //ÜRÜN EKLEME


        do {
            System.out.println("Ürün adını giriniz.");
            urunAdi = klavye.nextLine();


            System.out.println("Ürünün reyonunu giriniz.");
            reyon = klavye.nextLine();


            System.out.println("Barkod numarasını giriniz.");
            barkod=klavye.nextInt();
            klavye.skip("").nextLine();


            System.out.println("Ürünün fiyatını giriniz.");
            fiyat = klavye.nextDouble();
            klavye.skip("").nextLine();


            System.out.println("Ürünün stok adedini giriniz.");
            stok = klavye.nextInt();


            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                        "123456");
                Statement myStat = baglanti.createStatement();

                PreparedStatement prpStat = baglanti.prepareStatement("INSERT into urun(barkod, urunadı, reyon, fiyat, stok) values (?,?,?,?,?)");

                prpStat.setInt(1, barkod);
                prpStat.setString(2, urunAdi);
                prpStat.setString(3, reyon);
                prpStat.setDouble(4, fiyat);
                prpStat.setInt(5, stok);

                prpStat.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }// Üstteki kod Veritabanına girdi eklemek için.

            System.out.println("Yeni ürün eklemek ister misiniz? \n(Evet=1, Hayır=2)");
            secim = klavye.nextInt();
            klavye.skip("").nextLine();

            if (secim == 1) {

            } else {
                System.out.println("Ana ekrane yönlendiriliyor...");
                ListelemeIslemleri.kategori();
            }

        } while (true);

    }


    //ÜRÜN FİYAT GÜNCELLE
    public static void urunGuncelle() {

        do {
            System.out.println("Fiyatı Güncellenecek Ürünün Barkod numarasını giriniz.");
            barkod = klavye.nextInt();
            klavye.skip("").nextLine();


            System.out.println("Ürünün Yeni fiyatını giriniz.");
            fiyat = klavye.nextDouble();
            klavye.skip("").nextLine();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                        "123456");
                Statement myStat = baglanti.createStatement();
                ResultSet rs = myStat.executeQuery("select * from urun ");

                myStat.executeUpdate("update urun set fiyat=" + fiyat + " where barkod='" + barkod + "'");
            } catch (Exception e) {
                System.out.println(e);
            }// Üstteki kod Veritabanında güncellemesi yapmak için.

            System.out.println("Yeni güncelleme işlem yapmak ister misiniz? \n(Evet=1, Hayır=2)");
            secim = klavye.nextInt();
            klavye.skip("").nextLine();

            if (secim == 1) {
                System.out.println("Yeni güncelleme işlemi başlatılıyor...");
            } else {
                System.out.println("Ana ekrane yönlendiriliyor...");
                ListelemeIslemleri.kategori();
                break;
            }

        } while (true);
    }

    //SEPETE ÜRÜN EKLE
    public static void sepeteUrunEkle() {


        int barkod;


        System.out.println("Sepete eklenecek ürünün barkodunu giriniz.");
        barkod = klavye.nextInt();
        klavye.skip("").nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                    "123456");
            Statement myStat = baglanti.createStatement();
            ResultSet rs = myStat.executeQuery("select * from urun");

            PreparedStatement prpStat = baglanti.prepareStatement("INSERT into sepet(barkod, urunAdı, reyon, fiyat, stok) values (?,?,?,?,?)");

            while (rs.next()) {
                if (barkod == rs.getInt(1)) {

                    prpStat.setInt(1, rs.getInt(1));
                    prpStat.setString(2, rs.getString(2));
                    prpStat.setString(3, rs.getString(3));
                    prpStat.setDouble(4, rs.getDouble(4));
                    prpStat.setInt(5, rs.getInt(5));

                    prpStat.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void sepetTemizle() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                    "123456");
            Statement myStat = baglanti.createStatement();
            ResultSet rs = myStat.executeQuery("select * from sepet");

            PreparedStatement prpStat = baglanti.prepareStatement("Truncate Table sepet");

            prpStat.executeUpdate();

        } catch (Exception e) {

            System.out.println(e);
        }
    }//Veri tabanındaki sepet tablosunda bulunan verileri siler.


    public static void paraÜstüHesapla() {

        double ödeme;
        System.out.println("Kullanıcıdan ödeme alınız.");
        ödeme = klavye.nextDouble();
        klavye.skip("").nextLine();


        if (ödeme >= sepetToplam) {

            paraustu = ödeme - sepetToplam;
            System.out.println("Para üstü: " + paraustu + " TL");
            int ikiyuzluk = (int) (paraustu / 200);

            if (ikiyuzluk > 0) { // if the change is less than $20 this will be a 0
                paraustu = paraustu % 200;
                System.out.println(ikiyuzluk + " adet 200 TL banknot");
            }

            int yuzluk = (int) (paraustu / 100);
            if (yuzluk > 0) {
                paraustu = paraustu % 100;
                System.out.println(yuzluk + " adet 100 TL banknot");
            }

            int ellilik = (int) (paraustu / 50);
            if (ellilik > 0) {
                paraustu = paraustu % 50;
                System.out.println(ellilik + " adet 50 TL banknot");
            }

            int yirmilik = (int) (paraustu / 20);
            if (yirmilik > 0) {
                paraustu = paraustu % 20;
                System.out.println(yirmilik + " adet 20 TL banknot");
            }

            int onluk = (int) (paraustu / 10);
            if (onluk > 0) {
                paraustu = paraustu % 10;
                System.out.println(onluk + " adet 10 TL banknot");
            }

            int beslik = (int) (paraustu / 5);
            if (beslik > 0) {
                paraustu = paraustu % 5;
                System.out.println(beslik + " adet 5 TL banknot");
            }

            int birlik = (int) (paraustu / 1);
            if (birlik > 0) {
                paraustu = paraustu % 1;
                System.out.println(birlik + " adet 1 TL");
            }

            int ellikurus = (int) (paraustu / 0.5);
            if (ellikurus > 0) {
                paraustu = paraustu % 0.5;
                System.out.println(ellikurus + " adet elli kuruş");
            }

            int yirmibeskurus = (int) (paraustu / 0.25);
            if (yirmibeskurus > 0) {
                paraustu = paraustu % 0.25;
                System.out.println(yirmibeskurus + " adet yirmi beş kuruş");
            }

            int onkurus = (int) (paraustu / 0.1);
            if (onkurus > 0) {
                paraustu = paraustu % 0.1;
                System.out.println(onkurus + " adet on kuruş");
            }

            int beskurus = (int) (paraustu / 0.05);
            if (beskurus > 0) {
                paraustu = paraustu % 1;
                System.out.println(beskurus + " adet beş kuruş");
            }

            int birkurus = (int) (paraustu / 0.01);
            if (birkurus > 0) {
                paraustu = paraustu % 1;
                System.out.println(birkurus + " adet bir kuruş");
            }
        }
        for (int i = 0; i < 1; i++) {

            if (ödeme < sepetToplam) {
                System.out.println("Yetersiz bakiye");
                ödeme = klavye.nextDouble();
                if (ödeme < sepetToplam) {
                    i--;
                } else {
                    break;
                }


            }
        }
        if (ödeme == sepetToplam) {
            System.out.println("para üstü yok");
        }
    }

}
