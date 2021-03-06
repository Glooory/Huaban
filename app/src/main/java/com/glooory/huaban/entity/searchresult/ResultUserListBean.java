package com.glooory.huaban.entity.searchresult;

import com.glooory.huaban.entity.PinsUserBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/18 0018 18:22.
 * 搜索用户返回的实体类
 */
public class ResultUserListBean {


    /**
     * text : 海贼王
     * type : people
     */

    private QueryBean query;
    /**
     * query : {"text":"海贼王","type":"people"}
     * pin_count : 30250
     * board_count : 3761
     * people_count : 267
     * self_pin_count : 0
     * self_board_count : 0
     * users : [{"user_id":18201532,"username":"海贼王男人","urlname":"omlttvq7w4","created_at":1450064476,"avatar":{"id":88506847,"farm":"farm1","bucket":"hbimg","key":"a8d2c4d5f2b9cb0d6b347d4588cab93ac5345358160a-qRRh21","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null,"follower_count":0,"pins":[{},{}]},{"user_id":11321345,"username":"海贼王(----","urlname":"zvm3q50nwb","created_at":1388071777,"avatar":{"id":3813778,"farm":"farm1","bucket":"hbimg","key":"a8fc084887c070dbdd521dc75448f4b276189400807-9mjNWq","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null,"follower_count":0,"pins":[]},{"user_id":13623869,"username":"皇冠海贼王","urlname":"krwhvykgd8","created_at":1400935256,"avatar":{"id":44842486,"farm":"farm1","bucket":"hbimg","key":"aae212dae5a8cf1c12c3b909f21938d3fa5647941db9-Cr1fQL","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":null,"follower_count":79,"pins":[{},{},{}]},{"user_id":13953913,"username":"↖海贼王妃↗","urlname":"rourou6924","created_at":1402757799,"avatar":{"id":44860339,"farm":"farm1","bucket":"hbimg","key":"183596f96f60aa422ed87d9e7e2cef5dbf554080fe4-TELjRv","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null,"follower_count":0,"pins":[]},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int self_pin_count;
    private int self_board_count;
    /**
     * user_id : 18201532
     * username : 海贼王男人
     * urlname : omlttvq7w4
     * created_at : 1450064476
     * avatar : {"id":88506847,"farm":"farm1","bucket":"hbimg","key":"a8d2c4d5f2b9cb0d6b347d4588cab93ac5345358160a-qRRh21","type":"image/jpeg","width":100,"height":100,"frames":1}
     * extra : null
     * follower_count : 0
     * pins : [{},{}]
     */

    private List<PinsUserBean> users;

    public static ResultUserListBean objectFromData(String str) {

        return new Gson().fromJson(str, ResultUserListBean.class);
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getPeople_count() {
        return people_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }

    public int getSelf_pin_count() {
        return self_pin_count;
    }

    public void setSelf_pin_count(int self_pin_count) {
        this.self_pin_count = self_pin_count;
    }

    public int getSelf_board_count() {
        return self_board_count;
    }

    public void setSelf_board_count(int self_board_count) {
        this.self_board_count = self_board_count;
    }

    public List<PinsUserBean> getUsers() {
        return users;
    }

    public void setUsers(List<PinsUserBean> users) {
        this.users = users;
    }

    public static class QueryBean {
        private String text;
        private String type;

        public static QueryBean objectFromData(String str) {

            return new Gson().fromJson(str, QueryBean.class);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
