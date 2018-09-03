package byashad.qoutespicture.picture.quotes.picturequotes;

public class Getsampledata {
    private String imageurl;
    private String type;

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    private String likes;

    public Getsampledata() {
    }

    public Getsampledata(String imageurl, String type) {
        this.imageurl = imageurl;
        this.type = type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
