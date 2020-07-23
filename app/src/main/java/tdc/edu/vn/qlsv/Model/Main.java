package tdc.edu.vn.qlsv.Model;

public class Main {
    private int image;
    private String title;
    private int amout;
    private int colorCardView;

    public Main(int image, String title, int amout, int colorCardView) {
        this.image = image;
        this.title = title;
        this.amout = amout;
        this.colorCardView = colorCardView;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public int getColorCardView() {
        return colorCardView;
    }

    public void setColorCardView(int colorCardView) {
        this.colorCardView = colorCardView;
    }
}
