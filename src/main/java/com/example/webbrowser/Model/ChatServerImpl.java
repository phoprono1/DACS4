package com.example.webbrowser.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private User user; // Thêm trường để giữ tham chiếu đến đối tượng User

    private final CopyOnWriteArrayList<ChatClient> clients = new CopyOnWriteArrayList<>();

    public ChatServerImpl(String username, ChatClientGUI gui) throws RemoteException {
        super();
        this.user = new User(username); // Khởi tạo đối tượng User khi tạo ChatClient
    }

    public ChatServerImpl() throws RemoteException {
        super();
    }

    @Override
    public String getUsername() throws RemoteException {
        return user.getUsername();
    }

    @Override
    public void register(ChatClient client, String username) throws RemoteException {
        clients.add(client);
        notifyUserListChange();
        notifyNewUser(username);
    }

    @Override
    public void unregister(ChatClient client) throws RemoteException {
        String leavingUser = client.getName();
        clients.remove(client);
        notifyLeaveUser(leavingUser);
        notifyUserListChange();
    }


    @Override
    public List<String> getConnectedClients() throws RemoteException {
        List<String> clientNames = new ArrayList<>();
        for (ChatClient client : clients) {
            clientNames.add(client.getName());
        }
        return clientNames;
    }

    // Trong lớp ChatServerImpl
    @Override
    public void sendDirectMessage(ChatClient sender, String receiverName, String message) throws RemoteException {
        ChatClient receiver = findClientByUsername(receiverName);
        if (receiver != null) {
            receiver.receiveMessage(sender.getName() + ": " + message, message);
        } else {
            sender.receiveMessage(receiverName + " is not online.", message);
        }
    }

    @Override
    public void sendGroupMessage(ChatClient sender, String message) throws RemoteException {
        for (ChatClient client : clients) {
            if (!client.equals(sender)) {
                client.receiveMessage(sender.getName(), message); // Thêm tham số sender.getName()
            }
        }
    }

    @Override
    public void notifyNewUser(String username) throws RemoteException {
        for (ChatClient client : clients) {
            client.receiveNotification("System", "Người mới " + username + " đã tham gia vào phòng chat.");
        }
    }

    @Override
    public void notifyLeaveUser(String username) throws RemoteException {
        for (ChatClient client : clients) {
            client.receiveNotification("System", "Người dùng " + username + " đã rời khỏi phòng chat.");
        }
    }


    private ChatClient findClientByUsername(String username) throws RemoteException {
        for (ChatClient client : clients) {
            if (client.getName().equals(username)) {
                return client;
            }
        }
        return null;
    }

    private synchronized void notifyUserListChange() throws RemoteException {
        List<String> userList = getConnectedClients();
        for (ChatClient client : clients) {
            try {
                client.updateUserList(userList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
