/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shiyang
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
        LoginPane = new javax.swing.JPanel();
        LoginButton = new javax.swing.JButton();
        UserField = new javax.swing.JTextField();
        UserLabel = new javax.swing.JLabel();
        HeaderText = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        Response = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        ChatPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatArea = new javax.swing.JTextArea();
        ChatField = new javax.swing.JTextField();
        SendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

        UserField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserFieldActionPerformed(evt);
            }
        });

        UserLabel.setText("User:");

        HeaderText.setText("Welcome to Project Aether 2 Beta~ Please Log in");

        PasswordLabel.setText("Password:");

        PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoginPaneLayout = new javax.swing.GroupLayout(LoginPane);
        LoginPane.setLayout(LoginPaneLayout);
        LoginPaneLayout.setHorizontalGroup(
            LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPaneLayout.createSequentialGroup()
                .addGroup(LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(LoginPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(HeaderText)
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LoginPaneLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Response, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(LoginPaneLayout.createSequentialGroup()
                                .addGroup(LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(UserLabel)
                                    .addComponent(PasswordLabel))
                                .addGap(18, 18, 18)
                                .addGroup(LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(PasswordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                                    .addComponent(UserField, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(0, 82, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(LoginPaneLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(LoginButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LoginPaneLayout.setVerticalGroup(
            LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPaneLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(HeaderText)
                .addGap(18, 18, 18)
                .addGroup(LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserLabel))
                .addGap(18, 18, 18)
                .addGroup(LoginPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordLabel)
                    .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LoginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Response, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        LoginPane.setBounds(0, 0, 393, 204);
        jLayeredPane1.add(LoginPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ChatArea.setEditable(false);
        ChatArea.setColumns(20);
        ChatArea.setLineWrap(true);
        ChatArea.setRows(5);
        ChatArea.setWrapStyleWord(true);
        ChatArea.setDragEnabled(true);
        jScrollPane1.setViewportView(ChatArea);

        ChatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChatFieldActionPerformed(evt);
            }
        });

        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ChatPaneLayout = new javax.swing.GroupLayout(ChatPane);
        ChatPane.setLayout(ChatPaneLayout);
        ChatPaneLayout.setHorizontalGroup(
            ChatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ChatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChatPaneLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(ChatPaneLayout.createSequentialGroup()
                        .addComponent(ChatField, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SendButton)
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        ChatPaneLayout.setVerticalGroup(
            ChatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ChatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        ChatPane.setBounds(0, 0, 404, 300);
        jLayeredPane1.add(ChatPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        ChatPane.setVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void sendChat(){
        ChatArea.append(ChatField.getText()+"\n");
        ChatField.setText("");
    }
    
    private void ChatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChatFieldActionPerformed
        sendChat();
    }//GEN-LAST:event_ChatFieldActionPerformed

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        sendChat();
    }//GEN-LAST:event_SendButtonActionPerformed

    
    private void checkLogin(String user, String pass){
        if (user.equals("admin")&&pass.equals("pass")){
            LoginPane.setVisible(false);
            Response.setText("Login Successful!");
            //frame.add(frame.getChatPanel());
            ChatPane.setVisible(true);
        } else {
            Response.setText("Username or Password doesn't exist!");
        }
    }
    
    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        checkLogin(UserField.getText(),new String(PasswordField.getPassword()));
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void UserFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserFieldActionPerformed
        //if (evt.getActionCommand().equals()){
            checkLogin(UserField.getText(),new String(PasswordField.getPassword()));
            //}
    }//GEN-LAST:event_UserFieldActionPerformed

    private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordFieldActionPerformed
        // TODO add your handling code here:
        checkLogin(UserField.getText(),new String(PasswordField.getPassword()));
    }//GEN-LAST:event_PasswordFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ChatArea;
    private javax.swing.JTextField ChatField;
    private javax.swing.JPanel ChatPane;
    private javax.swing.JLabel HeaderText;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPanel LoginPane;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel Response;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextField UserField;
    private javax.swing.JLabel UserLabel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
