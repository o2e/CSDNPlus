package com.ifreedomer.cplus.adapter;

import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ifreedomer.cplus.BlogContentActivity;
import com.ifreedomer.cplus.R;
import com.ifreedomer.cplus.entity.BlogContentInfo;
import com.ifreedomer.cplus.http.protocol.resp.HistoryResp;
import com.ifreedomer.cplus.util.DateUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class HistoryAdapter extends BaseQuickAdapter<HistoryResp, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, @Nullable List<HistoryResp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryResp item) {
        helper.setText(R.id.titleTv, item.getTitle());
        helper.setText(R.id.summaryTv, item.getDescription());
        try {
            helper.setText(R.id.dateTv, DateUtil.convertToMonth(mContext, item.getPostTime()));
        } catch (Exception e) {
            helper.setVisible(R.id.dateTv, false);
            e.printStackTrace();
        }
//        Glide.with((View) helper.getView(R.id.avatarIv)).load(item.get()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into((ImageView) helper.getView(R.id.avatarIv));
        helper.setOnClickListener(R.id.rootRelayout, v -> {

            Intent intent = new Intent(mContext, BlogContentActivity.class);
            intent.putExtra(BlogContentActivity.DATA, BlogContentInfo.convert(item));
            mContext.startActivity(intent);
//            Intent intent = new Intent(mContext, BlogContentActivity.class);
//            intent.putExtra(BlogContentActivity.USER_NAME_KEY, item.getUsername());
//            String id = item.getBizId();
//            if (id.contains("&")) {
//                id = id.split("&")[0];
//            }
//            intent.putExtra(BlogContentActivity.ARTICLE_ID_KEY, id + "");
//            intent.putExtra(BlogContentActivity.TITLE_KEY, item.getTitle() + "");
//            mContext.startActivity(intent);
        });
    }
}
