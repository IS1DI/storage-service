param ($Ver = $("latest"))


function build
{
    ($Tag = ("is1di/storageservice:$Ver"))
    ./gradlew clean build
    docker build -t $Tag .
    docker push $Tag
}
build