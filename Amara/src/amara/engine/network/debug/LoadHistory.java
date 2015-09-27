/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.debug;

import java.util.Stack;

/**
 *
 * @author Carl
 */
public class LoadHistory{

    public LoadHistory(int interval){
        this.interval = interval;
        bytes.push(0l);
        lastTimestamp = System.currentTimeMillis();
    }
    private Stack<Long> bytes = new Stack<Long>();
    private long lastTimestamp;
    private int interval;
    
    public void add(long bytes){
        long timestamp = System.currentTimeMillis();
        long passedTime = (timestamp - lastTimestamp);
        long currentBytes;
        if(passedTime <= interval){
            currentBytes = this.bytes.pop();
        }
        else{
            passedTime -= interval;
            while(passedTime > interval){
                this.bytes.push(0l);
                passedTime -= interval;
            }
            lastTimestamp = (timestamp - passedTime);
            currentBytes = 0;
        }
        this.bytes.push(currentBytes + bytes);
    }
    
    public long[] getLastBytes(int count){
        if(count > bytes.size()){
            count = bytes.size();
        }
        long[] lastBytes = new long[count];
        for(int i=0;i<count;i++){
            lastBytes[i] = bytes.get((bytes.size() - count) + i);
        }
        return lastBytes;
    }

    public int getInterval(){
        return interval;
    }
}
