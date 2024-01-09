package com.example.webbrowser.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    private final String name;
    private final ChatClientGUI gui;

    public ChatClientImpl(String name, ChatClientGUI gui) throws RemoteException {
        this.name = name;
        this.gui = gui;
    }

    @Override
    public void receiveMessage(String sender, String message) throws RemoteException {
        System.out.println(sender + ": " + message);
        gui.updateChatArea(sender, message);
    }

    @Override
    public void updateUserList(List<String> userList) throws RemoteException {
        gui.updateUserList(userList);
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void receiveNotification(String sender, String message) throws RemoteException {
        gui.updateChatArea(sender, message);
    }
}