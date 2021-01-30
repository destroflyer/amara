package amara.libraries.applications.headless.applications;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HeadlessApplication {

    private boolean isRunning;
    protected HeadlessAppStateManager stateManager = new HeadlessAppStateManager(this);
    private ConcurrentLinkedQueue<Runnable> enqueuedTasks = new ConcurrentLinkedQueue<>();
    
    public void update(float lastTimePerFrame) {
        stateManager.update(lastTimePerFrame);
    }

    public HeadlessAppStateManager getStateManager() {
        return stateManager;
    }

    public void start(){
        isRunning = true;
        new Thread(() -> {
            long lastFrameTimestamp = System.currentTimeMillis();
            while (isRunning) {
                long currentTimestamp = System.currentTimeMillis();
                float lastTimePerFrame = ((currentTimestamp - lastFrameTimestamp) / 1000f);
                lastFrameTimestamp = currentTimestamp;
                runEnqueuedTasks();
                update(lastTimePerFrame);
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }

    public void enqueue(Runnable runnable) {
        enqueuedTasks.add(runnable);
    }

    private void runEnqueuedTasks() {
        for (Runnable runnable : enqueuedTasks) {
            runnable.run();
        }
        enqueuedTasks.clear();
    }

    public void stop() {
        isRunning = false;
    }
}
