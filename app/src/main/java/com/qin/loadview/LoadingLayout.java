package com.qin.loadview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingLayout extends FrameLayout implements View.OnClickListener {

    public final static int SUCCESS = 1;
    public final static int EMPTY = 2;
    public final static int ERROR = 3;
    public final static int NONETWORK = 4;
    public final static int LOADING = 5;

    private Context mContext;
    private View mLoadingPage;
    private View mErrorPage;
    private View mNoNetWorkPage;
    private View mEmptyPage;
    private View mContentView;
    private View mCustomLoadingView;
    private int mStatus;
    private OnReloadListener mListener;
    private ImageView ivEmpty;
    private TextView tvEmpty;
    private TextView tvErrorReload;
    private TextView tvError;
    private ImageView ivError;
    private ImageView ivNonetwork;
    private TextView tvNonetwork;
    private TextView tvNonetworkReload;



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

        ivEmpty = mEmptyPage.findViewById(R.id.iv_empty);
        tvEmpty = mEmptyPage.findViewById(R.id.tv_empty);

        tvError = mErrorPage.findViewById(R.id.tv_error);
        ivError = mErrorPage.findViewById(R.id.iv_error);
        tvErrorReload = mErrorPage.findViewById(R.id.tv_error_reload);
        tvErrorReload.setOnClickListener(this);

        ivNonetwork = mNoNetWorkPage.findViewById(R.id.iv_nonetwork);
        tvNonetwork = mNoNetWorkPage.findViewById(R.id.tv_nonetwork);
        tvNonetworkReload = mNoNetWorkPage.findViewById(R.id.tv_nonetwork_reload);
        tvNonetworkReload.setOnClickListener(this);

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
    private void setVisiable(View view) {
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

    public LoadingLayout setEmptyText(String text) {
        tvEmpty.setText(text);
        return this;
    }

    public LoadingLayout setEmptyImg(@DrawableRes int id) {
        ivEmpty.setImageResource(id);
        return this;
    }

    public LoadingLayout setErrorImg(@DrawableRes int id) {
        ivError.setImageResource(id);
        return this;
    }

    public LoadingLayout setErrorText(String text) {
        tvError.setText(text);
        return this;
    }

    public LoadingLayout setNoNetworkText(String text) {
        tvNonetwork.setText(text);
        return this;
    }

    public LoadingLayout setNoNetworkImg(@DrawableRes int id) {
        ivNonetwork.setImageResource(id);
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_error_reload:
                if (mListener != null)
                    mListener.onReload(view);
                break;
            case R.id.tv_nonetwork_reload:
                if (mListener != null)
                    mListener.onReload(view);
                break;
        }
    }

    public interface OnReloadListener {
        void onReload(View v);
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.mListener = onReloadListener;
    }

    /**
     * 布局状态码
     */
    @IntDef({SUCCESS, EMPTY, ERROR, NONETWORK, LOADING})
    private @interface Status {
    }
}
