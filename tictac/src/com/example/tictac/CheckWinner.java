package com.example.tictac;


public class CheckWinner {
 public static int WINNER_X = 2; 
 public static int WINNER_0 = 1; 
 public static int WINNER_TIE = 3; 
 public static int WINNER_NONE = 0; 

 static public int CheckForWinner(int[][] position) 
 {

		int winner = WINNER_NONE;
        
		for(int i= 0;i<3;i++)
		{
			if(position[0][i] == WINNER_X &&
			   position[1][i] == WINNER_X &&
			   position[2][i] == WINNER_X )
			{
				return WINNER_X;
			}
			if(position[0][i] == WINNER_0 &&
			   position[1][i] == WINNER_0 &&
			   position[2][i] == WINNER_0 )
			{
				return WINNER_0;
			}
			if(position[i][0] == WINNER_X &&
			   position[i][1] == WINNER_X &&
			   position[i][2] == WINNER_X )
			{
				return WINNER_X;
			}
			if(position[i][0] == WINNER_0 &&
			   position[i][1] == WINNER_0 &&
			   position[i][2] == WINNER_0 )
			{
				return WINNER_0;
			}
		}
                
                // diagonal
		if(position[0][0] == WINNER_X &&
		   position[1][1] == WINNER_X &&
		   position[2][2] == WINNER_X)
		{
		   return WINNER_X;
		}
		if(position[0][0] == WINNER_0 &&
		   position[1][1] == WINNER_0 &&
		   position[2][2] == WINNER_0)
		{
		   return WINNER_0;
		}
		if(position[0][2] == WINNER_X &&
		   position[1][1] == WINNER_X &&
		   position[2][0] == WINNER_X)
		{
		   return WINNER_X;
		}
		if(position[0][2] == WINNER_0 &&
		   position[1][1] == WINNER_0 &&
		   position[2][0] == WINNER_0)
		{
		   return WINNER_0;
		}
		
		
		// ok this point no winner the question we ask are any slots full
		// if they are full then we can call it a tie
		// assume tie
		winner = WINNER_TIE;
		for(int i = 0;i<3;i++)
		{
			for(int j = 0;j<3;j++)
			{
				if((position[i][j]!=WINNER_X) || (position[i][j]!= WINNER_0)  )
				{
					winner = WINNER_NONE;
					break;
				}
			}
		}
	
		return winner;  
	}
}
