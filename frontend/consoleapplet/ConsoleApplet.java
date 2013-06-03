package consoleapplet;

import java.net.URI;
import java.util.*;

import javax.swing.*;
import javax.tools.*;

public class ConsoleApplet extends JApplet {

    JTextArea txt = new JTextArea(100,100);
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public ConsoleApplet() {
        getContentPane().add(txt);
        setText("test");
    }

//    public String[] compileProgram(String[] programs)
    public String[] compileProgram(String[] programs)
    {
        try
        {
            JavaSourceFromString code = new JavaSourceFromString("test", programs[0]);
            List<JavaFileObject> files = new ArrayList<JavaFileObject>(); 
            files.add(code);
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

            compiler.getTask(null, null, diagnostics, null, null, files).call();
        
            //List<Diagnostic<JavaFileObject>> issues = diagnostics.getDiagnostics();
            List<String> info = new ArrayList<String>();
            for(Diagnostic issue : diagnostics.getDiagnostics())
            {
                info.add(issue.getCode() + ", " +
                           issue.getColumnNumber() + ", " +
                           issue.getEndPosition() + ", " +
                           issue.getKind() + ", " +
                           issue.getLineNumber() + ", " +
                           issue.getMessage(Locale.US) + ", " +
                           issue.getPosition() + ", " +
                           issue.getSource() + ", " +
                           issue.getStartPosition());
            }
            return ((String[])info.toArray());
        }
        catch(Exception e)
        {
            //TODO: send a message to the server? maybe this is better caught in js?
//            return null;
            String[] arr = new String[2];
            arr[0] = e.getMessage();
            return arr;
        }
    }

    public void setText(String s){
        txt.setText(s);
    }

    public void append(String s){
        txt.append(s);
    }

    /**
    * A file object used to represent source coming from a string.
    * source: http://docs.oracle.com/javase/6/docs/api/javax/tools/JavaCompiler.html
    */
    public class JavaSourceFromString extends SimpleJavaFileObject {
        /**
         * The source code of this "file".
         */
        final String code;
 
        /**
         * Constructs a new JavaSourceFromString.
         * @param name the name of the compilation unit represented by this file object
         * @param code the source code for the compilation unit represented by this file object
         */
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),
                  Kind.SOURCE);
            this.code = code;
        }
 
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
           return code;
        }
    }

}

