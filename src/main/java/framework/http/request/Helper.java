package framework.http.request;

import java.util.HashMap;

public class Helper {

    public static HashMap<String, String> getParametersFromRoute(String route) {
        String[] splittedRoute = route.split("\\?");

        if (splittedRoute.length == 1) {
            return new HashMap<>();
        }

        return Helper.getParametersFromString(splittedRoute[1]);
    }

    public static HashMap<String, String> getParametersFromString(String parametersString) {
        HashMap<String, String> parameters = new HashMap<>();
        String[] pairs = parametersString.split("&");
        for (String pair : pairs) {
            String[] keyPair = pair.split("=");
            parameters.put(keyPair[0], keyPair[1]);
        }

        return parameters;
    }
}
