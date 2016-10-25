package za.co.insaiyanmap.querycloudfeatureservice.drawinginfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This contains response data from the Arcgis Feature Service
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArcgisFeatureService {

    public long id;
    public String name;

    public ArcgisDrawingInfo drawingInfo;
}
