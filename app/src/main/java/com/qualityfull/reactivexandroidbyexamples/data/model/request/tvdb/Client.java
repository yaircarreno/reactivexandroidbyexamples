package com.qualityfull.reactivexandroidbyexamples.data.model.request.tvdb;

public class Client {
    final String apikey;
    final String userkey;
    final String username;

    public Client(String apikey, String userkey, String username) {
        this.apikey = apikey;
        this.userkey = userkey;
        this.username = username;
    }
}
