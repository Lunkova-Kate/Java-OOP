package bombSquad.model;

public class Score {
    private String playerName;
    private long time;
    private  int width;
    private  int height;
    private int countBombs;

    public Score(String playerName, long time, int width, int height, int bombs) {
        this.playerName = playerName;
        this.time = time;
        this.width = width;
        this.height = height;
        this.countBombs = bombs;
    }


    public int compareTo(Score other) {
        return Long.compare(this.time, other.time);
    }

    public String getPlayerName() {
        return playerName;
    }
    public String setPlayerName(String newPlayerName) {
       return this.playerName = newPlayerName;
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
/*Score.java
Поля :
playerName: Имя игрока.
time: Время игры.
width, height: Размеры поля.
bombs: Количество бомб.
Методы :
compareTo(other): Сравнение результатов.
Геттеры для всех полей.
getFormattedTime(): Возвращает форматированное время.
getDifficulty(): Определяет сложность.*/