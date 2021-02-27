package com.topru.client.Listeners;


public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}
