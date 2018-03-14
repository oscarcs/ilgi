rm -rf ./bin &&
mkdir -p ./bin && 
javac -cp "./dep/*" -sourcepath ./src -d ./bin src/ilgi/Ilgi.java &&
java -cp "./bin;./dep/*" ilgi.Ilgi 