package com.mohit.vidhidarpan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface OpenAIApiService {
    @Headers("Authorization: Bearer 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")
    @POST("v1/engines/text-davinci-003/completions")
    Call<OpenAIResponse> getResponse(@Body OpenAIRequest body);
}
