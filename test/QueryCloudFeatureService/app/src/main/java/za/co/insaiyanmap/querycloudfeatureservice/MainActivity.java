package za.co.insaiyanmap.querycloudfeatureservice;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.io.OnSelfSignedCertificateListener;
import com.esri.core.io.SelfSignedCertificateHandler;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;

import java.security.cert.X509Certificate;

public class MainActivity extends AppCompatActivity {

    MenuItem mQueryUsMenuItem = null;
    MenuItem mQueryCaMenuItem = null;
    MenuItem mQueryFrMenuItem = null;
    MenuItem mQueryAuMenuItem = null;
    MenuItem mQueryBrMenuItem = null;

    MapView mMapView;
    ArcGISFeatureLayer mFeatureLayer;
    GraphicsLayer mGraphicsLayer;
    String mFeatureServiceURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the map and initial extent from XML layout
        mMapView = (MapView) findViewById(R.id.map);
        // Get the feature service URL from values->strings.xml
        mFeatureServiceURL = this.getResources().getString(R.string.featureServiceURL);
        // Add Feature layer to the MapView
        mFeatureLayer = new ArcGISFeatureLayer(mFeatureServiceURL, ArcGISFeatureLayer.MODE.ONDEMAND);
        mMapView.addLayer(mFeatureLayer);
        // Add Graphics layer to the MapView
        mGraphicsLayer = new GraphicsLayer();
        mMapView.addLayer(mGraphicsLayer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the query params menu items.
        mQueryUsMenuItem = menu.getItem(0);
        mQueryCaMenuItem = menu.getItem(1);
        mQueryFrMenuItem = menu.getItem(2);
        mQueryAuMenuItem = menu.getItem(3);
        mQueryBrMenuItem = menu.getItem(4);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item selection.
        switch (item.getItemId()) {
            case R.id.Query_US:
                mQueryUsMenuItem.setChecked(true);
                new QueryFeatureLayer().execute("United States");
                return true;
            case R.id.Query_CA:
                mQueryCaMenuItem.setChecked(true);
                new QueryFeatureLayer().execute("Canada");
                return true;
            case R.id.Query_FR:
                mQueryFrMenuItem.setChecked(true);
                new QueryFeatureLayer().execute("France");
                return true;
            case R.id.Query_AU:
                mQueryAuMenuItem.setChecked(true);
                new QueryFeatureLayer().execute("Australia");
                return true;
            case R.id.Query_BR:
                mQueryBrMenuItem.setChecked(true);
                new QueryFeatureLayer().execute("Brazil");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean getUserPermission() {
        return true;
    }

    public void idntknow() {
        // Set listener to handle self-signed certificate
        SelfSignedCertificateHandler.setOnSelfSignedCertificateListener(
                new OnSelfSignedCertificateListener() {
                    public boolean checkServerTrusted(X509Certificate[] chain, String authType) {
                        try {
                            chain[0].checkValidity();
                        } catch (Exception e) {
                            return getUserPermission();
                        }

                        return true;
                    }
                });
    }

    private class QueryFeatureLayer extends AsyncTask<String, Void, FeatureResult> {
        @Override
        protected FeatureResult doInBackground(String... params) {

            String whereClause = "CNTRY_NAME='" + params[0] + "'";

            // Define a new query and set parameters
            QueryParameters mParams = new QueryParameters();
            mParams.setReturnGeometry(true);

            // Define the new instance of QueryTask
            QueryTask queryTask = new QueryTask(mFeatureServiceURL);
            FeatureResult results;

            try {
                // run the querytask
                results = queryTask.execute(mParams);
                return results;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(FeatureResult results) {

            // Remove the result from previously run query task
            mGraphicsLayer.removeAll();

            // Define a new marker symbol for the result graphics
            SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.BLUE, 10, SimpleMarkerSymbol.STYLE.CIRCLE);

            // Envelope to focus on the map extent on the results
            Envelope extent = new Envelope();

            // iterate through results
            for (Object element : results) {
                // if object is feature cast to feature
                if (element instanceof Feature) {
                    Feature feature = (Feature) element;
                    // convert feature to graphic
                    Graphic graphic = new Graphic(feature.getGeometry(), sms, feature.getAttributes());
                    // merge extent with point
                    extent.merge((Point)graphic.getGeometry());
                    // add it to the layer
                    mGraphicsLayer.addGraphic(graphic);
                }
            }

            // Set the map extent to the envelope containing the result graphics
            mMapView.setExtent(extent, 100);
        }
    }
}
