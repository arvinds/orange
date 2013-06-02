echo  rebuilding applet jar
rm -f frontend/consoleapplet/*.class
rm -f frontend/consoleapplet/*.jar
javac frontend/consoleapplet/ConsoleApplet.java
jar -cf frontend/consoleapplet/ConsoleApplet.jar frontend/consoleapplet/ConsoleApplet.class

echo running server
vertx run backend/Orange.java
