import main.scala.programPackage._
import org.scalatest.FlatSpec

class aircraftDetailsTest extends FlatSpec{

  it should "replace value with empty string" in {
    var actual_value = aircraftDetails.emptyStringReplace("test;",";")
    assert(actual_value == "test")
  }

}
