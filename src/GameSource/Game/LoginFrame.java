/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author Angus
 */
public class LoginFrame extends JPanel implements ActionListener {
    
    private JLabel lWelcome, lUserName, lPassword;
    private JTextField tfUserName;
    private JPasswordField pfPassword;
    private JButton bLogin;
    private JPanel pUserName;
    private JPanel pPassword;
    
    public static final int X = 400;
    public static final int Y = 300;
    
    public LoginFrame() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        lWelcome = new JLabel("Replace with Rich's GUI");
        lWelcome.setAlignmentX(this.CENTER_ALIGNMENT);
        
        
        lUserName = new JLabel("Username:");
        tfUserName = new JTextField(32);
        pUserName = new JPanel();
        pUserName.add(lUserName);
        pUserName.add(tfUserName);
        pUserName.setAlignmentX(this.CENTER_ALIGNMENT);
        
        lPassword = new JLabel("Password:");
        pfPassword = new JPasswordField(32);
        pPassword = new JPanel();
        pPassword.add(lPassword);
        pPassword.add(pfPassword);
        pPassword.setAlignmentX(this.CENTER_ALIGNMENT);  
        
        
        bLogin = new JButton("Login");
        bLogin.addActionListener(this);
        bLogin.setAlignmentX(this.CENTER_ALIGNMENT);
        
        add(lWelcome);
        add(pUserName);
        add(pPassword);
        add(bLogin);
    }
    
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == bLogin) {
            ClientMain.f.dispose();
            ClientMain.startGame();
        }
    }
}
