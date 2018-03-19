rm -rf ./bin &&
mkdir -p ./bin && 
javac -cp "./dep/*" -sourcepath ./src -d ./bin -Xlint:deprecation src/ilgi/Ilgi.java &&
java -cp "./bin;./dep/*" ilgi.Ilgi 