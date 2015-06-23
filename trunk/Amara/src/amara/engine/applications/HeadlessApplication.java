/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Carl
 */
public class HeadlessApplication{
    
    private boolean isRunning;
    protected HeadlessAppStateManager stateManager = new HeadlessAppStateManager(this);
    private ConcurrentLinkedQueue<Runnable> enqueuedTasks = new ConcurrentLinkedQueue<Runnable>();
    
    public void update(float lastTimePerFrame){
        stateManager.update(lastTimePerFrame);
    }

    public HeadlessAppStateManager getStateManager(){
        return stateManager;
    }
    
    public void start(){
        isRunning = true;
        new Thread(new Runnable(){

            @Override
            public void run(){
                long lastFrameTimestamp = System.currentTimeMillis();
                while(isRunning){
                    long currentTimestamp = System.currentTimeMillis();
                    float lastTimePerFrame = ((currentTimestamp - lastFrameTimestamp) / 1000f);
                    lastFrameTimestamp = currentTimestamp;
                    runEnqueuedTasks();
                    update(lastTimePerFrame);
                    try{
                        Thread.sleep(1000 / 60);
                    }catch(InterruptedException ex){
                    }
                }
            }
        }).start();
    }
    
    public void enqueueTask(Runnable runnable){
        enqueuedTasks.add(runnable);
    }
    
    private void runEnqueuedTasks(){
        Iterator<Runnable> iteratorEnqueuedTasks = enqueuedTasks.iterator();
        while(iteratorEnqueuedTasks.hasNext()){
            Runnable enqueuedTask = iteratorEnqueuedTasks.next();
            enqueuedTask.run();
        }
        enqueuedTasks.clear();
    }
    
    public void stop(){
        isRunning = false;
    }
}
