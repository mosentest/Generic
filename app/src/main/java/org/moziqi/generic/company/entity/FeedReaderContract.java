package org.moziqi.generic.company.entity;

/**
 * Created by moziqi on 2015/2/22 0022.
 */
public final class FeedReaderContract extends BaseContract {

    private String feedId;

    private String title;

    private String subtitle;


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumn {
        public static final String TABLE_NAME = "tb_feed";
        public static final String COLUMN_NAME_FEED_ID = "feed_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
