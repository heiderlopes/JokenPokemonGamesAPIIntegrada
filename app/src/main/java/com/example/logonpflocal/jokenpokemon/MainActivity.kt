package com.example.logonpflocal.jokenpokemon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.logonpflocal.jokenpokemon.api.JokenpokemonAPI
import com.example.logonpflocal.jokenpokemon.model.Pontuacao
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener {
            getJokenPokemonAPI()
                    .buscarPontuacao()
                    .enqueue(object : Callback<List<Pontuacao>>{
                        override fun onFailure(call: Call<List<Pontuacao>>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<List<Pontuacao>>?, response: Response<List<Pontuacao>>?) {
                            val pontuacao = response?.body()
                            for(pont in pontuacao!!) {
                                Log.i("PONTUACAO", "${pont.nome} - ${pont.pontos}")
                            }
                        }
                    })
        }

        btSalvar.setOnClickListener {
            val pontuacao = Pontuacao(etNome.text.toString(),
                    etPontos.text.toString().toInt())
            getJokenPokemonAPI()
                    .enviarPontos(pontuacao)
                    .enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>?, t: Throwable?) {
                            exibirMensagemErro()
                        }

                        override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                            if (response?.isSuccessful == true) {
                                exibirMensagemSucesso()
                            } else {
                                exibirMensagemErro()
                            }
                        }
                    })
        }
    }

    private fun exibirMensagemSucesso() {
        Toast.makeText(this, "Gravado", Toast.LENGTH_LONG).show()
    }

    private fun exibirMensagemErro() {
        Toast.makeText(this, "Deu erro", Toast.LENGTH_LONG).show()
    }


    private fun getJokenPokemonAPI(): JokenpokemonAPI {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gamestjd.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit
                .create<JokenpokemonAPI>(JokenpokemonAPI::class.java)
    }
}
