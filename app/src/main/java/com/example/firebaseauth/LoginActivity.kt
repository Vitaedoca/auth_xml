package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Inicialize o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inicialize os campos de email e senha
        emailEditText = findViewById(R.id.emailEditText)  // Certifique-se de que o ID está correto
        passwordEditText = findViewById(R.id.passwordEditText)  // Certifique-se de que o ID está correto

        // Botão de login
        findViewById<View>(R.id.loginButton).setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Verificar se os campos estão vazios
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botão de registro
        findViewById<View>(R.id.registerButton).setOnClickListener {
            // Ir para a tela de registro
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        // Tenta fazer o login no Firebase com o email e senha fornecidos
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Se o login for bem-sucedido, navegue para a MainActivity
                    Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()  // Finaliza a LoginActivity para que o usuário não possa voltar a ela
                } else {
                    // Se o login falhar, exibe uma mensagem de erro
                    Toast.makeText(this, "Erro ao fazer login: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
