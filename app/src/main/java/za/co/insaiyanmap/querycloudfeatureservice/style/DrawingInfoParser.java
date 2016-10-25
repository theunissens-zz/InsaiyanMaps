package za.co.insaiyanmap.querycloudfeatureservice.style;

import android.os.AsyncTask;

import com.esri.core.map.Graphic;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import za.co.insaiyanmap.querycloudfeatureservice.drawinginfo.ArcgisDrawingInfo;
import za.co.insaiyanmap.querycloudfeatureservice.drawinginfo.ArcgisFeatureService;

/**
 * Created by StevenT on 2016/09/16.
 */
public class DrawingInfoParser {

    private String url;

    public DrawingInfoParser(String url) {
        this.url = url;
    }

    private class RetrieveJsonFromLayerTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            StringBuilder total = null;
            try {
                URL url = new URL(DrawingInfoParser.this.url + "?f=pjson");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStream inputStream = new BufferedInputStream(is);
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                total = new StringBuilder();
                String line = null;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
            } catch(MalformedURLException ex) {
                ex.printStackTrace();
            } catch(IOException ex) {
                ex.printStackTrace();
            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return total.toString();
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    private Graphic parseToGraphic(ArcgisDrawingInfo drawingInfo) {
        drawingInfo.
        Graphic graphic = new Graphic()
    }

    public Graphic getGraphic() {
        String json = null;
        try {
            json = new RetrieveJsonFromLayerTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArcgisFeatureService featureService = mapper.readValue(json, ArcgisFeatureService.class);
            return parseToGraphic(featureService.drawingInfo);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
