package net.system.http;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.TimeZone;

/***
 * Handles WebApi 2.2 Http requests for Android Devices.
 */
public class HttpClient {
    private final String USER_AGENT = "WebApiClient/1.0";
    private Gson gson;
    private Context context;
    private URL baseAddress;
    private int connectionTimeout = 30000;
    private int readTimeout = 300000;
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private CookieStore cookieStore;

    public HttpClient(Context context) {
        gson = new GsonBuilder().setDateFormat(dateFormat).create();
        this.context = context;
        cookieStore = new HttpClientCookieStore(this.context);

    }

    public HttpClient(Context context, CookieStore cookieStore){
        gson = new GsonBuilder().setDateFormat(dateFormat).create();
        this.context = context;
        this.cookieStore = cookieStore;
    }

    /***
     * Defines the base address for WebApi 2.2 Hosting Services
     * @param baseAddress
     */
    public void setBaseAddress(URL baseAddress) {
        this.baseAddress = baseAddress;
    }

    /***
     * Sets the connection timeout in millis
     * @param timeout
     */
    public void setConnectionTimeout(int timeout) {
        this.connectionTimeout = timeout;
    }

    /***
     * Sets the operation timeout in millis
     * @param timeout
     */
    public void setOperationTimeout(int timeout) {
        this.readTimeout = timeout;
    }

    /***
     * Defines the date format for java.util.Date and .net DateTime conversions.
     * @param dateFormat
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        gson = new GsonBuilder().setDateFormat(dateFormat).create();
    }

    /***
     * Performs a generic a get request on http pipeline.
     * @param address Address of action /{controller}/{action} or default WebApi URL model.
     * @param contentType Content type of response.
     * @param callback Callback to execute when the request is done.
     * @throws IOException
     */
    public void get(final String address, final String contentType, final HttpAsyncCallback<byte[]> callback) throws IOException{
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final String wrappedAddressString = String.format("%s://%s:%d%s", baseAddress.getProtocol(), baseAddress.getHost(), baseAddress.getPort(), address);
                    URL wrappedAddress = new URL(wrappedAddressString);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) wrappedAddress.openConnection();
                    httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
                    httpURLConnection.setRequestProperty("Content-Type", contentType);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(connectionTimeout);
                    httpURLConnection.setReadTimeout(readTimeout);

                    appendCookies(httpURLConnection);
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    int contentLength = httpURLConnection.getContentLength();
                    readCookies(httpURLConnection);

                    byte[] buff = new byte[httpURLConnection.getContentLength()];
                    InputStream inputStream;
                    if (responseCode == 200 && contentLength > 0) {

                        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        inputStream.read(buff);
                        callback.onSuccess(buff);

                    } else if (responseCode == 400 && contentLength > 0) {
                        inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                        inputStream.read(buff);
                        String decoded = new String(buff, "UTF-8");
                        BadRequest json = gson.fromJson(decoded, BadRequest.class);
                        callback.onBadRequest(json);
                    }
                    else if(responseCode == 502){
                        BadRequest unauthorized = new BadRequest();
                        unauthorized.setMessage("502");
                        callback.onBadRequest(unauthorized);
                    }
                    httpURLConnection.disconnect();
                } catch (Exception ex) {
                    callback.onException(ex);
                }
                return null;
            }
        };
        task.execute();
    }

    /***
     * Performs a generic post on http pipeline.
     * @param address Address of action /{controller}/{action} or default WebApi URL model.
     * @param contentType Content type of response.
     * @param callback Callback to execute when the request is done.
     * @throws IOException
     */
    public void post(final String address, final String contentType, final HttpAsyncCallback<byte[]> callback) throws IOException{
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final String wrappedAddressString = String.format("%s://%s:%d%s", baseAddress.getProtocol(), baseAddress.getHost(), baseAddress.getPort(), address);
                    URL wrappedAddress = new URL(wrappedAddressString);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) wrappedAddress.openConnection();
                    httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
                    httpURLConnection.setRequestProperty("Content-Type", contentType);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(connectionTimeout);
                    httpURLConnection.setReadTimeout(readTimeout);

                    appendCookies(httpURLConnection);
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    int contentLength = httpURLConnection.getContentLength();
                    readCookies(httpURLConnection);

                    byte[] buff = new byte[httpURLConnection.getContentLength()];
                    InputStream inputStream;
                    if (responseCode == 200 && contentLength > 0) {

                        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        inputStream.read(buff);
                        callback.onSuccess(buff);

                    } else if (responseCode == 400 && contentLength > 0) {

                        inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                        inputStream.read(buff);
                        String decoded = new String(buff, "UTF-8");
                        BadRequest json = gson.fromJson(decoded, BadRequest.class);
                        callback.onBadRequest(json);
                    }
                    httpURLConnection.disconnect();
                } catch (Exception ex) {
                    callback.onException(ex);
                }
                return null;
            }
        };
        task.execute();
    }

    /***
     * Performs a json get request on http pipeline.
     * @param address Address of action /{controller}/{action} or default WebApi URL model.
     * @param callback Callback to execute when the request is done.
     * @param <Result> Type of object being parsed as json result.
     * @throws IOException
     */
    public <Result> void getAsJson(final String address, final HttpAsyncCallback<Result> callback) throws IOException {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final String wrappedAddressString = String.format("%s://%s:%d%s", baseAddress.getProtocol(), baseAddress.getHost(), baseAddress.getPort(), address);
                    URL wrappedAddress = new URL(wrappedAddressString);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) wrappedAddress.openConnection();
                    httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(connectionTimeout);
                    httpURLConnection.setReadTimeout(readTimeout);

                    appendCookies(httpURLConnection);
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    int contentLength = httpURLConnection.getContentLength();
                    readCookies(httpURLConnection);

                    byte[] buff = new byte[httpURLConnection.getContentLength()];
                    InputStream inputStream;
                    if (responseCode == 200 && contentLength > 0) {

                        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        inputStream.read(buff);
                        String decoded = new String(buff, "UTF-8");

                        ParameterizedType type = (ParameterizedType) callback.getClass().getGenericInterfaces()[0];
                        Type mappingType = type.getActualTypeArguments()[0];
                        Result obj = (Result) gson.fromJson(decoded, mappingType);
                        callback.onSuccess(obj);

                    } else if (responseCode == 400 && contentLength > 0) {
                        inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                        inputStream.read(buff);
                        String decoded = new String(buff, "UTF-8");
                        BadRequest json = gson.fromJson(decoded, BadRequest.class);
                        callback.onBadRequest(json);
                    }
                    httpURLConnection.disconnect();
                } catch (Exception ex) {
                    callback.onException(ex);
                }
                return null;
            }
        };
        task.execute();
    }

    /***
     * Performs a json post request on http pipeline.
     * @param address Address of action /{controller}/{action} or default WebApi URL model.
     * @param parameter Object parameter to be parsed as json parameter.
     * @param callback Callback to execute when the request is done.
     * @param <Result> Type of the object parsed as json result.
     */
    public <Result> void postAsJson(final String address, final Object parameter, final HttpAsyncCallback<Result> callback) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final String wrappedAddressString = String.format("%s://%s:%d%s", baseAddress.getProtocol(), baseAddress.getHost(), baseAddress.getPort(), address);
                    URL wrappedAddress = new URL(wrappedAddressString);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) wrappedAddress.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setConnectTimeout(connectionTimeout);
                    httpURLConnection.setReadTimeout(readTimeout);

                    OutputStream writer = httpURLConnection.getOutputStream();
                    String jsonParameter = gson.toJson(parameter);
                    writer.write(jsonParameter.getBytes("UTF-8"));
                    writer.flush();
                    writer.close();

                    appendCookies(httpURLConnection);
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    int contentLength = httpURLConnection.getContentLength();
                    readCookies(httpURLConnection);

                    byte[] contentBuff = new byte[httpURLConnection.getContentLength()];

                    InputStream inputStream;
                    if (responseCode == 200 && contentLength > 0) {

                        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        inputStream.read(contentBuff);
                        String decoded = new String(contentBuff, "UTF-8");
                        ParameterizedType type = (ParameterizedType) callback.getClass().getGenericInterfaces()[0];
                        Type mappingType = type.getActualTypeArguments()[0];
                        Result obj = (Result) gson.fromJson(decoded, mappingType);
                        callback.onSuccess(obj);

                    } else if (contentLength > 0) {

                        inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                        inputStream.read(contentBuff);
                        String decoded = new String(contentBuff, "UTF-8");
                        BadRequest json = gson.fromJson(decoded, BadRequest.class);
                        callback.onBadRequest(json);

                    }
                    httpURLConnection.disconnect();
                } catch (Exception ex) {
                    callback.onException(ex);
                }
                return null;
            }
        };
        task.execute();
    }

    private void readCookies(HttpURLConnection httpURLConnection) {
        List<HttpCookie> cookieList;
        List<String> cookies = httpURLConnection.getHeaderFields().get("Set-Cookie");
        if(cookies != null){
            for(String cookieHeader : cookies)
            {
                try {
                    cookieList = HttpCookie.parse(cookieHeader);
                    if(cookieList != null) {
                        for (HttpCookie cookie : cookieList) {
                            cookieStore.add(null, cookie);
                        }
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }
    }

    private void appendCookies(HttpURLConnection httpURLConnection) throws IOException {
        List<HttpCookie> cookieList = cookieStore.getCookies();
        if(cookieList != null && cookieList.size() > 0){
            StringBuilder sb = new StringBuilder();
            for(HttpCookie cookie : cookieList){
                sb.append(cookie.toString());
                sb.append(";");
            }
            httpURLConnection.setRequestProperty("Cookie", sb.toString());
        }

        httpURLConnection.connect();
    }




}
