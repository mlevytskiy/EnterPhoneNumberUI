package com.onboarding.sample;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.IntRange;

import org.drinkless.td.libcore.BuildConfig;
import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.Locale;

//import banana.digital.telegramapi.ConstantValues;

public class TelegramManager implements Client.ExceptionHandler, Client.ResultHandler{

    public static TelegramManager instance;
    private Context mContext;
    public static Client mClient;
    final String TAG = "TelegramManager";
    ArrayList<Client.ResultHandler> mResultHandlers = new ArrayList<>();



    public void initialize(Context context) {
        mClient = Client.create(this,this,this);
        mContext = context;
    }


    public static TelegramManager getInstance() {
        if (instance == null) {
            instance = new TelegramManager();
        }
        return instance;
    }




    @Override
    public void onResult(TdApi.Object object) {
        Log.v(TAG, "onResult: object.getClass()=" + object.getClass().getSimpleName());
        switch (object.getConstructor()) {
            case TdApi.UpdateAuthorizationState.CONSTRUCTOR: ;
                onUpdateAuthorizationState(((TdApi.UpdateAuthorizationState) object).authorizationState);

                break;
            case TdApi.UpdateCall.CONSTRUCTOR:
                //TODO
                break;
            case TdApi.UpdateChatLastMessage.CONSTRUCTOR://Новое сообщение
                //TODO
                break;
        }

        for(Client.ResultHandler resultHandler : mResultHandlers) {
            resultHandler.onResult(object);
        }
    }




    public void addResultHandler(Client.ResultHandler resultHandler) {
        mResultHandlers.add(resultHandler);
    }

    public void removeResultHandler(Client.ResultHandler resultHandler) {
        mResultHandlers.remove(resultHandler);
    }




    @Override
    public void onException(Throwable e) {
        Log.e(TAG, "        " + TAG + " Error");
    }


    public void onUpdateAuthorizationState(TdApi.AuthorizationState authorizationState) {
        switch (authorizationState.getConstructor()) {
            case TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR:
                final TdApi.TdlibParameters parameters = new TdApi.TdlibParameters();
                parameters.apiId = 11043772; //extra one
                parameters.apiHash = "9a7362f6ec33bc23187f757ecee5e83c";
                parameters.enableStorageOptimizer = true;
                parameters.useMessageDatabase = true;//Разрешаем кэшировать чаты и сообщения
                parameters.useFileDatabase = true;//Разрешаем кэшировать файлы
                parameters.filesDirectory =mContext.getExternalFilesDir(null).getAbsolutePath() + "/";//Путь к файлам
                parameters.databaseDirectory = mContext.getExternalFilesDir(null).getAbsolutePath() + "/";//Пусть к базе данных
                parameters.systemVersion = Build.VERSION.RELEASE;//Версия ос
                parameters.deviceModel = Build.DEVICE;//Модель устройства
                parameters.systemLanguageCode = Locale.getDefault().getLanguage();
                parameters.applicationVersion = BuildConfig.VERSION_NAME;
                mClient.send(new TdApi.SetTdlibParameters(parameters), this);
                break;
            case TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR:
                mClient.send(new TdApi.SetDatabaseEncryptionKey(), this);
                break;
        }
    }


    public void sendPhoneNumber(String phoneNumber) {
        mClient.send(new TdApi.SetAuthenticationPhoneNumber(phoneNumber, false, false), this);
    }

    public void sendCode(String code) {
        mClient.send(new TdApi.CheckAuthenticationCode(code, null, null), this);
    }

    public void getCurrentUser() {
        mClient.send(new TdApi.GetMe(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                if (object.getConstructor() == TdApi.User.CONSTRUCTOR) {
                    TdApi.User currUser = (TdApi.User) object;
                    Log.i(TAG, "id=" + currUser.id + " name=" + currUser.firstName +
                            " phone=" + currUser.phoneNumber);
                } else {
                    Log.i(TAG, "id=ERROR");
                }

            }
        });
    }

    public void getAllContacts() {
        mClient.send(new TdApi.GetContacts(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                if (object.getConstructor() == TdApi.Users.CONSTRUCTOR) {
                    TdApi.Users tdUsers =  (TdApi.Users) object;
                    for (int i = 0; i < tdUsers.userIds.length; i++) {
                        Log.i(TAG, "users id[" + i + "]=" + tdUsers.userIds[i]);
                    }
                } else {
                    Log.i(TAG, "users=ERROR");
                }

            }
        });
    }

    public void getChats() {
        mClient.send(new TdApi.GetChats(Long.MAX_VALUE, 0, 42), this);
    }


    public void downloadFile(int fileId, @IntRange(from = 1, to = 32) int priority) {
//        mClient.send(new TdApi.DownloadFile(fileId, priority), this);
    }

    public void requestMessages(long chatId, long fromMessageId) {
        mClient.send(new TdApi.GetChatHistory(chatId, fromMessageId, 0, 42, false),this);
    }
}



