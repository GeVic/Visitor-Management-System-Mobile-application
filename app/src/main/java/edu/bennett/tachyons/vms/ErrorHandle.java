package edu.bennett.tachyons.vms;

/**
 * Created by NAYA on 9/8/2017.
 */

public class ErrorHandle {
    // to map errors
    public String errorHandling(String errorCode) {
        switch (errorCode) {
            case "1403":
                return "This means that the login status or access token has expired, been revoked, or is otherwise invalid";

            case "1190":
                return "Access Token not provided as Authorization header or access token not provided in correct format in header.";

            case "1142":
                return "Credentials provided are wrong";

            case "1157":
                return "Required authentication data not provided";

            case "2200":
                return "Required POST data was not provided";

            case "2400":
                return "Incorrect data format/Data is too large";

            case "2415":
                return "Unsupported media type";

            case "5501":
                return "Temporary issue due to downtime or throttling. Wait and retry the operation.";

            default:
                return "Some internal issue";

        }
    }

}
