package za.co.insaiyanmap.querycloudfeatureservice.style;

import android.os.AsyncTask;

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

/**
 * Created by StevenT on 2016/09/16.
 */
public class DrawingInfo {

    private String url;

    public DrawingInfo(String url) {
        this.url = url;
    }

    private String getJsonFromLayer() {
        HttpURLConnection urlConnection = null;
        String line = null;
        try {
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch(MalformedURLException ex) {

        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return line;
    }

    public String getDrawingInfo() {
        String json = this.getJsonFromLayer();
        String color = null;
        try {
            JSONObject jsonObj = new JSONObject(json);
            String strDrawingInfo = jsonObj.get("drawingInfo").toString();
            JSONObject objDrawingInfo = new JSONObject(strDrawingInfo);
            String strRenderer = objDrawingInfo.get("renderer").toString();
            JSONObject objRenderer = new JSONObject(strRenderer);
            String strSymbol = objRenderer.get("symbol").toString();
            JSONObject objSymbol = new JSONObject(strSymbol);
            color = objSymbol.get("color").toString();
        } catch(JSONException ex) {
            ex.printStackTrace();
        }

        return color;
    }


}
