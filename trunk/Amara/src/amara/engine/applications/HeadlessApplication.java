/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications;

/**
 *
 * @author Carl
 */
public class HeadlessApplication{
    
    private boolean isRunning;
    protected HeadlessAppStateManager stateManager = new HeadlessAppStateManager(this);
    
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
                    update(lastTimePerFrame);
                    try{
                        Thread.sleep(1000 / 60);
                    }catch(InterruptedException ex){
                    }
                }
            }
        }).start();
    }
    
    public void stop(){
        isRunning = false;
    }
}
