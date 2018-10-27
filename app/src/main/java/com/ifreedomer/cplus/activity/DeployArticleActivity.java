package com.ifreedomer.cplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ifreedomer.cplus.R;
import com.ifreedomer.cplus.http.center.HttpManager;
import com.ifreedomer.cplus.http.protocol.PayLoad;
import com.ifreedomer.cplus.http.protocol.resp.BlogCategoryResp;
import com.ifreedomer.cplus.manager.GlobalDataManager;
import com.ifreedomer.cplus.util.WidgetUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import io.reactivex.Observable;
import me.gujun.android.taggroup.TagGroup;

import static com.ifreedomer.cplus.activity.BlogCategorySelectActivity.SELECT_KEY;

public class DeployArticleActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int ARTICLE_CATEGORY_REQUEST_CODE = 1;
    public static final int BLOG_CATEGORY_REQUEST_CODE = 2;

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.articleLabelTv)
    TextView articleLabelTv;
    @BindView(R.id.articleLabelTagGroup)
    TagGroup articleLabelTagGroup;
    @BindView(R.id.categoryTv)
    TextView categoryTv;

    @BindView(R.id.appendTv)
    TextView appendTv;
    @BindView(R.id.personalCategoryTagGroup)
    TagContainerLayout personalTagGroup;
    @BindView(R.id.blogCategoryCardView)
    CardView blogCategoryCardView;
    @BindView(R.id.articleCategoryCardView)
    CardView articleCategoryCardView;
    @BindView(R.id.privateSwith)
    SwitchCompat privateSwith;
    @BindView(R.id.blogCategoryTv)
    TextView blogCategoryTv;
    @BindView(R.id.articleCategoryTv)
    TextView articleCategoryTv;
    private List<String> mSelectTagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delopy_article);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        appendTv.setOnClickListener(this);
        personalTagGroup.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                TagView tagView = personalTagGroup.getTagView(position);
                if (mSelectTagList.contains(text)) {
                    tagView.setTagTextColor(getResources().getColor(R.color.tagUnselectColor));
                    tagView.setTagBackgroundColor(getResources().getColor(R.color.colorTranlate));
                    mSelectTagList.remove(text);
                } else {
                    tagView.setTagBackgroundColor(getResources().getColor(R.color.tagSelectColor));
                    tagView.setTagTextColor(getResources().getColor(R.color.whiteColor));
                    mSelectTagList.add(text);
                }

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });

        blogCategoryCardView.setOnClickListener(this);
        articleCategoryCardView.setOnClickListener(this);

    }

    private void initData() {
        Observable<PayLoad<List<BlogCategoryResp>>> blogCatergoryObserver = HttpManager.getInstance().getBlogCatergory(GlobalDataManager.getInstance().getUserInfo().getUserName());
        blogCatergoryObserver.subscribe(listPayLoad -> {
            if (listPayLoad.getCode() == PayLoad.SUCCESS) {
                List<BlogCategoryResp> data = listPayLoad.getData();
                addPersonalTag(data);
            } else {
                WidgetUtil.showSnackBar(DeployArticleActivity.this, listPayLoad.getMessage());
            }
        }, throwable -> WidgetUtil.showSnackBar(DeployArticleActivity.this, throwable.getMessage()));
    }

    private void addPersonalTag(List<BlogCategoryResp> categoryList) {
        List<String> tagList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            tagList.add(categoryList.get(i).getName());
        }
        personalTagGroup.setTags(tagList);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.blog_deploy_menu);
        toolbar.getMenu().findItem(R.id.sendItem).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }


    private void deploy() {


//        DeployBlogContentInfo blogContentInfo = new DeployBlogContentInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appendTv:
                articleLabelTagGroup.submitTag();
                break;
            case R.id.articleCategoryCardView:
                Intent intent = new Intent(this, ArticleCategorySelectActivity.class);
                startActivityForResult(intent, ARTICLE_CATEGORY_REQUEST_CODE);
                break;
            case R.id.blogCategoryCardView:
                intent = new Intent(this, BlogCategorySelectActivity.class);
                startActivityForResult(intent, BLOG_CATEGORY_REQUEST_CODE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARTICLE_CATEGORY_REQUEST_CODE) {
            articleCategoryTv.setText(data.getStringExtra(SELECT_KEY));
        } else if (requestCode == BLOG_CATEGORY_REQUEST_CODE) {
            blogCategoryTv.setText(data.getStringExtra(SELECT_KEY));
        }
    }
}
