package tdc.edu.vn.qlsv.Model;

public class TinhTrangThietBi {
    private int id;
    private String maPhong;
    private String maThietBi;

    public String getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    private String ngaySuDung;
    private String tinhTrang;

    public TinhTrangThietBi(int id,String maPhong, String maThietBi, String ngaySuDung, String tinhTrang) {
        this.id=id;
        this.maPhong = maPhong;
        this.maThietBi = maThietBi;
        this.ngaySuDung = ngaySuDung;
        this.tinhTrang = tinhTrang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }



    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
