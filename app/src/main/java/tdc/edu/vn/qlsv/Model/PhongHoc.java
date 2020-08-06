package tdc.edu.vn.qlsv.Model;

public class PhongHoc {
    private int id;
    private String maPhong;
    private String loaiPhong;
    private int tang;

    public PhongHoc(int id, String maPhong, String loaiPhong, int tang) {
        this.id = id;
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.tang = tang;
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

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    @Override
    public String toString() {
        return "PhongHoc{" +
                "maPhong='" + maPhong + '\'' +
                ", loaiPhong='" + loaiPhong + '\'' +
                ", tang=" + tang +
                '}';
    }
}
