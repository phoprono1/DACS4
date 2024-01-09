module com.example.webbrowser {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.web;
    requires java.sql;
    requires Java.WebSocket;
    requires java.rmi;
    requires org.slf4j;
    requires spring.beans;
    requires spring.context;
    requires org.jetbrains.annotations;
    requires spring.security.crypto;
    requires com.jfoenix;
    requires android.json;
    requires api;
    requires com.google.api.client;
    requires google.api.client;
    requires com.google.api.services.drive;
    requires java.net.http;
    requires org.fxmisc.richtext;

    opens com.example.webbrowser to javafx.fxml;
    exports com.example.webbrowser;
    exports com.example.webbrowser.Controller;
    exports com.example.webbrowser.Model;
    opens com.example.webbrowser.Controller to javafx.fxml;
}