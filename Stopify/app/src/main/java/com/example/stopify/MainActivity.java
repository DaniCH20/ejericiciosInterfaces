package com.example.stopify;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int MAX_POSITION = 1;  // Este valor se actualizará dinámicamente
    private static final int MIN_POSITION = 1;  // Define la posición mínima
    private WebView webView;
    private FirebaseFirestore db;
    private ImageButton next;
    private ImageButton previous;
    private ImageButton play;
    private TextView txtTitulo;
    private TextView txtGrupo;
    private int posicion = 1;  // Posición inicial en la colección de videos (comenzamos en 1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el WebView
        webView = findViewById(R.id.video);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        // Inicializa los botones y el texto
        next = findViewById(R.id.buttonNext);
        play = findViewById(R.id.buttonPlay);
        previous = findViewById(R.id.buttonPrevious);
        txtTitulo = findViewById(R.id.textTitulo);
        txtGrupo = findViewById(R.id.textSubtitulo);

        // Inicializa Firestore
        db = FirebaseFirestore.getInstance();
        // Obtener el número de documentos en la colección
        getDocumentCount();
        // Carga el video inicial
        loadVideoUrl(posicion);

        // Configurar los listeners para los botones
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Avanza a la siguiente posición o vuelve a la posición 1 si está en el máximo
                if (posicion >= MAX_POSITION) {
                    posicion = MIN_POSITION;  // Ciclo de regreso al inicio
                } else {
                    posicion++;
                }
                loadVideoUrl(posicion);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrocede a la posición anterior o va a la posición máxima si está en la mínima
                if (posicion <= MIN_POSITION) {
                    posicion = MAX_POSITION;  // Ciclo de regreso al final
                } else {
                    posicion--;
                }
                loadVideoUrl(posicion);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproduce el video actual en la posición
                loadVideoUrl(posicion);
            }
        });
    }

    // Método para cargar el video desde Firebase Firestore según la posición
    private void loadVideoUrl(int position) {
        // Referencia a la colección en Firestore
        CollectionReference videosRef = db.collection("canciones");

        // Obtiene el documento con el ID correspondiente a la posición
        videosRef.document(String.valueOf(position)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Obtiene la URL del video, título y grupo desde el documento
                        String videoUrl = document.getString("urlYoutube");
                        String titulo = document.getString("titulo");
                        String grupo = document.getString("grupo");

                        // Incrusta la URL en un iframe para reproducir en el WebView
                        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
                        webView.loadData(videoHtml, "text/html", "utf-8");

                        // Actualiza los TextView con el título y el grupo
                        txtTitulo.setText(titulo);
                        txtGrupo.setText(grupo);
                    } else {
                        Log.d(TAG, "El documento no existe en la posición " + position);
                    }
                } else {
                    Log.w(TAG, "Error al obtener documento", task.getException());
                }
            }
        });
    }
    private void getDocumentCount() {
        CollectionReference videosRef = db.collection("canciones");
        videosRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    MAX_POSITION = task.getResult().size();  // Asigna el conteo de documentos a MAX_POSITION
                    Log.d(TAG, "Número de documentos en la colección: " + MAX_POSITION);

                    // Carga el primer video después de obtener el número total de documentos
                    loadVideoUrl(posicion);
                } else {
                    Log.w(TAG, "Error al contar documentos", task.getException());
                }
            }
        });
    }
}
