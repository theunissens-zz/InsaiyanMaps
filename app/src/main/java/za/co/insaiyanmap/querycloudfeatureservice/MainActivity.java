package za.co.insaiyanmap.querycloudfeatureservice;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.GroupLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import za.co.insaiyanmap.querycloudfeatureservice.objects.LayerObject;

public class MainActivity extends AppCompatActivity {

    MenuItem mQueryMenuItem = null;

    MapView mMapView;
//    ArcGISFeatureLayer streetLightFeatureLayer;
//    ArcGISFeatureLayer slfeederFeatureLayer;
    GraphicsLayer graphicsLayer;
    private Map<String, String> mapLayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the map and initial extent from XML layout
        mMapView = (MapView) findViewById(R.id.map);
        // Get the feature service URL from values->strings.xml
        // Add Feature layer to the MapView
//        streetLightFeatureLayer = new ArcGISFeatureLayer(streetLightURL, ArcGISFeatureLayer.MODE.ONDEMAND);
//        slfeederFeatureLayer = new ArcGISFeatureLayer(SLFeederURL, ArcGISFeatureLayer.MODE.ONDEMAND);

//        GroupLayer groupsLayer = new GroupLayer();
//        groupsLayer.addLayer(streetLightFeatureLayer);
//        groupsLayer.addLayer(slfeederFeatureLayer);
//        mMapView.addLayer(groupsLayer);

//        GroupLayer groupGraphicsLayer = new GroupLayer();
//        groupGraphicsLayer.addLayer(streetLightGraphicsLayer);
//        groupGraphicsLayer.addLayer(slfeederGraphicsLayer);
        graphicsLayer = new GraphicsLayer();
        mMapView.addLayer(graphicsLayer);

        // Add all the feature layers
        Resources res = getResources();
        String[] layers = res.getStringArray(R.array.esriLayers);
        mapLayers = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            String name = layers[i].split(";")[0];
            String url = layers[i].split(";")[1];
            mapLayers.put(name, url);
//            layersObjs.add(new LayerObject(name, url));

            ArcGISFeatureLayer layer = new ArcGISFeatureLayer(url, ArcGISFeatureLayer.MODE.ONDEMAND);

            mMapView.addLayer(layer);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the query params menu items.
        mQueryMenuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item selection.
        switch (item.getItemId()) {
            case R.id.Query_Load:
                mQueryMenuItem.setChecked(true);
                Object[] keys = mapLayers.keySet().toArray();
                for (int i = 0; i < keys.length; i++) {
                    new QueryFeatureLayer().execute(keys[i].toString());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class QueryFeatureLayer extends AsyncTask<String, Void, FeatureResult> {
        @Override
        protected FeatureResult doInBackground(String... params) {

            String url = mapLayers.get(params[0]);

            // Define a new query and set parameters
            QueryParameters mParams = new QueryParameters();
            mParams.setWhere("1 = 1");
            mParams.setReturnGeometry(true);

            // Define the new instance of QueryTask
            QueryTask queryTask = new QueryTask(url);
            FeatureResult results;

            try {
                // run the querytask
                results = queryTask.execute(mParams);
                return results;
            } catch (Exception e) {
                e.printStackTrace();
            }

//            if (params[0].equals("StreetLight")) {
//                String whereClause = "SL_AssClass='INFRASTRUCTURE_AND_PLANNING'";
//
//                // Define a new query and set parameters
//                QueryParameters mParams = new QueryParameters();
//                mParams.setWhere(whereClause);
//                mParams.setReturnGeometry(true);
//
//                // Define the new instance of QueryTask
//                QueryTask queryTask = new QueryTask(streetLightURL);
//                FeatureResult results;
//
//                try {
//                    // run the querytask
//                    results = queryTask.execute(mParams);
//                    return results;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (params[0].equals("SLFeeder")) {
//                String whereClause = "SLC_AssClass='INFRASTRUCTURE_AND_PLANNING'";
//
//                // Define a new query and set parameters
//                QueryParameters mParams = new QueryParameters();
//                mParams.setWhere(whereClause);
//                mParams.setReturnGeometry(true);
//
//                // Define the new instance of QueryTask
//                QueryTask queryTask = new QueryTask(SLFeederURL);
//                FeatureResult results;
//
//                try {
//                    // run the querytask
//                    results = queryTask.execute(mParams);
//                    return results;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            return null;
        }

        @Override
        protected void onPostExecute(FeatureResult results) {

            // Remove the result from previously run query task
//            streetLightGraphicsLayer.removeAll();
//            slfeederGraphicsLayer.removeAll();

            // Envelope to focus on the map extent on the results
            Envelope extent = new Envelope();

            // iterate through results
            for (Object element : results) {
                // if object is feature cast to feature
                if (element instanceof Feature) {
                    Feature feature = (Feature) element;
                    Geometry geom = feature.getGeometry();
                    if (geom instanceof Point) {
                        SimpleMarkerSymbol point = new SimpleMarkerSymbol(Color.BLUE, 8, SimpleMarkerSymbol.STYLE.CIRCLE);
                        Graphic graphic = new Graphic(feature.getGeometry(), point, feature.getAttributes(), 2);
                        extent.merge((Point) graphic.getGeometry());
                        graphicsLayer.addGraphic(graphic);
                    } else if (geom instanceof Polyline){
                        Graphic graphic = new Graphic(feature.getGeometry(), new SimpleLineSymbol(Color.RED, 2), feature.getAttributes(), 1);
//                        extent.mer((Polyline) graphic.getGeometry());
                        graphicsLayer.addGraphic(graphic);
                    }
                }
            }

            // Set the map extent to the envelope containing the result graphics
            mMapView.setExtent(extent, 100);
        }
    }
}
