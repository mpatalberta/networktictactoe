package com.example.tictac;

import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
	
	GameService  theInstance = GameService.getInstance();
	
	
  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      LOG.set(true,true, true, true,false);
      setContentView(R.layout.activity_main);
      
      theInstance.setServiceAddress("192.168.1.71");
      theInstance.startThread();
      Button playx = (Button)this.findViewById(R.id.playx);
      playx.setOnClickListener( this );

      Button playo = (Button)this.findViewById(R.id.playo);
      playo.setOnClickListener( this );

      Timer timer = new Timer();
      UpdateTimer ut = new UpdateTimer();
      ut.boardView = (BoardView)this.findViewById(R.id.bview);
      timer.schedule( ut, 200, 200 );
    }

    public void onClick(View v) {
    BoardView board = (BoardView)this.findViewById(R.id.bview);
      if ( v.getId() == R.id.playx ) {
        board.setColor( 2 );
      }
      if ( v.getId() == R.id.playo ) {
        board.setColor( 1 );
      }
    }
}
