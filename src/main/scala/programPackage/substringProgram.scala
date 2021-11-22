package main.scala.programPackage
import scala.collection.mutable.ListBuffer
object substringProgram {
  /*Function takes 2 arguments, input string and length of output strings
    Function prints continuous strings in an incremental way*/
    def continuousString(Input: String, x: Int): ListBuffer[String] = {
      var outputRes = ListBuffer[String]()
      if (x == 0 || x > Input.length()) {
        println("The Length passed should not be zero or greater than Input String Length")
      }
      if (!(x == 0 || x > Input.length())) {
        var strLength = Input.length()
        var strIndex = 0
        var pos = 0
        var tempStr = ""
        while (strIndex + x <= strLength) {
          tempStr = Input.substring(strIndex, x + pos)
          outputRes += tempStr
          println(Input.substring(strIndex, x + pos))
          strIndex += 1
          pos += 1
        }
      }
      return outputRes
    }

  def main(args: Array[String]): Unit = {
    try {
      //Actual string on which the substrings to be created
      var stringInput = args(0)
      //Length of output strings to be printed
      var stringLength: Int = args(1).toInt
      substringProgram.continuousString(stringInput,stringLength)
    }
    catch {
      case e: ArrayIndexOutOfBoundsException => println("Array out of bound exception")
      case e: Exception => println(e.printStackTrace())
    }

  }

  }
