package org.example.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import org.example.Models.GoogleUser;
import org.example.utils.GoogleLocalServer;

import java.io.InputStreamReader;
import java.util.List;

public class GoogleAuthService {

    public GoogleUser login() throws Exception {

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),
                new InputStreamReader(getClass().getResourceAsStream("/client_secret.apps.googleusercontent.com.json"))
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                clientSecrets,
                List.of("email", "profile")
        )
                .setAccessType("offline")
                .build();

        GoogleLocalServer googleServer = new GoogleLocalServer();

        Credential credential = new AuthorizationCodeInstalledApp(flow, googleServer.getReceiver())
                .authorize("user");

        HttpRequest request = GoogleNetHttpTransport.newTrustedTransport()
                .createRequestFactory(credential)
                .buildGetRequest(new GenericUrl("https://www.googleapis.com/oauth2/v2/userinfo"));

        String json = request.execute().parseAsString();

        return new Gson().fromJson(json, GoogleUser.class);
    }
}

