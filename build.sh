rm -rf ./bin &&
mkdir -p ./bin && 
javac -sourcepath ./src -d ./bin src/ilgi/Ilgi.java &&
java -cp ./bin ilgi.Ilgi 