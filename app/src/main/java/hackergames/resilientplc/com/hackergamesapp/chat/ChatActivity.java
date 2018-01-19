 package hackergames.resilientplc.com.hackergamesapp.chat;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.pusher.chatkit.ErrorListener;
import com.pusher.chatkit.MessageSentListener;
import com.pusher.chatkit.Room;
import com.pusher.chatkit.RoomListener;
import com.pusher.chatkit.RoomSubscriptionListeners;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import elements.Error;
import hackergames.resilientplc.com.hackergamesapp.data.ChatProvider;
import hackergames.resilientplc.com.hackergamesapp.HackersGameApplication;
import hackergames.resilientplc.com.hackergamesapp.R;
import hackergames.resilientplc.com.hackergamesapp.data.NetworkClient;
import hackergames.resilientplc.com.hackergamesapp.data.model.User;
import hackergames.resilientplc.com.hackergamesapp.filter.FilterMilitaryActivity;
import toothpick.Toothpick;

 public class ChatActivity extends AppCompatActivity {

     @BindView(R.id.messagesList)
     MessagesList messagesList;
     @BindView(R.id.message_text)
     EditText messageText;
     @BindView(R.id.send_button)
     ImageView sendButton;

     @Inject
     ChatProvider chatProvider;

     @Inject
     NetworkClient networkClient;

     MessagesListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(HackersGameApplication.SCOPE));

        ImageLoader imageLoader = (imageView, url) -> Picasso.with(ChatActivity.this).load(url).into(imageView);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (User.MILITARY.equals(HackersGameApplication.getUser().getType())) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Kyle");

        }

        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        chatProvider.setRoomListener(room -> {
            //todo subscribe to room
            adapter = new MessagesListAdapter<>(chatProvider.getCurrentUser().getId(), imageLoader);
            messagesList.setAdapter(adapter);
            subscribeToRoom(room);
        });
        chatProvider.setErrorListener(new ErrorListener() {
            @Override
            public void onError(Error error) {
                Log.d("coisada", "error: " + error.toString());
            }
        });
        chatProvider.setMessageSentListener(new MessageSentListener() {
            @Override
            public void onMessage(int messageId) {
                runOnUiThread(() -> {
//                    Message message1 = new Message(String.valueOf(chatProvider.getCurrentUser().getId()), String.valueOf(messageText.getText()), new Date(System.currentTimeMillis()),
//                            new Author(chatProvider.getCurrentUser().getId(), chatProvider.getCurrentUser().getName(), ""));
//                    adapter.addToStart(message1, true);
                    messageText.setText("");

                });
            }
        });
        chatProvider.connectUser();
    }

    @OnClick(R.id.send_button)
    public void sendClick() {
        chatProvider.sendMessage(String.valueOf(messageText.getText()));
    }

    private void subscribeToRoom(Room room) {

        chatProvider.getCurrentUser().subscribeToRoom(room, new RoomSubscriptionListeners() {

            @Override
            public void onNewMessage(com.pusher.chatkit.Message message) {

                Date date = null;

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                try {
                    date = format.parse(message.getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Message message1 = new Message(String.valueOf(message.getId()), message.getText(), date,
                        new Author(String.valueOf(message.getUserId()), message.getUser().getName(), message.getUser().getAvatarURL()));
                adapter.addToStart(message1, true);
            }

            @Override
            public void onError(Error error) {

            }
        });
    }

     public class Message implements IMessage {


         private String id;
         private String text;
         private Date createdAt;
         private Author author;

         public Message(String id, String text, Date createdAt, Author author) {
             this.id = id;
             this.text = text;
             this.createdAt = createdAt;
             this.author = author;
         }

         @Override
         public String getId() {
             return id;
         }

         @Override
         public String getText() {
             return text;
         }

         @Override
         public Author getUser() {
             return author;
         }

         @Override
         public Date getCreatedAt() {
             return createdAt;
         }


     }

     public class Author implements IUser {
         private String id;
         private String name;
         private String avatar = "https://www.drupal.org/files/issues/default-avatar.png";

         public Author(String id, String name, String avatar) {
             this.id = id;
             this.name = name;
//             this.avatar = avatar;
         }

         @Override
         public String getId() {
             return id;
         }

         @Override
         public String getName() {
             return name;
         }

         @Override
         public String getAvatar() {
             return avatar;
         }
     }}
