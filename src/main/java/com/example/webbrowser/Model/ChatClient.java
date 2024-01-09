package com.example.webbrowser.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatClient extends Remote {
    void receiveMessage(String message, String s) throws RemoteException;

    void updateUserList(List<String> userList) throws RemoteException;

    String getName() throws RemoteException;

    void receiveNotification(String sender, String message) throws RemoteException;
}
