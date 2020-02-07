package com.omi.app.api;


import com.omi.app.models.BestSellerResponce;
import com.omi.app.models.CardResponce;
import com.omi.app.models.CartClearResponse;
import com.omi.app.models.CartListLoundryResponce;
import com.omi.app.models.CartListResponce;
import com.omi.app.models.CityResponce;
import com.omi.app.models.CommentResponce;
import com.omi.app.models.FcmSendResponse;
import com.omi.app.models.HomeServiceResponce;
import com.omi.app.models.ItemListLoundryResponce;
import com.omi.app.models.ItemListResponce;
import com.omi.app.models.LaundryOrderListResponse;
import com.omi.app.models.LoginResponse;
import com.omi.app.models.LondryCardResponce;
import com.omi.app.models.LoundryDetailsResponce;
import com.omi.app.models.LoundryMenuResponce;
import com.omi.app.models.OrderDetailsLoundryResponce;
import com.omi.app.models.OrderDetailsResponce;
import com.omi.app.models.OrderHistoryLoundryResponce;
import com.omi.app.models.OrderHistoryResponce;
import com.omi.app.models.OrderPlaceLoundryResponce;
import com.omi.app.models.OrderPlaceResponce;
import com.omi.app.models.OrderResponse;
import com.omi.app.models.OtpVerificationResponse;
import com.omi.app.models.RatingListResponce;
import com.omi.app.models.RegisterResponse;
import com.omi.app.models.ResturentDetailsResponce;
import com.omi.app.models.ResturentListResponce;
import com.omi.app.models.ResturentMenuResponce;
import com.omi.app.models.SendCurrentLocationResponse;
import com.omi.app.models.SendOtpVerificationResponse;
import com.omi.app.models.ServiceResponce;
import com.omi.app.models.SubServiceLoundryResponce;
import com.omi.app.models.SubServiceResponce;
import com.omi.app.models.TrendingFoodResponce;
import com.omi.app.models.VerifyDeviceResponce;
import com.omi.app.models.VerifyEmailResponce;
import com.omi.app.models.VerifyMobileResponce;
import com.omi.app.models.WalletResponse;
import com.omi.app.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServices {

   /* @GET(Constants.UrlPath.GET_COUNTRY_LIST)
    Call<CountryListResponse> getCountryList(); */

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<LoginResponse> getLogin(@Field("service") String service,
                                 @Field("user_email") String user_email,
                                 @Field("user_pwd") String user_pwd);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<VerifyMobileResponce> verifyMobile(@Field("service") String service,
                                            @Field("user_mobile") String user_mobile);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<VerifyEmailResponce> verifyEmail(@Field("service") String service,
                                          @Field("user_email") String user_email);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<VerifyDeviceResponce> verifyDevice(@Field("service") String service,
                                            @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<RegisterResponse> getRegister(@Field("service") String service,
                                       @Field("first_name") String first_name,
                                       @Field("last_name") String last_name,
                                       @Field("email") String email,
                                       @Field("mobile_no") String mobile_no,
                                       @Field("address") String address,
                                       @Field("gender") String gender,
                                       @Field("password") String password,
                                       @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RESTAURANT)
    Call<CityResponce> searchCity(@Field("service") String service);


    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<ServiceResponce> serviceList(@Field("service") String service);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<ItemListResponce> getItemList(@Field("service") String service,
                                       @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<ItemListLoundryResponce> getItemListLoundry(@Field("service") String service,
                                                     @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<SendOtpVerificationResponse> send_otp(@Field("service") String service,
                                               @Field("otp_type") String otp_type,
                                               @Field("user_mobile") String mobile_no);


    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<OtpVerificationResponse> otpVerification(@Field("service") String service,
                                                  @Field("user_mobile") String user_mobile,
                                                  @Field("user_otp") String user_otp,
                                                  @Field("otp_type") String otp_type,
                                                  @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST(Constants.UrlPath.WALLET)
    Call<WalletResponse> walletResponse(@Field("Flag") String flag,
                                        @Field("col") String col,
                                        @Field("value") String value);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<SubServiceResponce> subService(@Field("service") String service,
                                        @Field("pid") String pid);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<SendCurrentLocationResponse> sendCuurentLocation(@Field("service") String service,
                                                          @Field("addr_info") String addr_info);

    @FormUrlEncoded
    @POST(Constants.UrlPath.SEND_FCM)
    Call<FcmSendResponse> sendFcm(@Field("fcm_token") String fcm_token,
                                  @Field("u_id") String u_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RESTAURANT)
    Call<ResturentMenuResponce> resturentMenu(@Field("service") String service,
                                              @Field("res_id") String res_id,
                                              @Field("is_trend") String is_trend);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<CartClearResponse> clearCart(@Field("service") String service,
                                      @Field("user_id") String user_id,
                                      @Field("cart_type") String cart_type);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<CardResponce> getAddCard(@Field("service") String service,
                                  @Field("user_id") String user_id,
                                  @Field("res_id") String res_idd,
                                  @Field("item_id") String item_id,
                                  @Field("qty") String qty,
                                  @Field("price") String price);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<CardResponce> getRemoveItemFromCard(@Field("service") String service,
                                             @Field("user_id") String user_id,
                                             @Field("res_id") String res_idd,
                                             @Field("item_id") String item_id,
                                             @Field("qty") String qty,
                                             @Field("price") String price);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<CardResponce> getUpdateCard(@Field("service") String service,
                                     @Field("cart_id") String cart_id,
                                     @Field("qty") String qty);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<OrderPlaceResponce> getOrderPlace(@Field("service") String service,
                                           @Field("res_id") String res_id,
                                           @Field("user_id") String user_id,
                                           @Field("cust_name") String cust_name,
                                           @Field("cust_mobile") String cust_mobile,
                                           @Field("cust_address") String cust_address,
                                           @Field("payment_mode") String payment_mode,
                                           @Field("txtid") String txtid,
                                           @Field("is_wallet_used") boolean is_wallet_used,
                                           @Field("payable_amount") String payable_amount);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RESTAURANT)
    Call<ResturentListResponce> restaurentList(@Field("service") String service,
                                               @Field("city_id") String city_id,
                                               @Field("lat") String lat,
                                               @Field("long") String longi,
                                               @Field("rest_search") String rest_search);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<CartListResponce> cartList(@Field("service") String service,
                                    @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RATTING)
    Call<RatingListResponce> testRating(@Field("service") String service,
                                        @Field("m_id") String m_id);


    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<OrderDetailsResponce> orderDetails(@Field("service") String service,
                                            @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<OrderResponse> orderList(@Field("service") String service,
                                  @Field("user_id") String order_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RESTAURANT)
    Call<BestSellerResponce> bestSeller(@Field("service") String service,
                                        @Field("city") String city,
                                        @Field("lat") String lat,
                                        @Field("long") String longi);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORCART)
    Call<OrderHistoryResponce> orderHistory(@Field("service") String service,
                                            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.AUTHENTICATION)
    Call<HomeServiceResponce> homeService(@Field("service") String service,
                                          @Field("lat") String lat,
                                          @Field("long") String longi,
                                          @Field("sub_id") String sub_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RATTING)
    Call<CommentResponce> sendComment(@Field("service") String service,
                                      @Field("rating_count") String rating_count,
                                      @Field("role") String role,
                                      @Field("merchant_id") String merchant_id,
                                      @Field("date") String date,
                                      @Field("user_name") String user_name,
                                      @Field("review") String review);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<LondryCardResponce> getAddCardLoundry(@Field("service") String service,
                                               @Field("user_id") String user_id,
                                               @Field("dhobi_id") String dhobi_id,
                                               @Field("item_id") String item_id,
                                               @Field("qty") String qty,
                                               @Field("price") String price,
                                               @Field("item_opr") String item_opr);


    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<LondryCardResponce> getRemoveLaundryItemFromCard(@Field("service") String service,
                                                          @Field("user_id") String user_id,
                                                          @Field("dhobi_id") String res_idd,
                                                          @Field("item_id") String item_id,
                                                          @Field("qty") String qty,
                                                          @Field("price") String price);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<LoundryDetailsResponce> loundryDetails(@Field("service") String service,
                                                @Field("laundry_id") String laundry_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<CartListLoundryResponce> cartListLoundry(@Field("service") String service,
                                                  @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<OrderPlaceLoundryResponce> getOrderPlaceLoundry(@Field("service") String service,
                                                         @Field("dhobi_id") String dhobi_id,
                                                         @Field("user_id") String user_id,
                                                         @Field("cust_name") String cust_name,
                                                         @Field("cust_mobile") String cust_mobile,
                                                         @Field("cust_address") String cust_address,
                                                         @Field("payment_mode") String payment_mode,
                                                         @Field("txtid") String txtid,
                                                         @Field("is_wallet_used") boolean is_wallet_used);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<OrderDetailsLoundryResponce> orderDetailsLoundry(@Field("service") String service,
                                                          @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<LaundryOrderListResponse> orderHistoryLoundry(@Field("service") String service,
                                                       @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<SubServiceLoundryResponce> subServiceLoundry(@Field("service") String service,
                                                      @Field("city") String city,
                                                      @Field("lat") String lat,
                                                      @Field("long") String longi);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<ResturentDetailsResponce> resturentDetails(@Field("service") String service,
                                                    @Field("res_id") String res_id);

    @FormUrlEncoded
    @POST(Constants.UrlPath.FORLOUNDRY)
    Call<LoundryMenuResponce> loundryMenu(@Field("service") String service,
                                          @Field("laundry_id") String laundry_id,
                                          @Field("type1") String type1,
                                          @Field("type2") String type2);

    @FormUrlEncoded
    @POST(Constants.UrlPath.RESTAURANT)
    Call<TrendingFoodResponce> trendingFood(@Field("service") String service,
                                            @Field("city") String city,
                                            @Field("lat") String lat,
                                            @Field("long") String longi);

}