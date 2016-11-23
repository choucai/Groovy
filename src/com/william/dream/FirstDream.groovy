package com.william.dream

/**
 * Created by william on 16-11-17.
 */
class FirstDream {


    static void main(String... args) {

//        注释()

//        标识符()

//        单引号字符串()

//        三重单引号字符串()

        双引号字符串()

    }

    private static 注释() {
        /*2 语法基础*/
        /*2-1 注释*/

        /*[#!]特殊的注释，其它的注释和Java一样*/
        /*[#!]这种注释通常是用来给UNIX系统声明允许脚本运行的类型的*/
        println "Hello Groovy"
    }

    private static void 标识符() {
        /*2-3-2 引用标识符*/
        def map = [:]
        /*引用标示符中出现空格也是对的*/
        map."an identifier with a space and double quotes" = "ALLOWED"
        /*引用标示符中出现横线也是对的*/
        map.'with-dash-signs-and-single-quotes' = "ALLOWED"

        assert map."an identifier with a space and double quotes" == "ALLOWED"
        assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"

        /*Groovy的所有字符串都可以当作引用标示符定义*/
        /*如下类型字符串作为引用标识符都是对的*/
        map.'single quote'
        map."double quote"
        map.'''triple single quote'''
        map."""triple double quote"""
        map./slashy string/
        map.$/dollar slashy string/$

        /*稍微特殊的GString，也是对的*/
        def firstname = "Homer"
        map."Simson-${firstname}" = "Homer Simson"
        assert map.'Simson-Homer' == "Homer Simson"
    }


    static void 单引号字符串() {
        def name = 'Test Groovy'
        def body = 'Test $name'

        assert name == 'Test Groovy'
        assert body == 'Test $name'
        assert 'ab' == 'a' + 'b'
    }

    /**
     * TODO 此方法有问题 日后详查资料
     */
    static void 三重单引号字符串() {
        // 三重单引号字符串是java.lang.String类型的，不支持站位符插值操作，可以标示多行字符串
        def aMultilineString = '''line one
line two
line three'''
        println(aMultilineString)

        def strippedFirstNewline = '''\
line one
line two
line three
'''
        println(!strippedFirstNewline.startsWith('\n'))
        println(strippedFirstNewline)

    }

    /**
     * 双引号字符串支持站位插值操作，如果双引号字符串中不包含站位符则是java.lang.String类型的，如果双引号字符串中包含站位符则是groovy.lang.GString类型的
     * 对于插值占位符我们可以用${}或者$来标示，${}用于一般替代字串或者表达式，$主要用于A.B的形式中
     */
    static void 双引号字符串() {
        def name = 'Guillaume'
        def greeting = "Hello ${name}"
        assert greeting.toString() == 'Hello Guillaume'
        println(greeting)

        def sum = "The sum of 2 and 3 equals ${2 + 3}"
        assert sum.toString() == 'The sum of 2 and 3 equals 5'
        println(sum)

        def person = [name: 'Guillaume', age: 36]
        assert "$person.name is $person.age years old" == 'Guillaume is 36 years old'
        println(person)

        /*特别注意，$只对A.B等有效，如果表达式包含括号（像方法调用）、大括号、闭包等符号则是无效的*/
        /*def number = 3.14
        shouldFail(MissingPropertyException) {
            println "$number.toString()"
        }*/
        /*该代码运行抛出groovy.lang.MissingPropertyException异常，因为Groovy认为去寻找number的名为toString的属性，所以异常*/

        /*GString还支持延迟运算，譬如在GString中使用闭包，闭包在调用GString的toString()方法时被延迟执行
        闭包中可以有0或1个参数，若指定一个参数，则参数会被传入一个Writer对象
        我们可以利用这个Writer对象来写入字符，若没有参数，闭包返回值的toString()方法被调用*/

        //无参数闭包
        def sParameterLessClosure = "1 + 2 == ${-> 3}"
        assert sParameterLessClosure == '1 + 2 == 3'
        println(sParameterLessClosure)

        //一个参数闭包
        def sOneParamClosure = "1 + 2 == ${w -> w << 3}"
        assert sOneParamClosure == '1 + 2 == 3'
        println(sOneParamClosure)

        //------------------------------------------------------------------------------

        /*一个普通插值表达式值替换实际是在GString创建的时刻，一个包含闭包的表达式由于延迟运算调运toString()方法，所以会产生一个新的字符串值*/
        def number = 1
        def eagerGString = "value == ${number}"
        def lazyGString = "value == ${-> number}"

        assert eagerGString == "value == 1"
        assert lazyGString == "value == 1"

        number = 2
        assert eagerGString == "value == 1"
        assert lazyGString == "value == 2"

        /*GString和String即使字符串一样他们的HashCode也不会一样，譬如*/
        assert "one: ${1}".hashCode() != "one: 1".hashCode()

        /*由于相同字符串的String与GString的HashCode不同，所以我们一定要避免使用GString作为MAP的key，譬如*/
        def key = "a"
        def m = ["${key}": "letter ${key}"]
        assert m["a"] == null   //由于key的HashCode不同，所以取不到
    }


}
