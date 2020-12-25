package com.example.e_commerceadmin.ThongBao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAylCKmm8: APA91bF5KE2_0kLIeYdqTbe9J2khEJl1hQxo6dvNYJ8P24U1xUC_RlJnNqneGUUO2WyThdJ_PXKAVYLB9gOVpGG3xzSzPg1-tn95R1THQezjLFkBV8UlnYkY0X0BJW1cHXNg15zFOkXL"

    })
    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
