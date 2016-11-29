package com.william.dream

/**
 * Created by william on 16-11-29.
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


    /*普通参数：

    一个闭包的普通参数定义必须遵循如下一些原则：

    参数类型可选
    参数名字
    可选的参数默认值
    参数必须用逗号分隔*/
    static void closureParameter() {


    }

}
