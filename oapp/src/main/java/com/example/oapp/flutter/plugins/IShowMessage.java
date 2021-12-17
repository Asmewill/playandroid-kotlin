package com.example.oapp.flutter.plugins;

public interface IShowMessage {
    /**
     *
     * @param message Flutter -> Android
     */
    void onShowMessage(String message);

    /**
     *
     * @param message Android --> flutter
     */
    void sendMessage(String message);
}