package com.example.luozubang.pinglun.adpter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.luozubang.pinglun.R;

import com.example.luozubang.pinglun.dongtai.AppComment;
import com.example.luozubang.pinglun.dongtai.AppReplyComment;
import com.example.luozubang.pinglun.dongtai.NodeTable;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author arryluo
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

   private  List<AppComment> appComments;
    private Context context;

    public MyExpandableListAdapter(Context context, List<AppComment> appComments) {
        this.context = context;
        this.appComments = appComments;
    }

    @Override
    public int getGroupCount() {
        return appComments.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (appComments.get(i).getAppReplyComment() == null) {
            return 0;
        } else {
            return appComments.get(i).getAppReplyComment().size() > 0 ? appComments.get(i).getAppReplyComment().size() : 0;
        }
    }
    boolean isLike = false;
    @Override
    public Object getGroup(int i) {
        return appComments.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return appComments.get(i).getAppReplyComment().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(R.drawable.user_other)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(groupHolder.logo);
        groupHolder.tv_name.setText(appComments.get(groupPosition).getNickname());
        groupHolder.tv_time.setText(appComments.get(groupPosition).getCreatetime());
        groupHolder.tv_content.setText(appComments.get(groupPosition).getContent());
        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLike){
                    isLike = false;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
                }else {
                    isLike = true;
                    //setColorFilter这个可以将图片上色
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
                }
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

       // String replyUser = appComments.get(groupPosition).getAppReplyComment().get(childPosition));
       // String username = appComments.get(groupPosition).getAppReplyComment().get(childPosition).getUsername();
        String formusername = appComments.get(groupPosition).getAppReplyComment().get(childPosition).getFromname();
        String tousername = appComments.get(groupPosition).getAppReplyComment().get(childPosition).getToname();
        childHolder.tv_name.setText(formusername + "回复:"+tousername);

        childHolder.tv_content.setText(appComments.get(groupPosition).getAppReplyComment().get(childPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(AppComment commentDetailBean) {
        if (commentDetailBean != null) {

            appComments.add(commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     *
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(AppReplyComment replyDetailBean, int groupPosition) {
        if (replyDetailBean != null) {
            //Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            if (appComments.get(groupPosition).getAppReplyComment() != null) {
                appComments.get(groupPosition).getAppReplyComment().add(replyDetailBean);
            } else {
                List<AppReplyComment> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                appComments.get(groupPosition).setAppReplyComment(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private ImageView iv_like;

        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
        }
    }

    private class ChildHolder {
        private TextView tv_name, tv_content;

        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }
}
