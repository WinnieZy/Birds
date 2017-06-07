package com.zy.birds.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.zy.birds.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/*
 * 数据库类，对数据库进行操作，包含所有增删查改方法，代码纯手打
 */
public class BirdsDB {

	public static final String DB_NAME = "BirdsDB";
	public static final int VERSION = 2;
	private static BirdsDB bridsDB;
	private SQLiteDatabase db;
	
	private BirdsDB(Context context) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,DB_NAME , null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	/*
	 * 获取BridsDB实例
	 */
	public synchronized static BirdsDB getInstance(Context context) {
		if (bridsDB == null) {
			bridsDB = new BirdsDB(context);
		}
		return bridsDB;
	}
	
	/*
	 * 对User表的操作
	 */
	public boolean addUser(User user) {
		try {
			String sql = "insert into users(uname,upass,uimage,uemail,uphone) values(?,?,?,?,?)";
			db.execSQL(sql,new String[]{user.getUname(),user.getUpass(),user.getUimage(),null,null});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delUser(int id) {
		try {
			String sql = "delete from users where id=?";
			db.execSQL(sql,new String[]{String.valueOf(id)});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateUser(User user) {
		try {
			String sql = "update users set uname=?,upass=?,uimage=?,uemail=?,uphone=? where id=?";
			db.execSQL(sql,new String[]{user.getUname(),user.getUpass(),user.getUimage(),user.getUemail(),user.getUphone(),String.valueOf(user.getId())});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User queryUser(User user) {
		try {
			Cursor cursor = db.rawQuery("select * from users where uname=? and upass=? and uimage=? and uemail=? and uphone=?", 
										new String[]{user.getUname(),user.getUpass(),user.getUimage(),user.getUemail(),user.getUphone()});
			if (cursor.moveToFirst()) {
				do {
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uname = cursor.getString(cursor.getColumnIndex("uname"));
					String upass = cursor.getString(cursor.getColumnIndex("upass"));
					String uimage = cursor.getString(cursor.getColumnIndex("uimage"));
					String uemail = cursor.getString(cursor.getColumnIndex("uemail"));
					String uphone = cursor.getString(cursor.getColumnIndex("uphone"));
					user.setId(id);
					user.setUname(uname);
					user.setUpass(upass);	
					user.setUimage(uimage);
					user.setUemail(uemail);
					user.setUphone(uphone);
				} while (cursor.moveToNext());
			}else {
				return null;
			}
			cursor.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User queryUserByName(String uname) {
		User user = null;
		try {
			Cursor cursor = db.rawQuery("select * from users where uname=?", new String[]{uname});
			if (cursor.moveToFirst()) {
				do {
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String upass = cursor.getString(cursor.getColumnIndex("upass"));
					String uimage = cursor.getString(cursor.getColumnIndex("uimage"));
					String uemail = cursor.getString(cursor.getColumnIndex("uemail"));
					String uphone = cursor.getString(cursor.getColumnIndex("uphone"));
					user = new User(id,uname, upass,uimage,uemail,uphone);				
				} while (cursor.moveToNext());
			}else {
				return null;
			}
			cursor.close();
			return user;
				
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User queryUserByUnameUpass(String uname,String upass) {
		User user = null;
		try {
			Cursor cursor = db.rawQuery("select * from users where uname=? and upass=?", 
										new String[]{uname,upass});
			if (cursor.moveToFirst()) {
				do {
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uimage = cursor.getString(cursor.getColumnIndex("uimage"));
					String uemail = cursor.getString(cursor.getColumnIndex("uemail"));
					String uphone = cursor.getString(cursor.getColumnIndex("uphone"));
					user = new User(id, uname, upass, uimage, uemail, uphone);
				} while (cursor.moveToNext());
			}else {
				return null;
			}
			cursor.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	/*
	 * 对Poem表的操作
	 */
	public boolean addPoem(Poem poem) {
		try {
			String sql = "insert into poems(ptitle,pauthor,pcontent,psource,ppdate,plcount,pccount,pscount)values(?,?,?,?,?,?,?,?)";
			db.execSQL(sql,new String[]{poem.getPtitle(),poem.getPauthor(),poem.getPcontent(),String.valueOf(poem.getPsource()),poem.getPpdate(),
					   String.valueOf(poem.getPlcount()),String.valueOf(poem.getPccount()),String.valueOf(poem.getPscount())});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delPoem(int pid) {
		try {
			String sql = "delete from poems where id=?";
			db.execSQL(sql,new String[]{String.valueOf(pid)});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePoem(Poem poem) {
		try {
			String sql = "update poems set ptitle=?,pauthor=?,pcontent=?,psource=?,ppdate=?,plcount=?,pccount=?,pscount=? where id=?";
			db.execSQL(sql,new String[]{poem.getPtitle(),poem.getPauthor(),poem.getPcontent(),String.valueOf(poem.getPsource()),poem.getPpdate(),
					   String.valueOf(poem.getPlcount()),String.valueOf(poem.getPccount()),String.valueOf(poem.getPscount()),String.valueOf(poem.getId())});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Poem queryPoemById(int pid) {
		Poem poem = null;
		try {
			Cursor cursor = db.rawQuery("select * from poems where id=?", new String[]{String.valueOf(pid)});
			if (cursor.moveToFirst()) {
				do {					
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String pauthor = cursor.getString(cursor.getColumnIndex("pauthor"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					poem = new Poem(pid,ptitle, pauthor, pcontent, psource,ppdate, plcount,pccount,pscount);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return poem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Poem> queryAllPoems() {
		List<Poem> pList = new ArrayList<Poem>();
		try {
			Cursor cursor = db.rawQuery("select * from poems", null);
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String pauthor = cursor.getString(cursor.getColumnIndex("pauthor"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					Poem poem = new Poem(id,ptitle, pauthor, pcontent, psource,ppdate, plcount,pccount,pscount);
					pList.add(poem);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Poem> queryPoemByTitle(String ptitle) {
		List<Poem> pList = new ArrayList<Poem>();
		try {
			Cursor cursor = db.rawQuery("select * from poems where ptitle=?", new String[]{ptitle});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String pauthor = cursor.getString(cursor.getColumnIndex("pauthor"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					Poem poem = new Poem(id,ptitle, pauthor, pcontent, psource,ppdate, plcount,pccount,pscount);
					pList.add(poem);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Poem> queryPoemByAuthor(String pauthor) {
		List<Poem> pList = new ArrayList<Poem>();
		try {
			Cursor cursor = db.rawQuery("select * from poems where pauthor=?", new String[]{pauthor});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					Poem poem = new Poem(id,ptitle, pauthor, pcontent, psource, ppdate, plcount,pccount,pscount);
					pList.add(poem);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Poem> queryPoemByPdate(String ppdate) {
		List<Poem> pList = new ArrayList<Poem>();
		try {
			Cursor cursor = db.rawQuery("select * from poems where ppdate=?", new String[]{ppdate});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String pauthor = cursor.getString(cursor.getColumnIndex("pauthor"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					Poem poem = new Poem(id,ptitle, pauthor, pcontent, psource,ppdate, plcount,pccount,pscount);
					pList.add(poem);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Poem queryPoemByTitleAuthor(String ptitle,String pauthor) {
		Poem poem = null;
		try {
			Cursor cursor = db.rawQuery("select * from poems where ptitle=? and pauthor=?", new String[]{ptitle,pauthor});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					poem = new Poem(id,ptitle, pauthor, pcontent, psource, ppdate, plcount,pccount,pscount);			
				} while (cursor.moveToNext());
			}
			cursor.close();
			return poem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

	public Poem queryPoemByTitleAuthorDate(String ptitle,String pauthor,String ppdate) {
		Poem poem = null;
		try {
			Cursor cursor = db.rawQuery("select * from poems where ptitle=? and pauthor=? and ppdate=? ", new String[]{ptitle,pauthor,ppdate});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					poem = new Poem(id,ptitle, pauthor, pcontent, psource, ppdate, plcount,pccount,pscount);			
				} while (cursor.moveToNext());
			}
			cursor.close();
			return poem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	//查询热门诗歌，就是赞的数量前3位的诗歌
	public List<Poem> querySortPoemByPlcount(int index, int number) {
		String sql = "select * from poems order by plcount desc limit "+index+","+number;
		List<Poem> pList = new ArrayList<Poem>();
		try {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String pauthor = cursor.getString(cursor.getColumnIndex("pauthor"));
					String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
					int psource = cursor.getInt(cursor.getColumnIndex("psource"));
					String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
					int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
					int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
					int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
					Poem poem = new Poem(id,ptitle, pauthor, pcontent, psource, ppdate, plcount,pccount,pscount);
					pList.add(poem);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Poem queryPoemByPosition(int position) {
		Poem poem = null;
		try {
			Cursor cursor = db.rawQuery("select * from poems", null);
			if (cursor.moveToFirst()) {
				for (int i = 1; i < position; i++) {
					cursor.moveToNext();
				}									
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
				String pauthor = cursor.getString(cursor.getColumnIndex("pauthor"));
				String pcontent = cursor.getString(cursor.getColumnIndex("pcontent"));
				int psource = cursor.getInt(cursor.getColumnIndex("psource"));
				String ppdate = cursor.getString(cursor.getColumnIndex("ppdate"));
				int plcount = cursor.getInt(cursor.getColumnIndex("plcount"));
				int pccount = cursor.getInt(cursor.getColumnIndex("pccount"));
				int pscount = cursor.getInt(cursor.getColumnIndex("pscount"));
				poem = new Poem(id,ptitle, pauthor, pcontent, psource, ppdate, plcount,pccount,pscount);			
			}
			cursor.close();
			return poem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	/*
	 * 对Relationship表的操作
	 */
	public boolean addRelationship(Relationship relationship) {
		try {
			String sql = "insert into relationships(uid,pid,hearted,stared,sdate) values(?,?,?,?,?)";
			db.execSQL(sql,new String[]{String.valueOf(relationship.getUid()),String.valueOf(relationship.getPid()),
					String.valueOf(relationship.getHearted()),String.valueOf(relationship.getStared()),relationship.getSdate()});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delRelationship(int rid) {
		try {
			String sql = "delete from relationships where id=?";
			db.execSQL(sql,new String[]{String.valueOf(rid)});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateRelationship(Relationship relationship) {
		try {
			String sql = "update relationships set uid=?,pid=?,hearted=?,stared=?,sdate=? where id=?";
			db.execSQL(sql,new String[]{String.valueOf(relationship.getUid()),String.valueOf(relationship.getPid()),String.valueOf(relationship.getHearted()),
					String.valueOf(relationship.getStared()),relationship.getSdate(),String.valueOf(relationship.getId())});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Relationship queryRelationshipById(int rid) {
		Relationship relationship = null;
		try {
			Cursor cursor = db.rawQuery("select * from relationships where id = ?", new String[]{String.valueOf(rid)});
			if (cursor.moveToFirst()) {
				do {					
					int uid = cursor.getInt(cursor.getColumnIndex("uid"));
					int pid = cursor.getInt(cursor.getColumnIndex("pid"));
					int hearted = cursor.getInt(cursor.getColumnIndex("hearted"));
					int stared = cursor.getInt(cursor.getColumnIndex("stared"));
					String sdate = cursor.getString(cursor.getColumnIndex("sdate"));
					relationship = new Relationship(uid, pid, hearted, stared, sdate);				
				} while (cursor.moveToNext());
			}
			cursor.close();
			return relationship;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Relationship queryRelationshipByUidPid(int uid,int pid) {
		Relationship relationship = null;
		try {
			Cursor cursor = db.rawQuery("select * from relationships where uid = ? and pid = ?", new String[]{String.valueOf(uid),String.valueOf(pid)});
			if (cursor.moveToFirst()) {
				do {		
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					int hearted = cursor.getInt(cursor.getColumnIndex("hearted"));
					int stared = cursor.getInt(cursor.getColumnIndex("stared"));
					String sdate = cursor.getString(cursor.getColumnIndex("sdate"));
					relationship = new Relationship(id,uid, pid, hearted, stared, sdate);				
				} while (cursor.moveToNext());
			}
			cursor.close();
			return relationship;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

	public List<Relationship> queryRelationshipByUidStared(int uid,int stared) {
		List<Relationship> rList = new ArrayList<Relationship>();
		try {
			Cursor cursor = db.rawQuery("select * from relationships where uid = ? and stared = ?", new String[]{String.valueOf(uid),String.valueOf(1)});
			if (cursor.moveToFirst()) {
				do {		
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					int pid = cursor.getInt(cursor.getColumnIndex("pid"));
					int hearted = cursor.getInt(cursor.getColumnIndex("hearted"));
					String sdate = cursor.getString(cursor.getColumnIndex("sdate"));
					Relationship relationship = new Relationship(id,uid, pid, hearted, stared, sdate);				
					rList.add(relationship);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return rList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public List<RelationshipDIY> queryAllRelationships() {
		String sql = "select relationships.id,users.id,poems.id,users.uname,poems.ptitle,relationships.hearted,relationships.stared,relationships.sdate "
					+"from users,poems,relationships "
					+"where users.id = relationships.uid and poems.id = relationships.pid";
		List<RelationshipDIY> pList = new ArrayList<RelationshipDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uname = cursor.getString(cursor.getColumnIndex("uname"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					int hearted = cursor.getInt(cursor.getColumnIndex("hearted"));
					int stared = cursor.getInt(cursor.getColumnIndex("stared"));
					String sdate = cursor.getString(cursor.getColumnIndex("sdate"));
					RelationshipDIY starDIY = new RelationshipDIY(id,uname, ptitle, hearted,stared, sdate);
					pList.add(starDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<RelationshipDIY> queryRelationshipByUname(String uname) {
		String sql = "select relationships.id,users.id,poems.id,users.uname,poems.ptitle,relationships.hearted,relationships.stared,relationships.sdate "
				+"from users,poems,relationships "
				+"where users.id = relationships.uid and poems.id = relationships.pid and users.uname = ?";
		List<RelationshipDIY> pList = new ArrayList<RelationshipDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[]{uname});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					int hearted = cursor.getInt(cursor.getColumnIndex("hearted"));
					int stared = cursor.getInt(cursor.getColumnIndex("stared"));
					String sdate = cursor.getString(cursor.getColumnIndex("sdate"));
					RelationshipDIY relationshipDIY = new RelationshipDIY(id,uname, ptitle, hearted,stared, sdate);
					pList.add(relationshipDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<RelationshipDIY> queryRelationshipByPtitle(String ptitle) {
		String sql = "select relationships.id,users.id,poems.id,users.uname,poems.ptitle,relationships.hearted,relationships.stared,relationships.sdate "
				+"from users,poems,relationships "
				+"where users.id = relationships.uid and poems.id = relationships.pid and poems.ptitle = ?";
		List<RelationshipDIY> pList = new ArrayList<RelationshipDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[]{ptitle});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uname = cursor.getString(cursor.getColumnIndex("uname"));
					int hearted = cursor.getInt(cursor.getColumnIndex("hearted"));
					int stared = cursor.getInt(cursor.getColumnIndex("stared"));
					String sdate = cursor.getString(cursor.getColumnIndex("sdate"));
					RelationshipDIY relationshipDIY = new RelationshipDIY(id,uname, ptitle, hearted, stared, sdate);
					pList.add(relationshipDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 对Comment表的操作
	 */
	public boolean addComment(Comment comment) {
		try {
			String sql = "insert into comments(uid,pid,ccontent,cpdate) values(?,?,?,?)";
			db.execSQL(sql,new String[]{String.valueOf(comment.getUid()),String.valueOf(comment.getPid()),comment.getCcontent(),comment.getCpdate()});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateComment(Comment comment) {
		try {
			String sql = "update comments set uid=?,pid=?,ccontent=?,cpdate=?,where id=?";
			db.execSQL(sql,new String[]{String.valueOf(comment.getUid()),String.valueOf(comment.getPid()),comment.getCcontent(),comment.getCpdate(),String.valueOf(comment.getId())});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delComment(int cid) {
		try {
			String sql = "delete from comments where id=?";
			db.execSQL(sql,new String[]{String.valueOf(cid)});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Comment queryCommentById(int cid) {
		Comment comment = null;
		try {
			Cursor cursor = db.rawQuery("select * from comments where id = ?", new String[]{String.valueOf(cid)});
			if (cursor.moveToFirst()) {
				do {					
					int uid = cursor.getInt(cursor.getColumnIndex("uid"));
					int pid = cursor.getInt(cursor.getColumnIndex("pid"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					String cpdate = cursor.getString(cursor.getColumnIndex("cpdate"));
					comment = new Comment(uid, pid,ccontent, cpdate);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return comment;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CommentDIY> queryAllComments() {
		String sql = "select c.id,u.id,p.id,u.uname,p.ptitle,c.ccontent,c.cpdate "
				+"from users as u,poems as p,comments as c "
				+"where u.id = c.uid and p.id = c.pid";
		List<CommentDIY> cList = new ArrayList<CommentDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uname = cursor.getString(cursor.getColumnIndex("uname"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					String cpdate = cursor.getString(cursor.getColumnIndex("cpdate"));
					CommentDIY commentDIY = new CommentDIY(id,uname, ptitle, ccontent, cpdate);
					cList.add(commentDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return cList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CommentDIY> queryCommentByUname(String uname) {
		String sql = "select c.id,u.id,p.id,u.uname,p.ptitle,c.ccontent,c.cpdate "
				+"from users as u,poems as p,comments as c "
				+"where u.id = c.uid and p.id = c.pid and u.uname = ?";
		List<CommentDIY> cList = new ArrayList<CommentDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[]{uname});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					String cpdate = cursor.getString(cursor.getColumnIndex("cpdate"));
					CommentDIY commentDIY = new CommentDIY(id,uname, ptitle, ccontent, cpdate);
					cList.add(commentDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return cList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CommentDIY> queryCommentByPid(int pid) {
		String sql = "select comments.id,users.uname,poems.ptitle,comments.ccontent,comments.cpdate "
				+"from users,poems,comments "
				+"where users.id = comments.uid and poems.id = comments.pid and poems.id = ?";
		List<CommentDIY> cList = new ArrayList<CommentDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(pid)});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uname = cursor.getString(cursor.getColumnIndex("uname"));
					String ptitle = cursor.getString(cursor.getColumnIndex("ptitle"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					String cpdate = cursor.getString(cursor.getColumnIndex("cpdate"));
					CommentDIY commentDIY = new CommentDIY(id,uname, ptitle, ccontent, cpdate);
					cList.add(commentDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return cList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CommentDIY> queryCommentByPtitle(String ptitle) {
		String sql = "select comments.id,users.uname,poems.ptitle,comments.ccontent,comments.cpdate "
				+"from users,poems,comments "
				+"where users.id = comments.uid and poems.id = comments.pid and poems.ptitle = ?";
		List<CommentDIY> cList = new ArrayList<CommentDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[]{ptitle});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String uname = cursor.getString(cursor.getColumnIndex("uname"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					String cpdate = cursor.getString(cursor.getColumnIndex("cpdate"));
					CommentDIY commentDIY = new CommentDIY(id,uname, ptitle, ccontent, cpdate);
					cList.add(commentDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return cList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CommentDIY> queryCommentByUnamePtitle(String uname,String ptitle) {
		String sql = "select comments.id,users.uname,poems.ptitle,comments.ccontent,comments.cpdate "
				+"from users,poems,comments "
				+"where users.id = comments.uid and poems.id = comments.pid and users.uname = ? and poems.ptitle = ?";
		List<CommentDIY> cList = new ArrayList<CommentDIY>();
		try {
			Cursor cursor = db.rawQuery(sql, new String[]{uname,ptitle});
			if (cursor.moveToFirst()) {
				do {									
					int id = cursor.getInt(cursor.getColumnIndex("id"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					String cpdate = cursor.getString(cursor.getColumnIndex("cpdate"));
					CommentDIY commentDIY = new CommentDIY(id,uname, ptitle, ccontent, cpdate);
					cList.add(commentDIY);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return cList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Comment queryCommentByUidPidCpdate(int uid,int pid,String cpdate) {
		Comment comment = null;
		try {
			Cursor cursor = db.rawQuery("select * from comments where uid = ? and pid=? and cpdate=? ", 
					new String[]{String.valueOf(uid),String.valueOf(pid),cpdate});
			if (cursor.moveToFirst()) {
				do {					
					int cid = cursor.getInt(cursor.getColumnIndex("id"));
					String ccontent = cursor.getString(cursor.getColumnIndex("ccontent"));
					comment = new Comment(cid,uid, pid,ccontent, cpdate);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return comment;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

	/*
	 * 对Today表的操作
	 */
	public boolean addToday(Today today) {
		try {
			String sql = "insert into today(ttitle,tauthor,tcontent,tdate,timage)values(?,?,?,?,?)";
			db.execSQL(sql,new String[]{today.getTtitle(),today.getTauthor(),today.getTcontent(),today.getTdate(),today.getTimage()});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delToday(String tdate) {
		try {
			String sql = "delete from today where tdate=?";
			db.execSQL(sql,new String[]{tdate});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateToday(Today today) {
		try {
			String sql = "update today set ttitle=?,tauthor=?,tcontent=?,tdate=? timage=? where tdate=?";
			db.execSQL(sql,new String[]{today.getTtitle(),today.getTauthor(),today.getTcontent(),today.getTdate(),today.getTimage(),today.getTdate()});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Today queryTodayByTdate(String tdate) {
		Today today = null;
		try {
			Cursor cursor = db.rawQuery("select * from today where tdate=?", new String[]{tdate});
			if (cursor.moveToFirst()) {
				do {		
					String ttitle = cursor.getString(cursor.getColumnIndex("ttitle"));
					String tauthor = cursor.getString(cursor.getColumnIndex("tauthor"));
					String tcontent = cursor.getString(cursor.getColumnIndex("tcontent"));
					String timage = cursor.getString(cursor.getColumnIndex("timage"));
					today = new Today(ttitle,tauthor, tcontent, tdate,timage);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return today;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Today queryAllToday() {
		Today today = null;
		try {
			Cursor cursor = db.rawQuery("select * from today ", null);
			if (cursor.moveToFirst()) {
				do {	
					String tdate = cursor.getString(cursor.getColumnIndex("tdate"));
					String ttitle = cursor.getString(cursor.getColumnIndex("ttitle"));
					String tauthor = cursor.getString(cursor.getColumnIndex("tauthor"));
					String tcontent = cursor.getString(cursor.getColumnIndex("tcontent"));
					String timage = cursor.getString(cursor.getColumnIndex("timage"));
					today = new Today(ttitle,tauthor, tcontent, tdate,timage);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return today;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	/*
	 * 对Weekly表的操作
	 */
	public boolean addWeekly(Weekly weekly) {
		try {
			String sql = "insert into weekly(week,theme,wcontent,wimage)values(?,?,?,?)";
			db.execSQL(sql,new String[]{weekly.getWeek(),weekly.getTheme(),weekly.getWcontent(),weekly.getWimage()});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delWeekly(String week) {
		try {
			String sql = "delete from weekly where week=?";
			db.execSQL(sql,new String[]{week});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateWeekly(Weekly weekly) {
		try {
			String sql = "update weekly set week=?,theme=?,wcontent=?,wimage=? where week=?";
			db.execSQL(sql,new String[]{weekly.getWeek(),weekly.getTheme(),weekly.getWcontent(),weekly.getWimage(),weekly.getWeek()});
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Weekly queryWeeklyByWeek(String week) {
		Weekly weekly = null;
		try {
			Cursor cursor = db.rawQuery("select * from weekly where week=?", new String[]{String.valueOf(week)});
			if (cursor.moveToFirst()) {
				do {					
					String theme = cursor.getString(cursor.getColumnIndex("theme"));
					String wcontent = cursor.getString(cursor.getColumnIndex("wcontent"));
					String wimage = cursor.getString(cursor.getColumnIndex("wimage"));
					weekly = new Weekly(week,theme, wcontent, wimage);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return weekly;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Weekly> queryAllWeeklys() {
		List<Weekly> wList = new ArrayList<Weekly>();
		try {
			Cursor cursor = db.rawQuery("select * from weekly", null);
			if (cursor.moveToFirst()) {
				do {		
					String week = cursor.getString(cursor.getColumnIndex("week"));
					String theme = cursor.getString(cursor.getColumnIndex("theme"));
					String wcontent = cursor.getString(cursor.getColumnIndex("wcontent"));
					String wimage = cursor.getString(cursor.getColumnIndex("wimage"));
					Weekly weekly = new Weekly(week,theme, wcontent, wimage);
					wList.add(weekly);					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return wList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
}