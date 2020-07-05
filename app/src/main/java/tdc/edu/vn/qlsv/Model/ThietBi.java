package tdc.edu.vn.qlsv.Model;

public class ThietBi {
    private String maTB;
    private String tenTB;
    private String xuatXuTB;
    private String maLoaiTB;

    public ThietBi(String maTB, String tenTB, String xuatXuTB, String maLoaiTB) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.xuatXuTB = xuatXuTB;
        this.maLoaiTB = maLoaiTB;
    }

    public ThietBi(String tenTB, String xuatXu, String maLoai) {
        this.tenTB = tenTB;
        this.xuatXuTB = xuatXuTB;
        this.maLoaiTB = maLoaiTB;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getXuatXuTB() {
        return xuatXuTB;
    }

    public void setXuatXuTB(String xuatXuTB) {
        this.xuatXuTB = xuatXuTB;
    }

    public String getMaLoaiTB() {
        return maLoaiTB;
    }

    public void setMaLoaiTB(String maLoaiTB) {
        this.maLoaiTB = maLoaiTB;
    }

    @Override
    public String toString() {
        return "Device{" +
                "maTB='" + maTB + '\'' +
                ", tenTB='" + tenTB + '\'' +
                ", xuatXuTB='" + xuatXuTB + '\'' +
                ", maLoaiTB='" + maLoaiTB + '\'' +
                '}';
    }
}
