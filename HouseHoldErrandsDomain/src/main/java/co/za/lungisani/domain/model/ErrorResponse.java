package co.za.lungisani.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Error Response", name = "ErrorResponse")
public class ErrorResponse {

    @Schema(
            title = "The status code representing the error",
            example = "500",
            required = true
    )
    private String statusCode;

    @Schema(
            title = "A message providing context about the causes of the error",
            example = "An error has occured",
            required = true
    )
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public ErrorResponse setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

}
