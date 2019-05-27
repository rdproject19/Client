package com.example.messenger;

import android.app.Application;

import com.example.messenger.system.ChatHandler;

public class Global extends Application
{

        private ChatHandler chatHandler;

        public ChatHandler getChatHandler()
        {
            return this.chatHandler;
        }

        public void initChatHandler()
        {
            this.chatHandler = new ChatHandler();
        }


        @Override
        public void onCreate()
        {
            super.onCreate();
            this.initChatHandler();
        }



}
