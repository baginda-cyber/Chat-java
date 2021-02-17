/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1_sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author dinitelmo
 */
public class A_Chat_Client implements Runnable
{
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;
    
    public A_Chat_Client(Socket X)
    {
        this.SOCK = X;
    }
    
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();
            }
            finally
            {
                SOCK.close();
            }
        }
        catch(Exception X)
        {
            System.out.print(X);
        }
    }
    
    public void DISCONNECT() throws IOException
    {
        OUT.println(A_Chat_Client_GUI.UserName+" has disconnected");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null, "You disconnected");
        System.exit(0);
    }
    
    public void CheckStream()
    {
        while(true)
        {
            RECEIVE();
        }
    }
    
    public void RECEIVE()
    {
        if(INPUT.hasNext())
        {
            String MESSAGE = INPUT.nextLine();
            
            if(MESSAGE.contains("#?!"))
            {
                String TEMP1 = MESSAGE.substring(3);
                       TEMP1 = TEMP1.replace("[", "");
                       TEMP1 = TEMP1.replace("]", "");
                       
                String[] CurrentUser = TEMP1.split(", ");
                A_Chat_Client_GUI.JL_ONLINE.setListData(CurrentUser);
            }
            else
            {
                A_Chat_Client_GUI.TA_CONVERSATION.append(MESSAGE+"\n");
            }
        }
    }
    
    public void SEND(String X)
    {
        OUT.println(A_Chat_Client_GUI.UserName + " : " + X);
        OUT.flush();
        A_Chat_Client_GUI.TF_MESSAGE.setText("");
    }
    
}
