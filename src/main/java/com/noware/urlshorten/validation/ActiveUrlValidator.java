package com.noware.urlshorten.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ActiveUrlValidator implements
        ConstraintValidator<ActiveUrl, String> {

    @Override
    public void initialize(ActiveUrl url) {
    }

    @Override
    public boolean isValid(String urlField,
                           ConstraintValidatorContext cxt) {
        try {
            URI uri = URI.create(urlField);
            String protocol = uri.getScheme();
            if (protocol!=null) {
                //If a protocol is provided from the user, it must be https. I'm from certificates lobby :D
                if  (!protocol.equals("https")) {
                    return false;
                }
            } else {
                //if protocol is not provided, we try if it will work over https:
                urlField = "https://" + urlField;
            }

            //Make a call to check that the URL is valid:
            URL url = new URL(urlField);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            return HttpURLConnection.HTTP_OK == huc.getResponseCode();
        } catch (IOException e) {
            return false;
        }
    }

}