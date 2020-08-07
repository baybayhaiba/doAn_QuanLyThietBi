package tdc.edu.vn.qlsv.Model;

public class Country {
    private String country;
    private int flag;

    public Country(String country, int flag) {
        this.country = country;
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
