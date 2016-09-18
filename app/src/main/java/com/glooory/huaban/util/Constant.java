package com.glooory.huaban.util;

import com.glooory.huaban.R;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class Constant {

    public static final String EMPTY_STRING = "";

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static final String AUTHORIZATION = "Authorization";

    //token information
    public static final String TOKENACCESS = "TokenAccess";
    public static final String TOKENREFRESH = "TokenRefresh";
    public static final String TOKENTYPE = "TokenType";
    public static final String TOKENEXPIRESIN = "TokenExpiresIn";

    //user information
    public static final String ISLOGIN = "isLogin";
    public static final String LOGINTIME = "loginTime";
    public static final String USERACCOUNT = "userAccount";
    public static final String USERPASSWORD = "userPassword";

    public static final String USERNAME = "userName";
    public static final String USERID = "userID";

    public static final String USERHEADKEY = "userHeadKey";

    public static final String USEREMAIL = "userEmail";

    //board infomation
    public static final String BOARDTITLEARRAY = "boardTitleArray";
    public static final String BOARDIDARRAY = "boardIdArray";

    //,
    public static final String SEPARATECOMMA = ",";

    //search
    public static final String HISTORYTEXT = "historyText";

    //http limit number
    public static final int LIMIT = 20;

    //RxView 防抖动点击时间间隔
    public static final int THROTTDURATION = 300;

    //用户喜欢操作的操作字段
    public static final String OPERATELIKE = "like";
    public static final String OPERATEUNLIKE = "unlike";

    //用户对画板的关注操作字段
    public static final String OPERATEFOLLOW = "follow";
    public static final String OPERATEUNFOLLOW = "unfollow";

    //获得用户画板列表详情的操作符
    public static final String OPERATEBOARDEXTRA = "recommend_tags";
    public static final boolean OPERATECHECK = true;

    //删除画板的操作符
    public static final String OPERATEDELETEBOARD = "DELETE";

    //是否跳过登录界面
    public static final String ISSKIPLOGIN = "skip_login";

    public static final String ISME = "is_me";

    public static final String DATA_ITEM_COUNT = "data_item_count";

    public static final String BOARD_ITEM_BEAN = "board_item_bean";

    public static final String BOARD_ID = "board_id";
    public static final String PIN_COUNT = "pin_count";
    public static final String FOLLOWER_COUNT = "follower_count";

    public static final String AVATAR_URL = "avatar_url";
    public static final String PIN_ID = "pin_id";
    public static final String RATIO = "ratio";

    public static final String SEARCH_KEY_WORD = "search_key_word";

    public static int[] TYPE_ICON_RES_IDS = new int[]{
            R.drawable.ic_ui_ux_24dp, R.drawable.ic_design_24dp, R.drawable.ic_illustration_24dp, R.drawable.ic_home_faliture_24dp,
            R.drawable.ic_apparel_24dp, R.drawable.ic_men_24dp, R.drawable.ic_marriage_24dp, R.drawable.ic_industry_design_24dp,
            R.drawable.ic_photography_24dp, R.drawable.ic_hair_model_24dp, R.drawable.ic_food_drinks_24dp, R.drawable.ic_travling_24dp,
            R.drawable.ic_diy_crafts_24dp, R.drawable.ic_fitness_24dp, R.drawable.ic_kids_24dp, R.drawable.ic_pets_24dp,
            R.drawable.ic_quatos_24dp, R.drawable.ic_famouis_people_24dp, R.drawable.ic_beauty_24dp, R.drawable.ic_gifs_24dp,
            R.drawable.ic_geek_24dp, R.drawable.ic_anime_24dp, R.drawable.ic_archetature_24dp, R.drawable.ic_arts_24dp,
            R.drawable.ic_data_figure_24dp, R.drawable.ic_game_24dp, R.drawable.ic_cars_24dp, R.drawable.ic_film_24dp,
            R.drawable.ic_tips_24dp, R.drawable.ic_education_24dp, R.drawable.ic_sports_24dp, R.drawable.ic_funny_24dp
    };

}
