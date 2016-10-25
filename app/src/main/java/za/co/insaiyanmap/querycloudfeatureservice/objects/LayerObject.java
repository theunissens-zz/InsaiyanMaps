package za.co.insaiyanmap.querycloudfeatureservice.objects;

import com.esri.core.map.Graphic;

import za.co.insaiyanmap.querycloudfeatureservice.drawinginfo.ArcgisDrawingInfo;
import za.co.insaiyanmap.querycloudfeatureservice.style.DrawingInfoParser;

/**
 * Created by StevenT on 2016/09/16.
 */
public class LayerObject {

    private String name;
    private String url;
    private Graphic graphic;

    public LayerObject(String name, String url, Graphic graphic) {
        this.name = name;
        this.url = url;
        this.graphic = graphic;
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

    public Graphic getGraphic() {
        return graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }
}
