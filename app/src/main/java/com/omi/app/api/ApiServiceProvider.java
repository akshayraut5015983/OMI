package com.omi.app.api;

import android.content.Context;

import com.omi.app.listeners.RetrofitListener;
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
import com.omi.app.utils.HttpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServiceProvider extends RetrofitBase {
    private static ApiServiceProvider apiServiceProvider;
    private ApiServices apiServices;

    private ApiServiceProvider(Context context) {
        super(context, true);
        apiServices = retrofit.create(ApiServices.class);
    }

    public static ApiServiceProvider getInstance(Context context) {
        if (apiServiceProvider == null) {
            apiServiceProvider = new ApiServiceProvider(context);
        }
        return apiServiceProvider;
    }

    public void getRequestLogin(String email_id, String password, final RetrofitListener retrofitListener) {
        Call<LoginResponse> call = apiServices.getLogin("user_login", email_id, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestRegister(String first_name, String last_name, String email, String mobile_no, String address, String gender, String password, String device_id, final RetrofitListener retrofitListener) {
        Call<RegisterResponse> call = apiServices.getRegister("user_register", first_name, last_name, email, mobile_no, address, gender, password, device_id);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestMobileVerify(String user_mobile, final RetrofitListener retrofitListener) {
        Call<VerifyMobileResponce> call = apiServices.verifyMobile("user_mobile_verify", user_mobile);
        call.enqueue(new Callback<VerifyMobileResponce>() {
            @Override
            public void onResponse(Call<VerifyMobileResponce> call, Response<VerifyMobileResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<VerifyMobileResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestEmailVerify(String user_email, final RetrofitListener retrofitListener) {
        Call<VerifyEmailResponce> call = apiServices.verifyEmail("user_email_verify", user_email);
        call.enqueue(new Callback<VerifyEmailResponce>() {
            @Override
            public void onResponse(Call<VerifyEmailResponce> call, Response<VerifyEmailResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<VerifyEmailResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestDeviceVerify(String deviceId, final RetrofitListener retrofitListener) {
        Call<VerifyDeviceResponce> call = apiServices.verifyDevice("user_device_verify", deviceId);
        call.enqueue(new Callback<VerifyDeviceResponce>() {
            @Override
            public void onResponse(Call<VerifyDeviceResponce> call, Response<VerifyDeviceResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<VerifyDeviceResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestCityList(final RetrofitListener retrofitListener) {
        Call<CityResponce> call = apiServices.searchCity("city_list");
        call.enqueue(new Callback<CityResponce>() {
            @Override
            public void onResponse(Call<CityResponce> call, Response<CityResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<CityResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestServiceList(final RetrofitListener retrofitListener) {
        Call<ServiceResponce> call = apiServices.serviceList("service_list");
        call.enqueue(new Callback<ServiceResponce>() {
            @Override
            public void onResponse(Call<ServiceResponce> call, Response<ServiceResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestSendOTP(String mobile_number, String otp_type, final RetrofitListener retrofitListener) {
        Call<SendOtpVerificationResponse> call = apiServices.send_otp("send_otp", otp_type, mobile_number);
        call.enqueue(new Callback<SendOtpVerificationResponse>() {
            @Override
            public void onResponse(Call<SendOtpVerificationResponse> call, Response<SendOtpVerificationResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<SendOtpVerificationResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }


    public void getRequestVerifyOtp(String user_mobile, String otp, String otp_type, String password, final RetrofitListener retrofitListener) {
        Call<OtpVerificationResponse> call = apiServices.otpVerification("verify_otp", user_mobile, otp, otp_type, password);
        call.enqueue(new Callback<OtpVerificationResponse>() {
            @Override
            public void onResponse(Call<OtpVerificationResponse> call, Response<OtpVerificationResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OtpVerificationResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }


    public void getRequestWalletList(String user_id, final RetrofitListener retrofitListener) {
        Call<WalletResponse> call = apiServices.walletResponse("getWalletHistory", "user_id", user_id);
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestSendLocation(String address, final RetrofitListener retrofitListener) {
        Call<SendCurrentLocationResponse> call = apiServices.sendCuurentLocation("get_cur_city", address);
        call.enqueue(new Callback<SendCurrentLocationResponse>() {
            @Override
            public void onResponse(Call<SendCurrentLocationResponse> call, Response<SendCurrentLocationResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<SendCurrentLocationResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }


    public void getRequestSubService(String pid, final RetrofitListener retrofitListener) {
        Call<SubServiceResponce> call = apiServices.subService("service_sub_list", pid);
        call.enqueue(new Callback<SubServiceResponce>() {
            @Override
            public void onResponse(Call<SubServiceResponce> call, Response<SubServiceResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<SubServiceResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestSendFcm(String fcm_token, String user_id, final RetrofitListener retrofitListener) {
        Call<FcmSendResponse> call = apiServices.sendFcm(fcm_token, user_id);
        call.enqueue(new Callback<FcmSendResponse>() {
            @Override
            public void onResponse(Call<FcmSendResponse> call, Response<FcmSendResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<FcmSendResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestResturentMenu(String res_id, String is_trend, final RetrofitListener retrofitListener) {
        Call<ResturentMenuResponce> call = apiServices.resturentMenu("res_menu_list2", res_id, is_trend);
        call.enqueue(new Callback<ResturentMenuResponce>() {
            @Override
            public void onResponse(Call<ResturentMenuResponce> call, Response<ResturentMenuResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<ResturentMenuResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestClearCart(String user_id, String cart_type, final RetrofitListener retrofitListener) {
        Call<CartClearResponse> call = apiServices.clearCart("clear_all_cart", user_id, cart_type);
        call.enqueue(new Callback<CartClearResponse>() {
            @Override
            public void onResponse(Call<CartClearResponse> call, Response<CartClearResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CartClearResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestAddCard(String user_id, String res_id, String item_id, String qty, String price, final RetrofitListener retrofitListener) {
        Call<CardResponce> call = apiServices.getAddCard("add_to_cart", user_id, res_id, item_id, qty, price);
        call.enqueue(new Callback<CardResponce>() {
            @Override
            public void onResponse(Call<CardResponce> call, Response<CardResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CardResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestRemoveFromCard(String user_id, String res_id, String item_id, String qty, String price, final RetrofitListener retrofitListener) {
        Call<CardResponce> call = apiServices.getRemoveItemFromCard("remove_frm_cart", user_id, res_id, item_id, qty, price);
        call.enqueue(new Callback<CardResponce>() {
            @Override
            public void onResponse(Call<CardResponce> call, Response<CardResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CardResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getUpdateAddCard(String cart_id, String quantity, final RetrofitListener retrofitListener) {
        Call<CardResponce> call = apiServices.getUpdateCard("update_cart", cart_id, quantity);
        call.enqueue(new Callback<CardResponce>() {
            @Override
            public void onResponse(Call<CardResponce> call, Response<CardResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CardResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderPlace(String user_id, String res_id, String cust_name, String cust_number, String address, String payment_mode, String txn_id, boolean is_wallet_used, String payable_amount, final RetrofitListener retrofitListener) {
        Call<OrderPlaceResponce> call = apiServices.getOrderPlace("order_place", res_id, user_id, cust_name, cust_number, address, payment_mode, txn_id, is_wallet_used, payable_amount);
        call.enqueue(new Callback<OrderPlaceResponce>() {
            @Override
            public void onResponse(Call<OrderPlaceResponce> call, Response<OrderPlaceResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OrderPlaceResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestResturenList(String lat, String longi, String cityid,String serch, final RetrofitListener retrofitListener) {
        Call<ResturentListResponce> call = apiServices.restaurentList("res_city_order", cityid, lat, longi,serch);
        call.enqueue(new Callback<ResturentListResponce>() {
            @Override
            public void onResponse(Call<ResturentListResponce> call, Response<ResturentListResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<ResturentListResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestCartList(String user_id, final RetrofitListener retrofitListener) {
        Call<CartListResponce> call = apiServices.cartList("cart_list", user_id);
        call.enqueue(new Callback<CartListResponce>() {
            @Override
            public void onResponse(Call<CartListResponce> call, Response<CartListResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CartListResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getrequestRatingList(String user_id, final RetrofitListener retrofitListener) {
        Call<RatingListResponce> call = apiServices.testRating("testRating", user_id);
        call.enqueue(new Callback<RatingListResponce>() {
            @Override
            public void onResponse(Call<RatingListResponce> call, Response<RatingListResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<RatingListResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderDetails(String order_id, final RetrofitListener retrofitListener) {
        Call<OrderDetailsResponce> call = apiServices.orderDetails("order_details", order_id);
        call.enqueue(new Callback<OrderDetailsResponce>() {
            @Override
            public void onResponse(Call<OrderDetailsResponce> call, Response<OrderDetailsResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getrequestAddComment(String rateVal, String role, String restId, String date, String name, String cmt, final RetrofitListener retrofitListener) {
        Call<CommentResponce> call = apiServices.sendComment("addRating", rateVal, role, restId, date, name, cmt);
        call.enqueue(new Callback<CommentResponce>() {
            @Override
            public void onResponse(Call<CommentResponce> call, Response<CommentResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CommentResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderList(String user_id, final RetrofitListener retrofitListener) {
        Call<OrderResponse> call = apiServices.orderList("user_order_history", user_id);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestBestSeller(String lat, String longi, String cityId, final RetrofitListener retrofitListener) {
        Call<BestSellerResponce> call = apiServices.bestSeller("res_best_saller", cityId, lat, longi);
        call.enqueue(new Callback<BestSellerResponce>() {
            @Override
            public void onResponse(Call<BestSellerResponce> call, Response<BestSellerResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<BestSellerResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderHistory(String cityid, final RetrofitListener retrofitListener) {
        Call<OrderHistoryResponce> call = apiServices.orderHistory("user_order_history", cityid);
        call.enqueue(new Callback<OrderHistoryResponce>() {
            @Override
            public void onResponse(Call<OrderHistoryResponce> call, Response<OrderHistoryResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestHomeService(String lat,String lng, String pid, final RetrofitListener retrofitListener) {
        Call<HomeServiceResponce> call = apiServices.homeService("home_service_list", lat,lng, pid);
        call.enqueue(new Callback<HomeServiceResponce>() {
            @Override
            public void onResponse(Call<HomeServiceResponce> call, Response<HomeServiceResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<HomeServiceResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestLoundryDetails(String cityid, final RetrofitListener retrofitListener) {
        Call<LoundryDetailsResponce> call = apiServices.loundryDetails("laundry_details", cityid);
        call.enqueue(new Callback<LoundryDetailsResponce>() {
            @Override
            public void onResponse(Call<LoundryDetailsResponce> call, Response<LoundryDetailsResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<LoundryDetailsResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestCartListLoundry(String userId, final RetrofitListener retrofitListener) {
        Call<CartListLoundryResponce> call = apiServices.cartListLoundry("cart_list", userId);
        call.enqueue(new Callback<CartListLoundryResponce>() {
            @Override
            public void onResponse(Call<CartListLoundryResponce> call, Response<CartListLoundryResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<CartListLoundryResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestAddCardLoundry(String user_id, String dhobi_id, String item_id, String qty, String price, String item_opr, final RetrofitListener retrofitListener) {
        Call<LondryCardResponce> call = apiServices.getAddCardLoundry("add_to_cart", user_id, dhobi_id, item_id, qty, price, item_opr);
        call.enqueue(new Callback<LondryCardResponce>() {
            @Override
            public void onResponse(Call<LondryCardResponce> call, Response<LondryCardResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<LondryCardResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestRemoveCardLoundry(String user_id, String dhobi_id, String item_id, String qty, String price, String item_opr, final RetrofitListener retrofitListener) {
        Call<LondryCardResponce> call = apiServices.getAddCardLoundry("remove_frm_cart", user_id, dhobi_id, item_id, qty, price, item_opr);
        call.enqueue(new Callback<LondryCardResponce>() {
            @Override
            public void onResponse(Call<LondryCardResponce> call, Response<LondryCardResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<LondryCardResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderPlaceLoundry(String user_id, String dhobi_id, String cust_name, String cust_mobile, String cust_address, String payment_mode, String txtid, boolean is_wallet_used, final RetrofitListener retrofitListener) {
        Call<OrderPlaceLoundryResponce> call = apiServices.getOrderPlaceLoundry("order_place", dhobi_id, user_id, cust_name, cust_mobile, cust_address, payment_mode, txtid, is_wallet_used);
        call.enqueue(new Callback<OrderPlaceLoundryResponce>() {
            @Override
            public void onResponse(Call<OrderPlaceLoundryResponce> call, Response<OrderPlaceLoundryResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OrderPlaceLoundryResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderDetailsLoundry(String order_id, final RetrofitListener retrofitListener) {
        Call<OrderDetailsLoundryResponce> call = apiServices.orderDetailsLoundry("order_details", order_id);
        call.enqueue(new Callback<OrderDetailsLoundryResponce>() {
            @Override
            public void onResponse(Call<OrderDetailsLoundryResponce> call, Response<OrderDetailsLoundryResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsLoundryResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestRestOrderItemList(String ordId, final RetrofitListener retrofitListener) {
        Call<ItemListResponce> call = apiServices.getItemList("order_details", ordId);
        call.enqueue(new Callback<ItemListResponce>() {
            @Override
            public void onResponse(Call<ItemListResponce> call, Response<ItemListResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<ItemListResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderItemList(String ordId, final RetrofitListener retrofitListener) {
        Call<ItemListLoundryResponce> call = apiServices.getItemListLoundry("order_details", ordId);
        call.enqueue(new Callback<ItemListLoundryResponce>() {
            @Override
            public void onResponse(Call<ItemListLoundryResponce> call, Response<ItemListLoundryResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<ItemListLoundryResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestOrderHistoryLoundry(String cityid, final RetrofitListener retrofitListener) {
        Call<LaundryOrderListResponse> call = apiServices.orderHistoryLoundry("user_order_history", cityid);
        call.enqueue(new Callback<LaundryOrderListResponse>() {
            @Override
            public void onResponse(Call<LaundryOrderListResponse> call, Response<LaundryOrderListResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<LaundryOrderListResponse> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestSubServiceLoundry(String lat, String longi, String pid, final RetrofitListener retrofitListener) {
        Call<SubServiceLoundryResponce> call = apiServices.subServiceLoundry("list_laundry_city_wise", pid, lat, longi);
        call.enqueue(new Callback<SubServiceLoundryResponce>() {
            @Override
            public void onResponse(Call<SubServiceLoundryResponce> call, Response<SubServiceLoundryResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<SubServiceLoundryResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }


    public void getRequestLoundryMenu(String ldid, String typeOne, String typeTwo, final RetrofitListener retrofitListener) {
        Call<LoundryMenuResponce> call = apiServices.loundryMenu("laundry_details", ldid, typeOne, typeTwo);
        call.enqueue(new Callback<LoundryMenuResponce>() {
            @Override
            public void onResponse(Call<LoundryMenuResponce> call, Response<LoundryMenuResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<LoundryMenuResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getRequestResturentDetails(String pid, final RetrofitListener retrofitListener) {
        Call<ResturentDetailsResponce> call = apiServices.resturentDetails("res_details", pid);
        call.enqueue(new Callback<ResturentDetailsResponce>() {
            @Override
            public void onResponse(Call<ResturentDetailsResponce> call, Response<ResturentDetailsResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }
            }

            @Override
            public void onFailure(Call<ResturentDetailsResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getTrendingFood(String lat, String longi, String cityId, final RetrofitListener retrofitListener) {
        Call<TrendingFoodResponce> call = apiServices.trendingFood("res_menu_city_trend", cityId, lat, longi);
        call.enqueue(new Callback<TrendingFoodResponce>() {
            @Override
            public void onResponse(Call<TrendingFoodResponce> call, Response<TrendingFoodResponce> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess((Object) response.body(), Constants.ApiFlags.GET_SOMETHING);
                }

            }

            @Override
            public void onFailure(Call<TrendingFoodResponce> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }


}
