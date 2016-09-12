package com.glooory.huaban.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class PinsSimpleBean implements Parcelable {

    /**
     * pin_id : 670997406
     * user_id : 16211815
     * board_id : 18966393
     * file_id : 93950547
     * file : {"farm":"farm1","bucket":"hbimg","key":"9c53f8d22b15c0f10449c805fe26d5ac36a50a591a73f-MlJd1p","type":"image/jpeg","height":"1290","frames":"1","width":"860"}
     * media_type : 0
     * source : yxpjw.net
     * link : http://yxpjw.net/luyilu/2015/0811/1798_3.html
     * raw_text : 李丽莎
     * text_meta : {}
     * via : 670100202
     * via_user_id : 12976474
     * original : 614457129
     * created_at : 1459763878
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * orig_source : http:/
     * is_private : 0/images.yxpjw.net:8818/allimg/150811/152J1E34-7.jpg
     */

    private int pin_id;
    private int user_id;
    private int board_id;
    private int file_id;
    /**
     * farm : farm1
     * bucket : hbimg
     * key : 9c53f8d22b15c0f10449c805fe26d5ac36a50a591a73f-MlJd1p
     * type : image/jpeg
     * height : 1290
     * frames : 1
     * width : 860
     */

    private FileBean file;
    private int media_type;
    private String source;
    private String link;
    private String raw_text;
    private int via;
    private int via_user_id;
    private int original;
    private int created_at;
    private int like_count;
    private int comment_count;
    private int repin_count;
    private int is_private;
    private String orig_source;

    public int getPin_id() {
        return pin_id;
    }

    public void setPin_id(int pin_id) {
        this.pin_id = pin_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public FileBean getFile() {
        return file;
    }

    public void setFile(FileBean file) {
        this.file = file;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
    }

    public int getVia() {
        return via;
    }

    public void setVia(int via) {
        this.via = via;
    }

    public int getVia_user_id() {
        return via_user_id;
    }

    public void setVia_user_id(int via_user_id) {
        this.via_user_id = via_user_id;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getRepin_count() {
        return repin_count;
    }

    public void setRepin_count(int repin_count) {
        this.repin_count = repin_count;
    }

    public int getIs_private() {
        return is_private;
    }

    public void setIs_private(int is_private) {
        this.is_private = is_private;
    }

    public String getOrig_source() {
        return orig_source;
    }

    public void setOrig_source(String orig_source) {
        this.orig_source = orig_source;
    }

    public static class FileBean implements Parcelable {
        private String farm;
        private String bucket;
        private String key;
        private String type;
        private String height;
        private String frames;
        private String width;

        public String getFarm() {
            return farm;
        }

        public void setFarm(String farm) {
            this.farm = farm;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getFrames() {
            return frames;
        }

        public void setFrames(String frames) {
            this.frames = frames;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.farm);
            dest.writeString(this.bucket);
            dest.writeString(this.key);
            dest.writeString(this.type);
            dest.writeString(this.height);
            dest.writeString(this.frames);
            dest.writeString(this.width);
        }

        public FileBean() {
        }

        protected FileBean(Parcel in) {
            this.farm = in.readString();
            this.bucket = in.readString();
            this.key = in.readString();
            this.type = in.readString();
            this.height = in.readString();
            this.frames = in.readString();
            this.width = in.readString();
        }

        public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
            @Override
            public FileBean createFromParcel(Parcel source) {
                return new FileBean(source);
            }

            @Override
            public FileBean[] newArray(int size) {
                return new FileBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pin_id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.board_id);
        dest.writeInt(this.file_id);
        dest.writeParcelable(this.file, flags);
        dest.writeInt(this.media_type);
        dest.writeString(this.source);
        dest.writeString(this.link);
        dest.writeString(this.raw_text);
        dest.writeInt(this.via);
        dest.writeInt(this.via_user_id);
        dest.writeInt(this.original);
        dest.writeInt(this.created_at);
        dest.writeInt(this.like_count);
        dest.writeInt(this.comment_count);
        dest.writeInt(this.repin_count);
        dest.writeInt(this.is_private);
        dest.writeString(this.orig_source);
    }

    public PinsSimpleBean() {
    }

    protected PinsSimpleBean(Parcel in) {
        this.pin_id = in.readInt();
        this.user_id = in.readInt();
        this.board_id = in.readInt();
        this.file_id = in.readInt();
        this.file = in.readParcelable(FileBean.class.getClassLoader());
        this.media_type = in.readInt();
        this.source = in.readString();
        this.link = in.readString();
        this.raw_text = in.readString();
        this.via = in.readInt();
        this.via_user_id = in.readInt();
        this.original = in.readInt();
        this.created_at = in.readInt();
        this.like_count = in.readInt();
        this.comment_count = in.readInt();
        this.repin_count = in.readInt();
        this.is_private = in.readInt();
        this.orig_source = in.readString();
    }

    public static final Parcelable.Creator<PinsSimpleBean> CREATOR = new Parcelable.Creator<PinsSimpleBean>() {
        @Override
        public PinsSimpleBean createFromParcel(Parcel source) {
            return new PinsSimpleBean(source);
        }

        @Override
        public PinsSimpleBean[] newArray(int size) {
            return new PinsSimpleBean[size];
        }
    };
}
