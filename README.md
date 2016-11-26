# Asp.Net WebApi Client for Android
*Asp.Net WebApi is a great Microsoft Framework for HTTP services, thinking about that we've managed to simplify the communication with this HttpClient based on core Android native HttpClient for backward compatibility*

We don't use third party libraries for the WebApi communication, focusing just on core android and it's own JVM interfaces for the http requests. That being said we are proud to share that our client is pretty much fast, simple and safe.

The only dependency for this project is the google gson library to parse json objects.

By default this library uses cookies to handle authorization, based on official microsoft.aspnet.identity and owin interfaces, so if you have security with owin enabled, it's just a plug and play to create a login method on your webapi and protect your resources.

Getting started:
Checkout repos under your project and mark as library
Set your apk permissions to use internet

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Since WebApi and .Net DateTime differ from java.util.Date, you can set the datetime format for the requests, overrinding the default datetime format.


## Common Usage for Get as Json
To use a simple get, just write the code as follows on your activity or business object.
```
Context context = MyActivity.this;
HttpClient client = new HttpClient(context);
//set the base address of your WebApi
client.setBaseAddress(new URL("http://10.0.2.2:4005"));
client.getAsJson("/home/post/", new HttpAsyncCallback<ObjectModel>() {
	@Override
	public void onSuccess(ObjectModel response) {
		//operate with the response object
	}

	@Override
	public void onBadRequest(BadRequest badRequest) {
		//handle the badRequest
	}

	@Override
	public void onException(Exception ex) {
		//handle any unknown exception
	}
});
```

##Common Usage for Post as Json
```
//define the WebApi post parameter
ObjectModel model = new ObjectModel();
model.setId(1);
model.setCode(1);
model.setContent("test");
model.setDate(new Date());

Context context = MyActivity.this;
HttpClient client = new HttpClient(context);
//set the base address of your WebApi
client.setBaseAddress(new URL("http://10.0.2.2:4005"));
client.postAsJson("/home/post/", model, new HttpAsyncCallback<ObjectModel>() {
	@Override
	public void onSuccess(ObjectModel response) {
		//operate with the response object
	}

	@Override
	public void onBadRequest(BadRequest badRequest) {
		//handle the badRequest
	}

	@Override
	public void onException(Exception ex) {
		//handle any unknown exception
	}
});
```

##Another usages
If you want to send an image or another kind of content type, just use the default post object.
```
//define the WebApi post parameter
byte[] imageBytes = //convert your image to bytes

Context context = MyActivity.this;
HttpClient client = new HttpClient(context);
//set the base address of your WebApi
client.setBaseAddress(new URL("http://10.0.2.2:4005"));
String contentType = "image/png";
client.post("/home/post/", imageBytes, contentType, new HttpAsyncCallback<byte[]>() {
	@Override
	public void onSuccess(byte[] response) {
		//operate with the response object
	}

	@Override
	public void onBadRequest(BadRequest badRequest) {
		//handle the badRequest
	}

	@Override
	public void onException(Exception ex) {
		//handle any unknown exception
	}
});
```
##Get for another content types
The same applies when you want to request a download file or another content type from webapi
```
HttpClient client = new HttpClient(context);
//set the base address of your WebApi
client.setBaseAddress(new URL("http://10.0.2.2:4005"));
String contentType = "image/png";
client.get("/home/post/", contentType, new HttpAsyncCallback<byte[]>() {
	@Override
	public void onSuccess(byte[] response) {
		//operate with the response object
	}

	@Override
	public void onBadRequest(BadRequest badRequest) {
		//handle the badRequest
	}

	@Override
	public void onException(Exception ex) {
		//handle any unknown exception
	}
});
```

###Known issues
*Currently we support only the GET/POST methods for Json and generic Byte[] requests responses.*