package com.qin.loadview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingLayout extends FrameLayout {

    public final static int SUCCESS = 1;
    public final static int EMPTY = 2;
    public final static int ERROR = 3;
    public final static int NONETWORK = 4;
    public final static int LOADING = 5;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private Context mContext;
    private View mLoadingPage;
    private View mErrorPage;
    private View mNoNetWorkPage;
    private View mEmptyPage;
    private View mContentView;
    private View mCustomLoadingView;
    private int mStatus;

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("有且只有一个子控件或布局");
        }
        mContentView = getChildAt(0);
        if (mContentView != null) {
            mContentView.setVisibility(GONE);
        }
        initPage();
        setView();
    }

    /**
     * 初始化View
     */
    private void initPage() {
        mEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.empty_page, null, false);
        mNoNetWorkPage = LayoutInflater.from(mContext).inflate(R.layout.nonetwork_page, null, false);
        mErrorPage = LayoutInflater.from(mContext).inflate(R.layout.error_page, null, false);
        mLoadingPage = LayoutInflater.from(mContext).inflate(R.layout.loading_page, null, false);
        ButterKnife.bind(mEmptyPage);

//        ImageView ivEmpty = mEmptyPage.findViewById(R.id.iv_empty);
//        TextView tvEmpty = mEmptyPage.findViewById(R.id.tv_empty);

    }

    /**
     * 添加到LoadLayout
     */
    private void setView() {
        this.addView(mEmptyPage);
        this.addView(mErrorPage);
        this.addView(mNoNetWorkPage);
        this.addView(mLoadingPage);
    }

    /**
     * 设置显示的布局状态{SUCCESS，EMPTY，ERROR，NONETWORK，LOADING}
     *
     * @param status
     */
    public void setStatus(@Status int status) {
        this.mStatus = status;
        switch (status) {
            case SUCCESS:
                setVisiable(mContentView);
                break;
            case EMPTY:
                setVisiable(mEmptyPage);
                break;
            case ERROR:
                setVisiable(mErrorPage);
                break;
            case NONETWORK:
                setVisiable(mNoNetWorkPage);
                break;
            case LOADING:
                if (mCustomLoadingView != null) {
                    mCustomLoadingView.setVisibility(VISIBLE);
                } else {
                    setVisiable(mLoadingPage);
                }
                break;
            default:
                throw new IllegalArgumentException("请填写正确类型");
        }
    }

    /**
     * 返回当前状态{SUCCESS，EMPTY，ERROR，NONETWORK，LOADING}
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * 显示View
     *
     * @param view
     */
    public void setVisiable(View view) {
        mContentView.setVisibility(GONE);
        mEmptyPage.setVisibility(GONE);
        mErrorPage.setVisibility(GONE);
        mNoNetWorkPage.setVisibility(GONE);
        if (mCustomLoadingView != null) {
            mCustomLoadingView.setVisibility(GONE);
        } else {
            mLoadingPage.setVisibility(GONE);
        }
        view.setVisibility(VISIBLE);
    }

    /**
     * 自定义加载中布局
     *
     * @param view
     */
    public LoadingLayout setLoadingPage(View view) {
        mCustomLoadingView = view;
        this.removeView(mLoadingPage);
        this.addView(view);
        view.setVisibility(GONE);
        return this;
    }

    /**
     * 布局状态码
     */
    @IntDef({SUCCESS, EMPTY, ERROR, NONETWORK, LOADING})
    private @interface Status {
    }
}
