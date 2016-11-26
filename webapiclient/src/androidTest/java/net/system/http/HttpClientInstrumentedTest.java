package net.system.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.annotations.SerializedName;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HttpClientInstrumentedTest {

    public class ObjectModel {
        @SerializedName("Id")
        private long id;
        @SerializedName("Code")
        private int code;
        @SerializedName("Content")
        private String content;
        @SerializedName("Date")
        private Date date;
        @SerializedName("ArrayString")
        private String[] arrayString;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String[] getArrayString() {
            return arrayString;
        }

        public void setArrayString(String[] arrayString) {
            this.arrayString = arrayString;
        }
    }


    @Test
    public void homeGet() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpClient client = new HttpClient(appContext);
        client.setBaseAddress(new URL("http://10.0.2.2:4005"));
        client.getAsJson("/home/Get/", new HttpAsyncCallback<ObjectModel>() {
            @Override
            public void onSuccess(ObjectModel objectModel) {
                String obj = objectModel.toString();
            }

            @Override
            public void onBadRequest(BadRequest badRequest) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });
        Thread.sleep(600000);
    }

    @Test
    public void homePost() throws Exception {
        ObjectModel model = new ObjectModel();
        model.setId(1);
        model.setCode(1);
        model.setContent("Teste");
        model.setDate(new Date());
        model.setArrayString(new String[]{"Teste", "Teste", "Teste"});

        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpClient client = new HttpClient(appContext);
        client.setBaseAddress(new URL("http://10.0.2.2:4005"));
        client.postAsJson("/home/post/", model, new HttpAsyncCallback<ObjectModel>() {
            @Override
            public void onSuccess(ObjectModel objectModel) {
                String obj = objectModel.toString();
            }

            @Override
            public void onBadRequest(BadRequest badRequest) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });
        Thread.sleep(600000);
    }

    @Test
    public void homeGetList() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpClient client = new HttpClient(appContext);
        client.setBaseAddress(new URL("http://10.0.2.2:4005"));
        client.getAsJson("/home/GetList/", new HttpAsyncCallback<ObjectModel[]>() {
            @Override
            public void onSuccess(ObjectModel[] objectModel) {
                String obj = objectModel.toString();
            }

            @Override
            public void onBadRequest(BadRequest badRequest) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });
        Thread.sleep(600000);
    }

    @Test
    public void postList() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();

        List<ObjectModel> listModel = new ArrayList<>();

        ObjectModel model = new ObjectModel();
        model.setId(1);
        model.setCode(1);
        model.setContent("Teste");
        model.setDate(new Date());
        model.setArrayString(new String[]{"Teste", "Teste", "Teste"});
        listModel.add(model);

        ObjectModel model2 = new ObjectModel();
        model2.setId(1);
        model2.setCode(1);
        model2.setContent("Teste");
        model2.setDate(new Date());
        model2.setArrayString(new String[]{"Teste", "Teste", "Teste"});
        listModel.add(model2);

        HttpClient client = new HttpClient(appContext);
        client.setBaseAddress(new URL("http://10.0.2.2:4005"));
        client.postAsJson("/home/PostList/", listModel, new HttpAsyncCallback<ObjectModel[]>() {
            @Override
            public void onSuccess(ObjectModel[] objectModel) {
                String obj = objectModel.toString();
            }

            @Override
            public void onBadRequest(BadRequest badRequest) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });
        Thread.sleep(600000);
    }

    @Test
    public void login() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpClient client = new HttpClient(appContext);
        client.setBaseAddress(new URL("http://10.0.2.2:4005"));
        client.getAsJson("/home/GetLogin/?login=user", new HttpAsyncCallback<ObjectModel>() {
            @Override
            public void onSuccess(ObjectModel objectModel) {

            }

            @Override
            public void onBadRequest(BadRequest badRequest) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });
        Thread.sleep(600000);
    }

    @Test
    public void validateLogin() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpClient client = new HttpClient(appContext);
        client.setBaseAddress(new URL("http://10.0.2.2:4005"));
        client.getAsJson("/home/getvalidatelogin/", new HttpAsyncCallback<ObjectModel>() {
            @Override
            public void onSuccess(ObjectModel objectModel) {

            }

            @Override
            public void onBadRequest(BadRequest badRequest) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });
        Thread.sleep(600000);
    }
}
