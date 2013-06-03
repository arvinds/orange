#!/usr/bin/ruby

if ARGV.length == 0 || ARGV.include?('applet')
    puts "rebuilding applet jar"
    `rm -f frontend/consoleapplet/*.class`
    `rm -f frontend/consoleapplet/*.jar`
    `javac frontend/consoleapplet/ConsoleApplet.java`
    `jar -cf frontend/consoleapplet/ConsoleApplet.jar frontend/consoleapplet/ConsoleApplet.class`
    `jarsigner frontend/consoleapplet/ConsoleApplet.jar key -storepass #{File.read("backend/certstore.pass")}` 
end

if ARGV.length == 0
    puts "running server"
    `vertx run backend/Orange.java`
end
