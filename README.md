# Groovy
自学Groovy脚本语言以及资料的整理

package com.dream.lee.groovy
import groovy.Test.Test

println "Hello Gradle"

/**++++++++++++++++++++++++++++++++++++++【基础语法】+++++++++++++++++++++++++++++++++*/

// Groovy注释标记和Java一样，支持//或者/**/
// Groovy语句可以不用分号结尾。Groovy为了尽量减少代码的输入，确实煞费苦心
// Groovy中支持动态类型，即定义变量的时候可以不指定其类型。
// Groovy中，变量定义可以使用关键字def。
// 注意，虽然def不是必须的，但是为了代码清晰，建议还是使用def关键字
def v1 = 1
def v2 = "I am fine"
def int v3 = 21
println v2

/**
 * 函数定义时，参数的类型也可以不指定
 *
 * @param arg1
 * @param arg2
 * @return
 */
String testOne(arg1,arg2){

}

/**
 * 除了变量定义可以不指定类型外，Groovy中函数的返回值也可以是无类型的
 *【注意】无类型的函数定义，必须使用def关键字
 */
def  nonReturnTypeFunc(){
    last_line   //最后一行代码的执行结果就是本函数的返回值
}

/**
 * 【注意】如果指定了函数返回类型，则可不必加def关键字来定义函数
 */
String getString(){
    return"I am a string"
}

/**
 * 函数返回值：Groovy的函数里，可以不使用returnxxx来设置xxx为函数返回值
 * 如果不使用return语句的话，则函数里最后一句代码的执行结果被设置成返回值
 * 下面这个函数的返回值是字符串"getSomething return value"
 */
def getSomething() {
    "getSomething return value" //如果这是最后一行代码，则返回类型为String
    1000//如果这是最后一行代码，则返回类型为Integer
}

// Groovy对字符串支持相当强大，充分吸收了一些脚本余姚的优点：

// 1-单引号''中的内容严格对应Java中的String，不对$符号进行转义
    def singleQuote='I am $ dolloar'  //输出就是I am $ dolloar
    println singleQuote

// 2-双引号" "的内容和脚本语言的处理有点像，如果字符串有$号的话，则它会$表达式先求值
    def doubleQuoteWithoutDollar = "I am one dollar" //输出 I am one dollar
    println doubleQuoteWithoutDollar
    def x = 1
    def doubleQuoteWithDollar = "I am $x dolloar" //输出I am 1 dolloar
    println doubleQuoteWithDollar

// 3-三个引号'''xxx'''中的字符串支持随意换行 比如
    def multieLines = '''
         begin
         line  1
         line  2
         end
         '''
    println multieLines

    // 最后，除了每行代码不用加分号外，Groovy中函数调用的时候还可以不加括号。比如：
    // println("test") ---> println"test"

    // 注意，虽然写代码的时候，对于函数调用可以不带括号，但是Groovy经常把属性和函数调用混淆。比如
    def setSomething(){
        "hello"
    }
    println setSomething()
    // println setSomething  //如果不加括号的话，setSomething


/**++++++++++++++++++++++++++++++++++++【数据类型】+++++++++++++++++++++++++++++++++++*/

    /**
     * 作为动态语言，Groovy世界中的所有事物都是对象
     * 所以，int，boolean这些Java中的基本数据类型
     * 在Groovy代码中其实对应的是它们的包装数据类型
     * 比如int对应为Integer，boolean对应为Boolean
     */
    def int xAuto = 1
    println xAuto.getClass().getCanonicalName()

    //-------------------List---------------------------------------------

    // 变量定义：List变量由[]定义，比如
    def aList = [5,'string',true]
    println aList

    // 变量存取：可以直接通过索引存取，而且不用担心索引越界
    // 如果索引超过当前链表的长度，List会自动往该索引添加元素
    assert aList[1] == 'string'
    assert aList[5] == null // 第六个元素为空
    aList[100] = 100 // 设置第101个元素的值为10
    assert aList[100] == 100
    println aList.size() // 结果是101


    //-------------------Map---------------------------------------------

    //容器变量定义
    //变量定义：Map变量由[:]定义，比如
    def aMap = ['key1':'value1','key2':true]

    // Map由[:]定义，注意其中的冒号。冒号左边是key，右边是Value
    // key必须是字符串，value可以是任何对象
    // 另外，key可以用''或""包起来，也可以不用引号包起来。比如
    def aNewMap = [key1:"value",key2:true]

    //其中的key1和key2默认被处理成字符串"key1"和"key2"
    // 不过Key要是不使用引号包起来的话，也会带来一定混淆，比如
    def key1="wowo"
    def aConfusedMap=[key1:"who am i?"]

    // aConfuseMap中的key1到底是"key1"还是变量key1的值“wowo”？
    // 显然，答案是字符串"key1"。如果要是"wowo"的话，则aConfusedMap的定义必须设置成：
    aConfusedMap=[(key1):"who am i?"]

    // Map中元素的存取更加方便，它支持多种方法：
    println aMap.key1    // <==这种表达方法好像key就是aMap的一个成员变量一样
    println aMap['key2'] // <==这种表达方法更传统一点
    aMap.anotherkey = "i am map"  // <==为map添加新元素


    //-------------------Range-------------------------------------------

    //Range类型的变量 由begin值+两个点+end值表示
    def aRange = 1..12
    println aRange

    // 左边这个aRange包含1,2,3,4,5这5个值,如果不想包含最后一个元素，则
    def aRangeWithoutEnd = 1..<12  //包含1,2,3,4这4个元素
    println aRangeWithoutEnd

    println aRange.from
    println aRange.to


    //-------------------Closure-------------------------------------------
    /**
     *  闭包，是一种数据类型，它代表了一段可执行的代码-定义格式如下
     *  def xxx = {paramters -> code}  或者
     *  def xxx = {无参数，纯code}  这种case不需要->符号
     */

    def aClosure = {//闭包是一段代码，所以需要用花括号括起来..
        String param1, int param2 ->  //这个箭头很关键。箭头前面是参数定义，箭头后面是代码
        println"this is code" //这是代码，最后一句是返回值，
        //也可以使用return，和Groovy中普通函数一样
    }

    aClosure.call("this is string",100)
    // 或者
    aClosure("this is string", 100)

    // 如果闭包没定义参数的话，则隐含有一个参数，这个参数名字叫it
    // 和this的作用类似，it代表闭包的参数

    //    比如：
    def greeting = { "Hello, $it!" }
    assert greeting('Patrick') == 'Hello, Patrick!'
    //    等同于：
    greeting = { it -> "Hello, $it!"}
    assert greeting('Patrick') == 'Hello, Patrick!'
    //    但是，如果在闭包定义时，采用下面这种写法，则表示闭包没有参数！
    def noParamClosure = { -> true }
    //    这个时候，我们就不能给noParamClosure传参数了！
    //    noParamClosure ("test")  // <==报错喔！

    //public static <T> List<T>each(List<T> self, Closure closure)
    //上面这个函数表示针对List的每一个元素都会调用closure做一些处理。这里的closure，就有点回调函数的感觉。但是，在使用这个each函数的时候，我们传递一个怎样的Closure进去呢？比如：

    def iamList = [1,2,3,4,5]  //定义一个List
    iamList.each{ //调用它的each，这段代码的格式看不懂了吧？each是个函数，圆括号去哪了？
        println it
    }

    //    上面代码有两个知识点：
    //    each函数调用的圆括号不见了！原来，Groovy中，当函数的最后一个参数是闭包的话
    //   可以省略圆括号。比如
    def testClosure(int a1, String b1, Closure closure){
        //do something
        closure() //调用闭包
    }

    // 那么调用的时候，就可以免括号！
    testClosure (4, "test", {
        println "i am in closure"
    } )  //红色的括号可以不写..

    def testClz = new Test("William","Dram")
    testClz.print()
