package bombSquad.model;

public class Score {
    private final String playerName;
    private final long time;
    private final int width;
    private final int height;
    private final int countBombs;

    public Score(String playerName, long time, int width, int height, int bombs) {
        this.playerName = playerName;
        this.time = time;
        this.width = width;
        this.height = height;
        this.countBombs = bombs;
    }



    public String getPlayerName() {
        return playerName;
    }


    public long getTime() {
        return time;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBombs() {
        return countBombs;
    }
    public String getFormattedTime() {
        return String.format("%02d:%02d", time/60, time%60);
    }

    public String getDifficulty() {
        double ratio = (double)countBombs/(width*height);
        if (ratio < 0.12) return "Легко";
        if (ratio < 0.16) return "Средне";
        return "Сложно";
    }


}
