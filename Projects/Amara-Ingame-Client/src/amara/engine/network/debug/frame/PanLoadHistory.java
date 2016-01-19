/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.debug.frame;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import amara.libraries.network.debug.LoadHistory;

/**
 *
 * @author Carl
 */
public class PanLoadHistory extends JPanel{

    public PanLoadHistory(){
        
    }
    private LoadHistory loadHistory;
    private long defaultMaximumBytes;
    private long maximumBytes;

    public void setLoadHistory(LoadHistory loadHistory, long defaultMaximumBytes){
        this.loadHistory = loadHistory;
        this.defaultMaximumBytes = defaultMaximumBytes;
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        float perSecondFactor = (loadHistory.getInterval() / 1000f);
        int count = (int) Math.round(5 / perSecondFactor);
        long[] bytes = loadHistory.getLastBytes(count + 2);
        if(bytes.length > 2){
            maximumBytes = (int) (defaultMaximumBytes * perSecondFactor);
            for(long currentBytes : bytes){
                if(currentBytes > maximumBytes){
                    maximumBytes = currentBytes;
                }
            }
            int barWidth = (getWidth() / count);
            int lastX = 0;
            int lastY = getBytesY(bytes[0]);
            graphics.setColor(Color.BLUE);
            for(int i=1;i<(bytes.length - 1);i++){
                int x = ((i + 1) * barWidth);
                int y = getBytesY(bytes[i]);
                graphics.drawLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
            }
            long currentBytesPerSecond = (int) (bytes[bytes.length - 2] / perSecondFactor);
            String currentBytesPerSecondText = (((currentBytesPerSecond > 1024)?(currentBytesPerSecond / 1024) + " kB":currentBytesPerSecond + " B") + "/s");
            graphics.setColor(Color.BLACK);
            graphics.drawString(currentBytesPerSecondText, 6, 15);
        }
    }
    
    private int getBytesY(long bytes){
        return (int) Math.round((1 - (((float) bytes) / maximumBytes)) * getHeight());
    }
}
