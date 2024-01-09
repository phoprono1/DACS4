package com.example.webbrowser.Model;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class SharedCookieStore {
    private static final CookieStore cookieStore;

    static {
        // Khởi tạo CookieManager
        cookieStore = new CookieManager().getCookieStore();
    }

    public static CookieStore getCookieStore() {
        return cookieStore;
    }

    public static void addCookie(String url, List<HttpCookie> cookies) {
        try {
            URI uri = new URI(url);
            cookieStore.add(uri, cookies.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
