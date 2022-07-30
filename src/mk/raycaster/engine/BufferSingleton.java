package mk.raycaster.engine;


import mk.raycaster.Configuration;

public final class BufferSingleton {
    private static BufferSingleton instance;

    public int matrix [][];

    private BufferSingleton() {
        // The following code emulates slow initialization.

        this.matrix = new int [Configuration.WIDTH][Configuration.HEIGHT];
    }

    public static BufferSingleton getInstance() {
        if (instance == null) {
            instance = new BufferSingleton();
        }
        return instance;
    }
}