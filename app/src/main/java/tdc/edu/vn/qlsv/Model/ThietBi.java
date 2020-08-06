package tdc.edu.vn.qlsv.Model;

import java.util.Arrays;

public class ThietBi {
    private  int id;
    private String maTB;
    private String tenTB;
    private String xuatXuTB;
    private String maLoaiTB;
    private byte[] imageTB;

    public byte[] getImageTB() {
        return imageTB;
    }

    public void setImageTB(byte[] imageTB) {
        this.imageTB = imageTB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ThietBi(int id, String maTB, String tenTB, String xuatXuTB, String maLoaiTB,byte[] imageTB) {
        this.id = id;
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.xuatXuTB = xuatXuTB;
        this.maLoaiTB = maLoaiTB;
        this.imageTB=imageTB;
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
        return "ThietBi{" +
                "id=" + id +
                ", maTB='" + maTB + '\'' +
                ", tenTB='" + tenTB + '\'' +
                ", xuatXuTB='" + xuatXuTB + '\'' +
                ", maLoaiTB='" + maLoaiTB + '\'' +
                ", imageTB=" + Arrays.toString(imageTB) +
                '}';
    }
}
