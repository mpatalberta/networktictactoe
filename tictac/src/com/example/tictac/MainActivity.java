package com.example.tictac;

import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
	
	public GameService  theInstance = GameService.getInstance();
	
	
  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      LOG.set(true,true, true, true,false);
      setContentView(R.layout.activity_main);
      
      theInstance.setServiceAddress("10.0.1.5");
      theInstance.startThread();
      Button playx = (Button)this.findViewById(R.id.playx);
      playx.setOnClickListener( this );

      Button playo = (Button)this.findViewById(R.id.playo);
      playo.setOnClickListener( this );

      Timer timer = new Timer();
      UpdateTimer ut = new UpdateTimer(this);
      ut.boardView = (BoardView)this.findViewById(R.id.bview);
      timer.schedule( ut, 200, 200 );
    }

    public void onClick(View v) {
    BoardView board = (BoardView)this.findViewById(R.id.bview);
    
    GameService gm;
    gm = theInstance;
      if ( v.getId() == R.id.playx ) {
       if(gm.winner == CheckWinner.WINNER_NONE)
       {
    	   board.setColor( 2 );
       }
      }
      if ( v.getId() == R.id.playo ) {
    	if(gm.winner == CheckWinner.WINNER_NONE)
    	{
    		board.setColor( 1 );
    	}
      }
    }
}
