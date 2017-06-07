package com.zy.birds.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.zy.birds.util.DateUtil;

/*
 * databaseHelper 建表和加载测试数据
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

	public static final String CREATE_USER = "create table users("
			+ "id integer primary key autoincrement,"
			+ "uname text not null,"
			+ "upass text not null,"
			+ "uimage text,"
			+ "uemail text,"
			+ "uphone text)";
	public static final String CREATE_POEM = "create table poems("
			+ "id integer primary key autoincrement,"
			+ "ptitle text not null,"
			+ "pauthor text,"
			+ "pcontent text not null,"
			+ "psource integer,"
			+ "ppdate text,"
			+ "plcount integer,"
			+ "pccount integer,"
			+ "pscount integer)";
	public static final String CREATE_RELATIONSHIP = "create table relationships("
			+ "id integer primary key autoincrement,"
			+ "uid integer not null,"
			+ "pid integer not null,"
			+ "hearted integer not null,"
			+ "stared integer not null,"
			+ "sdate text)";	
	public static final String CREATE_COMMMENT = "create table comments("
			+ "id integer primary key autoincrement,"
			+ "uid integer not null,"
			+ "pid integer not null,"
			+ "ccontent text,"
			+ "cpdate text)";	
	
	public static final String CREATE_TODAY = "create table today("
			+ "ttitle text,"
			+ "tauthor text,"
			+ "tcontent text,"
			+ "tdate text,"
			+ "timage text)";	
	
	public static final String CREATE_WEEKLY = "create table weekly("
			+ "week integer,"
			+ "theme text,"
			+ "wcontent text,"
			+ "wimage text)";		
	
	public Context mContext;

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_POEM);
		db.execSQL(CREATE_RELATIONSHIP);
		db.execSQL(CREATE_COMMMENT);
		db.execSQL(CREATE_TODAY);
		db.execSQL(CREATE_WEEKLY);
		db.execSQL("insert into users(uname,upass,uimage,uemail,uphone) values(?,?,?,?,?)",new String[]{"a","a",null,null,null});
		db.execSQL("insert into poems(ptitle,pauthor,pcontent,psource,ppdate,plcount,pccount,pscount) values(?,?,?,?,?,?,?,?)",
				new String[]{"飞鸟集","a","夏天的飞鸟，飞到我的窗前唱歌，又飞去。"+'\n'+"秋天的黄叶，它们没有什么可唱。"
											+'\n'+"只叹息一声，飞落在那里。"+'\n'+"Stray birds of summer"
											+'\n'+"come to my window to sing and fly away"
											+'\n'+"And yellow leaves of autumn"
											+'\n'+"which have no songs,flutter and fall there...",
											String.valueOf(0),DateUtil.getDateString(),"0","3","0"});
		db.execSQL("insert into poems(ptitle,pauthor,pcontent,psource,ppdate,plcount,pccount,pscount) values(?,?,?,?,?,?,?,?)",
				new String[]{"抉择","a","假如我来世上一遭"+'\n'+"只为与你相聚一次"+'\n'+"只为了亿万光年里的那一刹那"
											+'\n'+"一刹那里所有的甜蜜与悲凄"+'\n'+"那麽就让一切该发生的"+'\n'+"都在瞬间出现吧"
											+'\n'+"我俯首感谢所有星球的相助"+'\n'+"让我与你相遇"+'\n'+"与你别离"+'\n'+"完成了上帝所作的一首诗"
											+'\n'+"然後再缓缓地老去",String.valueOf(0),DateUtil.getDateString(),"0","0","0"});
		db.execSQL("insert into poems(ptitle,pauthor,pcontent,psource,ppdate,plcount,pccount,pscount) values(?,?,?,?,?,?,?,?)",
				new String[]{"梦与诗","a","醉过才知酒浓，"+'\n'+"爱过才知情重，"+'\n'+"你不能做我的诗，"+'\n'+"正如"+'\n'+"我不能做你的梦。",
											String.valueOf(0),DateUtil.getDateString(),"0","0","0"});
		db.execSQL("insert into poems(ptitle,pauthor,pcontent,psource,ppdate,plcount,pccount,pscount) values(?,?,?,?,?,?,?,?)",
				new String[]{"冰心","a","月明之夜的梦呵！"+'\n'+"远呢"+'\n'+"近呢"+'\n'+"但我们只这般不言语"+'\n'+"听——听"
											+'\n'+"这微击心弦的声！"+'\n'+"眼前光雾万重"+'\n'+"柔波如醉呵！"+'\n'+"沉——沉",
											String.valueOf(0),DateUtil.getDateString(),"0","0","0"});
		db.execSQL("insert into poems(ptitle,pauthor,pcontent,psource,ppdate,plcount,pccount,pscount) values(?,?,?,?,?,?,?,?)",
				new String[]{"偶然","a","我是天空里的一片云，"+'\n'+"偶尔投影在你的波心──"+'\n'+"你不必讶异，"+'\n'+"更无须欢喜──"
											+'\n'+"在转瞬间消灭了踪影。"+'\n'+"你我相逢在黑夜的海上，"+'\n'+"你有你的，我有我的，方向；"
											+'\n'+"你记得也好，"+'\n'+"最好你忘掉"+'\n'+"在这交会时互放的光亮！",
											String.valueOf(0),DateUtil.getDateString(),"0","0","0"});		
		db.execSQL("insert into comments(uid,pid,ccontent,cpdate) values(?,?,?,?)",
				new String[]{"1","1","好。。。。。。",DateUtil.getDateString()});
		db.execSQL("insert into comments(uid,pid,ccontent,cpdate) values(?,?,?,?)",
				new String[]{"1","1","fantastic",DateUtil.getDateString()});
		db.execSQL("insert into comments(uid,pid,ccontent,cpdate) values(?,?,?,?)",
				new String[]{"1","1","good..",DateUtil.getDateString()});		
		db.execSQL("insert into today(ttitle,tauthor,tcontent,tdate,timage) values(?,?,?,?,?)",new String[]{"悯农","【唐】李坤",
									"锄禾日当午，汗滴禾下土。谁知盘中餐，粒粒皆辛苦。","8.May.2016",null});
		Toast.makeText(mContext, "Create succeed", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists users");
		db.execSQL("drop table if exists poems");
		db.execSQL("drop table if exists relationships");
		db.execSQL("drop table if exists comments");
		db.execSQL("drop table if exists today");
		db.execSQL("drop table if exists weekly");
		onCreate(db);
	}

}
