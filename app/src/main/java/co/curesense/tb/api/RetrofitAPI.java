package co.curesense.tb.api;

import java.util.List;

import co.curesense.tb.model.Medication;
import co.curesense.tb.model.TokenResponseVo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitAPI {

    String redirect_uri = "https://spacenos.com/uri";
    String client_id = "Pw1S4SfNNtOLbO0xYeIDRHEu4tZHveGOzg5PtiAR";
    String client_secret = "h2IQUuMY4wnKE9u00bTIZ3UBzsJrzZthZelhKHcFfjllfeFM3y7I5UrNOWsOwPObOzEuJgtRkYyFpDdrzRS146HyrQklsp8U2S2xjmaRhhahY8I4CCjTmKjzOD4JMSQS";
    String response_type = "code";
    String scope = "clinical:read patients:read patients:write clinical:write";

    //https://drchrono.com/o/authorize/?redirect_uri=REDIRECT_URI_ENCODED&response_type=code&client_id=CLIENT_ID_ENCODED&scope=SCOPES_ENCODED
    @FormUrlEncoded
    @POST("/o/authorize")
    Call doServerAuth(@Query("redirect_uri") String redirect_uri, @Query("client_id") String client_id,
                      @Query("response_type") String response_type, @Query("scope") String scope);

    //https://drchrono.com/o/token/
    @FormUrlEncoded
    @POST("/o/token")
    Call<ResponseBody> getToken(@Field("refresh_token") String refresh_token);

    // https://app.drchrono.com/api/medications?patient=91865832
    @GET("/api/medications")
    Call<Medication> getMedications(@Header("Authorization") String authorization, @Query("patient") int id);


    //https://app.drchrono.com/api/documents?patient=91865832
    @Multipart
    @POST("/api/documents")
    Call<ResponseBody> addRecord(@Header("Authorization") String authorization,
                                 @Query("patient") int patient,
                                 @Part("date") RequestBody date,
                                 @Part("description") RequestBody description,
                                 @Part("patient") RequestBody patientId,
                                 @Part("doctor") RequestBody doctor,
                                 @Part MultipartBody.Part document);


}
