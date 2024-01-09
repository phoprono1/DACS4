package com.example.webbrowser.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServer extends Remote {
    String getUsername() throws RemoteException;

    void register(ChatClient client, String nickname) throws RemoteException;

    void unregister(ChatClient client) throws RemoteException;

    List<String> getConnectedClients() throws RemoteException;

    void sendDirectMessage(ChatClient sender, String receiverName, String message) throws RemoteException;

    void sendGroupMessage(ChatClient sender, String message) throws RemoteException;

    void notifyNewUser(String username) throws RemoteException;

    void notifyLeaveUser(String username) throws RemoteException;

}
