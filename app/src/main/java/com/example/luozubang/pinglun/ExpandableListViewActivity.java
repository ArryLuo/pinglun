package com.example.luozubang.pinglun;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.luozubang.pinglun.adpter.MyExpandableListAdapter;

import com.example.luozubang.pinglun.dongtai.AppComment;
import com.example.luozubang.pinglun.dongtai.AppReplyComment;
import com.example.luozubang.pinglun.dongtai.NodeTable;
import com.example.luozubang.pinglun.util.DateUtil;
import com.example.luozubang.pinglun.view.CommentExpandableListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
/**
 * @author arryluo
 */
public class ExpandableListViewActivity extends AppCompatActivity {

    private CommentExpandableListView expandableListView;
    private NodeTable commentList;
    private MyExpandableListAdapter adapter;
    boolean isCollection_id=true;//收藏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandabledata);
        initview();
    }

    private NodeTable getdata() {

        NodeTable nodeTable = new NodeTable();
        nodeTable.setContent("大雨天去东湖骑车   幸得见梅花盛开");
        nodeTable.setCreate_time("2018-03-06 15:13:23");
        nodeTable.setDianzansize(3);
        nodeTable.setUid("9e256ba3f1ca42d0982efe6adb7de794");
        nodeTable.setUsername("黄*");
        nodeTable.setUimg("http://ov51d64rm.bkt.clouddn.com/1505750143567.jpg");
        List<Uri> fujianlist = new ArrayList<>();
        fujianlist.add(Uri.parse("http://ov51d64rm.bkt.clouddn.com/1520322723929.jpg"));
        nodeTable.setFujian(fujianlist);//添加附件
        //添加评论
        List<AppComment> appComments = new ArrayList<>();
        AppComment appComment = new AppComment();
        appComment.setContent("好美");
        appComment.setCreatetime("2018-03-06 15:16:46");
        appComment.setImg("http://ov51d64rm.bkt.clouddn.com/1505750163137.jpg");
        appComment.setNickname("爱你");
        appComment.setUid("6bcc5e8e6e7945f0a5c55142cd7e6d7b");

        List<AppReplyComment> appReplyCommentList = new ArrayList<>();//回复评论
        AppReplyComment appReplyComment1 = new AppReplyComment();
        appReplyComment1.setContent("梅花♧");
        appReplyComment1.setCreatetime("2018-03-06 15:18:34");
        appReplyComment1.setFrouimg("http://ov51d64rm.bkt.clouddn.com/1505028187929.jpg");
        appReplyComment1.setFromname("dsa");
        appReplyComment1.setFormuid("71cf1d3fc3334612af39a519de69037b");
        appReplyComment1.setToname("爱你");
        appReplyComment1.setTouid("6bcc5e8e6e7945f0a5c55142cd7e6d7b");
        appReplyComment1.setTouimg("http://ov51d64rm.bkt.clouddn.com/1505750163137.jpg");
        appReplyCommentList.add(appReplyComment1);
        AppReplyComment appReplyComment2 = new AppReplyComment();
        appReplyComment2.setContent("是吧  我也觉得  我拍的很好看   哈哈");
        appReplyComment2.setCreatetime("2018-03-06 15:22:56");
        appReplyComment2.setFrouimg("http://ov51d64rm.bkt.clouddn.com/1505750143567.jpg");
        appReplyComment2.setFromname("黄*");
        appReplyComment2.setFormuid("9e256ba3f1ca42d0982efe6adb7de794");
        appReplyComment2.setToname("爱你");
        appReplyComment2.setTouid("6bcc5e8e6e7945f0a5c55142cd7e6d7b");
        appReplyComment2.setTouimg("http://ov51d64rm.bkt.clouddn.com/1505750163137.jpg");
        appReplyCommentList.add(appReplyComment2);
        appComment.setAppReplyComment(appReplyCommentList);
        appComments.add(appComment);
        nodeTable.setAppComment(appComments);
        return nodeTable;
    }

    private void initview() {
        expandableListView = findViewById(R.id.detail_page_lv_comment);
        expandableListView.setGroupIndicator(null);
        commentList = getdata();
        //当前的详情
        CircleImageView detail_page_userLogo= findViewById(R.id.detail_page_userLogo);
        TextView detail_page_userName=findViewById(R.id.detail_page_userName);
        TextView detail_page_time=findViewById(R.id.detail_page_time);
        TextView detail_page_story=findViewById(R.id.detail_page_story);
        detail_page_userName.setText(commentList.getUsername());
        detail_page_time.setText(commentList.getCreate_time());
        detail_page_story.setText(commentList.getContent());

        Glide.with(this).load(commentList.getUimg())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(detail_page_userLogo);
        //默认展开所有回复
        adapter = new MyExpandableListAdapter(this, commentList.getAppComment());
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.getAppComment().size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(ExpandableListViewActivity.this, "点击了回复", Toast.LENGTH_SHORT).show();
                treedReplyDialog(groupPosition, childPosition);
                return true;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });
        //点击底部进行评论
        findViewById(R.id.detail_page_do_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downdilog();
            }
        });
      final ImageView imageView=  findViewById(R.id.collection_id);
        findViewById(R.id.collection_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCollection_id){
                    isCollection_id = false;
                    imageView.setColorFilter(Color.parseColor("#FF0000"));
                }else{
                    isCollection_id=true;
                    imageView.setColorFilter(Color.parseColor("#aaaaaa"));
                }
            }
        });
    }
    private void downdilog(){
        final BottomSheetDialog   dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    AppComment detailBean = new AppComment();
                    detailBean.setNickname("小红");
                    detailBean.setContent(commentContent);
                    detailBean.setCreatetime(DateUtil.getTime());
                    adapter.addTheCommentData(detailBean);
                    Toast.makeText(ExpandableListViewActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ExpandableListViewActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }
    //第三级的评论
    private void treedReplyDialog(final int position, final int childPosition) {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);

        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentList.getAppComment().get(position).getAppReplyComment().get(childPosition).getFromname() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    AppReplyComment detailBean = new AppReplyComment();
                    detailBean.setFromname("小红");
                    detailBean.setContent(replyContent);
                    detailBean.setToname(commentList.getAppComment().get(position).getAppReplyComment().get(childPosition).getFromname());
                    adapter.addTheReplyData(detailBean, position);

                    expandableListView.expandGroup(position);
                    Toast.makeText(ExpandableListViewActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExpandableListViewActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position) {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentList.getAppComment().get(position).getNickname() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    AppReplyComment detailBean = new AppReplyComment();
                    detailBean.setContent(replyContent);
                    detailBean.setFromname("小红");
                    detailBean.setToname(commentList.getAppComment().get(position).getNickname());
                    adapter.addTheReplyData(detailBean, position);
                    expandableListView.expandGroup(position);
                    Toast.makeText(ExpandableListViewActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExpandableListViewActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


}
