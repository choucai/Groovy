package com.william.dream

/**
 * Created by william on 16-11-29.
 * http://blog.csdn.net/yanbober/article/details/49047515
 */
class Base02 {


    static void main(String... args) {

        //最基本的闭包
        /*{ item++ }*/

        //使用->将参数与代码分离
        /*{ -> item++ }*/

        //使用隐含参数it（后面有介绍）
        /*{ println it }*/

        //使用明确的参数it替代
        /*{ it -> println it }*/

        //使用显示的名为参数
        /*{ name -> println name }*/

        //接受两个参数的闭包
        /*{ String x, int y ->
            println "hey ${x} the value is ${y}"
        }*/

        //包含一个参数多个语句的闭包
        /*{ reader ->
                def line = reader.readLine()
            line.trim()
        }*/

        closureDefinition()

        closureCall()

        closureParameter()

        closureEllipsisCall()
    }

    static void closureDefinition() {
        println('---closureDefinition---')

        // 定义一个Closure类型的闭包
        def listener = { e -> println "Clicked on $e.source" }
        println(listener instanceof Closure)

        // 定义【直接指定为Closure类型】的闭包
        Closure callback = { println 'Done!' }
        Closure<Boolean> isTextFile = {
            File it -> it.name.endsWith('.txt')
        }
    }

    /*调运闭包：其实闭包和C语言的函数指针非常像，我们定义好闭包后调用的方法有如下两种形式：
    1.闭包对象.call(参数)
    2.闭包对象(参数)*/

    static void closureCall() {
        println('---closureCall---')
        def code = { 123 }
        println(code() == 123)
        println(code.call() == 123)

        def isOdd = { int i -> i % 2 == 1 }
        println(isOdd(3) == true)
        println(isOdd.call(2) == false)

        /*特别注意，如果闭包没定义参数则默认隐含一个名为it的参数*/
        def isEven = { it % 2 == 0 }
        println(isEven(3) == false)
        println(isEven.call(2) == true)
    }


    static void closureParameter() {
        /*普通参数：一个闭包的普通参数定义必须遵循如下一些原则：[参数类型可选][参数名字][可选的参数默认值][参数必须用逗号分隔]*/
        println('---closureParameter-普通参数---')

        def closureWithOneArg = { str -> str.toUpperCase() }
        println(closureWithOneArg('groovy') == 'GROOVY')

        def closureWithOneArgAndExplicitType = { String str -> str.toUpperCase() }
        println(closureWithOneArgAndExplicitType('groovy') == 'GROOVY')

        def closureWithTwoArgs = { a, b -> a + b }
        println(closureWithTwoArgs(1, 2) == 3)

        def closureWithTwoArgsAndOptionalTypes = { a, int b -> a + b }
        println(closureWithTwoArgsAndOptionalTypes(1, 2) == 3)

        def closureWithTwoArgAndDefaultValue = { int a, int b = 2 -> a + b }
        println(closureWithTwoArgAndDefaultValue(1) == 3)


        /*隐含参数：当一个闭包没有显式定义一个参数列表时，闭包总是有一个隐式的it参数*/
        println('---closureParameter-隐含参数---')

        def greeting = {"Hello $it"}
        println(greeting('Patrick') == 'Hello Patrick')

        def greeting1 = { it -> "Hello, $it!" }
        println greeting1('Patrick') == 'Hello, Patrick!'

        /*隐含参数：如果你想声明一个不接受任何参数的闭包，且必须限定为没有参数的调用，那么你必须将它声明为一个空的参数列表*/

        def  maigNumber = { -> 12 }
        println maigNumber() == 12

        /*可变长参数：Groovy的闭包支持最后一个参数为不定长可变长度的参数*/
        println('---closureParameter-可变长参数---')

        def concat1 = { String... args -> args.join('') }
        println concat1('abc','def') == 'abcdef'

        def concat2 = { String[] args -> args.join('') }
        println concat2('abc', 'def') == 'abcdef'

        def multiConcat = { int n, String... args ->
            args.join('')*n
        }
        assert multiConcat(2, 'abc','def') == 'abcdefabcdef'
    }


    /*可以看见，当闭包作为闭包或方法的最后一个参数时,我们可以将闭包从参数圆括号中提取出来接在最后
    如果闭包是唯一的一个参数，则闭包或方法参数所在的圆括号也可以省略
    对于有多个闭包参数的，只要是在参数声明最后的，均可以按上述方式省略*/
    static def debugClosure(int num, String str, Closure closure){
        //do something
        closure.call()
        println num + '-' + str
    }


    /*[闭包省略调运]很多方法的最后一个参数都是一个闭包，我们可以在这样的方法调用时进行略写括弧*/
    static void closureEllipsisCall() {
        println('---closureEllipsisCall---')

        debugClosure(1, "groovy1", { println"hello groovy 1" })
        println()

        debugClosure 2, "groovy2", { println"hello groovy 2" }
        println()

        debugClosure(3, "groovy3") { println"hello groovy 3" }

    }

}
