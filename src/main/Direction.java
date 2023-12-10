package main;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public static Direction getOppositeDir(Direction direction) {
        switch (direction) {
            case UP -> {
                return Direction.DOWN;
            }
            case DOWN -> {
                return Direction.UP;
            }
            case LEFT -> {
                return Direction.RIGHT;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            default -> {
                throw new IllegalArgumentException("Illegal direction " + direction);
            }
        }
    }
}
