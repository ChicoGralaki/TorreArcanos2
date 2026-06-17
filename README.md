javac -d out (Get-ChildItem -Recurse -Filter *.java | % FullName); java -cp out torrearcanos.visao.MotorTextual
