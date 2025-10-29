package com.tictactoe;

import java.util.HashMap;
import java.util.Map;

public class GameConstants {
    public enum Player {
        X(1),
        O(-1);

        private final int value;
        private static final Map<Integer, Player> lookup = new HashMap<>();

        static {
            for (Player p : Player.values()) {
                lookup.put(p.value, p);
            }
        }

        Player(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Player fromValue(int value) {
            return lookup.get(value); // returns null if not found
        }
    }

    public enum GameState {
        WIN(1),
        DRAW(0),
        IN_PROGRESS(-1);

        GameState(int value) {
        }

    }
}
