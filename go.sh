#!/bin/sh -

# taken from https://github.com/harrah/xsbt/wiki/Setup
java_options="-Dfile.encoding=UTF8 -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256m"
sbt_command="java $java_options -jar project/sbt_0-11-1.jar"

case $1 in
  c | console)
    $sbt_command update console;;
    
  d | devloop)
    $sbt_command update ~test;;
    
  l | libraries)
    $sbt_command library-dependencies | grep List;;
    
  p | publish)
    $sbt_command update publish;;
    
  r | run)
    $sbt_command update run;;
    
  s | sbt)
    $sbt_command;;
    
  *)
    echo "specify a command: (c)onsole, (d)evloop, (l)ibraries, (p)ublish, (r)un, (s)bt";;
esac
