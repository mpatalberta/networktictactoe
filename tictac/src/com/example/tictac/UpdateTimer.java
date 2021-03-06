package com.example.tictac;

import java.util.TimerTask;

import android.content.Context;
import android.widget.Toast;

public class UpdateTimer extends TimerTask {
  public BoardView boardView;
  Context theAct = null;
  UpdateTimer(Context theAct)
  {
	  this.theAct = theAct;
  }
  
  @Override
  public void run() {
    GameService.getInstance().startGame( 0 );


    

    boardView.post(
        new Runnable()
	    { 
	    	public void run()
	    	{ 
	    		
	    		String sres = null;
	    	    MainActivity mact;
	    	    mact = (MainActivity)theAct;
	    	 
	    	    if(mact.theInstance.winner == CheckWinner.WINNER_TIE)
	    	    {
	    	    	sres = "Tie game";
	    	    }
	    	    
	    	    if(mact.theInstance.winner == CheckWinner.WINNER_X)
	    	    {
	    	    	sres = "X won game";
	    	    }
	    	    
	    	    if(mact.theInstance.winner == CheckWinner.WINNER_0)
	    	    {
	    	    	sres = "O won game";
	    	    }	    		
	    		
	    		boardView.SetToast(sres);
	    		boardView.invalidate(); 
	    	} 
    	}
    );
    
    
    
  }
}
