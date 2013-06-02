package consoleapplet;

import javax.swing.*;

public class ConsoleApplet extends JApplet {
    JTextArea txt = new JTextArea(100,100);
    public ConsoleApplet() {
    }

    public void compileProgram(String files)
    {
//        txt.setText(files[0]);
    }

    public void setText(String s){
        txt.setText(s);
    }

    public void setTex(String s){
        txt.setText(s);
    }
    public void append(String s){
        txt.append(s);
    }


}

