package webshop;

public class CustomResponseStatus {

    private Response response;
    private String message;

    public CustomResponseStatus(Response response, String message) {
        this.response = response;
        this.message = message;
    }

    public Response getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
