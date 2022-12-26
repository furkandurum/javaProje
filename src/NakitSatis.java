import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class NakitSatis extends BaseSatis {

    @Override
    public void satis() {
        // nakis satış metodunu buraya ekle

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_proje", "root",
                    "123456");
            Statement myStat = baglanti.createStatement();

            PreparedStatement prpStat = baglanti.prepareStatement("INSERT into satışgeçmişi(ödemeTipi, ödemeTutarı) values (?,?)");

            prpStat.setNString(1, "Nakit");
            prpStat.setDouble(2, SepetIslemleri.sepetToplam);

            prpStat.executeUpdate();


        } catch (Exception e) {
            System.out.println(e);
        }


        SepetIslemleri.paraÜstüHesapla();
        SepetIslemleri.sepetTemizle();
        SepetIslemleri.sepetToplam = 0;
    }
}
