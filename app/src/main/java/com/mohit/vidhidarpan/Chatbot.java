package com.mohit.vidhidarpan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chatbot extends Fragment {
    private EditText editTextQuery;
    private TextView textViewResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        editTextQuery = view.findViewById(R.id.edit_query);
        textViewResponse = view.findViewById(R.id.text_response);
        Button buttonSend = view.findViewById(R.id.button_send);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenAIApiService service = retrofit.create(OpenAIApiService.class);

        buttonSend.setOnClickListener(v -> {
            String query = editTextQuery.getText().toString();
            OpenAIRequest request = new OpenAIRequest(query, 30); // Adjust the number of tokens as needed

            service.getResponse(request).enqueue(new Callback<OpenAIResponse>() {
                @Override
                public void onResponse(Call<OpenAIResponse> call, Response<OpenAIResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Use the overridden toString() method of OpenAIResponse
                        textViewResponse.setText(response.body().toString());
                    } else {
                        handleError(response);
                    }
                }

                @Override
                public void onFailure(Call<OpenAIResponse> call, Throwable t) {
                    textViewResponse.setText("Network error: " + t.getMessage());
                }
            });



        });

        return view;
    }

    private void handleError(Response<OpenAIResponse> response) {
        try {
            String errorString = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            textViewResponse.setText("Error: " + errorString);
            Log.e("OpenAIError", errorString);
        } catch (IOException e) {
            textViewResponse.setText("Error in reading response: " + e.getMessage());
            Log.e("OpenAIError", "Error reading response", e);
        }
    }
}