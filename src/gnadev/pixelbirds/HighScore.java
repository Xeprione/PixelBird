package gnadev.pixelbirds;

public class HighScore {
	
	static Score[] scores;
	
	public class Score{
		private int score;
		private String name;
		
		public Score(int score, String name){
			this.score = score;
			this.name = name;
		}
		
		public String getPlayerName(){
			return name;
		}
		
		public int getScore(){
			return score;
		}
		
	}
	
	public static Score[] getHighScores(){
		return scores;
	}
	
	public static boolean isNewHightScore(int score){
		for(Score s : scores){
			if(s.getScore() < score){
				return true;
			}
		}
		return false;
	}

}
