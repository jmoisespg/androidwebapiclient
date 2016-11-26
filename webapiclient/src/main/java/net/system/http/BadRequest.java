package net.system.http;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/***
 * Generic badrequest of a failed http request.
 */
public class BadRequest {
    @SerializedName("Message")
    private String message;
    @SerializedName("ModelState")
    private Map<String, String[]> modelState;

    /***
     * Retrieves the base message of failure request.
     * @return
     */
    public String getMessage() {
        return message;
    }

    /***
     * Changes the message of failed request.
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /***
     * Retrieves the ModelStateDictionary containing key/valued pairs error messages.
     *
     * @return
     */
    public Map<String, String[]> getModelState() {
        return modelState;
    }

    /***
     * Sets the ModelStateDictionary.
     * @param modelState
     */
    public void setModelState(Map<String, String[]> modelState) {
        this.modelState = modelState;
    }

}
