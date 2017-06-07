package com.zy.birds.activity;

import java.lang.ref.WeakReference;
import java.util.List;

import com.zy.birds.R;
import com.zy.birds.Model.BirdsDB;
import com.zy.birds.Model.DiscoverData;
import com.zy.birds.Model.Poem;
import com.zy.birds.Model.Relationship;
import com.zy.birds.Model.User;
import com.zy.birds.util.DateUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * 对应discover页的adapter
 */
public class ListViewDCAdapter extends BaseAdapter implements OnClickListener {

	public Context context;
	public BirdsDB bridsDB = BirdsDB.getInstance(context);
	public User loginUser;//拿到当前登录用户
	public static Bitmap loginUserImage;//拿到当前用户头像
	public List<DiscoverData> list;
	MyHandler handler = new MyHandler(this);
	public Callback mCallback;
	public interface Callback {//回调接口，用于判断每个item中的点赞收藏事件
		public void click(View v);
	}
	
	public ListViewDCAdapter(Context context, List<DiscoverData> list,
			Callback mCallback,User loginUser) {
		super();
		this.context = context;
		this.list = list;
		this.mCallback = mCallback;
		this.loginUser = loginUser;
		loginUserImage = LoginActivity.loginUserImage;//拿到当前用户头像
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final List<Poem> poems = BirdsDB.getInstance(context).queryAllPoems();
		int point = list.size()-1-position;
		final DiscoverData data = list.get(point);
		final Poem poem = poems.get(point);
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.lvdiscover_item, null);
			final ViewHolder viewHolder = new ViewHolder();
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
			viewHolder.hImage.setImageBitmap(loginUserImage);
			viewHolder.userName.setText(data.getUserName());
			if (data.getPsource()==1) {
				viewHolder.psource.setText("原");
			} else {
				viewHolder.psource.setText("搬");
			}
			viewHolder.cImage.setImageResource(data.getcImage());
			viewHolder.time.setText(DateUtil.formatDisplayTime(data.getTime()));
			viewHolder.ptitle.setText(data.getPtitle());
			viewHolder.pcontent.setText(data.getPcontent());
			viewHolder.heart.setText(data.getHeart());
			viewHolder.comment.setText(data.getComment());
			viewHolder.star.setText(data.getStar());
			if (data.getHEART_STATUS()==1) {//判断用户对诗的点赞状态，1则表示已点赞，设置对应心型为红色
				Drawable redheart = context.getResources().getDrawable(R.drawable.redheart);
				redheart.setBounds(0, 0, redheart.getMinimumWidth(), redheart.getMinimumHeight());
				viewHolder.heart.setCompoundDrawables(redheart, null, null, null);
				viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
			}else {//判断用户对诗的点赞状态，0则表示已未赞，设置对应心型为无色
				Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
				unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
				viewHolder.heart.setCompoundDrawables(unheart, null, null, null);	
				viewHolder.heart.setTextColor(Color.parseColor("#000000"));
			}
			if (data.getSTAR_STATUS()==1) {//判断用户对诗的收藏状态，1则表示已收藏，设置对应星型为黄色
				Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
				yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
				viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);		
			}else {//判断用户对诗的收藏状态，0则表示已收藏，设置对应星型为无色
				Drawable unstar = context.getResources().getDrawable(R.drawable.star);
				unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
				viewHolder.star.setCompoundDrawables(unstar, null, null, null);	
			}
			//点赞事件监听
			viewHolder.heart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
					if (data.getHEART_STATUS()==1) {
						poem.setPlcount(Integer.parseInt(data.getHeart())-1);
						data.setHeart(String.valueOf(Integer.parseInt(data.getHeart())-1));	
						relationship.setHearted(0);
						relationship.setSdate(DateUtil.getDateString());
						data.setHEART_STATUS(0);
						bridsDB.updatePoem(poem);
						bridsDB.updateRelationship(relationship);						
						viewHolder.heart.setText(data.getHeart());
						if (data.getHEART_STATUS()==0) {
							Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
							unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
							viewHolder.heart.setCompoundDrawables(unheart, null, null, null);
							viewHolder.heart.setTextColor(Color.parseColor("#000000"));
						}else {
							Drawable heart = context.getResources().getDrawable(R.drawable.redheart);
							heart.setBounds(0, 0, heart.getMinimumWidth(), heart.getMinimumHeight());
							viewHolder.heart.setCompoundDrawables(heart, null, null, null);	
							viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
						}
					}else {
						if (relationship==null) {
							relationship = new Relationship(loginUser.getId(), poem.getId(), 1, 0, null);
							bridsDB.addRelationship(relationship);
							poem.setPlcount(Integer.parseInt(data.getHeart())+1);
							data.setHeart(String.valueOf(Integer.parseInt(data.getHeart())+1));	
							bridsDB.updatePoem(poem);
							data.setHEART_STATUS(1);
							viewHolder.heart.setText(data.getHeart());
							if (data.getHEART_STATUS()==1) {
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
							poem.setPlcount(Integer.parseInt(data.getHeart())+1);
							data.setHeart(String.valueOf(Integer.parseInt(data.getHeart())+1));	
							relationship.setHearted(1);
							relationship.setSdate(DateUtil.getDateString());
							data.setHEART_STATUS(1);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);
							viewHolder.heart.setText(data.getHeart());
							if (data.getHEART_STATUS()==1) {
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
			//收藏事件监听
			viewHolder.star.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
					if (data.getSTAR_STATUS()==1) {
						poem.setPscount(Integer.parseInt(data.getStar())-1);
						data.setStar(String.valueOf(Integer.parseInt(data.getStar())-1));	
					    //relationship.setHearted(0);
						relationship.setStared(0);
						relationship.setSdate(DateUtil.getDateString());
						data.setSTAR_STATUS(0);
						bridsDB.updatePoem(poem);
						bridsDB.updateRelationship(relationship);
						viewHolder.star.setText(data.getStar());
						if (data.getSTAR_STATUS()==0) {
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
							poem.setPscount(Integer.parseInt(data.getStar())+1);
							data.setStar(String.valueOf(Integer.parseInt(data.getStar())+1));						
							bridsDB.updatePoem(poem);
							data.setSTAR_STATUS(1);
							viewHolder.star.setText(data.getStar());
							if (data.getSTAR_STATUS()==1) {
								Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
								yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);
							}else {
								Drawable unstar = context.getResources().getDrawable(R.drawable.star);
								unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(unstar, null, null, null);	
							}
						}else {
							poem.setPscount(Integer.parseInt(data.getStar())+1);
							data.setStar(String.valueOf(Integer.parseInt(data.getStar())+1));	
							relationship.setStared(1);
							relationship.setSdate(DateUtil.getDateString());
							data.setSTAR_STATUS(1);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);
							viewHolder.star.setText(data.getStar());		
							if (data.getSTAR_STATUS()==1) {
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
			viewHolder.comment.setOnClickListener(this);
			viewHolder.comment.setTag(position);
			convertView.setTag(viewHolder);
		}else {
			final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
			viewHolder.hImage.setImageBitmap(loginUserImage);
			viewHolder.userName.setText(data.getUserName());
			if (data.getPsource()==1) {
				viewHolder.psource.setText("原");
			} else {
				viewHolder.psource.setText("搬");
			}
			viewHolder.cImage.setImageResource(data.getcImage());
			viewHolder.time.setText(DateUtil.formatDisplayTime(data.getTime()));
			viewHolder.ptitle.setText(data.getPtitle());
			viewHolder.pcontent.setText(data.getPcontent());
			viewHolder.heart.setText(data.getHeart());
			viewHolder.comment.setText(data.getComment());
			viewHolder.star.setText(data.getStar());
			if (data.getHEART_STATUS()==1) {
				Drawable redheart = context.getResources().getDrawable(R.drawable.redheart);
				redheart.setBounds(0, 0, redheart.getMinimumWidth(), redheart.getMinimumHeight());
				viewHolder.heart.setCompoundDrawables(redheart, null, null, null);
				viewHolder.heart.setTextColor(Color.parseColor("#e44549"));
			}else {
				Drawable unheart = context.getResources().getDrawable(R.drawable.heart);
				unheart.setBounds(0, 0, unheart.getMinimumWidth(), unheart.getMinimumHeight());
				viewHolder.heart.setCompoundDrawables(unheart, null, null, null);	
				viewHolder.heart.setTextColor(Color.parseColor("#000000"));
			}
			if (data.getSTAR_STATUS()==1) {
				Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
				yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
				viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);		
			}else {
				Drawable unstar = context.getResources().getDrawable(R.drawable.star);
				unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
				viewHolder.star.setCompoundDrawables(unstar, null, null, null);	
			}
			viewHolder.heart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Relationship relationship = bridsDB.queryRelationshipByUidPid(loginUser.getId(), poem.getId());
					if (data.getHEART_STATUS()==1) {
						poem.setPlcount(Integer.parseInt(data.getHeart())-1);
						data.setHeart(String.valueOf(Integer.parseInt(data.getHeart())-1));	
						relationship.setHearted(0);
						relationship.setSdate(DateUtil.getDateString());
						data.setHEART_STATUS(0);
						bridsDB.updatePoem(poem);
						bridsDB.updateRelationship(relationship);
						viewHolder.heart.setText(data.getHeart());
						if (data.getHEART_STATUS()==0) {
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
							poem.setPlcount(Integer.parseInt(data.getHeart())+1);
							data.setHeart(String.valueOf(Integer.parseInt(data.getHeart())+1));						
							bridsDB.updatePoem(poem);
							data.setHEART_STATUS(1);
							viewHolder.heart.setText(data.getHeart());
							if (data.getHEART_STATUS()==1) {
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
							poem.setPlcount(Integer.parseInt(data.getHeart())+1);
							data.setHeart(String.valueOf(Integer.parseInt(data.getHeart())+1));		
							relationship.setHearted(1);
							relationship.setSdate(DateUtil.getDateString());
							data.setHEART_STATUS(1);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);
							viewHolder.heart.setText(data.getHeart());	
							if (data.getHEART_STATUS()==1) {
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
					if (data.getSTAR_STATUS()==1) {
						poem.setPscount(Integer.parseInt(data.getStar())-1);
						data.setStar(String.valueOf(Integer.parseInt(data.getStar())-1));	
						relationship.setStared(0);
						relationship.setSdate(DateUtil.getDateString());
						data.setSTAR_STATUS(0);
						bridsDB.updatePoem(poem);
						bridsDB.updateRelationship(relationship);					
						viewHolder.star.setText(data.getStar());
						if (data.getSTAR_STATUS()==0) {
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
							poem.setPscount(Integer.parseInt(data.getStar())+1);
							data.setStar(String.valueOf(Integer.parseInt(data.getStar())+1));						
							bridsDB.updatePoem(poem);
							data.setSTAR_STATUS(1);
							viewHolder.star.setText(data.getStar());
							if (data.getSTAR_STATUS()==1) {
								Drawable yellowstar = context.getResources().getDrawable(R.drawable.yellowstar);
								yellowstar.setBounds(0, 0, yellowstar.getMinimumWidth(), yellowstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(yellowstar, null, null, null);							
							}else {
								Drawable unstar = context.getResources().getDrawable(R.drawable.star);
								unstar.setBounds(0, 0, unstar.getMinimumWidth(), unstar.getMinimumHeight());
								viewHolder.star.setCompoundDrawables(unstar, null, null, null);		
							}
						}else {
							poem.setPscount(Integer.parseInt(data.getStar())+1);
							data.setStar(String.valueOf(Integer.parseInt(data.getStar())+1));	
							relationship.setStared(1);
							relationship.setSdate(DateUtil.getDateString());
							data.setSTAR_STATUS(1);
							bridsDB.updatePoem(poem);
							bridsDB.updateRelationship(relationship);
							viewHolder.star.setText(data.getStar());	
							if (data.getSTAR_STATUS()==1) {
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
			viewHolder.comment.setOnClickListener(this);
			viewHolder.comment.setTag(position);
		}
		return convertView;
	}

	class ViewHolder{
		ImageView hImage;
		TextView userName;
		TextView psource;
		ImageView cImage;
		TextView time;
		TextView ptitle;
		TextView pcontent;
		TextView heart;
		TextView comment;
		TextView star;
	}
    
	//响应按钮点击事件,调用子定义接口，并传入View
	@Override
    public void onClick(View v) {
        mCallback.click(v);
    }
	
	static class MyHandler extends Handler{
		WeakReference<ListViewDCAdapter> mDCadapter; 
		MyHandler(ListViewDCAdapter DCadapter) { 
			mDCadapter = new WeakReference<ListViewDCAdapter>(DCadapter); 
		}
		@Override
		public void handleMessage(Message msg) {
//			ListViewDCAdapter thisAdapter = mDCadapter.get();
			switch (msg.what) {
			case 0:
				
				break;

			default:
				break;
			}
		}
	}
}