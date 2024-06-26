/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.windowed.tools;

import java.util.Set;
import javax.swing.DefaultListModel;

/**
 *
 * @author Carl
 */
public class PanThreads extends javax.swing.JPanel{

    public PanThreads(){
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblThreads = new javax.swing.JLabel();
        btnRefrehThreads = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstThreads = new javax.swing.JList();

        setBackground(new java.awt.Color(30, 30, 30));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lblThreads.setForeground(new java.awt.Color(255, 255, 255));
        lblThreads.setText("Threads:");

        btnRefrehThreads.setText("Refresh");
        btnRefrehThreads.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrehThreadsActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lstThreads);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblThreads, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrehThreads)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRefrehThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrehThreadsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrehThreadsActionPerformed
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        lblThreads.setText("Threads: (" + threads.size() + ")");
        DefaultListModel listModel = new DefaultListModel();
        listModel.setSize(threads.size());
        int i = 0;
        for(Thread thread : threads){
            listModel.setElementAt(thread, i);
            i++;
        }
        lstThreads.setModel(listModel);
    }//GEN-LAST:event_btnRefrehThreadsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefrehThreads;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblThreads;
    private javax.swing.JList lstThreads;
    // End of variables declaration//GEN-END:variables
}
