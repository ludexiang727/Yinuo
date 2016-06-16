package com.yinuo.ui.sub.partner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.PartnerRecyclerModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.utils.BitmapUtils;
import com.yinuo.utils.ResUtils;

/**
 * Created by ludexiang on 16/6/16.
 */
public class PartnerOwnCenterActivity extends BaseActivity {
    private TextView mPartnerName;
    private ImageView mPartnerHeader;
    private int mUserId;
    private int mHeaderSize;

    @Override
    protected int getTitleLayout() {
        return R.layout.partner_own_center_title_layout;
    }

    @Override
    protected void initTitleView(View titleView) {
        mPartnerName = (TextView) titleView.findViewById(R.id.partner_own_center_back);
        mPartnerHeader = (ImageView) titleView.findViewById(R.id.partner_own_center_header);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.partner_own_center_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);

        showTitle(true);
        PartnerRecyclerModel model = (PartnerRecyclerModel) intent.getSerializableExtra(Constants.PARTNER_OWN_CENTER);
        mUserId = model.getId();
        mHeaderSize = ResUtils.getDimen(this, R.dimen.partner_own_center_header_wh);
        loadImage(model.getBannerOrImgURL(), mPartnerHeader);
        mPartnerName.setText(model.getPartnerName());
    }

    /** TODO 此处有一问题在PartnerPageFragment已经下载该图片(已放大)
     * 通过ImageLoader 会从 memory catch中获取的图片大小会与PartnerPageFragment图片大小相同
     * 所以显示的时候会出现问题
     * 解决方案: 重新下载图片 (保存两份图片)**/
    @Override
    protected void loadBitmapSuccess(Bitmap bitmap, ImageView imageView) {
        if (bitmap != null) {
            imageView.setImageBitmap(BitmapUtils.circularBitmap(bitmap, mHeaderSize));
        }
    }

    @Override
    protected void loadView(View view) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
    }
}
