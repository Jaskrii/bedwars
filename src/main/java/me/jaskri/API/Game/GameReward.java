package me.jaskri.API.Game;

public abstract class GameReward {

    private String name;
    protected int reward;

    public GameReward(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return this.reward;
    }

    public void increment(int amount) {
        if (amount > 0) {
            this.reward += amount;
        }

    }

    public void decrement(int amount) {
        if (amount > 0) {
            this.reward -= amount;
            if (this.reward < 0) {
                this.reward = 0;
            }

        }
    }

    public void setAmount(int amount) {
        this.reward = amount >= 0 ? amount : 0;
    }
}
