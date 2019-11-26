package fall.out.wanandroid.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Owen on 2019/11/18
 */
public class SearchHistoryBean extends LitePalSupport {
    private long id=0;
    private String key ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
