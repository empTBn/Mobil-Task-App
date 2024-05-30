package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Foro : AppCompatActivity() {

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_foro)

        // Initialize RecyclerView and Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.foro_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(mutableListOf())
        recyclerView.adapter = messageAdapter

        // Initialize input field and button
        messageInput = findViewById(R.id.mensaje_input)
        val sendButton = findViewById<Button>(R.id.enviar_mensaje_btn)

        // Send button click listener
        sendButton.setOnClickListener {
            val messageContent = messageInput.text.toString()
            if (messageContent.isNotBlank()) {
                val message = Message(messageContent)
                messageAdapter.addMessage(message)
                messageInput.text.clear()
                recyclerView.scrollToPosition(messageAdapter.itemCount - 1) // Scroll to the last message
            }
        }

        // Return button click listener
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            val intent = Intent(this, GestionForos::class.java)
            startActivity(intent)
        }
    }
}
