package net.system.http;

/***
 * Callback to be executed after a http request is sent
 * @param <Response>
 */
public interface HttpAsyncCallback<Response> {
    /***
     * Handles success response with response object.
     * @param response
     */
    void onSuccess(Response response);

    /***
     * Handles or retrieves a BadRequest.
     * @param badRequest
     */
    void onBadRequest(BadRequest badRequest);

    /***
     * Handles exception related to http pipeline, object parser or another unspecified error.
     * @param ex
     */
    void onException(Exception ex);
}
