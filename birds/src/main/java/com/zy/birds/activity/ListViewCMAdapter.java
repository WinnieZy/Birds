package com.zy.birds.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.Comment;
import com.zy.birds.Model.CommentData;
import com.zy.birds.Model.DiscoverData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
import com.zy.birds.Model.User;
import com.zy.birds.util.DateUtil;
import com.zy.birds.util.ImageUtil;
import com.zy.birds.util.ToastUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * DetailActivity 页面对应listviewAdapter，用于加载对应诗及诗评
 */
public class ListViewCMAdapter extends BaseAdapter implements OnClickListener{

	public Context context;
	public BirdsDB bridsDB = BirdsDB.getInstance(context);;
	public List<CommentData> clist = new ArrayList<CommentData>();
	Poem poem = null;
	DiscoverData discoverData = null;
	public static User loginUser;
	public static Bitmap loginUserImage;
	public Callback mCallback;
	public interface Callback{//回调接口
		public void click(View v);
	}

	public ListViewCMAdapter(Context context, List<CommentData> clist,
			DiscoverData discoverData, Callback mCallback) {
		super();
		this.context = context;
		this.clist = clist;
//		this.poem = poem;
		this.discoverData = discoverData;
		this.mCallback = mCallback;
		loginUser = LoginActivity.loginUser;
		loginUserImage = LoginActivity.loginUserImage;
	}
	
	@Override
	public int getCount() {
		return clist.size()+2;
	}

	@Override
	public Object getItem(int position) {
		return clist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		poem = bridsDB.queryPoemById(discoverData.getDpid());
		if (convertView==null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			final ViewHolder viewHolder = new ViewHolder();
			if (position==0) {//position=0则加载对应诗的discoverData类型数据
				convertView = inflater.inflate(R.layout.lvdiscover_item, null);				
				viewHolder.hImage = (ImageView) convertView.findViewById(R.id.iv_dc_head);
				viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_dc_name);
				viewHolder.psource = (TextView) convertView.findViewById(R.id.tv_dc_psource);
				viewHolder.cImage = (ImageView) convertView.findViewById(R.id.iv_dc_clock);
				viewHolder.time = (TextView) convertView.findViewById(R.id.tv_dc_time);
				viewHolder.ptitle = (TextView) convertView.findViewById(R.id.tv_dc_pt);
				viewHolder.pcontent = (TextView) convertView.findViewById(R.id.tv_dc_poem);
				viewHolder.heart = (TextView) convertView.findViewById(R.id.tv_dc_heart);
				viewHolder.comment = (TextView) convertView.findViewById(R.id.tv_dc_comment);
				viewHolder.star = (TextView) convertView.findViewById(R.id.tv_dc_star);
//				viewHolder.hImage.setImageResource(R.id.iv_dc_head);
//				viewHolder.userName.setText(poem.getPauthor());
//				viewHolder.cImage.setImageResource(R.id.iv_dc_clock);
//				viewHolder.time.setText(DateUtil.formatDisplayTime(poem.getPpdate()));
//				viewHolder.ptitle.setText(poem.getPtitle());
//				viewHolder.pcontent.setText(poem.getPcontent());
//				viewHolder.heart.setText(String.valueOf(poem.getPlcount()));
//				viewHolder.comment.setText(String.valueOf(poem.getPccount()));
//				viewHolder.star.setText(String.valueOf(poem.getPscount()));
				viewHolder.hImage.setImageBitmap(loginUserImage);
				viewHolder.userName.setText(discoverData.getUserName());
				if (discoverData.getPsource()==1) {
					viewHolder.psource.setText("原");
				} else {
					viewHolder.psource.setText("搬");
				}
				viewHolder.cImage.setImageResource(discoverData.getcImage());
				viewHolder.time.setText(DateUtil.formatDisplayTime(discoverData.getTime()));
				viewHolder.ptitle.setText(discoverData.getPtitle());
				viewHolder.pcontent.setText(discoverData.getPcontent());
				viewHolder.heart.setText(discoverData.getHeart());
				viewHolder.comment.setText(discoverData.getComment());
				viewHolder.star.setText(discoverData.getStar());
				if (discoverData.getHEART_STATUS()==1) {//获取当前用户对当前对应诗歌的点赞状态
					Drawable redheart = context.getResources().getDrawable(R.drawable.redheart);
					redheart.setBounds(0, 0, redheart.getMinimumWidth(), redheart.getMinimumHeight());
					viewHolder.heart.setCompoundDrawables(redheart, null, null, null);
					viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
				}
				if (discoverData.getSTAR_STATUS()==1) {//获取当前用户对当前对应诗歌的收藏状态
					Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
					yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
					viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);		
				}
				//点赞监听
				viewHolder.heart.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
						if (discoverData.getHEART_STATUS()==1) {
							poem.setPlcount(Integer.parseInt(discoverData.getHeart())-1);
							relationship.setHearted(0);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);								
							viewHolder.heart.setText(String.valueOf(poem.getPlcount()));
							if (relationship.getHearted()==0) {
								Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
								unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
								viewHolder.heart.setCompoundDrawables(unheart, null, null, null);
								viewHolder.heart.setTextColor(Color.parseColor("#000000"));
							}
						}else {
							if (relationship==null) {
								relationship = new Relationship(loginUser.getId(), poem.getId(), 1, 0, null);
								bridsDB.addRelationship(relationship);
								poem.setPlcount(Integer.parseInt(discoverData.getHeart())+1);
								bridsDB.updatePoem(poem);
								viewHolder.heart.setText("1");
								if (relationship.getHearted()==1) {
									Drawable heart = context.getResources().getDrawable(R.drawable.redheart);
									heart.setBounds(0, 0, heart.getMinimumWidth(), heart.getMinimumHeight());
									viewHolder.heart.setCompoundDrawables(heart, null, null, null);
									viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
								}
							}else {
								poem.setPlcount(Integer.parseInt(discoverData.getHeart())+1);
								relationship.setHearted(1);
								bridsDB.updatePoem(poem);
								bridsDB.updateRelationship(relationship);								
								viewHolder.heart.setText(String.valueOf(poem.getPlcount()));
								if (relationship.getHearted()==1) {
									Drawable heart = context.getResources().getDrawable(R.drawable.redheart);
									heart.setBounds(0, 0, heart.getMinimumWidth(), heart.getMinimumHeight());
									viewHolder.heart.setCompoundDrawables(heart, null, null, null);
									viewHolder.heart.setTextColor(Color.parseColor("#e44549"));	
								}
							}
						}
					}
				});
				//收藏监听
				viewHolder.star.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
						if (discoverData.getSTAR_STATUS()==1) {
							poem.setPscount(Integer.parseInt(discoverData.getStar())-1);
							relationship.setHearted(0);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);						
							viewHolder.star.setText(String.valueOf(poem.getPscount()));
							if (relationship.getStared()==0) {
								Drawable unstar = context.getResources().getDrawable(R.drawable.star);
								unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(unstar, null, null, null);		
							}
						}else {
							if (relationship==null) {
								relationship = new Relationship(loginUser.getId(), poem.getId(), 0, 1, DateUtil.getDateString());
								bridsDB.addRelationship(relationship);
								poem.setPscount(Integer.parseInt(discoverData.getStar())+1);
								bridsDB.updatePoem(poem);
								viewHolder.star.setText(String.valueOf(poem.getPscount()));
								if (relationship.getStared()==1) {
									Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
									yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
									viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);
								}
							}else {
								poem.setPscount(Integer.parseInt(discoverData.getStar())+1);
								relationship.setStared(1);
								bridsDB.updatePoem(poem);
								bridsDB.updateRelationship(relationship);
								viewHolder.star.setText(String.valueOf(poem.getPscount()));		
								if (relationship.getStared()==1) {
									Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
									yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
									viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);	
								}
							}		
						}
					}
				});
				convertView.setTag(viewHolder);	
			} else if (position==1) {//position=1诗加载评论区
				convertView = inflater.inflate(R.layout.comment_item, null);
				viewHolder.cmEditText = (EditText) convertView.findViewById(R.id.et_comment);
				viewHolder.comment = (TextView) convertView.findViewById(R.id.tv_cm_comment);
				viewHolder.sendImageButton = (ImageButton) convertView.findViewById(R.id.btn_sendcm);
//				viewHolder.cmEditText.setMinLines(5);
				viewHolder.sendImageButton.setImageResource(R.drawable.btn_sendcm);
				convertView.setTag(viewHolder);	
				viewHolder.sendImageButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String text = viewHolder.cmEditText.getText().toString();
						if (text.equals("")) {
							ToastUtil.showMessage(context, "评论内容不能为空");
						} else {
							int uid = loginUser.getId();
							int pid = poem.getId();
							String cpdate = DateUtil.getDateString();
							Comment cm = new Comment(uid, pid, text, cpdate);
							bridsDB.addComment(cm);
							if (bridsDB.queryCommentByUidPidCpdate(uid, pid, cpdate)!=null) {
								ToastUtil.showMessage(context, "评论成功");
//								Drawable commentImage = context.getResources().getDrawable(R.drawable.greencomment);
//								commentImage.setBounds(0, 0, commentImage.getMinimumWidth(), commentImage.getMinimumHeight());
//								viewHolder.comment.setCompoundDrawables(commentImage, null, null, null);
								poem.setPccount(poem.getPccount()+1);
								bridsDB.updatePoem(poem);
//								viewHolder.comment.setText(poem.getPccount());
							} else {
								ToastUtil.showMessage(context, "评论失败，请重试");
							}			
						}	
						mCallback.click(v);
					}
				});
				viewHolder.sendImageButton.setTag(position);
			}else {//否则加载诗歌所有的评论内容
				int point = clist.size()-position+1;
				CommentData data = clist.get(point);
				if (point>=0) {
					convertView = inflater.inflate(R.layout.lvcomment_item, null);
					viewHolder.headImage = (ImageView) convertView.findViewById(R.id.iv_cm_headbg);
					viewHolder.uName = (TextView) convertView.findViewById(R.id.tv_cm_name);
					viewHolder.comment = (TextView) convertView.findViewById(R.id.tv_cm_comment);
					viewHolder.clockImage = (ImageView) convertView.findViewById(R.id.iv_cm_clock);
					viewHolder.date = (TextView) convertView.findViewById(R.id.tv_cm_date);
					viewHolder.headImage.setImageResource(data.getHeadImage());
					viewHolder.uName.setText(data.getuName());
					viewHolder.comment.setText(data.getComment());
					viewHolder.clockImage.setImageResource(data.getClockImage());
					viewHolder.date.setText(data.getDate());
					convertView.setTag(viewHolder);	
					point--;
				}
			}
			
		} else {
			if (position==0) {
				final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
//				viewHolder.hImage.setImageResource(R.id.iv_dc_head);
//				viewHolder.userName.setText(poem.getPauthor());
//				viewHolder.cImage.setImageResource(R.id.iv_dc_clock);
//				viewHolder.time.setText(DateUtil.formatDisplayTime(poem.getPpdate()));
//				viewHolder.ptitle.setText(poem.getPtitle());
//				viewHolder.pcontent.setText(poem.getPcontent());
//				viewHolder.heart.setText(String.valueOf(poem.getPlcount()));
//				viewHolder.comment.setText(String.valueOf(poem.getPccount()));
//				viewHolder.star.setText(String.valueOf(poem.getPscount()));
				viewHolder.hImage.setImageBitmap(loginUserImage);
				viewHolder.userName.setText(discoverData.getUserName());
				if (discoverData.getPsource()==1) {
					viewHolder.psource.setText("原");
				} else {
					viewHolder.psource.setText("搬");
				}
				viewHolder.cImage.setImageResource(discoverData.getcImage());
				viewHolder.time.setText(DateUtil.formatDisplayTime(discoverData.getTime()));
				viewHolder.ptitle.setText(discoverData.getPtitle());
				viewHolder.pcontent.setText(discoverData.getPcontent());
				viewHolder.heart.setText(discoverData.getHeart());
				viewHolder.comment.setText(discoverData.getComment());
				viewHolder.star.setText(discoverData.getStar());
				if (discoverData.getHEART_STATUS()==1) {
					Drawable redheart = context.getResources().getDrawable(R.drawable.redheart);
					redheart.setBounds(0, 0, redheart.getMinimumWidth(), redheart.getMinimumHeight());
					viewHolder.heart.setCompoundDrawables(redheart, null, null, null);
					viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
				}
				if (discoverData.getSTAR_STATUS()==1) {
					Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
					yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
					viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);		
				}
				viewHolder.heart.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
						if (discoverData.getHEART_STATUS()==1) {
							poem.setPlcount(Integer.parseInt(discoverData.getHeart())-1);
							discoverData.setHeart(String.valueOf(Integer.parseInt(discoverData.getHeart())-1));	
							relationship.setHearted(0);
							discoverData.setHEART_STATUS(0);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);
							viewHolder.heart.setText(discoverData.getHeart());
							if (discoverData.getHEART_STATUS()==0) {
								Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
								unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
								viewHolder.heart.setCompoundDrawables(unheart, null, null, null);
								viewHolder.heart.setTextColor(Color.parseColor("#000000"));
							}else {
								Drawable redheart = context.getResources().getDrawable(R.drawable.redheart);
								redheart.setBounds(0, 0, redheart.getMinimumWidth(), redheart.getMinimumHeight());
								viewHolder.heart.setCompoundDrawables(redheart, null, null, null);
								viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
							}
						}else {
							if (relationship==null) {
								relationship = new Relationship(loginUser.getId(), poem.getId(), 1, 0, null);
								bridsDB.addRelationship(relationship);
								poem.setPlcount(Integer.parseInt(discoverData.getHeart())+1);
								discoverData.setHeart(String.valueOf(Integer.parseInt(discoverData.getHeart())+1));						
								bridsDB.updatePoem(poem);
								discoverData.setHEART_STATUS(1);
								viewHolder.heart.setText(discoverData.getHeart());
								if (discoverData.getHEART_STATUS()==1) {
									Drawable heart = context.getResources().getDrawable(R.drawable.redheart);
									heart.setBounds(0, 0, heart.getMinimumWidth(), heart.getMinimumHeight());
									viewHolder.heart.setCompoundDrawables(heart, null, null, null);	
									viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
								}else {
									Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
									unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
									viewHolder.heart.setCompoundDrawables(unheart, null, null, null);
									viewHolder.heart.setTextColor(Color.parseColor("#000000"));
								}
							}else {
								poem.setPlcount(Integer.parseInt(discoverData.getHeart())+1);
								discoverData.setHeart(String.valueOf(Integer.parseInt(discoverData.getHeart())+1));		
								relationship.setHearted(1);
								discoverData.setHEART_STATUS(1);
								bridsDB.updatePoem(poem);
								bridsDB.updateRelationship(relationship);
								viewHolder.heart.setText(discoverData.getHeart());	
								if (discoverData.getHEART_STATUS()==1) {
									Drawable heart = context.getResources().getDrawable(R.drawable.redheart);
									heart.setBounds(0, 0, heart.getMinimumWidth(), heart.getMinimumHeight());
									viewHolder.heart.setCompoundDrawables(heart, null, null, null);
									viewHolder.heart.setTextColor(Color.parseColor("#e44549"));	
								}else {
									Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
									unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
									viewHolder.heart.setCompoundDrawables(unheart, null, null, null);
									viewHolder.heart.setTextColor(Color.parseColor("#000000"));
								}
							}
						}					
					}
				});
				viewHolder.star.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
						if (discoverData.getSTAR_STATUS()==1) {
							poem.setPscount(Integer.parseInt(discoverData.getStar())-1);
							discoverData.setStar(String.valueOf(Integer.parseInt(discoverData.getStar())-1));	
							relationship.setStared(0);
							discoverData.setSTAR_STATUS(0);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);					
							viewHolder.star.setText(discoverData.getStar());
							if (discoverData.getSTAR_STATUS()==0) {
								Drawable unstar = context.getResources().getDrawable(R.drawable.star);
								unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(unstar, null, null, null);								
							}else {
								Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
								yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);	
							}
						}else {
							if (relationship==null) {
								relationship = new Relationship(loginUser.getId(), poem.getId(), 0, 1, DateUtil.getDateString());
								bridsDB.addRelationship(relationship);
								poem.setPscount(Integer.parseInt(discoverData.getStar())+1);
								discoverData.setStar(String.valueOf(Integer.parseInt(discoverData.getStar())+1));						
								bridsDB.updatePoem(poem);
								discoverData.setSTAR_STATUS(1);
								viewHolder.star.setText(discoverData.getStar());
								if (discoverData.getSTAR_STATUS()==1) {
									Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
									yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
									viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);							
								}else {
									Drawable unstar = context.getResources().getDrawable(R.drawable.star);
									unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
									viewHolder.star.setCompoundDrawables(unstar, null, null, null);		
								}
							}else {
								poem.setPscount(Integer.parseInt(discoverData.getStar())+1);
								discoverData.setStar(String.valueOf(Integer.parseInt(discoverData.getStar())+1));	
								relationship.setStared(1);
								discoverData.setSTAR_STATUS(1);
								bridsDB.updatePoem(poem);
								bridsDB.updateRelationship(relationship);
								viewHolder.star.setText(discoverData.getStar());	
								if (discoverData.getSTAR_STATUS()==1) {
									Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
									yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
									viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);					
								}else {
									Drawable unstar = context.getResources().getDrawable(R.drawable.star);
									unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
									viewHolder.star.setCompoundDrawables(unstar, null, null, null);		
								}
							}						
						}
					}
				});
			} else if (position==1) {
				final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
//				viewHolder.cmEditText.setMinLines(5);
				viewHolder.sendImageButton.setImageResource(R.drawable.btn_sendcm);
				viewHolder.sendImageButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String text = viewHolder.cmEditText.getText().toString();
						if (text=="") {
							ToastUtil.showMessage(context, "评论内容不能为空");
						} else {
							int uid = loginUser.getId();
							int pid = poem.getId();
							String cpdate = DateUtil.getDateString();
							Comment cm = new Comment(uid, pid, text, cpdate);
							bridsDB.addComment(cm);
							if (bridsDB.queryCommentByUidPidCpdate(uid, pid, cpdate)!=null) {
								ToastUtil.showMessage(context, "评论成功");
//								Drawable commentImage = context.getResources().getDrawable(R.drawable.greencomment);
//								commentImage.setBounds(0, 0, commentImage.getMinimumWidth(), commentImage.getMinimumHeight());
//								viewHolder.comment.setCompoundDrawables(commentImage, null, null, null);
								poem.setPccount(poem.getPccount()+1);
								bridsDB.updatePoem(poem);
//								viewHolder.comment.setText(poem.getPccount());
							} else {
								ToastUtil.showMessage(context, "评论失败，请重试");
							}
						}	
						mCallback.click(v);
					}
				});
				viewHolder.sendImageButton.setTag(position);
			}else {
				int point = clist.size()-position+1;
				CommentData data = clist.get(point);
				if (point>=0) {					
					ViewHolder viewHolder = (ViewHolder)convertView.getTag();
					viewHolder.headImage.setImageResource(data.getHeadImage());
					viewHolder.uName.setText(data.getuName());
					viewHolder.comment.setText(data.getComment());
					viewHolder.clockImage.setImageResource(data.getClockImage());
					viewHolder.date.setText(data.getDate());
					point--;
				}							
			}
		}
		return convertView;
	}

	class ViewHolder{
		ImageView headImage;
		TextView uName;
		TextView comment;
		ImageView clockImage;
		TextView date;
		
		ImageView hImage;
		TextView userName;
		TextView psource;
		ImageView cImage;
		TextView time;
		TextView ptitle;
		TextView pcontent;
		TextView heart;
		TextView comment_num;
		TextView star;
		
		EditText cmEditText;
		ImageButton sendImageButton;
	}
	
	//响应按钮点击事件,调用子定义接口，并传入View
	@Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}
