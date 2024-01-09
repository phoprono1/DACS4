package com.example.webbrowser.Model;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServerApp {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ChatServer chatServer = new ChatServerImpl();
            Naming.rebind("ChatServer", chatServer);
            System.out.println("Chat Server is running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

