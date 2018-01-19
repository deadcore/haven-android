package hackergames.resilientplc.com.hackergamesapp.data;

import android.content.Context;
import android.util.Log;

import com.pusher.chatkit.ChatManager;
import com.pusher.chatkit.ChatkitTokenParams;
import com.pusher.chatkit.ChatkitTokenProvider;
import com.pusher.chatkit.CurrentUser;
import com.pusher.chatkit.ErrorListener;
import com.pusher.chatkit.MessageSentListener;
import com.pusher.chatkit.Room;
import com.pusher.chatkit.RoomListener;
import com.pusher.chatkit.User;
import com.pusher.chatkit.UserSubscriptionListenersAdapter;
import com.pusher.platform.logger.LogLevel;

import java.util.HashMap;
import java.util.Map;

import elements.Error;
import hackergames.resilientplc.com.hackergamesapp.HackersGameApplication;
import timber.log.Timber;

/**
 * Created by eduar on 18/01/2018.
 */

public class ChatProvider {

    private static final String TOKEN_PROVIDER_ENDPOINT =
            "https://us1.pusherplatform.io/services/chatkit_token_provider/v1/58bf27b4-2820-4237-9a6c-12a433d42f6f/token?instance_locator=v1:us1:58bf27b4-2820-4237-9a6c-12a433d42f6f";
    private static final String INSTANCE_LOCATOR = "v1:us1:58bf27b4-2820-4237-9a6c-12a433d42f6f";
    private static final String TOKEN_ENDPOINT = "http://resilient-hackathon.eu-west-1.elasticbeanstalk.com/auth";
    private final ChatManager chatManager;
    private  RoomListener roomListener;
    private  ErrorListener errorListener;
    private CurrentUser currentUser;
    private MessageSentListener messageSentListener;
    private Room room;

    public ChatProvider(Context context) {
        ChatkitTokenProvider tokenProvider = new ChatkitTokenProvider(TOKEN_ENDPOINT);
        Map userparams = new HashMap();
        userparams.put("user_id", HackersGameApplication.getUser().getUserId());

        chatManager = new ChatManager.Builder()
                .instanceLocator(INSTANCE_LOCATOR)
                .userId(HackersGameApplication.getUser().getUserId())
                .tokenParams(new ChatkitTokenParams(userparams))
                .logLevel(LogLevel.DEBUG)
                .context(context)
                .tokenProvider(tokenProvider)
                .build();

    }


    public void connectUser() {
        chatManager.connect(
                new UserSubscriptionListenersAdapter(){
                    @Override
                    public void currentUserReceived(CurrentUser currentUser) {
                        ChatProvider.this.currentUser = currentUser;
                        Timber.d("user connected %s", currentUser);
                        createRoom();
                    }

                    @Override
                    public void onError(Error error) {
                        super.onError(error);
                        Timber.d("Error %s", error.toString());
                    }

                    @Override
                    public void removedFromRoom(int roomId) {
                        super.removedFromRoom(roomId);
                        Timber.d("Removed from room: %d", roomId);
                    }

                    @Override
                    public void addedToRoom(Room room) {
                        super.addedToRoom(room);
                        Timber.d("Removed from room: %s", room);

                    }

                    @Override
                    public void roomDeleted(int roomId) {
                        super.roomDeleted(roomId);
                        Timber.d("Room deleted: %d", roomId);

                    }

                    @Override
                    public void roomUpdated(Room room) {
                        super.roomUpdated(room);
                        Timber.d("Room updated %s", room);

                    }

                    @Override
                    public void userCameOnline(User user) {
                        super.userCameOnline(user);
                        Timber.d("User came online%s", user);

                    }

                    @Override
                    public void userJoined(User user, Room room) {
                        super.userJoined(user, room);
                        Timber.d("User joined room: %s, %s ", user, room);

                    }

                    @Override
                    public void userLeft(User user, Room room) {
                        super.userLeft(user, room);
                        Timber.d("User left room: %s, %s ", user, room);

                    }

                    @Override
                    public void userWentOffline(User user) {
                        super.userWentOffline(user);
                        Timber.d("User went offline %s", user);

                    }
                });

    }


    private void createRoom() {
        room = currentUser.getRoom(3742568);
        joinRoom();
        //todo create room and add to queue in backend
//        currentUser.createRoom("demoroom",
//                new RoomListener() {
//                    @Override
//                    public void onRoom(Room room) {
//                    }
//                }, new ErrorListener() {
//                    @Override
//                    public void onError(Error error) {
//                        Timber.d("Creating room error %s", error);
//
//                    }
//                });

    }

    private void joinRoom() {
        currentUser.joinRoom(
                3742568, roomListener, new ErrorListener() {
                    @Override
                    public void onError(Error error) {
                        Timber.d("Joining room error %s", error);
                    }
                });
    }

    public void sendMessage(String message) {
        currentUser.addMessage(message, room, messageSentListener, error -> {
            Log.d("coisada", "error sending message: " + error.toString());
        });
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setMessageSentListener(MessageSentListener messageSentListener) {
        this.messageSentListener = messageSentListener;
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }
}
