/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.debug.frame;

import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.debug.LoadHistory;

/**
 *
 * @author Carl
 */
public class NetworkLoadDisplay extends javax.swing.JFrame{

    public NetworkLoadDisplay(final LoadHistory loadHistory, long defaultMaximumBytes, String title){
        initComponents();
        FrameUtil.initFrameSpecials(this);
        setTitle(title);
        panLoadHistory.setLoadHistory(loadHistory, defaultMaximumBytes);
        panContainer.add(panLoadHistory);
        new Thread(new Runnable(){

            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(loadHistory.getInterval());
                    }catch(InterruptedException ex){
                    }
                    loadHistory.add(0);
                    panLoadHistory.updateUI();
                }
            }
        }).start();
    }
    private PanLoadHistory panLoadHistory = new PanLoadHistory();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Network Load Display");

        panContainer.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panContainer;
    // End of variables declaration//GEN-END:variables
}
