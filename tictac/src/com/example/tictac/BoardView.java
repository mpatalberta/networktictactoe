package com.example.tictac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BoardView extends View {
  static public String TAG = "BoardView";
  private int _color = 1;
  Context theContext;
  static boolean bOnlyOncePerGame = false;
  public void setColor( int c ) {
    _color = c;
  }
  
  public BoardView(Context context) {
      super(context);
      theContext = context;
      bOnlyOncePerGame = false;
      GameService.getInstance().startGame(0);
      
  }
  
  public BoardView(Context context, AttributeSet attrs) {
      super(context,attrs);
      theContext = context;
      bOnlyOncePerGame = false;
      GameService.getInstance().startGame(0);
  }
  
  public BoardView(Context context, AttributeSet attrs, int defStyle) {
      super(context,attrs,defStyle);
      theContext = context;
      bOnlyOncePerGame = false;
      GameService.getInstance().startGame(0);
  }

  public boolean onTouchEvent( MotionEvent event ) {
	  
	// check if we have a winner
	if(GameService.getInstance().winner != CheckWinner.WINNER_NONE)
	{
		LOG.v(TAG,"TouchEvent ignored we have a winner or tie");
		return true;
	}
		
    if ( event.getAction() != MotionEvent.ACTION_UP )
      return true;
    int offsetX = getOffsetX();
    int offsetY = getOffsetY();
    int lineSize = getLineSize();
    for( int x = 0; x < 3; x++ ) {
      for( int y = 0; y < 3; y++ ) {
        Rect r = new Rect( ( offsetX + ( x * lineSize ) ),
            ( offsetY + ( y * lineSize ) ),
            ( ( offsetX + ( x * lineSize ) ) + lineSize ),
            ( ( offsetY + ( y * lineSize ) ) + lineSize ) );
        if ( r.contains( (int)event.getX(), (int)event.getY() ) ) {
          GameService.getInstance().setPosition(0, x, y, _color);
          invalidate();
          return true;
        }
      }
    }
    return true;
  }

  private int getSize() {
    return (int) ( (float) 
    ( ( getWidth() < getHeight() ) ? getWidth() : getHeight() ) * 0.8 );
  }

  private int getOffsetX() {
    return ( getWidth() / 2 ) - ( getSize( ) / 2 );
  }
  
  private int getOffsetY() {
    return ( getHeight() / 2 ) - ( getSize() / 2 );
  }
  
  private int getLineSize() {
    return ( getSize() / 3 );
  }

  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(Color.BLACK);
    canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(), paint);
    
    int size = getSize();
    int offsetX = getOffsetX();
    int offsetY = getOffsetY();
    int lineSize = getLineSize();
    
    paint.setColor(Color.DKGRAY);
    paint.setStrokeWidth( 5 );
    for( int col = 0; col < 2; col++ ) {
      int cx = offsetX + ( ( col + 1 ) * lineSize );
      canvas.drawLine(cx, offsetY, cx, offsetY + size, paint);
    }
    for( int row = 0; row < 2; row++ ) {
      int cy = offsetY + ( ( row + 1 ) * lineSize );
      canvas.drawLine(offsetX, cy, offsetX + size, cy, paint);
    }
    int inset = (int) ( (float)lineSize * 0.1 );
    
    paint.setColor(Color.WHITE);
    paint.setStyle(Paint.Style.STROKE); 
    paint.setStrokeWidth( 10 );
    for( int x = 0; x < 3; x++ ) {
      for( int y = 0; y < 3; y++ ) {
        Rect r = new Rect( ( offsetX + ( x * lineSize ) ) + inset,
            ( offsetY + ( y * lineSize ) ) + inset,
            ( ( offsetX + ( x * lineSize ) ) + lineSize ) - inset,
            ( ( offsetY + ( y * lineSize ) ) + lineSize ) - inset );
        if ( GameService.getInstance().positions[ x ][ y ] == 1 ) {
          canvas.drawCircle( ( r.right + r.left ) / 2, 
                  ( r.bottom + r.top ) / 2, 
                  ( r.right - r.left ) / 2, paint);
        }
        if ( GameService.getInstance().positions[ x ][ y ] == 2 ) {
          canvas.drawLine( r.left, r.top, r.right, r.bottom, paint);
          canvas.drawLine( r.left, r.bottom, r.right, r.top, paint);
        }
      }
    }
  }

public void SetToast(String sres) {
	
	if(sres!=null)
	{
		if(bOnlyOncePerGame == false)
		{
		 bOnlyOncePerGame = true;
	     // push a toast message up saying we have a winner
		 Toast.makeText(theContext, sres, Toast.LENGTH_LONG).show();
		}  
	}
	
}

}
