package com.guyj.lib_pictag.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.guyj.lib_pictag.R;
import com.guyj.lib_pictag.enums.TagStyleEnum;

import java.util.HashMap;

public class PicTagView extends ConstraintLayout {

    private TagStyleEnum tagStyleEnum=TagStyleEnum.PIC_TAG_STYLE_RIGHT_TEXT;//样式
    private Group group_left,group_right;//左右侧隐藏显示用的组
    private TextView tv_left_content,tv_right_content;//左右侧的文字
    private String content="";//文本内容
    private String type="";//标签类型
    private HashMap mParams;//自定义参数key value

    public PicTagView(Context context) {
        this(context,null);
    }

    public PicTagView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PicTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);

    }

    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_pic_tags,this,true);
        tv_left_content=findViewById(R.id.tv_pic_tag_content_left);
        group_left=findViewById(R.id.group_pic_tag_left_text);
        tv_right_content=findViewById(R.id.tv_pic_tag_content_right);
        group_right=findViewById(R.id.group_pic_tag_right_text);
        tv_left_content.setText(content);
        tv_right_content.setText(content);
        setGroupVisible();
    }

    public TagStyleEnum getTagStyleEnum() {
        return tagStyleEnum;
    }

    public PicTagView setTagStyleEnum(TagStyleEnum tagStyleEnum) {
        this.tagStyleEnum = tagStyleEnum;
        setGroupVisible();
        return this;
    }

    public String getContent() {
        return content;
    }

    public PicTagView setContent(String content) {
        this.content = content;
        if (tv_left_content!=null&&tv_right_content!=null){
            tv_left_content.setText(content);
            tv_right_content.setText(content);
        }
        return this;
    }

    public String getType(){
        return type;
    }

    public PicTagView setType(String type){
        this.type=type;
        return this;
    }

    public HashMap getParams(){
        return mParams;
    }

    public PicTagView setParams(HashMap mParams){
        this.mParams=mParams;
        return this;
    }

    public PicTagView changeTagStyle(){
        switch (tagStyleEnum){
            case PIC_TAG_STYLE_LEFT_TEXT:
                tagStyleEnum=TagStyleEnum.PIC_TAG_STYLE_RIGHT_TEXT;
                break;
            case PIC_TAG_STYLE_RIGHT_TEXT:
                tagStyleEnum=TagStyleEnum.PIC_TAG_STYLE_LEFT_TEXT;
                break;
        }
        setGroupVisible();
        return this;
    }

    private void setGroupVisible(){
        switch (tagStyleEnum){
            case PIC_TAG_STYLE_LEFT_TEXT:
                group_left.setVisibility(VISIBLE);
                group_right.setVisibility(GONE);
                break;
            case PIC_TAG_STYLE_RIGHT_TEXT:
                group_right.setVisibility(VISIBLE);
                group_left.setVisibility(GONE);
                break;
        }
    }

}
