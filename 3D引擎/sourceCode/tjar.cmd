@echo off
javac Appmain.java
jar cvfm ../loader.jar manifest.mf *.class camera/*.class point/*.class triangle/*.class
pause