package com.thida.websocketandroidokhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {
    TextView tvFlower;
    ImageView ivrose,ivjasmine;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivrose = findViewById(R.id.iv2);
        ivjasmine = findViewById(R.id.iv1);
        tvFlower = findViewById(R.id.flower);
        client = new OkHttpClient();
        ivjasmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(view);
            }
        });
        ivrose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(view);
            }
        });
    }

    private final class MyWebSocketListener extends WebSocketListener{
        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
            output("Closing : "+code+"/"+reason);
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            output("Error : "+t.getMessage());
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            super.onMessage(webSocket, text);
            output(text);
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            //webSocket.send("Hello");
           // webSocket.send("Thida");
           // webSocket.close(1000,"Closed!");
        }
    }

    private void output(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvFlower.setText(text);
                Toast.makeText(getApplicationContext(),"This is "+text,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void start(View view){
        //Existing websocket server
        //Request request = new Request.Builder().url("ws://echo.websocket.org").build();

        //Spring local Server
        Request request = new Request.Builder().url("http://172.16.4.155:8080/websocket").build();
        MyWebSocketListener listener = new MyWebSocketListener();
        WebSocket socket = client.newWebSocket(request, listener);
        if(view == ivjasmine)   socket.send("1");
        else socket.send("2");
       // client.dispatcher().executorService().shutdown();
    }
}
