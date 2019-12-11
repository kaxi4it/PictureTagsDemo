package com.guyj.lib_pictag.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;


import com.guyj.lib_pictag.callback.OnSingleClickListener;
import com.guyj.lib_pictag.enums.TagStyleEnum;
import com.guyj.lib_pictag.utils.CalculateUtils;
import com.guyj.lib_pictag.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class PicTagViewGroup extends FrameLayout {

    private int downX,downY=0;
    private int offsetX=0;
    private int offsetY=0;
    private boolean isTouchTag=false;//是否触碰到tag标签
    private boolean isMoveState=false;//tag是否在移动状态
    private PicTagView touchTag;
    private List<PicTagView> tagsAry;
    private LayoutParams mParams;
    private LayoutParams mTouchTagParams;
    private boolean isEditState=false;//tagView是编辑状态 还是 只读状态
    private int width;

    public PicTagViewGroup(Context context) {
        this(context,null);
    }

    public PicTagViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PicTagViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tagsAry=new ArrayList<>();
        width=getMeasuredWidth();
    }

    public void setImageBitmap(Bitmap imageBitmap){
        setBackground(new BitmapDrawable(getResources(),imageBitmap));
    }

    public void isEditState(boolean bool){
        isEditState=bool;
    }

    public boolean getEditState(){
        return isEditState;
    }

    private OnSingleClickListener onSingleClickListener;
    public void setOnSingleClickListener(OnSingleClickListener onSingleClickListener){
        this.onSingleClickListener=onSingleClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int touchX=(int)event.getX();
        int touchY=(int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                    downX = (int)event.getX();
                    downY = (int)event.getY();
                    for (int j = 0; j < getChildCount(); j++) {
                        if (downX>=getChildAt(j).getLeft()&&downX<=getChildAt(j).getRight()&&downY>=getChildAt(j).getTop()&&downY<=getChildAt(j).getBottom()){
                            isTouchTag=true;
                            offsetX=touchX-getChildAt(j).getLeft();
                            offsetY=touchY-getChildAt(j).getTop();
                            touchTag= (PicTagView) getChildAt(j);
                            break;
                        }else {
                        }
                    }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isEditState){
                    if (Math.abs(offsetX)>20||Math.abs(offsetY)>20){
                        isMoveState=true;
                    }
                    if (touchTag!=null){
                        int left=touchX-offsetX;
                        int top=touchY-offsetY;
                        if (left<0){
                            left=0;
                        }
                        if (left+touchTag.getMeasuredWidth()>getMeasuredWidth()){
                            left=getMeasuredWidth()-touchTag.getMeasuredWidth();
                        }
                        if (top<0) {
                            top=0;
                        }
                        if (top+touchTag.getMeasuredHeight()>getMeasuredHeight()){
                            top=getMeasuredHeight()-touchTag.getMeasuredHeight();
                        }

                        mTouchTagParams= (LayoutParams) touchTag.getLayoutParams();
                        mTouchTagParams.gravity = Gravity.LEFT | Gravity.TOP;//窗口位置的偏移量
                        mTouchTagParams.setMargins(
                                left
                                , top
                                ,0
                                ,0
                        );
                        touchTag.setLayoutParams(mTouchTagParams);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isEditState){
                    if (!isMoveState&&touchTag!=null){
                        touchTag.changeTagStyle();
                    }
                    if (!isMoveState&&!isTouchTag){
                        PicTagView picTagView = new PicTagView(getContext()).setContent("aaaaaa");
                        mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        if (downX<width/2){//画布左侧
                            mParams.gravity = Gravity.LEFT | Gravity.TOP;//窗口位置的偏移量
                            picTagView.setTagStyleEnum(TagStyleEnum.PIC_TAG_STYLE_RIGHT_TEXT);
                            mParams.setMargins(downX, downY - dp2px(15), 0, 0);
                        }else {
                            mParams.gravity = Gravity.RIGHT | Gravity.TOP;//窗口位置的偏移量
                            picTagView.setTagStyleEnum(TagStyleEnum.PIC_TAG_STYLE_LEFT_TEXT);
                            mParams.setMargins(0, downY - dp2px(15), width-downX, 0);
                        }
                        addView(picTagView, mParams);
                        tagsAry.add(picTagView);
                    }
                    touchTag=null;
                    isMoveState=false;
                    isTouchTag=false;
                }else {
                    if (isTouchTag&&touchX>=touchTag.getLeft()&&touchX<=touchTag.getRight()&&touchY>=touchTag.getTop()&&touchY<=touchTag.getBottom()){
                            onSingleClickListener.onClick(touchTag);
                    }
                    touchTag=null;
                    isMoveState=false;
                    isTouchTag=false;
                }
                break;
        }
            return true;
    }

    /**
     * 屏幕尺寸/图片宽度 = 系数
     * left坐标
     * @param content
     * @param tagStyleEnum
     * @param left
     * @param top
     * @param picWidth
     * @param picHeight
     */
    public void addPicTag(String content,String type,TagStyleEnum tagStyleEnum,int left,int top,int picWidth,int picHeight){
        String xishu=CalculateUtils.divide(ScreenUtils.getScreenWidth()+"",picWidth+"");
        String x=CalculateUtils.multiply(xishu,left+"");
        String y=CalculateUtils.multiply(xishu,top+"");
        String xx=CalculateUtils.multiply(xishu,picWidth+"");
        String yy=CalculateUtils.multiply(xishu,picHeight+"");
        PicTagView picTagView = new PicTagView(getContext()).setContent(content).setType(type);
        mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mParams.gravity = Gravity.LEFT | Gravity.TOP;//窗口位置的偏移量
        picTagView.setTagStyleEnum(tagStyleEnum);
        int mLeft=Float.valueOf(x).intValue();
        int mTop=Float.valueOf(y).intValue();
        if (mLeft+dp2px(90)>Float.valueOf(xx).intValue()){
            mLeft=Float.valueOf(xx).intValue()-dp2px(90);
        }
        if (mTop+dp2px(30)>Float.valueOf(yy).intValue()){
            mTop=Float.valueOf(yy).intValue()-dp2px(30);
        }
        mParams.setMargins(mLeft,mTop, 0, 0);
        addView(picTagView, mParams);
        tagsAry.add(picTagView);

    }

    private int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
