package za.co.insaiyanmap.querycloudfeatureservice.objects;

/**
 * Created by StevenT on 2016/09/16.
 */
public class LayerObject {

    private String name;
    private String url;

    public LayerObject(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
