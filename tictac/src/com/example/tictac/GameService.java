package com.example.tictac;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;
import android.util.Log;

public class GameService implements Runnable
{
  private static GameService _instance = new GameService();
  public static String TAG = "GameService";
  public int[][] positions = new int[][] { 
      { 0, 0, 0 },
      { 0, 0, 0 },
      { 0, 0, 0 }
  };
  Thread t;
  
  public void startThread()
  {
	  LOG.v(TAG,"Thread Starting");
      t=new Thread (this);
      bRunning = true;
      t. start ( );
      LOG.v(TAG,"Thread Started");
  }

  public boolean bRunning=true;
  public void endThread()
  {
	  bRunning = false;
  }
  static private String mTicTacToeServiceAddress = "192.168.1.71";
  public String setServiceAddress(String tictactoeServiceAddress)
  {
	  mTicTacToeServiceAddress = tictactoeServiceAddress;
	  return mTicTacToeServiceAddress;
  }
  public String getTicTacToeServiceAddres()
  {
	  return mTicTacToeServiceAddress;
  }
  public static GameService getInstance() {
    return _instance;
  }

  private void updatePositions( Document doc ) {
    for( int x = 0; x < 3; x++ ) {
      for( int y = 0; y < 3; y++ ) {
        positions[x][y] = 0;
      }
    }
    doc.getDocumentElement().normalize();
    NodeList items = doc.getElementsByTagName("move");
    for (int i=0;i<items.getLength();i++){
      Element me = (Element)items.item(i);
      int x = Integer.parseInt( me.getAttribute("x") );
      int y = Integer.parseInt( me.getAttribute("y") );
      int color = Integer.parseInt( me.getAttribute("color") );
      positions[x][y] = color;
    }
    UpdateWinnerStatus();
  }
  public int game_game;
  public boolean bStartGame = false;
  public int winner=CheckWinner.WINNER_NONE;
  
  public int startGame(int game)
  {
	  winner = UpdateWinnerStatus();  
	  if(winner==CheckWinner.WINNER_NONE)
	  {
		  game_game = game;
		  bStartGame = true;
	  }
	  return winner;
  }
  
  private int UpdateWinnerStatus()
  {
	  winner = CheckWinner.CheckForWinner(positions);
	  if(winner == CheckWinner.WINNER_NONE)
	  {
		  LOG.v(TAG,"TIC_TAC_TOE:NO_WINNER");
	  }
	  else
	  {
		  if(winner == CheckWinner.WINNER_TIE)
		  {	  
		   LOG.v(TAG,"TIC_TAC_TOE:TIE");
		  }
		  else if (winner == CheckWinner.WINNER_X)
		  {
			  LOG.v(TAG,"TIC_TAC_TOE:WINNER_X");  
		  }
		  else if (winner == CheckWinner.WINNER_0)
		  {
			  LOG.v(TAG,"TIC_TAC_TOE:WINNER_0");  
		  }
		  else
		  {
			  LOG.v(TAG,"Unexepected State"+winner);
		  }
	  }
	  return winner;
  }

public void startGameASync( int game ) {
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://"+mTicTacToeServiceAddress+"/ttt/moves.php");

    try {
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
      nameValuePairs.add(new BasicNameValuePair("game", Integer.toString(game)));
      
      HttpEntity entity;
      entity = new UrlEncodedFormEntity(nameValuePairs);
      httppost.setEntity(entity);
      String responseString = EntityUtils.toString(entity, "UTF-8");
      LOG.v(TAG,responseString);
      
      httppost.setEntity(entity);

      HttpResponse response = httpclient.execute(httppost);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      updatePositions( db.parse(response.getEntity().getContent()) );
    
    } 
    catch (Exception e) {
      Log.v(TAG,"ioexception:"+ e.toString());
    }
  }
  public int pos_game,pos_x,pos_y,pos_color;
  public boolean bSetPos = false;
  
  public void setPosition(int game, int x, int y, int color)
  {
	  pos_game = game;
	  pos_x = x;
	  pos_y = y;
	  pos_color = color;
	  bSetPos = true;
  }
  public void setPositionASync( int game, int x, int y, int color ) {
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://"+mTicTacToeServiceAddress+"/ttt/move.php");

    positions[x][y] = color;

    try {
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
      nameValuePairs.add(new BasicNameValuePair("game", Integer.toString(game)));
      nameValuePairs.add(new BasicNameValuePair("x", Integer.toString(x)));
      nameValuePairs.add(new BasicNameValuePair("y", Integer.toString(y)));
      nameValuePairs.add(new BasicNameValuePair("color", Integer.toString(color)));
      HttpEntity entity;
      entity = new UrlEncodedFormEntity(nameValuePairs);
      httppost.setEntity(entity);
      String responseString = EntityUtils.toString(entity, "UTF-8");
      LOG.v(TAG,responseString);
      
      
      HttpResponse response = httpclient.execute(httppost);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      updatePositions( db.parse(response.getEntity().getContent()) );
    } catch (Exception e) {
      LOG.v(TAG, "ioexception:"+e.toString());
    }
  }
  
  
  @Override
  public void run()
  {
	  boolean bUsed = false;
	  LOG.v(TAG,"runningthread");
		  while(bRunning = true)
		  {
			  
			  if(bUsed == false)
			  {
				  if(bSetPos==true)
				  {
					  bUsed = true;
					  bSetPos = false;
					  LOG.v(TAG, "in runnable thread calling setPositionASync");
					  setPositionASync( pos_game, pos_x, pos_y,pos_color);
					  LOG.v(TAG, "in runnable thread called setPositionASync");
					  UpdateWinnerStatus();
					  bUsed = false;
					  
				  }
				  if(bStartGame==true)
				  {
					  bUsed = true;
					  bStartGame = false;
					  LOG.v(TAG,"in runnable thread calling startGameASync");
					  startGameASync(game_game); 
					  LOG.v(TAG,"in runnable thread called startGameASync");
					  UpdateWinnerStatus();
					  bUsed = false;
				  }
			  }
		  }
	  
  }


}
