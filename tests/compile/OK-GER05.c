#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int boolean;
#define true  1
#define false 0

typedef void (*Func)();

typedef struct _class_A _class_A;

struct _class_A{
   Func *vt;
};

_class_A *new_A(void);


typedef enum {_enum_A_m} _class_A_methods;

void _A_m( _class_A *this ){
   char __s[512];
   int _a;
   int _b;
   int _c;
   int _d;
   int _e;
   int _f;
   gets(__s);
   sscanf(__s,"%d",&_a);
   sscanf(__s,"%d",&_b);
   sscanf(__s,"%d",&_c);
   sscanf(__s,"%d",&_d);
   sscanf(__s,"%d",&_e);
   sscanf(__s,"%d",&_f);
   printf("%d",_a);
   printf("%d",_b);
   printf("%d",_c);
   printf("%d",_d);
   printf("%d",_e);
   printf("%d",_f);
}

Func VTclass_A[] = {
   ( void (*)() ) _A_m
};

_class_A *new_A(){
   _class_A *t;
   if ( (t = malloc(sizeof(_class_A))) != NULL )
      t->vt = VTclass_A;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   _class_A *_a;
   puts("");
   puts("Ok-ger05");
   puts("The output should be what you give as input.");
   puts("Type in six numbers");
   _a = new_A();
   ( ( ( void (*)(_class_A * ) ) _a->vt[_enum_A_m] )( _a) );
}

Func VTclass_Program[] = {
   ( void (*)() ) _Program_run
};

_class_Program *new_Program(){
   _class_Program *t;
   if ( (t = malloc(sizeof(_class_Program))) != NULL )
      t->vt = VTclass_Program;
   return t;
}

int main() {
   _class_Program *program;
   program = new_Program();
   ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
   return 0;
}