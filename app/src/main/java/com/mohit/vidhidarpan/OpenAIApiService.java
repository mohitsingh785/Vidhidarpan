package com.mohit.vidhidarpan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface OpenAIApiService {
    @Headers("Authorization: Bearer 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")
    @POST("v1/completions")
    Call<OpenAIResponse> getResponse(@Body OpenAIRequest body);

}
