package com.omi.app.utils;

public class Constants {
    public interface TimeOut {
        int IMAGE_UPLOAD_CONNECTION_TIMEOUT = 120;
        int IMAGE_UPLOAD_SOCKET_TIMEOUT = 120;
        int SOCKET_TIME_OUT = 60;
        int CONNECTION_TIME_OUT = 60;
    }

    public interface UrlPath {
        String AUTHENTICATION = "authentication.php";
        String RESTAURANT = "restaurant.php";
        String FORCART = "cart.php";
        String RATTING = "rating1.php";
        String FORLOUNDRY = "laundry.php";
        String WALLET = "common_operations.php";
        String SEND_FCM = "vs_insertKey.php";
    }

    //Need unique flags for all apis in case if hitting multiple apis in same activity/fragment cart.php?
    public interface ApiFlags {
        int GET_SOMETHING = 0;
        int GET_SOMETHING_ELSE = 1;
    }

    public interface ErrorClass {
        String CODE = "code";
        String STATUS = "status";
        String MESSAGE = "message";
        String DEVELOPER_MESSAGE = "developerMessage";
    }

    public interface AppConst {
        String IS_LOGIN = "is_login";
        String LOGIN_ITEMS = "login_items";
        String WALLET_AMOUNT = "wallet_amount";
        String USER_NAME = "user_name";
        String CITY_LOCATION = "city_location";
        String HomePathMain = "http://adminapp.omionline.in/uploads/service/main_img/";
        String HomeService = "http://adminapp.omionline.in/uploads/home_service/";
        String LoundryHome = "http://adminapp.omionline.in/uploads/dhobi/";
        String LoundryHome_item = "http://adminapp.omionline.in/uploads/dhobi_item/";
        String IMAGE_BASE_URL = "http://adminapp.omionline.in/uploads/";
        String RESTAURANT_IMAGE_BASE_URL = "http://adminapp.omionline.in/uploads/restaurant/";
        String USER_LOCATION = "location";
        String FCM_TOKEN = "fcm_token";
        String LAT = "lat";
        String LONGI = "longi";
        String CITY = "city";
        String CART_COUNT = "cart_count";

    }

    public static String res_city_order = "res_city_order";
    public static String currentlat = "currentlat";
    public static String currentlon = "currentlon";
    public static String setdefaultaddress = "setdefaultaddress";
    public static String CITY_LOCATIONSAVE = "CITY_LOCATIONSAVE";
}
