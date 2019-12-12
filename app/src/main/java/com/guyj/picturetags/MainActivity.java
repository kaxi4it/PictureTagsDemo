package com.guyj.picturetags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.guyj.indicator.CirclePageIndicator;
import com.guyj.lib_pictag.callback.OnSingleClickListener;
import com.guyj.lib_pictag.enums.TagStyleEnum;
import com.guyj.lib_pictag.utils.ScreenUtils;
import com.guyj.lib_pictag.views.PicTagView;
import com.guyj.lib_pictag.views.PicTagViewGroup;

import java.util.ArrayList;

/**
 * 完成的还是比较简单的效果，标签的2种样式，是通过一个布局的隐藏显示实现的切换
 * 标签的长宽等未考虑 等比缩放的说法 处理边界越界的情况 暂时比较死板，默认按标签的最高最宽处理
 * 微调了 circleIndicator 移除了指示器的touch事件，setSupportTouch(false) 默认true
 * v1.1 修复了页面第二次进入时，viewPager高度恢复到xml高度的bug
 */
public class MainActivity extends AppCompatActivity {

    AppCompatActivity mContext=this;
    ViewPager vp_pic_tag;
    CirclePageIndicator cpi_pic_tag;

    private int[] imgHeights;//图片高
    private int screenWidth;//屏幕宽
    private PictureTagBean pictureTagBean;//图文标签实体类
    private PicTagViewGroup picTagViewGroup;//图文标签带背景控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp_pic_tag=findViewById(R.id.vp_pic_tag);
        cpi_pic_tag=findViewById(R.id.cpi_pic_tag);
        //假数据
        initData();
        //屏幕宽
        screenWidth= ScreenUtils.getScreenWidth();
        //图片加载第一张获取图片宽高比例scale 通过比例 乘 屏幕宽 获取图片在手机里的等比高度，传给viewpager做第一页高度
        Glide.with(mContext).asBitmap().load(pictureTagBean.dynamicViewList.get(0).imgUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                float scale = (float) resource.getHeight() / resource.getWidth();
                int defaultHeight = (int) (scale * screenWidth);
                //初始化viewpager
                initViewPager(defaultHeight);
            }
        });
        //缓存viewpager下所有页面，根据图片数改变
        vp_pic_tag.setOffscreenPageLimit(pictureTagBean.dynamicViewList.size());

    }

    /**
     * 假数据
     */
    private void initData() {
        pictureTagBean=new PictureTagBean();
        pictureTagBean.nickname="昵称";
        pictureTagBean.headUrl="";
        pictureTagBean.title="title";
        pictureTagBean.detail="detaildetaildetaildetail";
        pictureTagBean.attentionStatus=1;
        pictureTagBean.type =1;
        pictureTagBean.praiseCount=11;
        pictureTagBean.commentCount=21;
        pictureTagBean.productCount=31;
        pictureTagBean.createTime="";
        pictureTagBean.dynamicViewList=new ArrayList<>();
        //第一张图
        PictureTagBean.DynamicViewList dynamicViewList1 = new PictureTagBean.DynamicViewList();
        dynamicViewList1.id=1;
        dynamicViewList1.imgUrl= "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575865669062&di=ca53b2443293678a867bf2803853ecfa&imgtype=0&src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2015%2F0128%2F8b0f093a8edea9f7e7458406f19098af.jpg";
        dynamicViewList1.width="680";
        dynamicViewList1.height="453";
        dynamicViewList1.dynamicViewProduct=new ArrayList<>();
        //1图1标签
        PictureTagBean.DynamicViewList.DynamicViewProduct dynamicViewProduct1=new PictureTagBean.DynamicViewList.DynamicViewProduct();
        dynamicViewProduct1.dynamicId=1;
        dynamicViewProduct1.dynamicViewId=1;
        dynamicViewProduct1.productId="";
        dynamicViewProduct1.style="1";
        dynamicViewProduct1.title="1商品标题1";
        dynamicViewProduct1.type="1";
        dynamicViewProduct1.xAxis="0";
        dynamicViewProduct1.yAxis="0";
        dynamicViewList1.dynamicViewProduct.add(dynamicViewProduct1);
        //1图2标签
        PictureTagBean.DynamicViewList.DynamicViewProduct dynamicViewProduct2=new PictureTagBean.DynamicViewList.DynamicViewProduct();
        dynamicViewProduct2.dynamicId=2;
        dynamicViewProduct2.dynamicViewId=2;
        dynamicViewProduct2.productId="";
        dynamicViewProduct2.style="2";
        dynamicViewProduct2.title="2商品标题2";
        dynamicViewProduct2.type="2";
        dynamicViewProduct2.xAxis="600";
        dynamicViewProduct2.yAxis="300";
        dynamicViewList1.dynamicViewProduct.add(dynamicViewProduct2);
        //3标签
        PictureTagBean.DynamicViewList.DynamicViewProduct dynamicViewProduct3=new PictureTagBean.DynamicViewList.DynamicViewProduct();
        dynamicViewProduct3.dynamicId=3;
        dynamicViewProduct3.dynamicViewId=3;
        dynamicViewProduct3.productId="";
        dynamicViewProduct3.style="1";
        dynamicViewProduct3.title="3商品标题3";
        dynamicViewProduct3.type="1";
        dynamicViewProduct3.xAxis="180";
        dynamicViewProduct3.yAxis="400";
        dynamicViewList1.dynamicViewProduct.add(dynamicViewProduct3);
        //4标签
        PictureTagBean.DynamicViewList.DynamicViewProduct dynamicViewProduct4=new PictureTagBean.DynamicViewList.DynamicViewProduct();
        dynamicViewProduct4.dynamicId=4;
        dynamicViewProduct4.dynamicViewId=4;
        dynamicViewProduct4.productId="";
        dynamicViewProduct4.style="2";
        dynamicViewProduct4.title="4商品标题4";
        dynamicViewProduct4.type="1";
        dynamicViewProduct4.xAxis="400";
        dynamicViewProduct4.yAxis="370";
        dynamicViewList1.dynamicViewProduct.add(dynamicViewProduct4);
        pictureTagBean.dynamicViewList.add(dynamicViewList1);
        PictureTagBean.DynamicViewList dynamicViewList2 = new PictureTagBean.DynamicViewList();
        dynamicViewList2.id=2;
        dynamicViewList2.imgUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576061078108&di=39f4a2f64d183c5d9eac629fcf67ca98&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fac6eddc451da81cb2f68749d5366d016082431d7.jpg";
        dynamicViewList2.width="1000";
        dynamicViewList2.height="426";
        dynamicViewList2.dynamicViewProduct=new ArrayList<>();
        //2图1标签
        PictureTagBean.DynamicViewList.DynamicViewProduct dynamicViewProduct5=new PictureTagBean.DynamicViewList.DynamicViewProduct();
        dynamicViewProduct5.dynamicId=1;
        dynamicViewProduct5.dynamicViewId=1;
        dynamicViewProduct5.productId="";
        dynamicViewProduct5.style="1";
        dynamicViewProduct5.title="5商品标题5";
        dynamicViewProduct5.type="1";
        dynamicViewProduct5.xAxis="510";
        dynamicViewProduct5.yAxis="110";
        dynamicViewList2.dynamicViewProduct.add(dynamicViewProduct5);
        pictureTagBean.dynamicViewList.add(dynamicViewList2);
        PictureTagBean.DynamicViewList dynamicViewList3 = new PictureTagBean.DynamicViewList();
        dynamicViewList3.id=3;
        dynamicViewList3.imgUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575865669062&di=77f876c43bfff6aafb9c49c15e579804&imgtype=0&src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2014%2F0829%2F372edfeb74c3119b666237bd4af92be5.jpg";
        dynamicViewList3.width="680";
        dynamicViewList3.height="655";
        pictureTagBean.dynamicViewList.add(dynamicViewList3);
        //手机图片等比缩放后的高
        imgHeights=new int[pictureTagBean.dynamicViewList.size()];
    }
    /**
     * 初始化viewpager
     * @param defaultHeight
     */
    private void initViewPager(final int defaultHeight) {
        ViewGroup.LayoutParams vp_pic_tagLayoutParams = vp_pic_tag.getLayoutParams();
        vp_pic_tagLayoutParams.height=defaultHeight;
        vp_pic_tag.setLayoutParams(vp_pic_tagLayoutParams);
        vp_pic_tag.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                //图片数
                return pictureTagBean.dynamicViewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                //添加图文标签核心代码
                PictureTagBean.DynamicViewList dynamicViewList = pictureTagBean.dynamicViewList.get(position);
                //new 图文控件
                final PicTagViewGroup picTagViewGroup = new PicTagViewGroup(mContext);
                //设置不可编辑状态
                picTagViewGroup.isEditState(false);
                //图片加载
                Glide.with(mContext).asBitmap().load(pictureTagBean.dynamicViewList.get(position).imgUrl).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //scale 高/宽的比例
                        float scale = (float) resource.getHeight() / resource.getWidth();
                        //记录等比缩放后的高
                        imgHeights[position] = (int) (scale * screenWidth);
                        //图文标签大布局添加背景图 setBackground
                        picTagViewGroup.setImageBitmap(resource);
                    }
                });
                if (dynamicViewList.dynamicViewProduct!=null){
                    //遍历图片集合
                    for (int i = 0; i < dynamicViewList.dynamicViewProduct.size(); i++) {
                        TagStyleEnum tagStyleEnum;
                        //设置标签样式
                        if (TextUtils.equals("1",dynamicViewList.dynamicViewProduct.get(i).style)){
                            tagStyleEnum=TagStyleEnum.PIC_TAG_STYLE_LEFT_TEXT;
                        }else {
                            tagStyleEnum=TagStyleEnum.PIC_TAG_STYLE_RIGHT_TEXT;
                        }
                        //添加标签进控件，文本内容，标签跳转类型，标签样式，标签x,y坐标，上传者的图片展示宽高
                        picTagViewGroup.addPicTag(dynamicViewList.dynamicViewProduct.get(i).title,dynamicViewList.dynamicViewProduct.get(i).type ,tagStyleEnum,Integer.parseInt(dynamicViewList.dynamicViewProduct.get(i).xAxis),Integer.parseInt(dynamicViewList.dynamicViewProduct.get(i).yAxis),Integer.parseInt(dynamicViewList.width),Integer.parseInt(dynamicViewList.height));
                    }
                }
                //控件内标签的点击时间，返回的是标签view，属性get出来
                picTagViewGroup.setOnSingleClickListener(new OnSingleClickListener() {
                    @Override
                    public void onClick(PicTagView v) {
                        Toast.makeText(mContext,"touch"+v.getContent(),Toast.LENGTH_SHORT).show();
                    }
                });
                //viewpager内布局添加 图文控件
                container.addView(picTagViewGroup);
                return picTagViewGroup;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        //添加小圆点指示器
        cpi_pic_tag.setViewPager(vp_pic_tag);
        cpi_pic_tag.setSupportTouch(false);

        //viewpager滑动时，动态修改高度
        vp_pic_tag.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == imgHeights.length - 1) {
                    return;
                }

                //计算ViewPager现在应该的高度,imgheights[]表示页面高度的数组。
                int height = (int) ((imgHeights[position] == 0 ? defaultHeight : imgHeights[position])
                        * (1 - positionOffset) +
                        (imgHeights[position + 1] == 0 ? defaultHeight : imgHeights[position + 1])
                                * positionOffset);

                //为ViewPager设置高度
                ViewGroup.LayoutParams params = vp_pic_tag.getLayoutParams();
                params.height = height;
                vp_pic_tag.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
