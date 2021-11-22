import main.scala.programPackage.substringProgram
import org.scalatest.FlatSpec
class substringTestProgram extends FlatSpec{
  it should "get substring of length passed" in {
    var actual_value = substringProgram.continuousString("132765",3).mkString
    assert(actual_value == "132327276765")
  }
}
