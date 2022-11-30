package com.example.practiceforandroid.servicios;

import com.example.practiceforandroid.entidades.Image;
import com.example.practiceforandroid.entidades.ImageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ImageService {

    @Headers("Authorization: Client-ID 8bcc638875f89d9")
    @POST("3/image")
    Call<ImageResponse> sendImage(@Body Image imgPok);

}