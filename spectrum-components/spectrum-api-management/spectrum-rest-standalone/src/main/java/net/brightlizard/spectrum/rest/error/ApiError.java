package net.brightlizard.spectrum.rest.error;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public class ApiError {

    private int code;
    private String message;
    private String details;

    public ApiError(HttpStatus internalServerError, String reasonPhrase, String message) {
        this.code = internalServerError.value();
        this.message = reasonPhrase;
        this.details = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(code, apiError.code) &&
                Objects.equals(message, apiError.message) &&
                Objects.equals(details, apiError.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, details);
    }
}
