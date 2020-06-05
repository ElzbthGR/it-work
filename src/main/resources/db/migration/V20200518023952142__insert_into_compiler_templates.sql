insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), 'import java.util.*;
import java.lang.*;
import java.io.*;

class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
	}
}', 10);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '#include <stdio.h>

int main(void) {
	// your code goes here
	return 0;
}', 11);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), 'using System;

public class Test
{
	public static void Main()
	{
		// your code goes here
	}
}', 27);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '#include <iostream>
using namespace std;

int main() {
	// your code goes here
	return 0;
}', 41);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), 'package main
import "fmt"

func main(){
	// your code goes here
}', 114);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), 'main = -- your code goes here', 21);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), 'import java.util.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    // your code goes here
}', 47);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '#import <objc/objc.h>
#import <objc/Object.h>
#import <Foundation/Foundation.h>

@implementation TestObj
int main()
{
	// your code goes here
	return 0;
}
@end', 43);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '// your code goes here', 112);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '# your code goes here', 116);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '# your code goes here', 17);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), 'object Main extends App {
	// your code goes here
}', 39);

insert into compiler_templates (id, base_template, compiler_id)
values (nextval('compiler_templates_seq'), '// your code goes here', 85);