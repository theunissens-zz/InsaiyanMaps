package za.co.insaiyanmap.querycloudfeatureservice.drawinginfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by DonovanT on 7/11/2016.
 * This collects the drawing info that is used to theme the ArcGIS maps.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArcgisDrawingInfo {
    public ArcgisRenderer renderer;
    public int transparency;
//    public String labelingInfo;
}
