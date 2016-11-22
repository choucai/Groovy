package com.william.dream

/**
 * Created by william on 16-11-17.
 */
class FirstDream {


    static void main(String... args) {

        注释()

        标识符()

        单引号字符串()

        三重单引号字符串()

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


    static void 三重单引号字符串() {
        /*三重单引号字符串是java.lang.String类型的，不支持站位符插值操作，可以标示多行字符串*/
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


}
