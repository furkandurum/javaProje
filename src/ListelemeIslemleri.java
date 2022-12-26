import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ListelemeIslemleri {
    // kategori, sepet listele, ürün listele gibi yazdırıcı metodları burada topla


    public static double toplamSatis = 0;

    public static int getSecim() {
        return secim;
    }

    public static void setSecim(int secim) {
        ListelemeIslemleri.secim = secim;
    }

    private static int secim;
    public static void kategori() {


        Scanner klavye = new Scanner(System.in);

        for (; true; ) {
            boolean error;
            do {
                error = false;
                try {
                    System.out.println("hangi işlemi yapmak istiyorsunuz");
                    System.out.println("""
                            1-Urün Ekle
                            2-Urün listele
                            3-Fiyat Guncelleme
                            4-Sepete Urün Ekle
                            5-Sepeti Göster
                            6-Sepeti Temizle
                            7-Nakit Satis
                            8-Kredi Satis
                            9-Satış Geçmişini Yazdir
                            10-Bitir""");
                    secim = klavye.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("hatalı giris ");
                    error = true;
                    klavye.nextLine();
                }
            } while (error);


            if (getSecim() == 1) {
                SepetIslemleri.urunEkle();
            } else if (getSecim() == 2) {
                urunListele();
            } else if (getSecim() == 3) {
                SepetIslemleri.urunGuncelle();
            } else if (getSecim() == 4) {
                SepetIslemleri.sepeteUrunEkle();
            } else if (getSecim() == 5) {
                sepetListele();
            } else if (getSecim() == 6) {
                SepetIslemleri.sepetTemizle();
            } else if (getSecim() == 7) {
                NakitSatis nakitSatis = new NakitSatis();
                nakitSatis.satis();
            } else if (getSecim() == 8) {
                KrediSatis krediSatis =new KrediSatis();
                krediSatis.satis();
            } else if (getSecim() == 9) {
                ListelemeIslemleri.satisGecmisi();
            } else if (getSecim() == 10) {
                MesajYonet mesajYonet = new MesajYonet(new Mesaj());
                mesajYonet.Add();ğ+

                break;
            } else {
                System.out.println("Yanlış seçim!!!" + '\n' + "Secenekler Arasından Secim Yapın");
            }
        }

    }


    //ÜRÜN LİSTELE
    public static void urunListele() {
        //ÜRÜN LİSTELENİYOR
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                    "123456");
            Statement myStat = baglanti.createStatement();

            ResultSet rs = myStat.executeQuery("select * from urun order by 'reyon'");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " +
                        rs.getDouble(4) + " " + rs.getInt(5));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void sepetListele() {
        {
            //ÜRÜN LİSTELENİYOR
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                        "123456");
                Statement myStat = baglanti.createStatement();

                ResultSet rs = myStat.executeQuery("select * from sepet order by 'reyon'");

                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " +
                            rs.getDouble(4));
                    SepetIslemleri.sepetToplam = SepetIslemleri.sepetToplam + rs.getDouble(4);
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("toplam sepet tutarı " + SepetIslemleri.sepetToplam);
        }
    }

    //FİŞ YAZDIR
    public static void satisGecmisi() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                    "123456");
            Statement myStat = baglanti.createStatement();

            ResultSet rs = myStat.executeQuery("select * from satışgeçmişi order by 'ödemeTipi'");

            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getDouble(2));
                toplamSatis = toplamSatis + rs.getDouble(2);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Yapılan Toplam satış miktarı:" + toplamSatis + "TL'dir.");
    }

}