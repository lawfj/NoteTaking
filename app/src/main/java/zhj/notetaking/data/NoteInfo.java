package zhj.notetaking.data;

import cn.bmob.v3.BmobObject;
import zhj.notetaking.domain.User;

/**
 * Created by HongJay on 2016/8/16.
 */
public class NoteInfo extends BmobObject{
    private String note;
    private String time;
    private String uuid;
    private String uid;
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NoteInfo() {
        super();
        this.setTableName("Note");
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
