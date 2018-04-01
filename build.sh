rm -rf ./bin &&
mkdir -p ./bin && 
javac -cp "./dep/*" -sourcepath . -d ./bin -Xlint:deprecation ilgi/Ilgi.java &&
javac -cp "./dep/*" -sourcepath . -d ./bin -Xlint:deprecation ilgi/module/**/*.java &&
java -cp "./bin:./dep/*" ilgi.Ilgi 