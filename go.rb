#!/home/ubuntu/.rvm/rubies/ruby-2.0.0-p353/bin/ruby
if ARGV.join.include? 'jison'
`jison javaParser.jison`
`cp javaParser.js frontend/js/javaParser.js`
else
`vertx run backend/Orange.java`
end
