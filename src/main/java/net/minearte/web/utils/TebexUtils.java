package net.minearte.web.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import net.minearte.web.config.Config;

public class TebexUtils {
    private static String packages;
    public static String getTebexLink(String username, String packageId) {
        long start = System.currentTimeMillis();

        Unirest.config().reset();
        Unirest.config().followRedirects(false);

        HttpResponse<String> response = Unirest.post("https://plugin.tebex.io/checkout")
                .header("X-Tebex-Secret", Config.tebexApiKey())
                .header("Content-Type", "application/json")
                .header("Cookie", "__cf_bm=g4Fy46LX0eOGHRNgMtF9OZymbmWR5U4VwbSStTH3qz8-1676139938-0-ASvU7Oh0oNsJ/Y8nCpxFsRrereFsfciBHPsqY0FWmiUIpJBxnFfpdmg5twOEG3tvg6xudHKnL6rS4utHBD6Aah0=")
                .body("{\r\n    \"package_id\":\"{pkg}\",\r\n    \"username\":\"{usr}\"\r\n}"
                        .replace("{pkg}", packageId)
                        .replace("{usr}", username))
                .asString();

        System.out.println("{\r\n    \"package_id\":\"{pkg}\",\r\n    \"username\":\"{usr}\"\r\n}"
                .replace("{pkg}", packageId)
                .replace("{usr}", username));
        JsonObject json = JsonParser.parseString(response.getBody()).getAsJsonObject();

        if (json.get("url") == null) {
            return "Error";
        }

        HttpResponse<String> response2 = Unirest.get(json.get("url").getAsString())
                .asString();

        String newLocation = response2.getHeaders().get("Location").get(0);


        HttpResponse<String> response3 = Unirest.get(newLocation)
                .asString();


        HttpResponse<String> response4 = Unirest.post("https://minearte.tebex.io/checkout/pay")
                .header("Cookie", response3.getCookies().toString())
                .asString();

        System.out.println("Link: " + response4.getHeaders().get("Location").get(0));

        long end = System.currentTimeMillis();

        System.out.println("Time: " + (end - start));

        return response4.getHeaders().get("Location").get(0);
    }

    public static String getPackages(){
        if (packages != null) {
            return packages;
        }

        HttpResponse<String> response = Unirest.get("https://plugin.tebex.io/packages?verbose=true")
                .header("X-Tebex-Secret", Config.tebexApiKey())
                .asString();
        return response.getBody();
    }

    public static void cleanCache() {
        packages = null;
    }
}
